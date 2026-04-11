import java.util.*;

class Solution {
    public int minimumDistance(int[] nums) {
        Map<Integer, List<Integer>> map = new HashMap<>();

        // Step 1: Store indices for each value
        for (int i = 0; i < nums.length; i++) {
            map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }

        int minDist = Integer.MAX_VALUE;

        // Step 2: Process each value group
        for (List<Integer> list : map.values()) {
            if (list.size() < 3) continue;

            // Step 3: Sliding window of size 3
            for (int i = 0; i <= list.size() - 3; i++) {
                int dist = 2 * (list.get(i + 2) - list.get(i));
                minDist = Math.min(minDist, dist);
            }
        }

        return minDist == Integer.MAX_VALUE ? -1 : minDist;
    }
}
