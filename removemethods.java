class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        // Build adjacency list for the graph
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        // Build reverse graph to check incoming edges
        List<List<Integer>> reverseGraph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            reverseGraph.add(new ArrayList<>());
        }
        
        for (int[] invocation : invocations) {
            int a = invocation[0];
            int b = invocation[1];
            graph.get(a).add(b);
            reverseGraph.get(b).add(a);
        }
        
        // Find all suspicious methods (reachable from k)
        boolean[] suspicious = new boolean[n];
        dfs(k, graph, suspicious);
        
        // Check if any non-suspicious method invokes a suspicious method
        boolean canRemove = true;
        for (int i = 0; i < n; i++) {
            if (!suspicious[i]) {
                for (int callee : graph.get(i)) {
                    if (suspicious[callee]) {
                        canRemove = false;
                        break;
                    }
                }
            }
            if (!canRemove) break;
        }
        
        // If can't remove all suspicious methods, return all methods
        if (!canRemove) {
            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                result.add(i);
            }
            return result;
        }
        
        // Otherwise, return all non-suspicious methods
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!suspicious[i]) {
                result.add(i);
            }
        }
        return result;
    }
    
    private void dfs(int node, List<List<Integer>> graph, boolean[] visited) {
        visited[node] = true;
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfs(neighbor, graph, visited);
            }
        }
    }
}