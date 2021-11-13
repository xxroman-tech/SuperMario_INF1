/**
 * Trieda Kolizia ktorá slúži na kontrolu kolízie objektov ktoré potrebujem napríklad pre zistenie či je 
 * objekt hráč alebo enemák na zemi. Alebo pre kontrolu kolízie hráča s coinom.
 * 
 * @author Roman Lojko 
 * @version 19.12.2020
 */

public class Kolizia {
    
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;
    
    /**
     * Parametrický konštruktor pre nastavnie hodnôt atribútov z parametrov.
     * 
     * @param minX minmálna hodnota x súradnice pre objekt
     * @param maxX maximálna hodnota x súradnice pre objekt
     * @param minY minmálna hodnota y súradnice pre objekt
     * @param maxY maximálna hodnota y súradnice pre objekt
     */
    public Kolizia(int minX, int maxX, int minY, int maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }
     /**
      * Metóda ktorá vracia boolean ak sa prekrývajú ohraničenia objektov
      * 
      * @return true/false
      */
    public boolean jeVKolizii(Kolizia other) {
        return this.maxX >= other.minX && this.minX <= other.maxX
                && this.maxY >= other.minY && this.minY <= other.maxY;
    }
    
     /**
      * Metóda ktorá vracia boolean ak sa prekrývajú ohraničenia objektov.
      * Špeciálna metóda pre gravitáciu. Pretože kontrolujem viacer kolízií naraz.
      * 
      * @return true/false
      */
    public boolean jeNaVrchuBloku(Kolizia other) {
        return this.maxX >= other.minX && this.minX <= other.maxX
                && this.maxY >= other.minY && this.minY <= other.maxY;
    }
    
     /**
      * Metóda ktorá vracia boolean ak sa prekrývajú ohraničenia objektov. 
      * Špeciálna metóda pre EnemakaAHraca. Pretože kontrolujem viacer kolízií naraz.
      * 
      * @return true/false
      */
    public boolean koliziaEnemakaAHraca(Kolizia other) {
        return this.maxX >= other.minX && this.minX <= other.maxX
                && this.maxY >= other.minY && this.minY <= other.maxY;
    }
}
