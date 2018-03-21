import java.util.ArrayList;
import java.util.List;

public class Game {
    //
    private String[][] grid = new String[10][10];
    private int[][] possible_shoot = new int[10][10];
    private List<Ship> shiplist = new ArrayList<>(10);
    private int num = 0;
    private int ships = 0;

    public void start() {

        initialize();
        setShips();
        show();

    }

    private void show() {
        for (int j = 0; j < 10; j++) {
            if (j == 0) System.out.println("   1  2  3  4  5  6  7  8  9  10");
            System.out.print(grid[j][0].substring(0, 1) + " ");
            for (int i = 0; i < 10; i++) {
                if (possible_shoot[i][j] == 8) {
                    System.out.print(" X ");
                } else {
                    if (possible_shoot[i][j] == 7) {
                        System.out.print(" . ");
                    }/*else {
                    if (possible_shoot[i][j] != 0 && possible_shoot[i][j] != 9)
                        System.out.print(" " + possible_shoot[i][j] + " ");
                        //расскоментировать, чтобы увидеть местоположение кораблей
                    */ else {
                        System.out.print(" # ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private void initialize() {
        grid = Singleton.getInstance().getGrid();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                possible_shoot[i][j] = 0;
            }
        }
    }

    private void setShips() {
        ArrayList<String> position_list = new ArrayList<>();
        boolean horizontal = true;
        shiplist.clear();
        for (int i = 10; i > 0; i--) {
            position_list = new ArrayList<>();
            int n = 4;
            if (i < 10) n--;
            if (i < 8) n--;
            if (i < 5) n--;
            if (horizontal) {
                int x = 0;
                int y = 0;
                do {
                    x = (int) (Math.random() * (10 - n));
                    y = (int) (Math.random() * (9));
                } while (!isPossible(x, y, n, horizontal));
                for (int k = x; k < x + n; k++) {
                    position_list.add(grid[k][y]);
                }
                if (x + n != 9) {
                    possible_shoot[x + n][y] = 9;
                    if (y != 9) {
                        possible_shoot[x + n][y + 1] = 9;
                    }
                    if (y != 0) {
                        possible_shoot[x + n][y - 1] = 9;
                    }
                }
                if (x != 0) {
                    possible_shoot[x - 1][y] = 9;
                    if (y != 0) {
                        possible_shoot[x - 1][y - 1] = 9;
                    }
                    if (y != 9) {
                        possible_shoot[x - 1][y + 1] = 9;
                    }
                }
                for (int k = x; k < x + n; k++) {
                    possible_shoot[k][y] = n;
                }
                for (int k = x; k <= x + n; k++) {
                    if (y != 9) {
                        possible_shoot[k][y + 1] = 9;
                    }
                    if (y != 0) {
                        possible_shoot[k][y - 1] = 9;
                    }
                }
            } else {
                int y = (int) (Math.random() * (100 - n));
            }
            Ship sh = new Ship(n, position_list);
            shiplist.add(sh);
        }
    }

    private boolean isPossible(int a, int b, int n, boolean h) {
        if (h) {
            for (int i = a; i < a + n; i++) {
                if (possible_shoot[i][b] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private int row;
    private int column;

    public boolean isexist(String ab) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (grid[i][j].equals(ab)) {
                    row = i;
                    column = j;
                    return true;
                }
            }
        }
        return false;
    }

    //2 - выигрыш, 1 - какой-то результат, 0 - мимо, -1 - поле уже было использовано
    public int check(String n) {
        num++;
        int x = column;
        int y = row;
        byte type = 0;
        if (possible_shoot[x][y] != 7 && possible_shoot[x][y] != 8) {
            String tr = grid[x][y];
            int ans = 0;
            for (Ship ship : shiplist) {
                ans = ship.delete(tr);
                if (ans == 1) {
                    possible_shoot[x][y] = 8;
                    System.out.println("Убил");
                    shiplist.remove(ship);
                    type = 2;
                    break;
                }
                if (ans == 2) {
                    possible_shoot[x][y] = 8;
                    System.out.println("Попал");
                    type = 1;
                    break;
                }
                //Если местоположение совпало с ячейкой сетки, убираем у корабля, который содержит эту ячейку
                //в положении, которую надо задать, одну палубу. Если палуб ноль, убиваем внутри класса Ship.
                //Потом делаем проверку на то, мертв ли корабль, если мертв, пишем "убил". Удаляем из коллекции
            }
            if (ans == 0) {
                ships++;
                possible_shoot[x][y] = 7;
                System.out.println("Мимо");
                type = 0;
            }
            show();

            if (shiplist.isEmpty()) {
                int result = 100 - ships;
                System.out.println("Результат: " + result);
                return 3;
            }
        } else {
            return -1;
        }
        return type;
    }

    public void result() {
        System.out.println("Количество ходов - " + this.num);
    }
}
