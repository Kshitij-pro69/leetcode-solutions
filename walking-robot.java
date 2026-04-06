class Solution {
    public int robotSim(int[] commands, int[][] obstacles) {
        // Direction arrays: North, East, South, West
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        
        // Store obstacles in a HashSet for O(1) lookup
        Set<String> obstacleSet = new HashSet<>();
        for (int[] obs : obstacles) {
            obstacleSet.add(obs[0] + "," + obs[1]);
        }
        
        int x = 0, y = 0;
        int dir = 0; // 0=North, 1=East, 2=South, 3=West
        int maxDist = 0;
        
        for (int cmd : commands) {
            if (cmd == -2) {
                // Turn left 90 degrees
                dir = (dir + 3) % 4;
            } else if (cmd == -1) {
                // Turn right 90 degrees
                dir = (dir + 1) % 4;
            } else {
                // Move forward k steps
                for (int i = 0; i < cmd; i++) {
                    int nx = x + dx[dir];
                    int ny = y + dy[dir];
                    
                    // Check if next position is an obstacle
                    if (!obstacleSet.contains(nx + "," + ny)) {
                        x = nx;
                        y = ny;
                        maxDist = Math.max(maxDist, x * x + y * y);
                    } else {
                        break; // Stop moving in this direction
                    }
                }
            }
        }
        
        return maxDist;
    }
}