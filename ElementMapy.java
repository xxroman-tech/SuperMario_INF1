import java.util.Random;

/**
 * Trieda ElementyMapy predstavuje bloky mapy ktoré sú umiestnené na plátne a tvoria mapu.
 * 
 * @author Roman Lojko 
 * @version 20.12.2020
 */

public class ElementMapy {
    
    private Obdlznik objekt;
    private Random random;
    private int pozX;
    private int pozY;
    private int sirkaElementu;
    private int vyskaElementu;
    
    /**
     * Parametrický konštruktor ktorý vytvára inštanciu triedy obdĺžnik podľa 
     * parametrov prebratých z parametrov konštruktora.
     * 
     * @param x X pozícia Obdĺžnika na plátne
     * @param y Y pozícia Obdĺžnika na plátne
     * @param sirka Šírka obdĺžnika
     * @param vyska Výška obdĺžnika
     */
    public ElementMapy(int x, int y, int sirka, int vyska) {
        this.setSuradnicePreKoliziu(x, y);
        this.setRozmeryElementu(sirka, vyska);
        this.random = new Random();
        this.objekt = new Obdlznik();
        this.objekt.zmenPolohu(x, y);
        
        this.objekt.zmenFarbu("green");
        this.objekt.zmenStrany(sirka, vyska);
        
        this.nakresliElementMapy();
    }
    
    /**
     * Metóda ktorá skryje obdĺžnik na plátne.
     */
    public void skryElement() {
        this.objekt.skry();
    }
    
    /**
     * Metóda ktorá nastaví súradnice do atribútov z parametrov metódy.
     * 
     * @param x X pozícia obdĺžnika pre Koliziu
     * @param y Y pozícia obdĺžnika pre Koliziu
     */
    public void setSuradnicePreKoliziu(int x, int y) {
        this.pozX = x;
        this.pozY = y;
    }
    
    /**
     * Metóda ktorá nastaví atribúty rozmerovElementu podľa parametrov metódy.
     * 
     * @param sirka Šírka obdĺžnika pre Kolíziu
     * @param vyska Výška obdĺžnika pre Kolíziu
     */
    public void setRozmeryElementu(int sirka, int vyska) {
        this.sirkaElementu = sirka;
        this.vyskaElementu = vyska;
    }
    
    /**
     * Metóda ktorá zobrazí objekty(Obdĺžniky) na plátne.
     */
    public void nakresliElementMapy() {
        this.objekt.zobraz();
    }
    
    /**
     * Ohraničenie ElementuMapy pre triedu Kolízia. 
     * 
     * @return Kolizia inštancia triedy Kolízia
     */
    public Kolizia ohranicenieElementovMapy() {
        return new Kolizia(this.pozX, this.pozX + this.sirkaElementu, this.pozY, this.pozY + (this.vyskaElementu / 3));
    }
}
