import java.util.*;

class Solution {
    public List<Integer> findMissingElements(int[] nums) {
        // Find the minimum and maximum values in the array
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        
        // Create a set for O(1) lookups
        Set<Integer> numSet = new HashSet<>();
        
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
            numSet.add(num);
        }
        
        // Find all missing numbers in the range [min, max]
        List<Integer> missing = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            if (!numSet.contains(i)) {
                missing.add(i);
            }
        }
        
        return missing;
    }
}