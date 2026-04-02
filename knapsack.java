class Solution {
    public int maximumAmount(int[][] coins) {
        int m = coins.length, n = coins[0].length;
        // dp[i][j][k] = max coins at (i,j) with k neutralizations used
        int[][][] dp = new int[m][n][3];
        
        // Initialize with very small values
        for (int[][] layer : dp)
            for (int[] row : layer)
                Arrays.fill(row, Integer.MIN_VALUE / 2);
        
        // Base case: starting cell
        dp[0][0][0] = coins[0][0];
        dp[0][0][1] = Math.max(coins[0][0], 0); // neutralize if negative
        dp[0][0][2] = Math.max(coins[0][0], 0);
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) continue;
                
                for (int k = 0; k <= 2; k++) {
                    int best = Integer.MIN_VALUE / 2;
                    
                    // Come from top
                    if (i > 0) best = Math.max(best, dp[i-1][j][k]);
                    // Come from left
                    if (j > 0) best = Math.max(best, dp[i][j-1][k]);
                    
                    if (best == Integer.MIN_VALUE / 2) continue;
                    
                    // Option 1: Don't neutralize this cell
                    dp[i][j][k] = Math.max(dp[i][j][k], best + coins[i][j]);
                    
                    // Option 2: Neutralize this cell (uses one neutralization)
                    if (k > 0 && coins[i][j] < 0) {
                        int prevBest = Integer.MIN_VALUE / 2;
                        if (i > 0) prevBest = Math.max(prevBest, dp[i-1][j][k-1]);
                        if (j > 0) prevBest = Math.max(prevBest, dp[i][j-1][k-1]);
                        
                        if (prevBest != Integer.MIN_VALUE / 2)
                            dp[i][j][k] = Math.max(dp[i][j][k], prevBest); // skip the negative
                    }
                }
            }
        }
        
        return Math.max(dp[m-1][n-1][0], Math.max(dp[m-1][n-1][1], dp[m-1][n-1][2]));
    }
}
