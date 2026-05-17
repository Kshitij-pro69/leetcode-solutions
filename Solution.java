public class Solution {
    public boolean canReach(int[] arr, int start) {
        // To avoid infinite loops and revisiting indices
        boolean[] visited = new boolean[arr.length];
        return dfs(arr, start, visited);
    }
    
    private boolean dfs(int[] arr, int index, boolean[] visited) {
        // If out of bounds or already visited, return false
        if (index < 0 || index >= arr.length || visited[index]) {
            return false;
        }
        
        // If we found a zero, return true
        if (arr[index] == 0) {
            return true;
        }
        
        // Mark current index as visited
        visited[index] = true;
        
        // Try jumping forward and backward
        int forward = index + arr[index];
        int backward = index - arr[index];
        
        return dfs(arr, forward, visited) || dfs(arr, backward, visited);
    }
} jumpGame {
    
}
