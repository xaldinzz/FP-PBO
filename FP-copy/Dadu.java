import java.util.Random;

public class Dadu {
    private static Random rand = new Random();

    public static int lempar(int sisi) {
        return rand.nextInt(sisi) + 1;
    }
}