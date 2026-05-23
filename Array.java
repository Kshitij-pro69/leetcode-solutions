class Solution {
    public boolean check(int[] nums) {
        int n = nums.length;
        int dropCount = 0;
        
        for (int i = 0; i < n; i++) {
            if (nums[i] > nums[(i + 1) % n]) {
                dropCount++;
            }
        }
        
        return dropCount <= 1;
    }
}