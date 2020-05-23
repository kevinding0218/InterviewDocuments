package leetcode.easy;

import java.util.Stack;

/**
 * Created by Ran on May 23, 2020.
 */
public class ImplementQueueusingStacks {
  /**
   * LC. 232 Implement Queue using Stacks (Apple)
   * 
   * Implement the following operations of a queue using stacks.

    push(x) -- Push element x to the back of queue.
    pop() -- Removes the element from in front of queue.
    peek() -- Get the front element.
    empty() -- Return whether the queue is empty.
    Example:

    MyQueue queue = new MyQueue();

    queue.push(1);
    queue.push(2);  
    queue.peek();  // returns 1
    queue.pop();   // returns 1
    queue.empty(); // returns false
    Notes:

    You must use only standard operations of a stack -- which means only push to top, peek/pop from top, size, and is empty operations are valid.
    Depending on your language, stack may not be supported natively. You may simulate a stack by using a list or deque (double-ended queue), as long as you use only standard operations of a stack.
    You may assume that all operations are valid (for example, no pop or peek operations will be called on an empty queue).
   */

  Stack<Integer> s1;
  Stack<Integer> s2;

  public ImplementQueueusingStacks() {
      s1 = new Stack<>();
      s2 = new Stack<>();
  }

  /** Push element x to the back of queue. */
  public void push(int x) {
      /**
       * keep pushing into s1
       */
      s1.push(x);
  }

  // time : O(n);
  /** Removes the element from in front of queue and returns that element. */
  public int pop() {
    /**         s1
     * push 1   3
     * push 2   2
     * push 3   1
     * s1: 3 -> 2 -> 1
     * pop()   s2
     *          1
     *          2
     *          3
     * s2: 1 -> 2 -> 3
     * s1: empty
     * s2.pop -> s2: 2 -> 3
     * s1: empty
     */
      if (!s2.isEmpty()) return s2.pop();
      else {
          while (!s1.isEmpty()) s2.push(s1.pop());
          return s2.pop();
      }
  }

  // time : O(n);
  /** Get the front element. */
  public int peek() {
    /**         s1
     * push 1   3
     * push 2   2
     * push 3   1
     * s1: 3 -> 2 -> 1
     * 
     * peek()   s2
     *          1
     *          2
     *          3
     * s2: 1 -> 2 -> 3
     * s1: empty
     * 
     * s2.peek() = 1
     */
      if (!s2.isEmpty()) return s2.peek();
      else {
          while (!s1.isEmpty()) s2.push(s1.pop());
          return s2.peek();
      }
  }

  /** Returns whether the queue is empty. */
  public boolean empty() {
      return s1.isEmpty() && s2.isEmpty();
  }
}