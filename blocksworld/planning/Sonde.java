package blocksworld.planning;

/**
 * La classe Sonde sert à savoir le nombre de répétitions.
 */
public class Sonde {
    
    private int count;

    public Sonde() {
        this.count = 0;
    }

    public void add() {
        this.count += 1;
    }

    public int getCount() {
        return this.count;
    }

    public void reset() {
        this.count = 0;
    }



}
