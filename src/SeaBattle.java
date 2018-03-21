import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class SeaBattle {
    public static void main(String args[]) throws IOException {
        Game you = new Game();
        Game computer = new Game();
        AI PC = new AI();
        System.out.println("Инициализация...");
        Singleton.getInstance().setGrid();
        System.out.println("Здравствуйте! Меня зовут " + PC.getName(1) + ". Приятно познакомитьс и приятного проигрыша.");
        System.out.println("Игра началась");
        you.start();
        computer.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String in;
        int checkResult = 0;
        int humanCheck = 0;
        int a = 0;
        while (true) {
            try {
                do {
                    in = br.readLine();
                    in = in.toUpperCase();
                    if (!you.isexist(in)) {
                        throw new Exception();
                    }
                    humanCheck = you.check(in);
                    if (humanCheck == -1) System.out.println("Вы уже стреляли сюда");
                    if (humanCheck == 1) System.out.println("Хмм.. Почти потопили");
                    if (humanCheck == 2) System.out.println("Так его!");
                } while (humanCheck == -1 || humanCheck == 1 || humanCheck == 2);
                if (you.check(in) == 3) {
                    you.result();
                    System.out.println("Вы победили! Но Василий поробует отыграться");
                    break;
                }

                do {
                    System.out.println(PC.getName(1) + " думает...");
                    Thread.sleep(1000);
                    in = PC.computerChecker(checkResult);
                    System.out.println(in);
                    if (!computer.isexist(in)) {
                        throw new Exception();
                    }
                    checkResult = computer.check(in);
                    if (checkResult == 1) System.out.println(PC.getName(1) + ": сейчас я потоплю твой корабль!");
                    if (checkResult == 2) System.out.println("Ха-ха-ха");
                }
                while (checkResult == -1 || checkResult == 1 || checkResult == 2);
                a++;
                System.out.println(checkResult);
                if (checkResult == 3) {
                    computer.result();
                    System.out.println(PC.getName(1) + " победил! Ходов без перерыва: " + a);
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Неверный ввод");
            }
        }
    }
}