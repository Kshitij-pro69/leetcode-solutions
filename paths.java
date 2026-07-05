class Solution {
    public int[] pathsWithMaxScore(List<String> board) {
        int n = board.size();
        int MOD = 1_000_000_007;
        
        // dp[i][j] = max sum to reach (i,j) from bottom-right
        int[][] dp = new int[n][n];
        // count[i][j] = number of ways to reach (i,j) with max sum
        int[][] count = new int[n][n];
        
        // Initialize with -1 (unreachable)
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
            Arrays.fill(count[i], 0);
        }
        
        // Start from bottom-right (S)
        dp[n-1][n-1] = 0;
        count[n-1][n-1] = 1;
        
        // Process from bottom-right to top-left
        for (int i = n-1; i >= 0; i--) {
            for (int j = n-1; j >= 0; j--) {
                // Skip if current cell is obstacle or unreachable
                if (board.get(i).charAt(j) == 'X' || dp[i][j] == -1) {
                    continue;
                }
                
                // Check three possible moves: up, left, up-left
                int[][] moves = {{-1, 0}, {0, -1}, {-1, -1}};
                
                for (int[] move : moves) {
                    int ni = i + move[0];
                    int nj = j + move[1];
                    
                    // Check if within bounds
                    if (ni < 0 || nj < 0) continue;
                    
                    // Check if destination is obstacle
                    if (board.get(ni).charAt(nj) == 'X') continue;
                    
                    // Calculate value to add (unless it's 'E' or 'S')
                    int addValue = 0;
                    if (board.get(ni).charAt(nj) != 'E' && board.get(ni).charAt(nj) != 'S') {
                        addValue = board.get(ni).charAt(nj) - '0';
                    }
                    
                    int newSum = dp[i][j] + addValue;
                    
                    // Update dp and count for destination
                    if (newSum > dp[ni][nj]) {
                        dp[ni][nj] = newSum;
                        count[ni][nj] = count[i][j];
                    } else if (newSum == dp[ni][nj]) {
                        count[ni][nj] = (count[ni][nj] + count[i][j]) % MOD;
                    }
                }
            }
        }
        
        // If top-left is unreachable
        if (dp[0][0] == -1) {
            return new int[]{0, 0};
        }
        
        return new int[]{dp[0][0], count[0][0]};
    }
}
