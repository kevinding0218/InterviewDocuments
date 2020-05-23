package leetcode.easy;

/**
 * Created by Ran on May 21, 2020.
 */
public class BuyandSellStock {
  /**
   * LC. 121 Best Time to Buy and Sell Stock (Apple)
   * 
   * Say you have an array for which the ith element is the price of a given stock on day i.

    If you were only permitted to complete at most one transaction (i.e., buy one and sell one share of the stock), design an algorithm to find the maximum profit.

    Note that you cannot sell a stock before you buy one.

    Example 1:

    Input: [7,1,5,3,6,4]
    Output: 5
    Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
                Not 7-1 = 6, as selling price needs to be larger than buying price.
    Example 2:

    Input: [7,6,4,3,1]
    Output: 0
    Explanation: In this case, no transaction is done, i.e. max profit = 0.
   * 
   */

  /**
   * time : O(n);
     space : O(1);

   * @param prices
   * @return
   */
  public int maxProfit(int[] prices) {
    if (prices == null || prices.length < 2) return 0;
    int min = prices[0];
    int profit = 0;
    for (int price : prices) {
      /**
       * price: iterator through the prices
       * min:   min price of prices
       * profit:current max profit while iterator
       * 
       * [7,1,5,3,6,4]
       * price min  price-min   min=min(min, price)     profit=max(profit, price-min)
       * 7     7    7-7=0       min(7, 7)=7             max(0, 0) = 0 [7->7 earn 0]
       * 1     7    1-7=-6      min(7, 1)=1             max(0, -6) = 0[7->7 earn 0]
       * 5     1    5-1=4       min(1, 5)=1             max(0, 4) = 4 [1->5 earn 4]
       * 3     1    3-1=2       min(1, 3)=1             max(4, 2) = 4 [1->5 earn 4]
       * 6     1    6-1=5       min(1, 6)=1             max(4, 5) = 5 [1->6 earn 5]
       * 4     1    4-1=3       min(1, 4)=1             max(5, 3) = 5 [1->6 earn 5]
       */
        min = Math.min(min, price);
        profit = Math.max(profit, price - min);
    }
    return profit;
  }
}