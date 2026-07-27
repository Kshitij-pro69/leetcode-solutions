class Solution {
    public int maxProduct(int[] nums) {
        // Find the two largest numbers in the array
        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        
        for (int num : nums) {
            if (num > max1) {
                max2 = max1;
                max1 = num;
            } else if (num > max2) {
                max2 = num;
            }
        }
        
        // Return (largest - 1) * (second largest - 1)
        return (max1 - 1) * (max2 - 1);
    }
}