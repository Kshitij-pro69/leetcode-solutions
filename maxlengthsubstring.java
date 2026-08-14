class Solution {
    public int maximumLengthSubstring(String s) {
        int n = s.length();
        int maxLen = 0;
        int left = 0;
        int[] freq = new int[26]; // assuming lowercase letters
        
        for (int right = 0; right < n; right++) {
            char c = s.charAt(right);
            freq[c - 'a']++;
            
            // If current character appears more than twice, shrink window
            while (freq[c - 'a'] > 2) {
                char leftChar = s.charAt(left);
                freq[leftChar - 'a']--;
                left++;
            }
            
            maxLen = Math.max(maxLen, right - left + 1);
        }
        
        return maxLen;
    }
}