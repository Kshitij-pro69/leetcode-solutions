class Solution {
    public int numberOfSubstrings(String s) {
        int n = s.length();
        int[] count = new int[3]; // count of 'a', 'b', 'c'
        int left = 0;
        int result = 0;
        
        for (int right = 0; right < n; right++) {
            // Add current character to window
            count[s.charAt(right) - 'a']++;
            
            // Shrink window from left while we have all three characters
            while (count[0] > 0 && count[1] > 0 && count[2] > 0) {
                // All substrings starting from any index <= left and ending at 'right' are valid
                // There are (n - right) such substrings
                result += (n - right);
                
                // Remove leftmost character and move left pointer
                count[s.charAt(left) - 'a']--;
                left++;
            }
        }
        
        return result;
    }
}