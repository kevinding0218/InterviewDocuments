````Java
fun findNearbyRestaurants(latitude, longitude, radius): List<BusinessObject> {
  // return 32aej
  val currentLocationGeohash: String = computeGeohashcode(latitude, longitude)
  // return 6
  val geohash_level: Int = computeGeohashLenghth(radius)
  // return Map({32aej1: {..}, 32aej2: {..}, 41aed2: {}, ..)
  val geohash_collection = geohashCache.get(geohash_level)
  // with cache
  // return [32aej1, 32aej2, 32aej3, ...]
  val geohash_neighbors: List<String> = geohash_collection.keys.filter(k -> 	k.contains(currentLocationGeohash))  
  
  /*
   * with DB
   * select geohash_id as geohash_neighbors
   * from Geohash_VI
   * where geohash like '%geohash_id%'
   * 
   */
  // return [bizId1, bizId2, ... ]
  val geohash_neighbor_businessIds: List<String> = geohash_neighbors.reduce(
    geohash -> geohash_collection.get(geohash).values
  )
  val neighbor_businesses: List<BusinessObject> = geohash_neighbor_businessIds.map(
    bizId -> businessCache.get(bizId)
  ).filter(businessObject -> businessObject.type == BUS
  return neighbor_businesses
}
````
<!--stackedit_data:
eyJoaXN0b3J5IjpbOTA2OTUxMTgyXX0=
-->