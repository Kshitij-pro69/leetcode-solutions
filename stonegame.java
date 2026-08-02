class Solution {
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        // dp[i][j] represents the maximum stones the current player can get
        // from piles[i..j] when it's their turn
        int[][] dp = new int[n][n];
        
        // Base case: when there's only one pile, the current player takes it
        for (int i = 0; i < n; i++) {
            dp[i][i] = piles[i];
        }
        
        // Fill the dp table for subarrays of increasing length
        for (int length = 2; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                int j = i + length - 1;
                // If current player takes piles[i], they get piles[i] plus
                // whatever they can get from the remaining subarray after opponent's turn
                // The opponent will play optimally from piles[i+1..j]
                // So current player gets piles[i] + (total sum of piles[i+1..j] - dp[i+1][j])
                // Similarly for taking piles[j]
                int total = 0;
                for (int k = i; k <= j; k++) {
                    total += piles[k];
                }
                dp[i][j] = Math.max(
                    piles[i] + (total - piles[i] - dp[i+1][j]),
                    piles[j] + (total - piles[j] - dp[i][j-1])
                );
            }
        }
        
        // Calculate total sum
        int totalSum = 0;
        for (int pile : piles) {
            totalSum += pile;
        }
        
        // Alice wins if she can get more than half of the total stones
        return dp[0][n-1] > totalSum / 2;
    }
}