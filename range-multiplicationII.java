import java.util.*;

class Solution {
    static final long MOD = 1_000_000_007;

    public int xorAfterQueries(int[] nums, int[][] queries) {
        int n = nums.length;
        int BLOCK = (int) Math.sqrt(n) + 1;

        long[] mult = new long[n];
        Arrays.fill(mult, 1L);

        // Use HashMap per small k — sparse, avoids huge 2D array
        @SuppressWarnings("unchecked")
        HashMap<Integer, Long>[] smallDiff = new HashMap[BLOCK + 1];
        for (int i = 1; i <= BLOCK; i++) smallDiff[i] = new HashMap<>();

        for (int[] q : queries) {
            int l = q[0], r = q[1], k = q[2];
            long v = q[3];

            if (k > BLOCK) {
                // Large k: at most sqrt(n) indices touched per query
                for (int idx = l; idx <= r; idx += k) {
                    mult[idx] = mult[idx] * v % MOD;
                }
            } else {
                // Small k: multiplicative difference array (sparse via HashMap)
                int last = l + ((r - l) / k) * k;
                int cancelAt = last + k;

                smallDiff[k].merge(l, v, (a, b) -> a * b % MOD);
                if (cancelAt < n) {
                    long inv = modPow(v, MOD - 2, MOD);
                    smallDiff[k].merge(cancelAt, inv, (a, b) -> a * b % MOD);
                }
            }
        }

        // Sweep each small k
        for (int k = 1; k <= BLOCK; k++) {
            if (smallDiff[k].isEmpty()) continue;
            for (int start = 0; start < k && start < n; start++) {
                long running = 1L;
                for (int i = start; i < n; i += k) {
                    Long delta = smallDiff[k].get(i);
                    if (delta != null) running = running * delta % MOD;
                    if (running != 1L) mult[i] = mult[i] * running % MOD;
                }
            }
        }

        int xor = 0;
        for (int i = 0; i < n; i++) {
            xor ^= (int)((long) nums[i] * mult[i] % MOD);
        }
        return xor;
    }

    static long modPow(long base, long exp, long mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) result = result * base % mod;
            base = base * base % mod;
            exp >>= 1;
        }
        return result;
    }
}
