class Solution {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int total = m * n;
        
        // Effective shifts (k can be larger than total)
        k = k % total;
        if (k == 0) {
            // No shift needed
            return convertToList(grid);
        }
        
        // Flatten the grid into a 1D array
        int[] flat = new int[total];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                flat[i * n + j] = grid[i][j];
            }
        }
        
        // Create shifted array
        int[] shifted = new int[total];
        for (int i = 0; i < total; i++) {
            int newIndex = (i + k) % total;
            shifted[newIndex] = flat[i];
        }
        
        // Convert back to 2D grid
        int[][] result = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = shifted[i * n + j];
            }
        }
        
        return convertToList(result);
    }
    
    // Helper method to convert int[][] to List<List<Integer>>
    private List<List<Integer>> convertToList(int[][] grid) {
        List<List<Integer>> result = new ArrayList<>();
        for (int[] row : grid) {
            List<Integer> list = new ArrayList<>();
            for (int val : row) {
                list.add(val);
            }
            result.add(list);
        }
        return result;
    }
}