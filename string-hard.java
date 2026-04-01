class Solution {
    public String generateString(String str1, String str2) {
        int n = str1.length(), m = str2.length();
        int len = n + m - 1;
        char[] word = new char[len];
        Arrays.fill(word, 'a');
        
        // forced[i] = true means word[i] is fixed by some 'T' constraint
        boolean[] forced = new boolean[len];
        
        // Apply all 'T' constraints first
        for (int i = 0; i < n; i++) {
            if (str1.charAt(i) == 'T') {
                for (int j = 0; j < m; j++) {
                    if (forced[i + j] && word[i + j] != str2.charAt(j)) {
                        return ""; // conflict between two T constraints
                    }
                    word[i + j] = str2.charAt(j);
                    forced[i + j] = true;
                }
            }
        }
        
        // Apply 'F' constraints: window must NOT equal str2
        for (int i = 0; i < n; i++) {
            if (str1.charAt(i) == 'F') {
                // Check if current window matches str2
                boolean matches = true;
                for (int j = 0; j < m; j++) {
                    if (word[i + j] != str2.charAt(j)) {
                        matches = false;
                        break;
                    }
                }
                if (matches) {
                    // Find rightmost non-forced position to bump
                    boolean fixed = true;
                    for (int j = m - 1; j >= 0; j--) {
                        if (!forced[i + j] && word[i + j] < 'z') {
                            word[i + j]++;
                            fixed = false;
                            break;
                        }
                    }
                    if (fixed) return ""; // can't break the match
                }
            }
        }
        
        // Final validation: verify all constraints
        for (int i = 0; i < n; i++) {
            boolean matches = true;
            for (int j = 0; j < m; j++) {
                if (word[i + j] != str2.charAt(j)) {
                    matches = false;
                    break;
                }
            }
            if (str1.charAt(i) == 'T' && !matches) return "";
            if (str1.charAt(i) == 'F' && matches) return "";
        }
        
        return new String(word);
    }