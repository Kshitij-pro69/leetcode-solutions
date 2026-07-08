class Solution {
    private static final int MOD = 1_000_000_007;
    
    public int[] sumAndMultiply(String s, int[][] queries) {
        int n = s.length();
        int q = queries.length;
        int[] ans = new int[q];
        
        // Store prefix information for non-zero digits
        long[] prefixVal = new long[n + 1]; // Concatenated value of non-zero digits
        int[] prefixSum = new int[n + 1];   // Sum of non-zero digits
        int[] prefixCount = new int[n + 1]; // Count of non-zero digits
        
        // Precompute powers of 10 for quick concatenation
        long[] pow10 = new long[n + 1];
        pow10[0] = 1;
        for (int i = 1; i <= n; i++) {
            pow10[i] = (pow10[i - 1] * 10) % MOD;
        }
        
        // Build prefix arrays
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            int digit = c - '0';
            
            prefixCount[i + 1] = prefixCount[i];
            prefixSum[i + 1] = prefixSum[i];
            prefixVal[i + 1] = prefixVal[i];
            
            if (digit != 0) {
                prefixCount[i + 1]++;
                prefixSum[i + 1] = (prefixSum[i] + digit) % MOD;
                // Append digit to the concatenated number
                prefixVal[i + 1] = (prefixVal[i] * 10 + digit) % MOD;
            }
        }
        
        // Process each query
        for (int i = 0; i < q; i++) {
            int l = queries[i][0];
            int r = queries[i][1];
            
            // Get the number of non-zero digits in the range
            int countInRange = prefixCount[r + 1] - prefixCount[l];
            
            if (countInRange == 0) {
                ans[i] = 0;
                continue;
            }
            
            // Get the sum of digits in the range
            int sumInRange = (prefixSum[r + 1] - prefixSum[l] + MOD) % MOD;
            
            // Get the concatenated value in the range
            // The value in the range is: prefixVal[r+1] - prefixVal[l] * 10^(count in range)
            long valInRange = (prefixVal[r + 1] - (prefixVal[l] * pow10[countInRange]) % MOD + MOD) % MOD;
            
            ans[i] = (int)((valInRange * sumInRange) % MOD);
        }
        
        return ans;
    }
}