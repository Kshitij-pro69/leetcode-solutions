class Solution {
    public int smallestNumber(int n, int t) {
        // Start from n and keep checking until we find a valid number
        int current = n;
        while (true) {
            if (isDigitProductDivisible(current, t)) {
                return current;
            }
            current++;
        }
    }
    
    private boolean isDigitProductDivisible(int num, int t) {
        // Calculate product of digits
        int product = 1;
        int temp = num;
        
        // If number is 0, product is 0
        if (num == 0) {
            product = 0;
        } else {
            while (temp > 0) {
                int digit = temp % 10;
                product *= digit;
                temp /= 10;
            }
        }
        
        // Check if product is divisible by t
        return product % t == 0;
    }
}