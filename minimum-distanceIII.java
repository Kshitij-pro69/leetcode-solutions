class Solution {
    public long minimumTotalDistance(List<Integer> robot, int[][] factory) {
        // Sort robots
        List<Integer> robots = new ArrayList<>(robot);
        Collections.sort(robots);
        
        // Sort factories by position
        Arrays.sort(factory, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Expand factories into individual positions
        List<Integer> factoryPositions = new ArrayList<>();
        for (int[] f : factory) {
            for (int i = 0; i < f[1]; i++) {
                factoryPositions.add(f[0]);
            }
        }
        
        int m = robots.size();
        int n = factoryPositions.size();
        
        // DP array: dp[i][j] = min distance for first i robots using first j factory positions
        long[][] dp = new long[m + 1][n + 1];
        
        // Initialize with infinity
        for (int i = 0; i <= m; i++) {
            Arrays.fill(dp[i], Long.MAX_VALUE / 2);
        }
        
        // Base case: 0 robots, 0 distance
        dp[0][0] = 0;
        
        // For 0 robots, distance is 0 regardless of how many factory positions used
        for (int j = 0; j <= n; j++) {
            dp[0][j] = 0;
        }
        
        // Fill DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // Option 1: Don't use the j-th factory position for i-th robot
                dp[i][j] = dp[i][j - 1];
                
                // Option 2: Use j-th factory position for i-th robot
                // This means we need to match the i-th robot with the j-th factory position
                // and recursively match the previous robots with previous factory positions
                long distance = Math.abs(robots.get(i - 1) - factoryPositions.get(j - 1));
                if (dp[i - 1][j - 1] != Long.MAX_VALUE / 2) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1] + distance);
                }
            }
        }
        
        return dp[m][n];
    }
}