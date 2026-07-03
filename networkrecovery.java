class Solution {
    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;
        
        // Build adjacency list
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], cost = edge[2];
            graph.get(u).add(new int[]{v, cost});
        }
        
        // Binary search on the answer
        int low = 0, high = 1000000000;
        int answer = -1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (hasValidPath(graph, online, n, k, mid)) {
                answer = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        return answer;
    }
    
    private boolean hasValidPath(List<List<int[]>> graph, boolean[] online, int n, long k, int minEdgeCost) {
        long[] dp = new long[n];
        Arrays.fill(dp, -1);
        long minTotal = dfs(0, graph, online, n, minEdgeCost, dp);
        return minTotal != Long.MAX_VALUE && minTotal <= k;
    }
    
    private long dfs(int node, List<List<int[]>> graph, boolean[] online, int n, int minEdgeCost, long[] dp) {
        if (node == n - 1) {
            return 0;
        }
        
        if (dp[node] != -1) {
            return dp[node];
        }
        
        long minTotal = Long.MAX_VALUE;
        
        for (int[] edge : graph.get(node)) {
            int next = edge[0];
            int cost = edge[1];
            
            // Edge must meet minimum cost requirement
            if (cost < minEdgeCost) continue;
            
            // Intermediate nodes must be online
            if (next != n - 1 && !online[next]) continue;
            
            long nextCost = dfs(next, graph, online, n, minEdgeCost, dp);
            if (nextCost != Long.MAX_VALUE) {
                long total = cost + nextCost;
                if (total < minTotal) {
                    minTotal = total;
                }
            }
        }
        
        dp[node] = minTotal;
        return minTotal;
    }
}