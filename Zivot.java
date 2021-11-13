/**
 * Trieda vytvára obrázok ktorý bude použitý v triede DisplejovZivotov.
 * 
 * @author Roman Lojko 
 * @version 19.12.2020
 */

public class Zivot {
    
    private Obrazok obr;
    
    /**
     * Parametrický konštruktor, ktorý vytvorí obrázok reprezentujúci život hráča na pozícii načítanej
     * z parametrov konštruktora.
     * 
     * @param x Pozícia X obrázku.
     * @param y Pozícia Y obrázku.
     */
    public Zivot(int x, int y) {
        this.obr = new Obrazok("charakter/srdce.png");
        this.obr.zmenPolohu(x, y);
        this.obr.zobraz();
    }
    
    /**
     * Metóda pre odstránenie obrázka z plátna.
     */
    public void skryZivot() {
        this.obr.skry();
    }
}
