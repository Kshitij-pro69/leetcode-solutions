class Solution {
    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> result = new ArrayList<>();
        
        // Try all possible lengths
        for (int len = 1; len <= 9; len++) {
            // Try all possible starting digits for this length
            for (int start = 1; start <= 10 - len; start++) {
                int num = 0;
                
                // Build the sequential number
                for (int i = 0; i < len; i++) {
                    num = num * 10 + (start + i);
                }
                
                if (num >= low && num <= high) {
                    result.add(num);
                }
            }
        }
        
        return result;
    }
}