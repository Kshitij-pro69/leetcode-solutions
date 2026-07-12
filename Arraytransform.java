class Solution {
    public int[] arrayRankTransform(int[] arr) {
        if (arr == null || arr.length == 0) {
            return arr;
        }
        
        // Create a copy of the array and sort it
        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        
        // Map to store rank for each unique value
        Map<Integer, Integer> rankMap = new HashMap<>();
        int rank = 1;
        
        // Assign ranks to unique values
        for (int i = 0; i < sorted.length; i++) {
            if (!rankMap.containsKey(sorted[i])) {
                rankMap.put(sorted[i], rank);
                rank++;
            }
        }
        
        // Replace each element with its rank
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = rankMap.get(arr[i]);
        }
        
        return result;
    }
}