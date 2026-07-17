import java.util.Arrays;

public class Solution {
    public int[] gcdValues(int[] nums, long[] queries) {
        // Find the maximum value in nums to define our array bounds
        int maxNum = 0;
        for (int num : nums) {
            maxNum = Math.max(maxNum, num);
        }

        // countFreq[i] stores the frequency of number i in nums
        int[] countFreq = new int[maxNum + 1];
        for (int num : nums) {
            countFreq[num]++;
        }

        // countDivisors[i] stores how many numbers in nums are divisible by i
        long[] countDivisors = new long[maxNum + 1];
        for (int i = 1; i <= maxNum; i++) {
            for (int j = i; j <= maxNum; j += i) {
                countDivisors[i] += countFreq[j];
            }
        }

        // countGcdPair[i] will store the exact number of pairs with GCD equal to i
        long[] countGcdPair = new long[maxNum + 1];
        for (int i = maxNum; i >= 1; i--) {
            // Number of pairs formed by multiples of i
            long totalPairs = countDivisors[i] * (countDivisors[i] - 1) / 2;
            
            // Subtract pairs that have a strictly larger GCD (multiples of i)
            for (int j = 2 * i; j <= maxNum; j += i) {
                totalPairs -= countGcdPair[j];
            }
            countGcdPair[i] = totalPairs;
        }

        // Create a prefix sum array to map index ranges to GCD values
        long[] prefixCountGcdPair = new long[maxNum + 1];
        for (int i = 1; i <= maxNum; i++) {
            prefixCountGcdPair[i] = prefixCountGcdPair[i - 1] + countGcdPair[i];
        }

        // Process queries using binary search
        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            ans[i] = getNthGcdPair(queries[i], prefixCountGcdPair);
        }

        return ans;
    }

    // Binary search to find the GCD value corresponding to the query index
    private int getNthGcdPair(long query, long[] prefixCountGcdPair) {
        int low = 1;
        int high = prefixCountGcdPair.length - 1;
        int result = high;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (prefixCountGcdPair[mid] > query) {
                result = mid; // Possible candidate
                high = mid - 1; // Look for a smaller matching GCD
            } else {
                low = mid + 1;
            }
        }
        return result;
    }
}