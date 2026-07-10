import java.util.*;

public class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        int maxVal = 0;
        for (int num : nums) {
            maxVal = Math.max(maxVal, num);
        }

        // Track which values are present in the nums array
        boolean[] present = new boolean[maxVal + 1];
        for (int num : nums) {
            present[num] = true;
        }

        // goRight[0][x] stores the largest value <= x + maxDiff present in nums
        // goLeft[0][x] stores the smallest value >= x - maxDiff present in nums
        int[][] goRight = new int[18][maxVal + 1];
        int[][] goLeft = new int[18][maxVal + 1];

        // Fill base cases (2^0 = 1 step) using two-pointer scans
        for (int i = 0; i <= maxVal; i++) {
            if (present[i]) {
                int targetRight = Math.min(maxVal, i + maxDiff);
                goRight[0][i] = lastPresentRight(present, targetRight);
            }
        }

        for (int i = maxVal; i >= 0; i--) {
            if (present[i]) {
                int targetLeft = Math.max(0, i - maxDiff);
                goLeft[0][i] = firstPresentLeft(present, targetLeft);
            }
        }

        // Build the binary lifting tables
        for (int j = 1; j < 18; j++) {
            for (int i = 0; i <= maxVal; i++) {
                if (present[i]) {
                    goRight[j][i] = goRight[j - 1][goRight[j - 1][i]];
                    goLeft[j][i] = goLeft[j - 1][goLeft[j - 1][i]];
                }
            }
        }

        int[] ans = new int[queries.length];
        for (int q = 0; q < queries.length; q++) {
            int u = queries[q][0];
            int v = queries[q][1];

            if (u == v) {
                ans[q] = 0;
                continue;
            }

            int start = nums[u];
            int target = nums[v];

            if (start == target) {
                ans[q] = 1; // Different nodes but same value -> 1 step apart
                continue;
            }

            if (start < target) {
                ans[q] = lift(start, target, goRight, true);
            } else {
                ans[q] = lift(start, target, goLeft, false);
            }
        }

        return ans;
    }

    // Helper to find the largest present value <= target
    private int lastPresentRight(boolean[] present, int target) {
        while (target >= 0 && !present[target]) {
            target--;
        }
        return target;
    }

    // Helper to find the smallest present value >= target
    private int firstPresentLeft(boolean[] present, int target) {
        while (target < present.length && !present[target]) {
            target++;
        }
        return target;
    }

    // Performs binary lifting to count the steps required
    private int lift(int start, int target, int[][] go, boolean movingUp) {
        int steps = 0;
        int curr = start;

        for (int j = 17; j >= 0; j--) {
            int nextNode = go[j][curr];
            if ((movingUp && nextNode < target) || (!movingUp && nextNode > target)) {
                if (nextNode == curr) {
                    return -1;
                }
                steps += (1 << j);
                curr = nextNode;
            }
        }

        int finalStep = go[0][curr];
        if ((movingUp && finalStep >= target) || (!movingUp && finalStep <= target)) {
            return steps + 1;
        }

        return -1;
    }
}