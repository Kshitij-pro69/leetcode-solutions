class Solution {
    public String processStr(String s) {
        StringBuilder result = new StringBuilder();
        
        for (char c : s.toCharArray()) {
            if (Character.isLowerCase(c)) {
                // Append lowercase letter
                result.append(c);
            } else if (c == '*') {
                // Remove last character if exists
                if (result.length() > 0) {
                    result.deleteCharAt(result.length() - 1);
                }
            } else if (c == '#') {
                // Duplicate current result and append to itself
                String current = result.toString();
                result.append(current);
            } else if (c == '%') {
                // Reverse current result
                result.reverse();
            }
        }
        
        return result.toString();
    }
}