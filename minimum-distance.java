class Solution {
    public int minimumDistance(int[] nums) {
        int n = nums.length;
        int minDist = Integer.MAX_VALUE;
        
        // For each possible value from 1 to n
        for (int val = 1; val <= n; val++) {
            // Collect indices where nums[i] == val
            List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (nums[i] == val) {
                    indices.add(i);
                }
            }
            
            // Need at least 3 occurrences
            if (indices.size() >= 3) {
                // We only need to check consecutive triples to minimize distance
                // Because for sorted indices, the smallest distance is 2*(last - first) 
                // among the smallest possible (first, last) pair
                // Actually, we should check all combinations of 3 consecutive indices
                for (int j = 0; j <= indices.size() - 3; j++) {
                    int i = indices.get(j);
                    int k = indices.get(j + 2);
                    int dist = 2 * (k - i);
                    minDist = Math.min(minDist, dist);
                }
            }
        }
        
        return minDist == Integer.MAX_VALUE ? -1 : minDist;
    }
}
