import java.util.*;

class Solution {

    // Helper class to store character index ranges for groups of contiguous '0's
    private static class Group {
        int start;
        int length;

        Group(int start, int length) {
            this.start = start;
            this.length = length;
        }
    }

    // Sparse Table for static Range Maximum Queries (RMQ)
    private static class SparseTable {
        private final int n;
        private final int[][] st;

        public SparseTable(int[] nums) {
            this.n = nums.length;
            if (n == 0) {
                this.st = new int[0][0];
                return;
            }
            int k = Integer.SIZE - Integer.numberOfLeadingZeros(n);
            this.st = new int[k][n];
            for (int j = 0; j < n; j++) {
                st[0][j] = nums[j];
            }
            for (int i = 1; i < k; i++) {
                for (int j = 0; j + (1 << i) <= n; j++) {
                    st[i][j] = Math.max(st[i - 1][j], st[i - 1][j + (1 << (i - 1))]);
                }
            }
        }

        public int query(int l, int r) {
            if (l > r || l < 0 || r >= n) return 0;
            int i = Integer.SIZE - Integer.numberOfLeadingZeros(r - l + 1) - 1;
            return Math.max(st[i][l], st[i][r - (1 << i) + 1]);
        }
    }

    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int ones = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') ones++;
        }

        // Group '0' blocks and record index mappings
        List<Group> zeroGroups = new ArrayList<>();
        int[] zeroGroupIndex = new int[n];
        Arrays.fill(zeroGroupIndex, -1);

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                if (i > 0 && s.charAt(i - 1) == '0') {
                    zeroGroups.get(zeroGroups.size() - 1).length++;
                } else {
                    zeroGroups.add(new Group(i, 1));
                }
                zeroGroupIndex[i] = zeroGroups.size() - 1;
            }
        }

        int m = zeroGroups.size();
        List<Integer> ans = new ArrayList<>(queries.length);

        // If less than 2 zero groups exist, no trade can merge two adjacent zero groups
        if (m < 2) {
            for (int i = 0; i < queries.length; i++) {
                ans.add(ones);
            }
            return ans;
        }

        // Merge lengths of adjacent zero groups
        int[] zeroMergeLengths = new int[m - 1];
        for (int i = 0; i < m - 1; i++) {
            zeroMergeLengths[i] = zeroGroups.get(i).length + zeroGroups.get(i + 1).length;
        }

        SparseTable st = new SparseTable(zeroMergeLengths);

        for (int q = 0; q < queries.length; q++) {
            int l = queries[q][0];
            int r = queries[q][1];

            int gL = zeroGroupIndex[l];
            int gR = zeroGroupIndex[r];

            int leftLen = (s.charAt(l) == '0') ? (zeroGroups.get(gL).length - (l - zeroGroups.get(gL).start)) : 0;
            int rightLen = (s.charAt(r) == '0') ? (r - zeroGroups.get(gR).start + 1) : 0;

            int activeSections = ones;

            // Case 1: Both endpoints lie inside two adjacent zero groups
            if (s.charAt(l) == '0' && s.charAt(r) == '0' && gL + 1 == gR) {
                activeSections = Math.max(activeSections, ones + leftLen + rightLen);
            }

            // Case 2: Maximize over fully contained adjacent zero groups inside s[l...r]
            int startGroup = (s.charAt(l) == '0') ? gL + 1 : nextGroup(zeroGroupIndex, l);
            int endGroup = (s.charAt(r) == '0') ? gR - 1 : prevGroup(zeroGroupIndex, r);

            if (startGroup <= endGroup - 1) {
                activeSections = Math.max(activeSections, ones + st.query(startGroup, endGroup - 1));
            }

            // Case 3: Partial left zero group merged with a full right neighbor
            int endBound = (s.charAt(r) == '0' ? gR : (prevGroup(zeroGroupIndex, r) + 1));
            if (s.charAt(l) == '0' && gL + 1 <= endBound - 1) {
                activeSections = Math.max(activeSections, ones + leftLen + zeroGroups.get(gL + 1).length);
            }

            // Case 4: Full left neighbor merged with a partial right zero group
            int startBound = (s.charAt(l) == '0' ? gL : (nextGroup(zeroGroupIndex, l) - 1));
            if (s.charAt(r) == '0' && gR - 1 >= startBound + 1) {
                activeSections = Math.max(activeSections, ones + rightLen + zeroGroups.get(gR - 1).length);
            }

            ans.add(activeSections);
        }

        return ans;
    }

    private int nextGroup(int[] zeroGroupIndex, int idx) {
        while (idx < zeroGroupIndex.length && zeroGroupIndex[idx] == -1) idx++;
        return idx < zeroGroupIndex.length ? zeroGroupIndex[idx] : Integer.MAX_VALUE / 2;
    }

    private int prevGroup(int[] zeroGroupIndex, int idx) {
        while (idx >= 0 && zeroGroupIndex[idx] == -1) idx--;
        return idx >= 0 ? zeroGroupIndex[idx] : -Integer.MAX_VALUE / 2;
    }
}