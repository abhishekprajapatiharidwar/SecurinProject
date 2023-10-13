import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Read input from the user
        String s = new Scanner(System.in).next().toLowerCase();

        // Initialize variables
        int l = 0;
        boolean prev = false;
        int max = 0, cur = 0, temp, min = 0;
        boolean up = false;

        // Iterate through the input string
        for (int i = 0; i < s.length(); i++) {
            temp = 1 + s.charAt(i) - 'a';

            // Handle the first character differently
            if (i == 0) {
                prev = isVowel(s.charAt(i));
                cur -= temp;
            } else {
                // If the current character has the same vowel/consonant status as the previous,
                // adjust the position and depth.
                if (prev == isVowel(s.charAt(i))) {
                    l += 1;
                    cur += up ? 2 : -2;
                } else {
                    up ^= true; // Toggle the direction.
                }
                cur += up ? temp : -temp;
                prev = isVowel(s.charAt(i));
            }
            
            // Update the maximum and minimum depths, as well as the total length.
            max = Math.max(cur, max);
            min = Math.min(cur, min);
            l += temp;
        }
        
        // Calculate the size of the grid for drawing.
        int rows = max - min + 5;
        int cols = l + 4;
        char[][] result = new char[rows][cols];

        // Initialize the grid with spaces.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                result[i][j] = ' ';
        }

        // Set the starting position in the grid.
        int x = rows + min;
        int y = 2;
        up = false;
        prev = false;
        boolean added = false;

        // If the maximum depth is 0, add a human figure.
        if (max == 0) {
            addHuman(result, x, y);
            added = true;
        }

        // Calculate the target depth for adding the human figure.
        int target = rows + min - max - 1;

        // Iterate through the input string to draw the pattern in the grid.
        for (int i = 0; i < s.length(); i++) {
            temp = 1 + s.charAt(i) - 'a';

            // Handle the first character differently
            if (i == 0) {
                while (temp-- > 0) {
                    result[x++][y++] = '\\';
                }
                prev = isVowel(s.charAt(i));
            } else {
                if (prev == isVowel(s.charAt(i))) {
                    // Draw a segment with appropriate characters and direction.
                    if (up) {
                        result[x--][y] = '|';
                        result[x--][y] = '|';
                        while (temp-- > 0) {
                            result[x--][y++] = '/';
                        }
                    } else {
                        result[x++][y] = '|';
                        result[x++][y] = '|';
                        while (temp-- > 0) {
                            result[x++][y++] = '\\';
                        }
                    }
                } else {
                    if (up) {
                        x++;
                        while (temp-- > 0) {
                            result[x++][y++] = '\\';
                        }
                    } else {
                        x--;
                        while (temp-- > 0) {
                            result[x--][y++] = '/';
                        }
                    }
                    up ^= true;
                }
                prev = isVowel(s.charAt(i));
            }

            // Add the human figure at the target depth.
            if (!added && target == x) {
                addHuman(result, x, y);
                added = true;
                y++;
            }
        }

        // Print the result grid.
        for (char[] ar : result) {
            for (char c : ar)
                System.out.print(c);
            System.out.println();
        }
    }

    // Method to add the human figure in the grid.
    public static void addHuman(char[][] arr, int x, int y) {
        arr[x][y - 1] = '<';
        arr[x][y + 1] = '>';
        arr[x - 1][y] = '|';
        arr[x - 2][y - 1] = '/';
        arr[x - 2][y + 1] = '\\';
        arr[x - 3][y] = 'o';
    }

    // Method to check if a character is a vowel.
    public static boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }
}
