class Solution {
    public int minimumPushes(String word) {
        int[] freq = new int[26];
        
        // Count frequency of each character
        for (char c : word.toCharArray()) {
            freq[c - 'a']++;
        }
        
        // Sort frequencies in descending order
        Integer[] sortedFreq = Arrays.stream(freq)
            .boxed()
            .sorted((a, b) -> b - a)
            .toArray(Integer[]::new);
        
        int pushes = 0;
        int keyPositions = 0;
        
        // Assign letters to key positions
        for (int count : sortedFreq) {
            if (count == 0) break;
            // Each key has 8 positions (keys 2-9)
            // First 8 letters get 1 push, next 8 get 2 pushes, etc.
            pushes += count * (keyPositions / 8 + 1);
            keyPositions++;
        }
        
        return pushes;
    }
}