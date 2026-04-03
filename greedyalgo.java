import java.util.*;

class Solution {
    public int maxWalls(int[] robots, int[] distance, int[] walls) {
        int n = robots.length;

        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> robots[a] - robots[b]);

        long[] sr = new long[n];
        long[] sd = new long[n];
        for (int i = 0; i < n; i++) {
            sr[i] = robots[idx[i]];
            sd[i] = distance[idx[i]];
        }

        long[] sortedWalls = new long[walls.length];
        for (int i = 0; i < walls.length; i++) sortedWalls[i] = walls[i];
        Arrays.sort(sortedWalls);

        // For each robot i, it fires either LEFT or RIGHT.
        // If it fires RIGHT: covers [sr[i], min(sr[i]+sd[i], sr[i+1]-1)]
        // If it fires LEFT:  covers [max(sr[i]-sd[i], sr[i-1]+1), sr[i]]
        //
        // Two adjacent robots firing TOWARD each other don't block each other
        // (robot i fires right, robot i+1 fires left — independent).
        // But if robot i fires right and robot i+1 ALSO fires right,
        // robot i blocks robot i+1's leftward shot — wait, they fire independently.
        //
        // KEY INSIGHT: The only blocking is:
        // - Robot i firing RIGHT is blocked by robot i+1 (nearest right neighbor)
        // - Robot i firing LEFT is blocked by robot i-1 (nearest left neighbor)
        // Each robot independently chooses direction.
        // So for each robot, its reachable interval is EITHER left-interval OR right-interval.
        // We want to pick one per robot to maximize union of walls covered.
        //
        // This is actually solvable: for each robot, both choices are fixed intervals.
        // We want max unique walls in chosen union.
        //
        // DP: after sorting robots, process left to right.
        // dp[i][0] = max walls if robot i fires LEFT
        // dp[i][1] = max walls if robot i fires RIGHT
        // 
        // Transition: walls covered by robot i's choice + walls covered by previous robots
        // But intervals can overlap between non-adjacent robots too, making this complex.
        //
        // CORRECT SIMPLER INSIGHT:
        // Since robots are sorted, robot i firing RIGHT covers up to min(sr[i]+sd[i], sr[i+1]-1).
        // Robot i firing LEFT covers down to max(sr[i]-sd[i], sr[i-1]+1).
        // These two intervals for robot i NEVER overlap with robot i+2 or beyond
        // (they're bounded by immediate neighbors).
        // So between any two consecutive robots, walls are covered by at most 2 robots:
        // the left one firing right, and the right one firing left.
        // We pick the BETTER one? No — both can fire independently!
        //
        // WAIT: both robot i (firing right) AND robot i+1 (firing left) can BOTH be chosen.
        // They don't block each other since they fire away from each other... 
        // Actually robot i fires RIGHT toward robot i+1, robot i+1 fires LEFT toward robot i.
        // They fire TOWARD each other — robot i's bullet goes right and hits robot i+1 before
        // passing, so it's blocked. But robot i+1's bullet goes left and hits robot i.
        // So in the GAP between robot i and robot i+1:
        // - robot i fires right: covers [sr[i], min(sr[i]+sd[i], sr[i+1]-1)]  ✓
        // - robot i+1 fires left: covers [max(sr[i+1]-sd[i+1], sr[i]+1), sr[i+1]] ✓
        // BOTH can cover walls in the gap simultaneously! They don't block each other
        // because robots are not destroyed by bullets.
        // So ALL walls in the gap reachable by EITHER robot are destroyed.
        // Each robot also covers its own position and beyond in chosen direction.
        //
        // So the problem reduces to: for each robot choose LEFT or RIGHT,
        // and count unique walls. The gap walls are additive from both sides.
        // The only conflict: robot i choosing RIGHT means it can't also fire LEFT.

        // For each gap between consecutive robots (and edges), walls can be hit from:
        // - left side robot firing right
        // - right side robot firing left
        // Both independently contribute. So all reachable gap walls are always destroyed
        // regardless of direction choice? NO — robot i might choose LEFT instead of RIGHT,
        // giving up the right-side gap coverage.

        // So this IS an optimization: for each robot choose direction to max total walls.
        // DP on sorted robots with states based on direction.

        int m = sortedWalls.length;

        // Precompute: walls in range [lo, hi]
        // Use binary search on sortedWalls

        // For robot i:
        //   leftInterval:  [max(sr[i]-sd[i], i>0 ? sr[i-1]+1 : 0), sr[i]]
        //   rightInterval: [sr[i], i<n-1 ? min(sr[i]+sd[i], sr[i+1]-1) : sr[i]+sd[i]]

        // Between robots i and i+1, the gap is (sr[i], sr[i+1]).
        // Walls in gap covered by robot i firing right: up to min(sr[i]+sd[i], sr[i+1]-1)
        // Walls in gap covered by robot i+1 firing left: down to max(sr[i+1]-sd[i+1], sr[i]+1)
        // These two ranges together cover all reachable gap walls.
        // If robot i fires LEFT, it loses its right coverage of this gap.
        // If robot i+1 fires RIGHT, it loses its left coverage of this gap.

