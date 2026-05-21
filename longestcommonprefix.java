class Solution {
    public int longestCommonPrefix(int[] arr1, int[] arr2) {
        // Store all prefixes of arr1 in a set
        Set<Integer> prefixes = new HashSet<>();
        
        // For each number in arr1, add all its prefixes to the set
        for (int num : arr1) {
            while (num > 0) {
                prefixes.add(num);
                num /= 10;
            }
        }
        
        int maxLength = 0;
        
        // Check each number in arr2 against the prefixes set
        for (int num : arr2) {
            while (num > 0) {
                if (prefixes.contains(num)) {
                    maxLength = Math.max(maxLength, String.valueOf(num).length());
                    break; // Found the longest prefix for this number
                }
                num /= 10;
            }
        }
        
        return maxLength;
    }
}