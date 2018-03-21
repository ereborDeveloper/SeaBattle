import java.util.ArrayList;
import java.util.List;

public class AI {
    private byte hWay = 1; //1 - вверх, -1 - вниз, 0 - нет перемещения
    private byte vWay = 0; //1 - вправо, -1 - влево, 0 - нет перемещения
    private boolean possibleGrid[][] = new boolean[10][10];
    private int x;
    private int y;
    private int decks = 0;
    private boolean isOnShip = false;
    private boolean horizontal = true;
    private int missed; //количество промахов может быть от 0 до 3
    private List<Check> shoots = new ArrayList<>();
    private String[] names = {"Лосось", "Василий", "Читер", "Бог"};

    AI() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                possibleGrid[i][j] = true;
            }
        }
    }

    public void showGrid() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(" " + possibleGrid[i][j] + " ");
            }
            System.out.println();
        }

    }

    public String computerChecker(int result) {
        //НАЧАЛО
        String ans = null;

        if (result == 0 || result == -1) {
            missed++;
            if (!isOnShip) {
                //пока не ляжем на корабль
                x = (int) (Math.random() * 10);
                y = (int) (Math.random() * 10);
                if (possibleGrid[x][y]) {
                    possibleGrid[x][y] = false;
                }//отметка о сделанном ходе
                else {
                    return computerChecker(0);
                }//если по клетке стреляли, идем в начало,
                shoots.add(new Check(x, y));
                ans = Singleton.getInstance().getGrid()[x][y];
                return ans; //ходим
            } else {
                //обработка для промаха после первого попадания
                if (missed == 1 && shoots.size() >= 1) {
                    if (hWay != 0) hWay *= -1;//меняем направление выстрела
                    if (vWay != 0) {
                        vWay *= -1;
                    }
                    x = shoots.get(0).x;
                    y = shoots.get(0).y;
                }
                //два выстрела полсе первого попадания
                if (missed == 2 && shoots.size() == 1) {
                    if (hWay != 0) {
                        vWay = 1;
                        hWay = 0;
                    }
                    if (vWay != 0) {
                        hWay = 1;
                        vWay = 0;
                    }
                    x = shoots.get(0).x;
                    y = shoots.get(0).y;
                }

            }
        }
        if (result == 2) {
            missed = 0;
            isOnShip = false;

            decks++;
            int a, b;
            int d;
            shoots.add(new Check(x, y));
            if (x != 0) possibleGrid[x - 1][y] = false;
            if ((x != 0) && (y != 0)) possibleGrid[x - 1][y - 1] = false;
            if (y != 0) possibleGrid[x][y - 1] = false;
            if (x != 9) possibleGrid[x + 1][y] = false;
            if (x != 9 && y != 9) possibleGrid[x + 1][y + 1] = false;
            if (y != 9) possibleGrid[x][y + 1] = false;
            if ((x != 0) && (y != 9)) possibleGrid[x - 1][y + 1] = false;
            if (x != 9 && y != 0) possibleGrid[x + 1][y - 1] = false;
            shoots.clear();
            decks = 0;
            return computerChecker(0);
        }
        //последняя координата, индекс с нуля!
        if (result == 1) {

            if (isOnShip == false) {
                missed = 0;
                shoots.clear();
                decks = 0;
            }
            isOnShip = true;
            //после попадания
            decks++;
            shoots.add(new Check(x, y));

            if (shoots.size() > 1) {
                x = shoots.get(shoots.size() - 1).x;
                y = shoots.get(shoots.size() - 1).y;
            }
            if (x != 0) possibleGrid[x - 1][y] = false;
            if ((x != 0) && (y != 0)) possibleGrid[x - 1][y - 1] = false;
            if (y != 0) possibleGrid[x][y - 1] = false;
            if (x != 9) possibleGrid[x + 1][y] = false;
            if (x != 9 && y != 9) possibleGrid[x + 1][y + 1] = false;
            if (y != 9) possibleGrid[x][y + 1] = false;
            if ((x != 0) && (y != 9)) possibleGrid[x - 1][y + 1] = false;
            if (x != 9 && y != 0) possibleGrid[x + 1][y - 1] = false;

        }
        if (y == 9 && hWay == 1) {
            hWay = -1;
            return computerChecker(0);
        }
        if (y == 0 && hWay == -1) {
            hWay = 1;
            return computerChecker(0);
        }
        if (x == 0 && vWay == -1) {
            vWay = 1;
            return computerChecker(0);
        }
        if (x == 9 && vWay == 1) {
            vWay = -1;
            return computerChecker(0);
        }
        x = x + vWay;
        y = y + hWay;
        ans = Singleton.getInstance().getGrid()[x][y];
        //координаты последнего элемента в листе
        return ans;
    }

    public String getName(int index) {
        return names[index];
    }

    private class Check {
        private int x;
        private int y;

        Check(int xx, int yy) {
            x = xx;
            y = yy;
        }
    }
}
