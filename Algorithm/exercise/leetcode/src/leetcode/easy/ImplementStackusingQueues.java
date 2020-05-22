package leetcode.easy;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Ran on May 23, 2020.
 */
public class ImplementStackusingQueues {
    /**
     * LC. 225. Implement Stack using Queues
     * 
     * Implement the following operations of a stack using queues.

      push(x) -- Push element x onto stack.
      pop() -- Removes the element on top of the stack.
      top() -- Get the top element.
      empty() -- Return whether the stack is empty.
      Example:

      MyStack stack = new MyStack();

      stack.push(1);
      stack.push(2);  
      stack.top();   // returns 2
      stack.pop();   // returns 2
      stack.empty(); // returns false
      Notes:

      You must use only standard operations of a queue -- which means only push to back, peek/pop from front, size, and is empty operations are valid.
      Depending on your language, queue may not be supported natively. You may simulate a queue by using a list or deque (double-ended queue), as long as you use only standard operations of a queue.
      You may assume that all operations are valid (for example, no pop or top operations will be called on an empty stack).
     */
    Queue<Integer> queue;

    /** Initialize your data structure here.*/
    public ImplementStackusingQueues() {
        queue = new LinkedList<>();
    }

    /** Push element x onto stack. */
    public void push(int x) {
      /**
       * push (1)
       * queue before: 1
       * queue after: 1
       * 
       * push (2)
       * queue before: 1 -> 2
       * get first element 1 add to 2's next
       * queue after: 2 -> 1
       * 
       * push (3)
       * queue before: 2 -> 1 -> 3
       * get first element 2 add to 1 -> 3's next
       * queue after: 1 -> 3 -> 2
       * get first element 1 add to 3 -> 2's next
       * queue after: 3 -> 2 -> 1
       * 
       * push (4)
       * queue before: 3 -> 2 -> 1 -> 4
       * get first element 3 add to 2 -> 1 -> 4's next
       * queue after: 2 -> 1 -> 4 -> 3
       * get first element 2 add to 1 -> 4 -> 3's next
       * queue after: 1 -> 4 -> 3 -> 2
       * get first element 1 add to 4 -> 3 -> 2's next
       * queue after: 4 -> 3 -> 2 -> 1
       * 
       * queue: 4 -> 3 -> 2 -> 1
       */
        queue.add(x);
        for (int i = 0; i < queue.size() - 1; i++) {
            //poll: retrieves and removes the first element of a list.
            queue.add(queue.poll());
        }
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
      // poll: retrieves and removes the first element of a list.
      return queue.poll();
    }

    /** Get the top element. */
    public int top() {
        // peek: retrieves the first element of a list
        return queue.peek();
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return queue.isEmpty();
    }
}