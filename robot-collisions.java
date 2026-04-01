class Solution {
    public List<Integer> survivedRobotsHealths(int[] positions, int[] healths, String directions) {
        int n = positions.length;
        
        // Create index array and sort by position
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) indices[i] = i;
        Arrays.sort(indices, (a, b) -> positions[a] - positions[b]);
        
        Stack<Integer> stack = new Stack<>(); // stores original indices of 'R' robots
        int[] finalHealths = new int[n];
        Arrays.fill(finalHealths, -1); // -1 means destroyed
        
        for (int idx : indices) {
            char dir = directions.charAt(idx);
            int health = healths[idx];
            
            if (dir == 'R') {
                // Push onto stack, may collide with future L robots
                stack.push(idx);
                
            } else { // dir == 'L'
                boolean survived = true;
                
                while (!stack.isEmpty() && survived) {
                    int topIdx = stack.peek();
                    int topHealth = healths[topIdx];
                    
                    if (topHealth > health) {
                        // R robot wins, L robot dies
                        healths[topIdx]--;  // R loses 1 health
                        survived = false;
                        
                    } else if (topHealth < health) {
                        // L robot wins, R robot dies
                        stack.pop();
                        health--;           // L loses 1 health
                        
                    } else {
                        // Both die (equal health)
                        stack.pop();
                        survived = false;
                    }
                }
                
                if (survived) {
                    // L robot survived all collisions
                    finalHealths[idx] = health;
                }
            }
        }
        
        // Remaining R robots in stack all survived
        while (!stack.isEmpty()) {
            int idx = stack.pop();
            finalHealths[idx] = healths[idx];
        }
        
        // Collect results in original order
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (finalHealths[i] != -1) {
                result.add(finalHealths[i]);
            }
        }
        
        return result;
    }
}
