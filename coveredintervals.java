class Solution {
    public int removeCoveredIntervals(int[][] intervals) {
        // Sort by start ascending, and if start equal, by end descending
        Arrays.sort(intervals, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1];
            }
            return a[0] - b[0];
        });
        
        int remaining = 0;
        int maxEnd = 0;
        
        for (int[] interval : intervals) {
            // If current interval's end is greater than maxEnd seen so far,
            // it's not covered by any previous interval
            if (interval[1] > maxEnd) {
                remaining++;
                maxEnd = interval[1];
            }
            // Otherwise, it's covered by some previous interval
        }
        
        return remaining;
    }
}