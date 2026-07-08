class Solution {
    public long sumAndMultiply(int n) {
        if (n == 0) {
            return 0L;
        }

        int[] digits = new int[10]; // max 10 digits since n <= 10^9
        int count = 0;
        int sum = 0;
        int temp = n;

        // Extract digits from right to left
        while (temp > 0) {
            int digit = temp % 10;
            if (digit != 0) {
                digits[count] = digit;
                sum += digit;
                count++;
            }
            temp /= 10;
        }

        // If no non-zero digits
        if (count == 0) {
            return 0L;
        }

        // Build x by reversing the digits (since we extracted from right to left)
        long x = 0L;
        for (int i = count - 1; i >= 0; i--) {
            x = x * 10 + digits[i];
        }

        return x * sum;
    }
}