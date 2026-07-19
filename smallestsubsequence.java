class Solution {
    public String smallestSubsequence(String s) {
        // Step 1: Count frequency of each character
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Step 2: Boolean array to keep track of characters already in result
        boolean[] inStack = new boolean[26];

        // Step 3: Stack to build the result
        StringBuilder stack = new StringBuilder();

        for (char c : s.toCharArray()) {
            int idx = c - 'a';
            freq[idx]--; // Decrease frequency as we process this character

            // If character is already in stack, skip it
            if (inStack[idx]) continue;

            // While stack is not empty, top character is greater than current,
            // and top character appears later in the string, we can remove it
            while (stack.length() > 0 && stack.charAt(stack.length() - 1) > c
                    && freq[stack.charAt(stack.length() - 1) - 'a'] > 0) {
                char removed = stack.charAt(stack.length() - 1);
                inStack[removed - 'a'] = false;
                stack.deleteCharAt(stack.length() - 1);
            }

            // Add current character to stack
            stack.append(c);
            inStack[idx] = true;
        }

        return stack.toString();
    }
}