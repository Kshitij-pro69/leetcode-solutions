class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int n = nums.length;
        
        // Base cases for small array sizes
        if (n < 3) {
            return n;
        }
        
        // Find the number of bits needed to represent 'n'
        int bitLength = 32 - Integer.numberOfLeadingZeros(n);
        
        // The total number of unique XOR triplets is 2^(bitLength)
        return 1 << bitLength;
    }
}