class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        // last[j] stores the largest index in word1 from which we can match word2[j...m-1] as a subsequence.
        int[] last = new int[m];
        
        int ptr2 = m - 1;
        for (int i = n - 1; i >= 0 && ptr2 >= 0; i--) {
            if (word1.charAt(i) == word2.charAt(ptr2)) {
                last[ptr2] = i;
                ptr2--;
            }
        }

        // If even without changes, suffix cannot be matched, set remaining to -1
        while (ptr2 >= 0) {
            last[ptr2] = -1;
            ptr2--;
        }

        int[] result = new int[m];
        boolean changed = false; // Tracks whether the 1-character change has been used
        
        ptr2 = 0;
        int i = 0;

        while (i < n && ptr2 < m) {
            // Case 1: Characters match
            if (word1.charAt(i) == word2.charAt(ptr2)) {
                result[ptr2] = i;
                ptr2++;
            } 
            // Case 2: Characters don't match, try using our single change
            else if (!changed) {
                // Check if the remainder of word2[ptr2+1...m-1] can still be matched in word1[i+1...n-1]
                boolean canFinishWithoutChange = (ptr2 == m - 1) || (last[ptr2 + 1] > i);
                
                if (canFinishWithoutChange) {
                    result[ptr2] = i;
                    changed = true; // Use up the single allowed change
                    ptr2++;
                }
            }

            i++;
        }

        // If we were unable to match all m characters of word2, return empty array
        if (ptr2 < m) {
            return new int[0];
        }

        return result;
    }
}