import java.util.ArrayList;
import java.util.List;

public class Ship {
    private List<String> position = new ArrayList<>();
    private boolean alive = true;
    private int deck;

    public Ship(int n, List<String> lst) {
        deck = n;
        position = lst;
        String c = null;
        switch (deck) {
            case (1):
                c = "ќдно";
                break;
            case (2):
                c = "ƒвух";
                break;
            case (3):
                c = "“рех";
                break;
            case (4):
                c = "„етырех";
                break;
        }

        //System.out.println(c+"палубный корабль расположен с €чейки "+position.get(0)+ " по "+position.get(position.size()-1));
    }

    public List<String> getPosition() {
        return (this.position);
    }

    public int delete(String x) {
        if (position.contains(x)) {
            position.remove(x);
            if (position.isEmpty()) return 1;
            return 2;
        } else return 0;
    }
}
