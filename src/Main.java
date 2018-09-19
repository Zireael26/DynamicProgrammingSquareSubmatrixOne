import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        maxOneSubmatrixTabulated(originalMatrix);

        int largestSubMatSize = maxOneSubmatrixMemoized(0, 0, originalMatrix,
                new int[originalMatrix.length][originalMatrix[0].length]);
        System.out.println("Largest matrix of size: " + overAllMax + " x " + overAllMax +
                " at (" + (overAllRow + 1) + ", " + (overAllCol + 1) + ")");

    }

    // the original 6 x 6 matrix
    static int[][] originalMatrix = {
            {1, 0, 1, 0, 0, 1},
            {1, 0, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 0, 0},
            {1, 1, 1, 1, 1, 1},
            {0, 0, 1, 1, 1, 0}
    };

    public static void maxOneSubmatrixTabulated(int[][] origMatrix) {
        int[][] tabulation = new int[origMatrix.length][origMatrix[0].length];
        int lastRow = origMatrix.length - 1;
        int lastCol = origMatrix[0].length - 1;

        // variables to store the details of the largest sub-matrix
        int overAllMax = 0;
        int overAllRow = -1;
        int overAllCol = -1;

        for (int r = lastRow; r >= 0; r--) {
            for (int c = lastCol; c >= 0; c--) {
                if (r == lastRow || c == lastCol) { // this covers first 3 cases of the case when we
                    // break the 2D array into 4 parts
                    tabulation[r][c] = origMatrix[r][c];
                } else {    // non-edge cases, we see min of f[x+1][y], f[x][y+1] and f[x+1][y+1] and add 1
                    if (origMatrix[r][c] != 0) {
                        tabulation[r][c] = 1 + Math.min(Math.min( //
                                tabulation[r][c + 1],
                                tabulation[r + 1][c]),
                                tabulation[r + 1][c + 1]);
                        if (overAllMax < tabulation[r][c]) {
                            overAllMax = tabulation[r][c];
                            overAllRow = r;
                            overAllCol = c;
                        }
                    } else {
                        tabulation[r][c] = 0;
                    }
                }
            }
        }

        for (int r = 0; r <= lastRow; r++) {
            for (int c = 0; c <= lastCol; c++) {
                System.out.print(tabulation[r][c] + "  ");
            }
            System.out.println();
        }

        System.out.println("Largest matrix of size: " + overAllMax + " x " + overAllMax +
                " at (" + (overAllRow + 1) + ", " + (overAllCol + 1) + ")");
    }

    private static int overAllMax = 0;
    private static int overAllRow = -1;
    private static int overAllCol = -1;

    public static int maxOneSubmatrixMemoized(int row, int col, int[][] originalMatrix, int[][] memArray) {
        int lastRow = originalMatrix.length - 1;
        int lastCol = originalMatrix[0].length - 1;

        if (row == lastRow || col == lastCol) { // handling the edges case
            return originalMatrix[row][col];
        }


        // saving recalculations by returning already calculated values
        if (memArray[row][col] != 0) {
            return memArray[row][col];
        }

        // if the original matrix has a 0 somewhere, return 0
        if (originalMatrix[row][col] == 0) {
            return 0;
        } else { // otherwise get faith values from vertical, horizontal and diagonal calls
            int maxSubVert = maxOneSubmatrixMemoized(row + 1, col, originalMatrix, memArray);
            int maxSubHoriz = maxOneSubmatrixMemoized(row, col + 1, originalMatrix, memArray);
            int maxSubDiagonal = maxOneSubmatrixMemoized(row + 1, col + 1, originalMatrix, memArray);

            // add 1 to their minimum and update results
            int result = 1 + Math.min(maxSubDiagonal, Math.min(maxSubVert, maxSubHoriz));

            if (result > overAllMax) {
                overAllMax = result;
                overAllRow = row;
                overAllCol = col;
            }

            // store in memoization array for faster execution
            memArray[row][col] = result;
            return result;
        }

    }
}
