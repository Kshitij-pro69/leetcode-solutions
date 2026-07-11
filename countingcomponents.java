import java.util.*;

class Solution {
    public int countCompleteComponents(int n, int[][] edges) {
        // Step 1: Build the adjacency list and track the degree of each vertex
        List<List<Integer>> adj = new ArrayList<>();
        int[] degree = new int[n];
        
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            adj.get(u).add(v);
            adj.get(v).add(u);
            degree[u]++;
            degree[v]++;
        }
        
        boolean[] visited = new boolean[n];
        int completeComponentsCount = 0;
        
        // Step 2: Traverse each component using DFS
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                List<Integer> componentNodes = new ArrayList<>();
                dfs(i, adj, visited, componentNodes);
                
                // Step 3: Validate if the component is complete
                int componentSize = componentNodes.size();
                boolean isComplete = true;
                
                for (int node : componentNodes) {
                    if (degree[node] != componentSize - 1) {
                        isComplete = false;
                        break;
                    }
                }
                
                if (isComplete) {
                    completeComponentsCount++;
                }
            }
        }
        
        return completeComponentsCount;
    }
    
    private void dfs(int node, List<List<Integer>> adj, boolean[] visited, List<Integer> componentNodes) {
        visited[node] = true;
        componentNodes.add(node);
        
        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                dfs(neighbor, adj, visited, componentNodes);
            }
        }
    }
}
