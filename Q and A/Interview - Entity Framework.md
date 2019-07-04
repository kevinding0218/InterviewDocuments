- [Fluent Api](#fluent-api)
- [Group by](#group-by)
- [Transaction](#transaction)
- [DbQuery & FromSql](#dbquery---fromsql)
- [How to improve EF performance](#how-to-improve-ef-performance)

## Fluent Api
- used to configure domain classes to override conventions
	- **Model Configuration**: Configures an **EF model to database mappings**. Configures the default Schema, DB functions, additional data annotation attributes and entities to be excluded from mapping.
	- **Entity Configuration**: Configures **entity to table and relationships mapping** e.g. PrimaryKey, AlternateKey, Index, table name, one-to-one, one-to-many, many-to-many relationships etc.
	- **Property Configuration**: Configures **property to column mapping** e.g. column name, default value, nullability, Foreignkey, data type, concurrency column etc.
- **Configure table relationship**
	- **One to one**
		- use the `HasOne`, `WithOne` and `HasForeignKey` methods
		- For example: _Student_ and _StudentAddress_ are _one-to-one_ relationship and _StudentAddress_ has a **ForeignKey** called _AddressOfStudentId_.
			```
			modelBuilder.Entity<Student>()
			            .HasOne<StudentAddress>(s => s.Address)
			            .WithOne(ad => ad.Student)
			            .HasForeignKey<StudentAddress>(ad => ad.AddressOfStudentId);
			```
			![enter image description here](https://www.entityframeworktutorial.net/images/efcore/onetoone-fluent.png)
	- **One to many**
		- use the `HasOne`, `WithMany` and `HasForeignKey` methods if starting point is _One_
		- For example: _City_ and _Country_ are one-to-many relationship, one City could only4 have one Country but one Country could have many City, _City_ had a foreign key called _FKCountry_.
			```
			public class Country
			{
				public int Id { get; set;}
				public string Name { get; set;}
				public ICollection<City> Cities { get; set;}
			}
			public class City`
			{
				public int Id { get; set;}
				public string Name { get; set;}
				public int FKCountry { get; set;}
				public Country Country { get; set;}
			}
			...
			modelBuilder.Entity<City>()
						.HasOne(e => e.Country)
						.WithMany(e => e.City)
						.HasForeignKey(e => e.FKCountry);
			```
		- or `HasMany`, `WithOne` and `HasForeignKey` method if starting point is _Many_
			```
			modelBuilder.Entity<Country>()
						.HasMany(e => e.City)
						.WithOne(e=>e.Country)
						.HasForeignKey(e => e.FKCountry);
			```
		- **Cascade delete**: automatically deletes the child row when the related parent row is deleted.
			```
			modelBuilder.Entity<Grade>()
			    .HasMany<Student>(g => g.Students)
			    .WithOne(s => s.Grade)
			    .HasForeignKey(s => s.CurrentGradeId)
			    .OnDelete(DeleteBehavior.Cascade);
			```
	- **Many to many**
		- use the `HasOne`, `WithMany` and `HasForeignKey` methods on both side of tables with joining table
		- For example: _Student_ and _Course_ are many-to-many relationship, meaning one _Student_ could have many _Course_ and one _Course_ could have many _Student_.
		![
](https://www.entityframeworktutorial.net/images/efcore/manytomany-db-diagram.png)
			- Step 1: Define a new joining entity class which includes the foreign key property and the reference navigation property for each entity
				```
				public class StudentCourse
				{
				    public int StudentId { get; set; }
				    public Student Student { get; set; }

				    public int CourseId { get; set; }
				    public Course Course { get; set; }
				}
				```
			- Step 2: 1.  Define a one-to-many relationship between other two entities and the joining entity, by including a collection navigation property in entities at both sides (`Student`  and  `Course`, in this case).
				```
				public class Student
				{
				    public int StudentId { get; set; }
				    public string Name { get; set; }

				    public virtual Collection<StudentCourse> StudentCourses { get; set; }
				}
				public class Course
				{
				    public int CourseId { get; set; }
				    public string CourseName { get; set; }
				    public string Description { get; set; }

				    public virtual Collection<StudentCourse> StudentCourses { get; set; }
				}
				```
			- Step 3: From _joining table configuration_, configure both the foreign keys in the joining entity as a composite key. In this case, _StudentCourse_ would have a composite key of _StudentId_ and _CourseId_. Then , build **One-to-Many** in between joining table and both table
				```
				modelBuilder.Entity<StudentCourse>().HasKey(sc => new { sc.StudentId, sc.CourseId });

				modelBuilder.Entity<StudentCourse>()
				    .HasOne<Student>(sc => sc.Student)
				    .WithMany(s => s.StudentCourses)
				    .HasForeignKey(sc => sc.StudentId);


				modelBuilder.Entity<StudentCourse>()
				    .HasOne<Course>(sc => sc.Course)
				    .WithMany(s => s.StudentCourses)
				    .HasForeignKey(sc => sc.CourseId );
				```
## Group by
- Introduced from EF Core 2.1
- For example:
	```
	var query = context.Orders
					.GroupBy(o => new { o.CustomerId, o.EmployeeId }) 
					.Select(g => new { 
						g.Key.CustomerId, 
						g.Key.EmployeeId, 
						Sum = g.Sum(o => o.Amount), 
						Min = g.Min(o => o.Amount), 
						Max = g.Max(o => o.Amount), 
						Avg = g.Average(o => Amount) 
					});
	```
## Transaction
- Transactions allow several database operations to be processed in an atomic manner. 
	- If the transaction is committed, all of the operations are successfully applied to the database.
	- If the transaction is rolled back, none of the operations are applied to the database.
	- `context.Database.BeginTransaction()` and `transaction.Commit()`
- For example:
	```
	using (var context = new BloggingContext()) { 
		using (var transaction = context.Database.BeginTransaction()) { 
			try { 
				context.Blogs.Add(new Blog { Url = "http://blogs.msdn.com/visualstudio" }); 
				context.SaveChanges(); 
				var blogs = context.Blogs .OrderBy(b => b.Url) .ToList(); 
				// Commit transaction if all commands succeed, transaction will auto-rollback when disposed if either commands fails  
				transaction.Commit(); 
				} 
				catch (Exception) { // TODO: Handle failure } 
		} 
	}
	```
## DbQuery & FromSql
- Introduced from EF Core 2.1
- **dbQuery**: an EF Core model can contain query types, which can be used to carry out database queries against data that isn't mapped to entity types.
- **DBContext** are **partial class**, so you can create one or more separate files to organize your **raw SQL DbQuery definitions** as best suits you.
	```
	public  class  SampleContext : DbContext`
	{
		public  DbSet<Order>  Orders  {  get;  set;  }
		public  DbSet<OrderItem>  OrderItems  {  get;  set;  }
		public  DbSet<Customer>  Customers  {  get;  set;  }

		public  DbQuery<OrderHeader>  OrderHeaders  {  get;  set;  }
	}
	var result = await context.OrderHeader.FromSql("SQL_SCRIPT").ToListAsync();
	```
## How to improve EF performance
- **Let SQL Server do the filtering (Use Eager loading instead Lazy Loading)**
	- For example	
		```
		string city = "New York";
		List<School> schools = db.Schools.ToList();
		List<School> newYorkSchools = schools.Where(s => s.City == city).ToList();
		```
	- On line 2 when we do **.ToList(),** Entity Framework will go out to the database to materialize the entities, so that the application has access to the actual values of those objects, rather than just having an understanding of how to look them up from the database. It’s going to retrieve every row in that Schools table, then filter the list in .NET. So better use:
			```
			List<School> newYorkSchools = db.Schools.Where(s => s.City == city).ToList();
			```
- **Minimizing the trips to the database (Use Eager loading instead Lazy Loading)**
	- For example:
		```
		List<School> schools = db.Schools.Where(s => s.City == city).ToList();
		foreach(var school in schools)
		{
			Console.Writeline(school.Pupils.Count);
		}
		```
	- If we look in sql behind at what happens when this code runs, we see a query run once to get a list of schools in New York, but **another query is also run 500 times to fetch Pupil information**.
	- This happens because by default, EF uses a loading strategy called **Lazy Loading**, where it doesn’t fetch any data associated with the virtual Pupils property on the School object when the first query is run.
	- use the **Eager Loading** data access strategy, which fetches the related data in a single query when you use an **Include()** statement. Since the Pupils data would be in memory, there would be no need for Entity Framework to hit the database again. So better use:
		```
		List<School> schools = db.Schools.Where(s => s.City == city).Include(x => x.Pupils).ToList();
		```
- Get as less column as you want, not entire columns
	- For example:
		```
		List<Pupil> pupils = db.Pupils.Where(p => p.SchoolId == schoolId).ToList();
		```
	- problem here is that, at the point when the query is run, EF has no idea what properties you might want to read, so its only option is to retrieve all of an entity’s properties, i.e. every column in the table. **This impacts everything from SQL Server I/O and network performance, through to memory usage in our client application**. So better use:
		```
		List<PupilName> pupils = db.Pupils.Where(p => p.SchoolId == schoolId)
										.Select(x => new PupilName
										{
											FirstName = x.FirstName,
											LastName = x.LastName
										}).ToList();
		``` 
- **Mismatched data types**
	- For example
		```
		var pupils = db.Pupils.Where(p => p.PostalZipCode == "90210").ToList();
		```
	- In the query plan, we start by looking for the expensive operations – in this case is showing us that we’re using an ‘Index Scan’ operation instead of an ‘Index Seek’. By default,  the parameter sent to SQL Server as nvarchar, but in table definition the column is varchar type, so SQL Server must convert every row in the index from VARCHAR to NVARCHAR first. So better define the column data type correctly
		```
		[Column(TypeName = "varchar")]
		public string PostalZipCode { get; set; }
		```
- **Missing indexes**
	- A good indexing strategy involves considering what columns you frequently match against and what columns are returned when searching against them
		```
		CREATE NONCLUSTERED INDEX [NonClusteredIndex_City] ON [dbo].[Pupils] ([City]) INCLUDE ([FirstName], [LastName]) ON [PRIMARY]
		```
- **Change Tracking**
	- When you retrieve entities from the database, it’s possible that you will modify those objects and expect to be able to write them back to the database. Because Entity Framework doesn’t know your intentions, it has to assume that you will make modifications, so must set these objects up to track any changes you make. That adds extra overhead, and also significantly increases memory requirements. This is particularly problematic when retrieving larger data sets.
	- If you know you only want to read data from a database you can explicitly tell Entity Framework not to do this tracking by add **AsNoTracking()**:
		```
		List<School> schools = db.Schools.AsNoTracking().Where(s => s.City == city).Take(100).ToList();
		```
- **Remember to Dispose**
- **Using Async**. Async can dramatically improve scalability by ensuring that resources are returned to the ThreadPool while queries are running.
