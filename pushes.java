class Solution {
    public int minimumPushes(String word) {
        int n = word.length();
        
        // Since all letters are distinct, each appears once
        // We have 8 keys (2-9)
        // To minimize pushes, distribute letters evenly across 8 keys
        
        int pushes = 0;
        int keyIndex = 0;
        int position = 1; // 1st position on key costs 1 push
        
        for (int i = 0; i < n; i++) {
            // Each letter gets assigned to the next key in round-robin fashion
            pushes += position;
            
            keyIndex++;
            if (keyIndex == 8) {
                keyIndex = 0;
                position++; // Move to next position on each key
            }
        }
        
        return pushes;
    }
}