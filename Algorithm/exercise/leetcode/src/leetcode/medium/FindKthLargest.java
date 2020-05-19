package leetcode.medium;

import java.util.PriorityQueue;

/**
 * Created by Ran on May 17, 2020.
 */
public class FindKthLargest {
  /**
   * LC. 215 Kth Largest Element in an Array
   * 
   * Find the kth largest element in an unsorted array. 
   * Note that it is the kth largest element in the sorted order, 
   * not the kth distinct element.

    Example 1:
    Input: [3,2,1,5,6,4] and k = 2
    Output: 5

    Example 2:
    Input: [3,2,3,1,2,4,5,5,6] and k = 4
    Output: 4
   */

   // time : O(n) space : O(1)

   public int findKthLargest(int[] nums, int k) {
    if (nums == null || nums.length == 0) return 0;
    int left = 0;
    int right = nums.length - 1;
    while (true) {
      int pos = partition(nums, left, right);
      if (pos + 1 == k) {
        return nums[pos];
      } else if (pos + 1 > k) {
        right = pos - 1;
      } else {
        left = pos + 1;
      }
    }
  }

    /**
     *   3,2,1,5,6,4  k = 3 -> 3
     *   0 1 2 3 4 5
     * 

    partition(nums, 0, 5)
    pivot : 3   [3, 2, 1, 5, 6, 4] -> l = 1, r = 5
                num[l] = 2, pivot = 3, num[r] = 4, swap(2, 4)
                [3, 4, 1, 5, 6, 2] -> l++ = 2, r-- = 4
                num[l] = 1, pivot = 3, num[r] = 6, swap(1, 6)
                [3, 4, 6, 5, 1, 2] -> l++ = 3, r-- = 3
                num[l] = 5, pivot = 3, num[r] = 5, l++ = 4, over
                swap([0], [3]) -> swap(3, 5)
                [5, 4, 6, 3, 1, 2]  return r = 3 -> pos
    
    pos + 1 = 4 > k = 3
    right = pos - 1 = 2
    partition(nums, 0, 2)

    pivot : 5   [5, 4, 6, 3, 1, 2] -> l = 1, r = 2
                num[l] = 4, pivot = 5, num[r] = 6, swap(4, 6)
                [5, 6, 4, 3, 1, 2] -> l++ = 2, r-- = 1, over
                swap([0], [1]) -> swap(5, 6)
                [6, 5, 4, 3, 1, 2] return r = 1 -> pos

    pos + 1 = 2 < k = 3
    left = pos + 1 = 2
    partition(nums, 2, 2)
    pivot : 4   [6, 5, 4, 3, 1, 2] -> l = 3, r = 2
                swap([2], [2])  
                [6, 5, 4, 3, 1, 2]  eturn r = 2 -> pos

    pos + 1 = 3 = k
    return nums[3]  = 3
    */

    private int partition(int[] nums, int left, int right) {
      int pivot = nums[left];
      int l = left + 1;
      int r = right;
      while (l <= r) {
        if (nums[l] < pivot && nums[r] > pivot) {
            swap(nums, l++, r--);
        }
        if (nums[l] >= pivot) l++;
        if (nums[r] <= pivot) r--;
      }
      swap(nums, left, r);
      return r;
    }

  private void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }

  /**
   * time : O(nlogk)
   * space : O(n)
   *
   * @param nums
   * @param k
   * @return
   */


  /**
   * Priority Queue implements the heap data structure with Queue interface
   * 
   * add() - Inserts the specified element to the queue. If the queue is full, it throws an exception.
   * offer() - Inserts the specified element to the queue. If the queue is full, it returns false.
   * e.g. queue.add(4); queue.add(2); queue.offer(1)
   * queue: [1, 2, 4]
   * 
   * remove() - removes the specified element from the queue
   * poll() - returns and removes the head of the queue
   * 
   * @param nums
   * @param k
   * @return
   */
  public int findKthLargest2(int[] nums, int k) {
    if (nums == null || nums.length == 0) return 0;
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    for (int num : nums) {
        minHeap.offer(num);
        if (minHeap.size() > k) {
            minHeap.poll();
        }
    }
    return minHeap.poll();
  }
}