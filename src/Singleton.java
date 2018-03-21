public class Singleton {
    private static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    private static String str = "ÀÁÂÃÄÅÆÇÈÊ";
    private String[][] grid = new String[10][10];

    public void setGrid() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                j++;
                String el = str.substring(i, i + 1) + j;
                j--;
                grid[i][j] = el;
            }
        }
    }

    public String[][] getGrid() {
        return grid;
    }
}
