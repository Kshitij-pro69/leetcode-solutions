class Solution {
    public String decodeCiphertext(String encodedText, int rows) {
        if (rows == 1 || encodedText.isEmpty()) return encodedText;
        
        int len = encodedText.length();
        int cols = len / rows;
        
        // Create the matrix and fill it row by row from encodedText
        char[][] matrix = new char[rows][cols];
        int idx = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = encodedText.charAt(idx++);
            }
        }
        
        // Read diagonally to get original text
        StringBuilder result = new StringBuilder();
        
        // Start from each column in the first row
        for (int startCol = 0; startCol < cols; startCol++) {
            int i = 0, j = startCol;
            while (i < rows && j < cols) {
                result.append(matrix[i][j]);
                i++;
                j++;
            }
        }
        
        // Remove trailing spaces
        String original = result.toString();
        int lastNonSpace = original.length() - 1;
        while (lastNonSpace >= 0 && original.charAt(lastNonSpace) == ' ') {
            lastNonSpace--;
        }
        
        return original.substring(0, lastNonSpace + 1);
    }
}