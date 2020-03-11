package leetcode.easy;

/**
 * Created by Ran on Mar 10, 2020.
 */
public class ImplementStrStr {
    /**
     *  LC 28. Implement strStr()
        
        Return the index of the first occurrence of needle in haystack, 
        or -1 if needle is not part of haystack.

        Example 1:
        Input: haystack = "hello", needle = "ll"
        Output: 2

        Example 2:
        Input: haystack = "aaaaa", needle = "bba"
        Output: -1
        Clarification:

        What should we return when needle is an empty string? This is a great question to ask during an interview.
        
     * @param haystack
     * @param needle
     * @return
     */

     /*
      * KMP
      * take advantage of success compare character 
      * e.g
      * adsgwadsxdsgwadsgz
      *  dsgwadsgz
      *  ds.. ds..    
      *  we're looking for a suffix as well as prefix in the substring ('dsgwads') before the mismatch
      *  even it's not match, we found there is prefix and suffix match in failed case
      *       dsgwadsgz <- so we can skip matching for 'dsgwa' and move couple of characters forward
      *  now we compare dsx vs dsg, we're trying to prevent going backwards in progress, but we can't find
      *  a equal prefix and suffix in dsg, so we continue one character
      *  
      * build a prefix table for needle that looks for count of suffix and prefix matching in substring
      * 0 1 2 3 4 5 6 7 8
      * d s g w a d s g z
      * i j j j j
      * 0 0 0 0 0           <-- length 0 for common prefix/suffix substring d, ds, dsg, dsgw, dsgwa
      * 0 0 0 0 0 1         <-- we see 'd' and 'd' match, we assign 'd' as 1 from i + 1 => 0 + 1 = 1
      *                     <-- '1' tells us length of common prefix/suffix in substring 'dsgwad' is 1 (d)
      *                     <-- if we have a mismatch at next character of 'd', we can move string to the next 'd'
      *
      *   i         j       <-- we advance i and j by 1
      *                     <-- we see 's' and 's' match, we assign 's' as 2 from <-- i + 1 => 1 + 1 = 2
      *                     <-- '2' tells us length of common prefix/suffix in substring 'dsgwads' is 2 (ds)
      * 0 0 0 0 0 1 2       <-- if we have a mismatch at next character of 's', we can move string to the next 'ds'
      *                     we advance i and j by 1
      *     i         j
      *                     <-- we see 'g' and 'g' match, we assign 'g' as 3 from <-- i + 1 => 2 + 1 = 3
      *                     <-- '3' tells us length of common prefix/suffix in substring 'dsgwadsg' is 3 (dsg)
      * 0 0 0 0 0 1 2 3     <-- if we have a mismatch at next character of 'g', we can move string to the next 'dsg'
      *                     we advance i and j by 1
      *       i         j   <-- no match for w and z, assign 'z' as 0
      * 0 0 0 0 0 1 2 3 0
      * n = len(haystack)
      * m = len(needle)
      * build prefix table: time: O(m), space: O(m)
      * matching traversal: time: O(n), space: O(1)
      * overall: time: O(m+n), space: O(m)
    */
    public static int strStr_KMP(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();
        if (m == 0) return 0;

        int[] pi = new int[m];
        computePrefix(needle, pi);

        int k = 0;
        // 0   0  0  0  0  1  2  3   0
        // d   s  g  w  a  d  s  g   z
        // 0   1  2  3  4  5  6  7   8  9 10  11 12 13 14 15 16 17
        // a   d  s  g  w  a  d  s   x  d  s  g  w  a  d  s  g  z
        // 
        //  k  i    needle.charAt(k)   haystack.charAt(i)
        //  0  0    d                      a
        //  0  1    d                      d   k++ = 1
        //  1  2    s                      s   k++ = 2
        //  ...
        // a   d  s  g  w  a  d  s  'x'  d  s  g  w  a  d  s  g  z
        //     d  s  g  w  a  d  s  'g'  z
        // 7  8    g                      x   k=pi[k]=pi[6]=2
        // a   d  s  g  w  a  d  s  'x'  d  s  g  w  a  d  s  g  z
        //                    d  s  'g'  w  a  d  s  g  z
        // 1  8    g                      x   k=pi[k]=pi[1]=0
        // a   d  s  g  w  a  d  s  x  'd'  s  g  w  a  d  s  g  z
        //                             'd'  s  g  w  a  d  s  g  z                     
        // 0   9    d                      d   k=k++ = 1
        for (int i = 0; i < n; i++) {
            while (k > 0 && needle.charAt(k) != haystack.charAt(i)) {
                k = pi[k-1];
            }
            if (needle.charAt(k) == haystack.charAt(i)) {
                k++;
            }
            if (k == m) {
                return i - m + 1;
            }
        }

        return -1;
    }

    //  0  1  2  3  4  5  6  7  8
    //  d  s  g  w  a  d  s  g  z
    //  0  0  0  0  0  1  2  3  0   <-- pi
    private static void computePrefix(String needle, int[] pi) {
        pi[0] = 0;
        int i = 0;
        for (int j = 1; j < needle.length(); j++) {
            while (i > 0 && needle.charAt(i) != needle.charAt(j)) {
                i = pi[i-1];
            }
            if (needle.charAt(i) == needle.charAt(j)) {
                i++;
            }
            pi[j] = i;
        }
    }
     /*
      * brute force
      * time : O(n^2) <- length substraction of 'haystack'  and 'needle' * length of needle
      * space : O(1)  
     */
    public static int strStr_BF(String haystack, String needle) {
        int res = -1;
        if (haystack == "" || needle == "") return 0;
        // important!: a little bit like sliding window
        // e.g: 'hello' vs 'll', here we know if 'hello' would contain 'll'
        // means 'll' would be either at index 0, 1, 2 or 3 position of 'hello'
        // it cannot be at position 4 because if it starts at 4, with its own length of 2
        // would exceed the length of 'hello'
        // so instead of looping all characters, we loop only from index 0 to (haystack.length - needle.length)
        for (int i = 0; i <= haystack.length() - needle.length(); i++) {
            if (haystack.substring(i, i + needle.length()).equals(needle)) {
                res = i;
            }
        }
        return res;
    }
}
