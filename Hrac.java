/**
 * Trieda Hrac predstavuje objekt riadený hráčom hry, ktorý sa snaží získať
 * všetky vygenerované Coiny.
 * 
 * @author Roman Lojko 
 * @version 20.12.2020
 */

public class Hrac {
    
    private Obrazok obr;
    private int pozX = 100;
    private int pozY = 735;
    
    private static final int SIRKA_HRACA = 10;
    private static final int VYSKA_HRACA = 30;
    private static final int RYCHLOST_HRACA = 6;
    private int rychlostPadania = 0;
    private static final int GRAVITACIA = 1;
    private static final int SILA_SKOKU = -16;
    
    private int skore;
    private int zivoty;
    
    
    /**
     * Parametrický konśtruktor, ktorý vytvára inśtanciu obrázku a nastabuje skore
     * a počet životov z paramterov konštruktora.
     * 
     * @param pocetZivotov Počet životov hráča v kole
     * @param skore Skore hráča v kole
     */
    public Hrac(int pocetZivotov, int skore) {
        this.obr = new Obrazok("charakter/char_stoji_vpravo.png");
        this.obr.zmenPolohu(this.pozX, this.pozY);
        this.skore = skore;
        this.zivoty = pocetZivotov;
    }
    
    /**
     * Metóda ktorá vracia pozíciu objektu na Y osi pre zisťovanie
     * či je mimo obrazovky.
     * 
     * @return pozY + VYSKAHRACA
     */
    public int getPozY() {
        return this.pozY + this.VYSKA_HRACA;
    }
    
    /**
     * Ohraničenie hraca pre triedu Kolízia. Toto je klasické ohraničenie kde je zabraný celý objekt.
     * 
     * @return Kolizia inštancia triedy Kolízia
     */
    public Kolizia ohranicenieHraca() {
        return new Kolizia(this.pozX - this.SIRKA_HRACA, this.pozX + this.SIRKA_HRACA, this.pozY + (this.VYSKA_HRACA - 5), this.pozY + this.VYSKA_HRACA);
    }
    
    /**
     * Ohraničenie hraca pre triedu Kolízia. Toto je ohranicenie pre útok,
     * takže sú hitboxy na úrovni nôh objektu.
     * 
     * @return Kolizia inštancia triedy Kolízia
     */
    public Kolizia ohranicenieHracaPreUtok() {
        return new Kolizia(this.pozX - this.SIRKA_HRACA, this.pozX + this.SIRKA_HRACA, this.pozY + (this.VYSKA_HRACA - 2), this.pozY + this.VYSKA_HRACA);
    }
    
    /**
     * Kotrola kolízie Hráča s coinom.
     * 
     * @return True/False z triedy Kolizia 
     */
    public boolean jeVKolizii(Coin other) {
        return this.ohranicenieHraca().jeVKolizii(other.ohranicenieCoinu());
    }
    
    /**
     * Kotrola kolízie Hráča s nemákom pri útoku.
     * 
     * @return True/False z triedy Kolizia 
     */
    public boolean utocnaKoliziaHraca(Enemak other) {
        return this.ohranicenieHraca().jeVKolizii(other.ohranicenieEnemakaPreObranu());
    }
    
    /**
     * Kotrola kolízie Hráča s elemenamyMapy, pre gravitáciu.
     * 
     * @return True/False z triedy Kolizia 
     */
    public boolean jeNaVrchuBloku(ElementMapy other) {
        return this.ohranicenieHraca().jeNaVrchuBloku(other.ohranicenieElementovMapy());
    }
    
    /**
     * Metóda ktorá zobrazí obrázok, ktorý predstavuje tento objekt.
     */
    public void nakresliHraca() {
        this.obr.zobraz();
    }
    
    /**
     * Skryje obrázok, ktorý predstavuje tento objekt.
     */
    public void skryHraca() {
        this.obr.skry();
    }
    
    /**
     * Metóda ktorá je volaná v triede Hra, v metóde tik aby hráč padal a teda
     * simuloval gravitáciu pokial nenastane kolízia s objektom ElementyMapy.
     */
    public void hracSpadni() {
        this.obr.zmenPolohu(this.pozX, this.pozY + this.rychlostPadania);
        this.rychlostPadania += this.GRAVITACIA;
        this.pozY += this.rychlostPadania;
    }
    
    /**
     * Metóda ktorá presunie hráča o silu skoku pomocou metódy hracSpadni.
     */
    public void skoc() {
        this.rychlostPadania = this.SILA_SKOKU;
        this.hracSpadni();
    }
    
    /**
     * Metóda hradStoji slúźi na správne otočenie obrázku popohybe hráča určitým smerom.
     * 
     * @param smer smerHráča ktorý mal naposledy
     */
    public void hracStoji(int smer) {
        if (smer == 1) {
            this.obr.zmenObrazok("charakter/char_stoji_vpravo.png");
            this.obr.zmenPolohu(this.pozX, this.pozY);  
        } else if (smer == 2) {
            this.obr.zmenObrazok("charakter/char_stoji_vlavo.png");
            this.obr.zmenPolohu(this.pozX, this.pozY);  
        }
    }
    
    /**
     * Metóda ktorá slúži na zmenu polohy doprava obrázka o rýchlosť hráča.
     */
    public void presunHracaDoPrava() {
        this.obr.zmenObrazok("charakter/char_bezi_vpravo.png");
        this.pozX += RYCHLOST_HRACA;
        this.obr.zmenPolohu(this.pozX, this.pozY);
    }
    
    /**
     * Metóda ktorá slúži na zmenu polohy dolava obrázka o rýchlosť hráča.
     */
    public void presunHracaDoLava() {
        this.obr.zmenObrazok("charakter/char_bezi_vlavo.png");
        this.pozX -= RYCHLOST_HRACA;
        this.obr.zmenPolohu(this.pozX, this.pozY);
    }
}
