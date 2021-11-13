/**
 * Trieda Enemak predstavuje objekt riadený počítačom a snaží sa zabrániť hráćovi vyhrať.
 * 
 * @author Roman Lojko 
 * @version 19.12.2020
 */

public class Enemak {
    
    private Obrazok obr;
    private int pozX;
    private int pozY;
    
    private static final int SIRKA_ENEMAKA = 40;
    private static final int VYSKA_ENEMAKA = 74;
    private static final int RYCHLOST_ENEMAKA = 2;
    private int rychlostPadania = 0;
    private static final int GRAVITACIA = 1;
        
    /**
     * Parametrický konštruktor
     * 
     * @param pozX X pozícia Enemaka
     * @param pozY Y pozícia Enemaka
     */
    public Enemak(int pozX, int pozY) {
        this.pozX = pozX;
        this.pozY = pozY;
        this.obr = new Obrazok("charakter/enemak.png");
        this.obr.zmenPolohu(this.pozX, this.pozY);
    }
    
    /**
     * Metóda pre zobrazenie obrázku predstavujúceho Enemáka.
     */
    public void nakresliEnemaka() {
        this.obr.zobraz();
    }
    
    /**
     * Metóda pre skrytie obrázku predstavujúceho Enemáka.
     */
    public void skryEnemaka() {
        this.obr.skry();
    }
    
    /**
     * Metóda ktorá vracia Y pozíciu Enemáka na mape.
     * 
     * @return pozY Y pozícia Enemáka
     */
    public int getPozY() {
        return this.pozY + (this.VYSKA_ENEMAKA / 2);
    }
    
    /**
     * Metóda ktorá predstavuje gravitáciu a teda pád Objektu. Je zavolaná v triede Hra v metóde tik.
     */
    public void enemakSpadni() {
        this.obr.zmenPolohu(this.pozX, this.pozY + this.rychlostPadania);
        this.rychlostPadania += this.GRAVITACIA;
        this.pozY += this.rychlostPadania;
    }
    
    /**
     * Metóda pre posúvanie enemáka doprava pomocou rýchlosti enemáka.
     */
    public void presunEnemakaDoPrava() {
        this.pozX += RYCHLOST_ENEMAKA;
        this.obr.zmenPolohu(this.pozX, this.pozY);
    }
    
    /**
     * Ohraničenie enemáka pre triedu Kolízia. Toto je klasické ohraničenie kde je zabraný celý objekt.
     * 
     * @return Kolizia inštancia triedy Kolízia
     */
    public Kolizia ohranicenieEnemaka() {
        return new Kolizia(this.pozX - (this.SIRKA_ENEMAKA / 2), this.pozX + (this.SIRKA_ENEMAKA / 2), this.pozY - (this.VYSKA_ENEMAKA / 2), this.pozY + (this.VYSKA_ENEMAKA / 2));
    }
    
    /**
     * Ohraničenie enemáka pre triedu Kolízia. Toto je ohranićenie pre obranu a teda sú hitboxy umiestnené na hlave enemáka 
     * aby došlo pri dotyku útočného ohraničenia hráča k zničeniu enemáka.
     * 
     * @return Kolizia inštancia triedy Kolízia
     */
    public Kolizia ohranicenieEnemakaPreObranu() {
        return new Kolizia(this.pozX - (this.SIRKA_ENEMAKA / 2), this.pozX + (this.SIRKA_ENEMAKA / 2), this.pozY - (this.VYSKA_ENEMAKA / 2), this.pozY - 10);
    }
    
    /**
     * Ohraničenie enemáka pre triedu Kolízia. Toto je ohranićenie pre útok a teda pokial sa hráč dostane do tohto ohraničenia, tak 
     * ho nemák zabije.
     * 
     * @return Kolizia inštancia triedy Kolízia
     */
    public Kolizia ohranicenieEnemakaPreUtok() {
        return new Kolizia(this.pozX - (this.SIRKA_ENEMAKA / 2), this.pozX + (this.SIRKA_ENEMAKA / 2), this.pozY, this.pozY + (this.VYSKA_ENEMAKA));
    }
    
    /**
     * Kotrola kolízie Enemáka s hráčom
     * 
     * @return True/False z triedy Kolizia 
     */
    public boolean utocnaKoliziaEnemaka(Hrac other) {
        return this.ohranicenieEnemakaPreUtok().koliziaEnemakaAHraca(other.ohranicenieHracaPreUtok());
    }
    
    /**
     * Kotrola kolízie Enemáka elemnetomMapy. Aby na neho prestala pôsobiť 
     * gravitácia keď vojde do ohraničenia elementovMapy.
     * 
     * @return True/False z triedy Kolizia 
     */
    public boolean enemakJeNaVrchuBloku(ElementMapy other) {
        return this.ohranicenieEnemaka().jeNaVrchuBloku(other.ohranicenieElementovMapy());
    }
}
