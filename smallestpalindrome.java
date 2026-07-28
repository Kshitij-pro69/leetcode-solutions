class Solution {
    public String smallestPalindrome(String s) {
        int n = s.length();
        int[] count = new int[26];
        
        // Count character frequencies
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        
        // Build the first half
        StringBuilder half = new StringBuilder();
        char middle = 0;
        
        for (int i = 0; i < 26; i++) {
            if (count[i] % 2 == 1) {
                middle = (char)('a' + i);
            }
            // Add half of the characters to the first half
            for (int j = 0; j < count[i] / 2; j++) {
                half.append((char)('a' + i));
            }
        }
        
        // Build the full palindrome: half + middle + reverse(half)
        String firstHalf = half.toString();
        String secondHalf = new StringBuilder(firstHalf).reverse().toString();
        
        if (middle != 0) {
            return firstHalf + middle + secondHalf;
        } else {
            return firstHalf + secondHalf;
        }
    }
}