public class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int n = nums.length;
        
        // Max value in nums is 1500, so any XOR result < 2048
        boolean[] hasPairXor = new boolean[2048];
        boolean[] hasTripletXor = new boolean[2048];
        
        // Step 1: Find all unique pairwise XOR values
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                hasPairXor[nums[i] ^ nums[j]] = true;
            }
        }
        
        // Step 2: Extract unique elements from nums to minimize duplicate iterations
        boolean[] hasNum = new boolean[2048];
        for (int num : nums) {
            hasNum[num] = true;
        }
        
        // Step 3: Combine each unique pair XOR with each unique number
        for (int p = 0; p < 2048; p++) {
            if (!hasPairXor[p]) continue;
            
            for (int x = 0; x < 2048; x++) {
                if (hasNum[x]) {
                    hasTripletXor[p ^ x] = true;
                }
            }
        }
        
        // Step 4: Count unique triplet XOR values
        int count = 0;
        for (int val = 0; val < 2048; val++) {
            if (hasTripletXor[val]) {
                count++;
            }
        }
        
        return count;
    }
}
