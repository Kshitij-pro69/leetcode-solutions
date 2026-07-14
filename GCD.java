import java.util.Arrays;

class Solution {
    public int subsequencePairCount(int[] nums) {
        final int MOD = 1_000_000_007;
        
        // Find the maximum value in nums to define the bounds of our DP table
        int maxNum = 0;
        for (int num : nums) {
            maxNum = Math.max(maxNum, num);
        }
        
        // dp[x][y] stores the count of disjoint pairs with GCDs x and y
        // 0 represents an empty subsequence
        int[][] dp = new int[maxNum + 1][maxNum + 1];
        dp[0][0] = 1; // Base case: Both subsequences are empty
        
        for (int num : nums) {
            int[][] nextDp = new int[maxNum + 1][maxNum + 1];
            
            for (int x = 0; x <= maxNum; x++) {
                for (int y = 0; y <= maxNum; y++) {
                    if (dp[x][y] == 0) continue;
                    
                    // 1. Skip num (leave subsequences unchanged)
                    nextDp[x][y] = (nextDp[x][y] + dp[x][y]) % MOD;
                    
                    // 2. Add num to the first subsequence
                    int nextX = gcd(x, num);
                    nextDp[nextX][y] = (nextDp[nextX][y] + dp[x][y]) % MOD;
                    
                    // 3. Add num to the second subsequence
                    int nextY = gcd(y, num);
                    nextDp[x][nextY] = (nextDp[x][nextY] + dp[x][y]) % MOD;
                }
            }
            dp = nextDp;
        }
        
        // Sum up the cases where both subsequences are non-empty (g > 0) 
        // and have the exact same GCD (x == y == g)
        int ans = 0;
        for (int g = 1; g <= maxNum; ++g) {
            ans = (ans + dp[g][g]) % MOD;
        }
        
        return ans;
    }
    
    // Helper method to compute Greatest Common Divisor (GCD)
    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}