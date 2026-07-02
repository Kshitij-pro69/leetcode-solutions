class Solution {
    public boolean findSafeWalk(List<List<Integer>> grid, int health) {
        int m = grid.size();
        int n = grid.get(0).size();
        
        // If starting cell is unsafe and we don't have enough health
        if (grid.get(0).get(0) >= health) {
            return false;
        }
        
        // Track minimum health cost to reach each cell
        int[][] minCost = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(minCost[i], Integer.MAX_VALUE);
        }
        
        // Deque for 0-1 BFS
        Deque<int[]> deque = new ArrayDeque<>();
        deque.offer(new int[]{0, 0});
        minCost[0][0] = grid.get(0).get(0);
        
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        while (!deque.isEmpty()) {
            int[] curr = deque.poll();
            int row = curr[0];
            int col = curr[1];
            int cost = minCost[row][col];
            
            if (row == m - 1 && col == n - 1) {
                return cost < health;
            }
            
            for (int[] dir : dirs) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n) {
                    int newCost = cost + grid.get(newRow).get(newCol);
                    
                    if (newCost < minCost[newRow][newCol] && newCost < health) {
                        minCost[newRow][newCol] = newCost;
                        if (grid.get(newRow).get(newCol) == 0) {
                            deque.offerFirst(new int[]{newRow, newCol});
                        } else {
                            deque.offerLast(new int[]{newRow, newCol});
                        }
                    }
                }
            }
        }
        
        return false;
    }
}