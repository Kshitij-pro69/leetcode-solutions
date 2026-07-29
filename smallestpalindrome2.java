public class Solution {
    private static final long MAX_LIMIT = 1_000_001L;

    public String smallestPalindrome(String s, int k) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        int[] halfFreq = new int[26];
        int halfLen = 0;
        char midChar = 0;

        for (int i = 0; i < 26; i++) {
            halfFreq[i] = count[i] / 2;
            halfLen += halfFreq[i];
            if (count[i] % 2 != 0) {
                midChar = (char) ('a' + i);
            }
        }

        // Check if total distinct palindromes is less than k
        if (countPermutations(halfFreq, MAX_LIMIT) < k) {
            return "";
        }

        StringBuilder leftHalf = new StringBuilder();

        // Build the left half character by character
        for (int pos = 0; pos < halfLen; pos++) {
            for (int ch = 0; ch < 26; ch++) {
                if (halfFreq[ch] == 0) {
                    continue;
                }

                // Try placing character 'ch' at the current position
                halfFreq[ch]--;
                long ways = countPermutations(halfFreq, MAX_LIMIT);

                if (ways >= k) {
                    leftHalf.append((char) ('a' + ch));
                    break;
                } else {
                    k -= ways;
                    halfFreq[ch]++; // Backtrack and try next character
                }
            }
        }

        // Construct the full palindromic string
        StringBuilder result = new StringBuilder();
        result.append(leftHalf);
        if (midChar != 0) {
            result.append(midChar);
        }
        result.append(new StringBuilder(leftHalf).reverse());

        return result.toString();
    }

    // Counts multiset permutations: N! / (c1! * c2! * ... * c26!)
    private long countPermutations(int[] freq, long limit) {
        int total = 0;
        for (int f : freq) {
            total += f;
        }

        long res = 1;
        for (int f : freq) {
            if (f > 0) {
                res *= nCk(total, f, limit);
                if (res >= limit) return limit;
                total -= f;
            }
        }
        return res;
    }

    // Calculates n Choose k (nCk) capped at limit
    private long nCk(int n, int k, long limit) {
        if (k < 0 || k > n) return 0;
        if (k == 0 || k == n) return 1;
        k = Math.min(k, n - k);

        long res = 1;
        for (int i = 1; i <= k; i++) {
            res = res * (n - i + 1) / i;
            if (res > limit) return limit;
        }
        return res;
    }
}