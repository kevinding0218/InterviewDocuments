package leetcode.hard;

import java.util.Stack;

/**
 * Created by Ran on May 27, 2020.
 */
public class TrappingRainWater {
  /**
   * LC. 42 Trapping Rain Water (Uber)
   * 
   * Given n non-negative integers representing an elevation map where the width of each bar is 1, 
   * compute how much water it is able to trap after raining.
   * 
   *  0,1,0,2,1,0,1,3,2,1,2,1

                   *
           *       * *   *
       *   * *   * * * * * *
     0 1 2 3 4 5 6 7 8 9 0 1
     0,1,0,2,1,0,1,3,2,1,2,1
                 l r
   */

  /**
   * time : O(n)
     space : O(1)

    From the figure in dynamic programming approach, notice that as long as right_max[i]>left_max[i] 
    (from element 0 to 6), the water trapped depends upon the left_max, 
    and similar is the case when left_max[i]>right_max[i] (from element 8 to 11). 
    
    So, we can say that if there is a larger bar at one end (say right), 
    we are assured that the water trapped would be dependant on height of bar in current direction (from left to right). 
    
    As soon as we find the bar at other end (right) is smaller, 
    we start iterating in opposite direction (from right to left). 
    We must maintain left_max and right_max during the iteration, 
    but now we can do it in one iteration using 2 pointers, switching between the two.

    Algorithm

    Initialize left pointer to 0 and right pointer to size-1
    While left<right, do:
      If height[left] is smaller than height[right]
        If height[left]≥left_max, update left_max
        Else add left_max−height[left] to ans
        Add 1 to left.
      Else
        If height[right]≥right_max, update right_max
        Else add right_max−height[right] to ans
        Subtract 1 from right.

   * 0,1,0,2,1,0,1,3,2,1,2,1

                   *
           *       * *   *
       *   * *   * * * * * *
     0,1,0,2,1,0,1,3,2,1,2,1
     0 1 2 3 4 5 6 7 8 9 0 1
                 l r
     0                     1  height[l]:0<height[r]:1, left_max=0, ans=0, left ++
       1                   1  height[l]:1=height[r]:1, right_max=1, ans=0, right --
       1                 2    height[l]:1<height[r]:2, left_max=1, ans=0, left++
         0               2    height[l]:0<height[r]:2, ans+=left_max-0=1-0=1, left++
           2             2    height[l]:2=height[r]:2, right_max=2, ans=1, right --
           2           1      height[l]:2>height[r]:1, ans+=right_max-1=2-1=1, ans=2, right--
           2         2        height[l]:2=height[r]:2, right--
           2       3          height[l]:2<height[r]:3, left_max=2, left++
             1     3          height[l]:1<height[r]:3, ans+=left_max-1=2-1=1, ans = 3, left++
               0   3          height[l]:0<height[r]:3, ans+=left_max-0=2-0=2, ans = 5, left++
                 1 3          height[l]:1<height[r]:3, ans+=left_max-1=2-1=1, ans = 6, left++
   * @param height
   * @return
   */
   public int trap(int[] height) {
    int left = 0;
    int right = height.length - 1;
    int res = 0;
    int leftMax = 0;
    int rightMax = 0;
    while (left < right) {
        if (height[left] < height[right]) {
            leftMax = Math.max(height[left], leftMax);
            res += leftMax - height[left];
            left++;
        } else {
            rightMax = Math.max(height[right], rightMax);
            res += rightMax - height[right];
            right--;
        }
    }
    return res;
  }
  
  /**
   * we can use stack to keep track of the bars that are bounded by longer bars and hence, 
   * may store water. 
   * Using the stack, we can do the calculations in only one iteration.
   * 
   * We keep a stack and iterate over the array. 
   * We add the index of the bar to the stack if bar is smaller than or equal to the bar at top of stack,
   * which means that the current bar is bounded by the previous bar in the stack. 
   * If we found a bar longer than that at the top, 
   * we are sure that the bar at the top of the stack is bounded by the current bar and a previous bar in the stack, 
   * hence, we can pop it and add resulting trapped water to ans.
   * 
   * 0,1,0,2,1,0,1,3,2,1,2,1

                   *
           *       * *   *
       *   * *   * * * * * *
     0 1 2 3 4 5 6 7 8 9 0 1
     0,1,0,2,1,0,1,3,2,1,2,1
                 l r
   * @param height
   * @return
   */
  public int trap2(int[] height) {
    int ans = 0, current = 0;
    Stack<Integer> st = new Stack<Integer>();
    while (current < height.length) {
      /**
       * if iterator is decreasing, continue adding into stack
       * while next iterator is greater than current stack top
       * cacluate the area which is distance * bounded_height = 
       * (distance between index stack.top and k) * (min height between index stack.top and k)
       * 
       * e.g: 
       * 1, 0, 2
       * 1  2  3
       * answer += distance * bounded_height = (distance between index 1 and 3) * (min height between index 1 and 2) = 1 * 1 = 1
       *        
       * 2, 1, 0, 1, 3
       * 3  4  5  6  7
       * answer += distance * bounded_height = (distance between index 4 and 6) * (min height between index 4 and 6) = 1 * 1 = 1
       * answer += distance * bounded_height = (distance between index 3 and 7) * (min height between index 3 and 7) = 3 * 1 = 3
       * 
       * 2, 1, 2
       * 8  9  10
       * answer += distance * bounded_height = (distance between index 8 and 10) * (min height between index 8 and 10) = 1 * 1 = 1
       * 
       * answer = 1 + 1 + 3 + 1 = 6
       */
        while (!st.empty() && height[current] > height[st.peek()]) {
            int top = st.peek();
            st.pop();
            if (st.empty())
                break;
            int distance = current - st.peek() - 1;
            int bounded_height = Math.min(height[current], height[st.peek()]) - height[top];
            ans += distance * bounded_height;
        }
        st.push(current++);
    }
    return ans;
  }
}