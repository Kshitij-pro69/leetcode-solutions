import java.util.ArrayList;
import java.util.List;

class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        // Form augmented string
        String t = "1" + s + "1";
        
        List<Integer> ones = new ArrayList<>();
        List<Integer> zeros = new ArrayList<>();
        
        int n = t.length();
        int idx = 0;
        int initialOnes = 0;

        // Parse into alternating 1-blocks and 0-blocks
        while (idx < n) {
            int start1 = idx;
            while (idx < n && t.charAt(idx) == '1') {
                idx++;
            }
            ones.add(idx - start1);
            
            if (idx < n) {
                int start0 = idx;
                while (idx < n && t.charAt(idx) == '0') {
                    idx++;
                }
                zeros.add(idx - start0);
            }
        }
        
        // Count initial '1's in original string s
        for (char c : s.toCharArray()) {
            if (c == '1') {
                initialOnes++;
            }
        }
        
        int k = zeros.size(); // number of 0-blocks
        // If k < 2, there are no internal 1-blocks surrounded by 0s
        if (k < 2) {
            return initialOnes;
        }
        
        // Precompute prefix and suffix max of zeros lengths
        int[] prefMax = new int[k];
        int[] suffMax = new int[k];
        
        prefMax[0] = zeros.get(0);
        for (int i = 1; i < k; i++) {
            prefMax[i] = Math.max(prefMax[i - 1], zeros.get(i));
        }
        
        suffMax[k - 1] = zeros.get(k - 1);
        for (int i = k - 2; i >= 0; i--) {
            suffMax[i] = Math.max(suffMax[i + 1], zeros.get(i));
        }
        
        int maxActive = initialOnes;
        
        // Try picking each internal 1-block 1_i (where 1 <= i <= k - 1)
        for (int i = 1; i < k; i++) {
            int len1 = ones.get(i);
            int len0_left = zeros.get(i - 1);
            int len0_right = zeros.get(i);
            
            // Option 1: Flip the newly merged block (0_{i-1} + 1_i + 0_i)
            int candidate1 = initialOnes + len0_left + len0_right;
            maxActive = Math.max(maxActive, candidate1);
            
            // Option 2: Flip some other 0-block 0_j (j != i-1 and j != i)
            int maxOtherZero = 0;
            if (i - 2 >= 0) {
                maxOtherZero = Math.max(maxOtherZero, prefMax[i - 2]);
            }
            if (i + 1 < k) {
                maxOtherZero = Math.max(maxOtherZero, suffMax[i + 1]);
            }
            
            if (maxOtherZero > 0) {
                int candidate2 = initialOnes - len1 + maxOtherZero;
                maxActive = Math.max(maxActive, candidate2);
            }
        }
        
        return maxActive;
    }
}