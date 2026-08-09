class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length;
        
        // suffix sum array to quickly calculate sum of remaining piles
        int[] suffixSum = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            suffixSum[i] = suffixSum[i + 1] + piles[i];
        }
        
        // dp[i][m] = max stones current player can get from piles[i:] with M = m
        // m can go up to n (since at most we can take n piles)
        int[][] dp = new int[n][n + 1];
        
        // Fill DP from the end
        for (int i = n - 1; i >= 0; i--) {
            for (int m = 1; m <= n; m++) {
                // If we can take all remaining piles
                if (i + 2 * m >= n) {
                    dp[i][m] = suffixSum[i];
                } else {
                    int maxStones = 0;
                    // Try taking x piles where 1 <= x <= 2*m
                    for (int x = 1; x <= 2 * m; x++) {
                        int taken = suffixSum[i] - suffixSum[i + x];
                        // After taking x piles, opponent's turn with M = max(m, x)
                        int opponent = dp[i + x][Math.max(m, x)];
                        int current = taken + (suffixSum[i + x] - opponent);
                        maxStones = Math.max(maxStones, current);
                    }
                    dp[i][m] = maxStones;
                }
            }
        }
        
        return dp[0][1];
    }
}