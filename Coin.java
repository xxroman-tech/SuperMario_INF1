import java.util.Random;

/**
 * Trieda Coin predstavuje point ktorý hráč zbiera, trieda vytvorí obrázok na určitej pozícii, ktorá 
 * príde ako parameter.
 * 
 * @author Roman Lojko 
 * @version 19.12.2020
 */

public class Coin {
    
    private Random random;
    private Obrazok coin;
    private int coinX;
    private int coinY;
    
    /**
     * Parametrický konštruktor
     * 
     * @param pozX X pozícia Coinu
     * @param pozXMax maximálna pozícia Coinu kde sa môže vygenerovať
     * @param pozY Y pozícia Coinu
     */
    public Coin(int pozX, int pozXMax, int pozY) {
        this.random = new Random();
        
        this.coinX = this.random.nextInt(pozX) + pozXMax;
        this.coinY = pozY;
        
        this.coin = new Obrazok("charakter/point.png");
        this.coin.zmenPolohu(this.coinX, this.coinY);
        this.coin.zobraz();
    }
    
    /**
     * Metóda pre vytvorenie inštancie triedy Kolízia pre kontrolu kolízie objektov.
     * 
     * @return Kolizia inštancia triedy Kolízia
     */
    public Kolizia ohranicenieCoinu() {
        return new Kolizia(this.coinX - 25, this.coinX + 25, this.coinY - 25, this.coinY + 25);
    }
    
    /**
     * Metóda pre kontrolu kolízie Coinu s hráčom pomocou triedy Kolízia.
     */
    public boolean jeVKolizii(Hrac other) {
        return this.ohranicenieCoinu().jeVKolizii(other.ohranicenieHraca());
    }
    
    /**
     * Metóda pre vymazanie obrázka reprezentujúceho túto triedu z plátna.
     */
    public void skryCoin() {
        this.coin.skry();
    }
}
