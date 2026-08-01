class Solution {
    public boolean predictTheWinner(int[] nums) {
        int n = nums.length;
        // dp[i][j] represents the maximum score difference the current player can get
        // from subarray nums[i..j] compared to the opponent
        int[][] dp = new int[n][n];
        
        // Base case: when there's only one element
        for (int i = 0; i < n; i++) {
            dp[i][i] = nums[i];
        }
        
        // Fill the dp table for subarrays of increasing length
        for (int length = 2; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                int j = i + length - 1;
                // Current player can pick either nums[i] or nums[j]
                // If pick nums[i], opponent gets dp[i+1][j] advantage
                // If pick nums[j], opponent gets dp[i][j-1] advantage
                dp[i][j] = Math.max(nums[i] - dp[i+1][j], nums[j] - dp[i][j-1]);
            }
        }
        
        // If the maximum score difference Player 1 can get is >= 0, Player 1 wins
        return dp[0][n-1] >= 0;
    }
}