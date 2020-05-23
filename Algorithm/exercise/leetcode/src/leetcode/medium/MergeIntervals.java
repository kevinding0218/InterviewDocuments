package leetcode.medium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ran on May 20, 2020.
 */
public class MergeIntervals {
  /**
   * LC. 56 Merge Intervals (Microsoft)
   * 
   * Given a collection of intervals, merge all overlapping intervals.

    Example 1:
    Input: [[1,3],[2,6],[8,10],[15,18]]
    Output: [[1,6],[8,10],[15,18]]
    Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].

    Example 2:
    Input: [[1,4],[4,5]]
    Output: [[1,5]]
    Explanation: Intervals [1,4] and [4,5] are considered overlapping.
    NOTE: input types have been changed on April 15, 2019. 
    Please reset to default code definition to get new method signature.
   */

   /**
     * Intuition
     * If we sort the intervals by their start value, then each set of intervals that 
     * can be merged will appear as a contiguous "run" in the sorted list.
     * 
     * Algorithm
     * First, we sort the list as described. Then, we insert the first interval into our merged 
     * list and continue considering each interval in turn as follows: 
     * 
     * If the current interval begins after the previous interval ends, 
     * then they do not overlap and we can append the current interval to merged. 
     * 
     * Otherwise, they do overlap, and we merge them by updating the end of the previous interval 
     * if it is less than the end of the current interval.
     * 
     * (1, 9), (2, 5), (19, 20), (10, 11), (0, 3), (0, 2)
     * -->
     * (0, 3), (0, 2), (1, 9), (2, 5), (10, 11), (19, 20)
     * 
     * (0, 3), (0, 2) -> 0 <= 3 has overlap -> (0, max(2, 3)) -> (0, 3)
     * (0, 3), (1, 9) -> 1 < 3 has overlap -> (0, max(3, 9)) -> (0, 9)
     * (0, 9), (2, 5) -> 2 < 9 has overlap -> (0, max(9, 5)) -> (0, 9)
     * (0, 9), (10, 11) -> 10 > 9 no overlap -> (0, 9), (10, 11)
     * (10, 11), (19, 20) -> 19 > 11, no overlap -> (0, 9), (10, 11), (19, 20)

     time : O(nlogn) space : O(n)

     * @param intervals
     * @return
     */
    public List<Interval> merge(List<Interval> intervals) {
      if (intervals == null || intervals.size() <= 1) return intervals;
      Collections.sort(intervals, (a, b) -> a.start - b.start);
      int start = intervals.get(0).start;
      int end = intervals.get(0).end;
      List<Interval> res = new ArrayList<>();
      for (Interval interval : intervals) {
          if (interval.start <= end) {
              // if current interval's start is less then previous internal's end
              // meaning there is overlap, which would be (prev start, max(prev end, current end))
              end = Math.max(end, interval.end);
          } else {
              // if current interval's start is greater then previous internal's end
              // meaning there is no overlap, add into result set
              res.add(new Interval(start, end));
              start = interval.start;
              end = interval.end;
          }
      }
      res.add(new Interval(start, end));
      return res;
    }

    private class Interval {
      int start;
      int end;
      Interval() {
          start = 0;
          end = 0;
      }
      Interval(int s, int e) {
          start = s;
          end = e;
      }
    }
}