        // dp[i][dir]: max walls considering robots 0..i, robot i fires in direction dir (0=left,1=right)
        // For robot i firing right: gains rightInterval[i] walls (unique from left side)
        //   but we must account for overlap with robot i-1's right interval if i-1 also fired right.
        // This overlap tracking is complex.

        // ALTERNATIVE: Since each robot's intervals are bounded by neighbors,
        // left and right intervals of robot i don't overlap with left/right intervals of robot i+2+.
        // Only adjacent robots' intervals can overlap.
        // Specifically: robot i's rightInterval and robot i+1's leftInterval can overlap
        // (both in the gap between i and i+1).

        // So total walls = sum of non-overlapping contributions.
        // Let's define for each robot i:
        //   L[i] = walls in leftInterval[i] that are NOT in rightInterval[i-1]
        //         (exclusive left walls of robot i)
        //   R[i] = walls in rightInterval[i] that are NOT in leftInterval[i+1]  
        //         (exclusive right walls of robot i)
        //   G[i] = walls in gap(i, i+1) reachable by BOTH robot i (right) and robot i+1 (left)
        //        = walls in intersection of rightInterval[i] and leftInterval[i+1]
        //   Gr[i] = walls in rightInterval[i] only (not reachable by robot i+1 from left)
        //   Gl[i+1] = walls in leftInterval[i+1] only (not reachable by robot i from right)

        // If robot i fires RIGHT and robot i+1 fires LEFT: gain Gr[i] + G[i] + Gl[i+1] in gap
        // If robot i fires RIGHT and robot i+1 fires RIGHT: gain Gr[i] + G[i] (robot i+1 loses Gl)
        //   wait no: robot i+1 fires right means it does NOT cover leftInterval[i+1]
        // If robot i fires LEFT and robot i+1 fires LEFT: gain Gl[i+1] + G[i] (robot i loses Gr)
        // If robot i fires LEFT and robot i+1 fires RIGHT: gain nothing from this gap

        // This gives a clean DP!

        // Let's define intervals precisely:
        long[] leftLo = new long[n], leftHi = new long[n];
        long[] rightLo = new long[n], rightHi = new long[n];
        for (int i = 0; i < n; i++) {
            leftLo[i] = (i > 0) ? Math.max(sr[i] - sd[i], sr[i-1] + 1) : sr[i] - sd[i];
            leftHi[i] = sr[i];
            rightLo[i] = sr[i];
            rightHi[i] = (i < n-1) ? Math.min(sr[i] + sd[i], sr[i+1] - 1) : sr[i] + sd[i];
        }

        // walls(lo, hi) = count of sortedWalls in [lo, hi]
        // dp[0] = robot fires left, dp[1] = robot fires right
        long[] dp = new long[2];
        dp[0] = countWalls(sortedWalls, leftLo[0], leftHi[0]);
        dp[1] = countWalls(sortedWalls, rightLo[0], rightHi[0]);

        for (int i = 1; i < n; i++) {
            long[] ndp = new long[2];

            // Walls exclusively in leftInterval[i] (below any right interval of i-1)
            // and exclusively in rightInterval[i]
            long liWalls = countWalls(sortedWalls, leftLo[i], leftHi[i]);
            long riWalls = countWalls(sortedWalls, rightLo[i], rightHi[i]);

            // Overlap between rightInterval[i-1] and leftInterval[i]:
            // rightInterval[i-1] = [sr[i-1], rightHi[i-1]]
            // leftInterval[i]    = [leftLo[i], sr[i]]
            // overlap = [max(sr[i-1], leftLo[i]), min(rightHi[i-1], sr[i])]
            //         = [leftLo[i], min(rightHi[i-1], sr[i])]  since leftLo[i] >= sr[i-1]
            long overlapLo = leftLo[i]; // >= sr[i-1]
            long overlapHi = Math.min(rightHi[i-1], sr[i]);
            long overlapWalls = (overlapLo <= overlapHi) ? countWalls(sortedWalls, overlapLo, overlapHi) : 0;

            // If robot i fires LEFT (ndp[0]):
            //   best previous + liWalls
            //   but if prev fired RIGHT, liWalls overlaps with prev's right interval by overlapWalls
            //   so actual new walls from left = liWalls - overlapWalls (if prev=right)
            //                                 = liWalls (if prev=left, no overlap since leftInterval[i-1] ends at sr[i-1] < leftLo[i])
            ndp[0] = Math.max(
                dp[0] + liWalls,           // prev fired left, no overlap
                dp[1] + liWalls - overlapWalls  // prev fired right, subtract overlap
            );

            // If robot i fires RIGHT (ndp[1]):
            //   best previous + riWalls (right interval starts at sr[i], no overlap with anything prior)
            //   riWalls has no overlap with prev robot's intervals (prev right ends at sr[i]-1 at most,
            //   prev left ends at sr[i-1] < sr[i])
            ndp[1] = Math.max(dp[0], dp[1]) + riWalls;

            dp = ndp;
        }

        return (int) Math.max(dp[0], dp[1]);
    }

    private long countWalls(long[] walls, long lo, long hi) {
        if (lo > hi) return 0;
        return upperBound(walls, hi) - lowerBound(walls, lo);
    }

    private int lowerBound(long[] arr, long target) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (arr[mid] < target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    private int upperBound(long[] arr, long target) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (arr[mid] <= target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}
