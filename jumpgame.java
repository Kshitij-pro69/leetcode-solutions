class Solution {
    public int maxJumps(int[] arr, int d) {
        int n = arr.length;
        int[] dp = new int[n];
        int maxVisits = 0;
        
        for (int i = 0; i < n; i++) {
            maxVisits = Math.max(maxVisits, dfs(arr, d, i, dp));
        }
        
        return maxVisits;
    }
    
    private int dfs(int[] arr, int d, int start, int[] dp) {
        if (dp[start] != 0) {
            return dp[start];
        }
        
        int maxSteps = 1; // Can always visit the current index
        
        // Try jumping to the right
        for (int i = start + 1; i <= Math.min(start + d, arr.length - 1); i++) {
            if (arr[i] >= arr[start]) {
                break; // Can't jump past a higher or equal height
            }
            maxSteps = Math.max(maxSteps, 1 + dfs(arr, d, i, dp));
        }
        
        // Try jumping to the left
        for (int i = start - 1; i >= Math.max(start - d, 0); i--) {
            if (arr[i] >= arr[start]) {
                break; // Can't jump past a higher or equal height
            }
            maxSteps = Math.max(maxSteps, 1 + dfs(arr, d, i, dp));
        }
        
        dp[start] = maxSteps;
        return maxSteps;
    }
}