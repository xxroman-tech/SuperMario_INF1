import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * Hlavná trieda ktorá spúšta celú hru. Kombinuje všetky triedy dokopy pre výslednú hru.
 * 
 * @author Roman Lojko
 * @version 20.12.2020
 */

public class Hra {
   
    private ArrayList<Coin> zoznamCoinov;
    private ArrayList<ElementMapy> zoznamElementovMapy;
    private ArrayList<Integer> skore;
    private Random random;
    private Manazer manazer;
    private Hrac hrac;
    private Enemak enemak;
    private ElementMapy mapa;
    private NacitajMapu nacitajMapu;
    private MenuHry menu;
    private DisplejZivotov displejZivotov;
    private CelkoveSkore skoreNaKonciHry;
    
    private int smerHraca;
    private boolean hracJeNaZemi;
    private int zivotyHraca;
    private int skoreHraca;
    private int zabityEnemaci;
    private boolean hracVyhral;
    
    private int pocetVygenerovanychCoinov = 0;
    
    private static Hra instancia;
    private int cisloMapy;
    
    /**
     * Súkromný bezparametrický konštruktor ktorý nám slúži na to aby sme nevedli vytvoriť
     * inštanciu cez tento konštruktor.
     */
    private Hra() { }
    
    /**
     * Metóda Singleton ktorá slúži na vytvorenie práve jednej inštancie triedy Hra
     * 
     * @return instacia Atribút triedy Hra
     */
    public static Hra getInstancia() {
        if (Hra.instancia == null) {
            Hra.instancia = new Hra();
        }
        return Hra.instancia;
    }
    
    /**
     * Metóda ktorá spustí hru tak že vytvorí inštancie pre jednotlivé triedy a nastaví 
     * potrebné atribúty ako skoreHraca, zivotyHrac. Kontroluje či hráč prešiel celú hru pomocou
     * podmienky. Nakreslí level, displejZivotov, coiny, hráča, enemáka, mapu a na konci
     * zavolá metódu spustiHru.
     * 
     * @param cisloMapy Pre vybranie mapy pomocou triedy NacitajMapu ktorej je tento parameter
     *                  poslaný.
     */
    public void start(int cisloMapy) {
        this.skore = new ArrayList<Integer>();
        if (cisloMapy == 3) {
            this.manazer.prestanSpravovatObjekt(this);
            try {
                this.skoreNaKonciHry.spocitajSkore("levely\\celkoveSkore.txt");
            } catch (Exception e) { }
            JOptionPane.showMessageDialog(null, "Prešiel si moju hru!\nTvoje skore: " + this.skoreNaKonciHry.getSkore());
            this.vymazDataSkore();
            this.prepniSaDoMenu();
        } else {
        
            try {
                this.nacitajMapu = new NacitajMapu(cisloMapy);
            } catch (Exception e) { }
            this.cisloMapy = cisloMapy;
        
            this.zivotyHraca = 3;
            this.skoreHraca = 0;
            this.hracVyhral = false;
            this.zabityEnemaci = 0;
            this.hrac = new Hrac(this.zivotyHraca, this.skoreHraca);
            this.enemak = new Enemak(this.nacitajMapu.getXEnemaka(), this.nacitajMapu.getYEnemaka());
            this.manazer = new Manazer();
            this.random = new Random();
            this.zoznamCoinov = new ArrayList<Coin>();
            this.zoznamElementovMapy = new ArrayList<ElementMapy>();
            this.displejZivotov = new DisplejZivotov(this.zivotyHraca);
            this.skoreNaKonciHry = new CelkoveSkore();
        
            this.nakresliLevel();
            this.nakresliCoiny();
        
            this.pocetVygenerovanychCoinov = this.zoznamCoinov.size();
        
            this.spustiHru();
        }
    }
    
    /**
     * Metóda tik ktorá slúźi pre prekreslovanie objektov na plátne po každom
     * poslaní správy tik od triedy Manazer. Kontroluje taktiež stav hry.
     */
    public void tik() {
        this.jeHracMimoObrazovky();
        this.jeEnemakMimoObrazovky();
        
        this.hrac.nakresliHraca();
        this.enemak.nakresliEnemaka();
        this.hracZobralCoin();
        
        this.zautocilHracNaEnemaka();
        
        if (this.hracJeNaZemi()) {
            if (this.smerHraca == 3) {
                this.hrac.skoc();
            }
        } else {
            this.hrac.hracSpadni();
            this.smerHraca = 1;
        }
        
        if (this.enemakJeNaZemi()) {
            this.enemak.presunEnemakaDoPrava();
        } else {
            this.enemak.enemakSpadni();
        }
        
        if (this.zautocilHracNaEnemaka()) {
            //System.out.println("Hrac zabil enemaka. Skore +1");
            this.zabityEnemaci++;
            this.vymazEnemaka();
        }
        
        if (this.enemakZautocilNaHraca()) {
            this.vymazHraca();
        }
        
        if (this.jeKoniec()) {
            this.manazer.prestanSpravovatObjekt(this);
            if (!this.hracVyhral) {
                int moznost = JOptionPane.showConfirmDialog(null, "Došli ti životy\nChceš opakovať level?");
                if (moznost == 0) {
                    this.pokracujNaNovejMape(this.cisloMapy);
                } else {
                    this.vymazDataSkore();
                    this.prepniSaDoMenu();
                }
            }
            if (this.hracVyhral) {
                int celkoveSkore = this.skoreHraca + this.zabityEnemaci;
                int moznost = JOptionPane.showConfirmDialog(null, "Vyhral si! Skore: " + celkoveSkore + "\nChceš pokračovať do ďalšieho levelu?");
                if (moznost == 0) {
                    this.skoreNaKonciHry.pridajSkoreDoZoznamu(celkoveSkore);
                    try {
                        this.skoreNaKonciHry.ulozCelkoveSkore();
                    } catch (Exception e) { }
                    this.cisloMapy++;
                    this.pokracujNaNovejMape(this.cisloMapy);
                } else {
                    this.vymazDataSkore();
                    this.prepniSaDoMenu();
                }
            }
        }
    }
    
    /**
     * Metóda ktorá povolí manazerovi spravovať tento objekt, pre ovladáne objektov a 
     * metódu tik.
     */
    public void spustiHru() {
        this.manazer.spravujObjekt(this);
        this.tik();
    }
    
    /**
     * Metóda ktorá nakreslí level na plátno z načítaných údajov z
     * triedy NacitajMapu a uloží ich do zoznamu Elementov Mapy.
     */
    public void nakresliLevel() {
        this.zoznamElementovMapy = this.nacitajMapu.getMapu();
    }
    
    /**
     * Metóda ktorá kontroluje či je hráč v kolízii s objektami samotnej mapy.
     * Ak áno pošle true. Toto sa vyhodnocuje v triede Hra.
     * 
     * @return true/false Hrač je na zemi alebo nieje.
     */
    private boolean hracJeNaZemi() {
        for (ElementMapy aktElement : this.zoznamElementovMapy) {
            if (this.hrac.jeNaVrchuBloku(aktElement)) {
                return true;
            }
        } 
        return false;
    }
    
    /**
     * Metóda ktorá kontroluje či je Y súradnica hráča mimo obrazovky hry.
     */
    private void jeHracMimoObrazovky() {
        if (this.hrac.getPozY() > 1650) {
            this.hrac = null;
            this.zivotyHraca--;
            this.hrac = new Hrac(this.zivotyHraca, this.skoreHraca);
            this.aktualizujDisplejZivotov();
            //System.out.println("Zivoty Hraca : " + this.zivotyHraca);
        }
    }
    
    /**
     * Kontroluje či sa hráč nachádza v útočnom ohraničení enemáka. Ak áno, vracia true.
     * 
     * @return true/false Enemak zaútočil na hráča alebo nie.
     */
    private boolean enemakZautocilNaHraca() {
        return this.enemak.utocnaKoliziaEnemaka(this.hrac);
    }
    
    /**
     * Metóda ktorá vymaže hráča ak bol zabitý alebo padol mimo mapy, alebo 
     * pri vytvorení novej mapy.
     */
    private void vymazHraca() {
        this.hrac.skryHraca();
        this.hrac = null;
        this.zivotyHraca--;
        this.hrac = new Hrac(this.zivotyHraca, this.skoreHraca);
        this.aktualizujDisplejZivotov();
        //System.out.println("Hraca zabil enemak.");
        //System.out.println("Zivoty Hraca : " + this.zivotyHraca);
    }
    
    /**
     * Metóda ktorá kontroluje či je enemák v kolízii s objektami samotnej mapy.
     * Ak áno pošle true. Toto sa vyhodnocuje v triede Hra.
     * 
     * @return true/false Hrač je na zemi alebo nieje.
     */
    private boolean enemakJeNaZemi() {
        for (ElementMapy aktElement : this.zoznamElementovMapy) {
            if (this.enemak.enemakJeNaVrchuBloku(aktElement)) {
                return true;
            }
        } 
        return false;
    }
    
    /**
     * Metóda ktorá kontroluje či je Y súradnica enemáka mimo obrazovky hry.
     */
    private void jeEnemakMimoObrazovky() {
        if (this.enemak.getPozY() > 1650) {
            this.enemak = null;
            this.enemak = new Enemak(this.nacitajMapu.getXEnemaka(), this.nacitajMapu.getYEnemaka());
        }
    }
    
    /**
     * Metóda ktorá vymaže enemáka ak bol zabitý alebo padol mimo mapy, alebo 
     * pri vytvorení novej mapy.
     */
    private void vymazEnemaka() {
        this.enemak.skryEnemaka();
        this.enemak = null;
        this.enemak = new Enemak(this.nacitajMapu.getXEnemaka(), this.nacitajMapu.getYEnemaka());
    }
    
    /**
     * Kontroluje či sa enemák nachádza v útočnom ohraničení Hráča. Ak áno, vracia true.
     * 
     * @return true/false Enemak zaútočil na hráča alebo nie.
     */
    private boolean zautocilHracNaEnemaka() {
        return this.hrac.utocnaKoliziaHraca(this.enemak);
    }
    
    /**
     * Metóda ktorá načíta coiny z triedy NacitajMapu a uloží ich do 
     * zozonamu Coinov.
     */
    private void nakresliCoiny() {
        this.zoznamCoinov = this.nacitajMapu.getCoiny();
    }
    
    /**
     * Kontrola či hráč vošiel do ohraničenia coinu pomocou triedy Kolizia.
     */
    private void hracZobralCoin() {
        for (Coin aktCoin : this.zoznamCoinov) {
            if (this.hrac.jeVKolizii(aktCoin)) {
                this.skoreHraca++;
                this.zoznamCoinov.remove(aktCoin);
                aktCoin.skryCoin();
                //System.out.println("Skore Hraca : " + this.skoreHraca);
            }
        }
    }
    
    /**
     * Metóda ktorá aktualizuje displej aby zobrazoval nové poslané hodnoty
     * zivotov hráča.
     */
    private void aktualizujDisplejZivotov() {
        this.displejZivotov.skryDisplej();
        this.displejZivotov = null;
        this.displejZivotov = new DisplejZivotov(this.zivotyHraca);
    }
    
    /**
     * Metóda ktorá slúži ako reakcia na správu od triedy Manazer pri stlačení pravej šípky.
     * Zmení smer hráča na 1 a posunie ho doprava pomocou metódy v triede Hrac.
     */
    public void pohybVpravo() {
        this.hrac.presunHracaDoPrava();
        this.smerHraca = 1;
    }
    
    /**
     * Metóda ktorá slúži ako reakcia na správu od triedy Manazer pri stlačení lavej šípky.
     * Zmení smer hráča na 2 a posunie ho dolava pomocou metódy v triede Hrac.
     */
    public void pohybVlavo() {
        this.hrac.presunHracaDoLava();
        this.smerHraca = 2;
    }
    
    /**
     * Metóda ktorá slúži ako reakcia na správu od triedy Manazer pri stlačení medzerníka.
     * Zmení smer hráča na 3. Toto s vyhodnotí v metóde tik a pošle správu triede Hrac, skoc!
     */
    public void vyskoc() {
        this.smerHraca = 3;
    }
    
    /**
     * Metóda ktorá slúži ako reakcia na správu od triedy Manazer pri stlačení Esc.
     * Zastaví hru a opýta sa hráča hry či chce pokračovať ďalej alebo či chce ukončiť levevl.
     */
    public void stopniHru() {
        this.manazer.prestanSpravovatObjekt(this);
        int moznost = JOptionPane.showConfirmDialog(null, "Stlačil si Esc\nChceš ukončiť hru ?");
        if (moznost == 0) {
            this.vymazDataSkore();
            this.prepniSaDoMenu();
        } else {
            this.manazer.spravujObjekt(this);
        }
    }
    
    /***
     * Metóda ktorá pošle správu triede Hrac aby bol obrázok hráča správne natočení po 
     * pohybe hráča daným smerom.
     */
    private void hracSaNepohybuje() {
        this.hrac.hracStoji(this.smerHraca);
    }
    
    /**
     * Metóda ktorá slúźi na zistenie či už hráč skončil level a to tým spôsobom
     * že zistí či sa skoreHraca rovná počtuVygenerovanychCoinov.
     * 
     * @return true/false
     */
    private boolean pozbieralHracVsetkyCoiny() {
        if (this.skoreHraca == this.pocetVygenerovanychCoinov) {
            return true;
        }
        return false;
    }
    
    /**
     * Kontrolu či je koniec hry a vracia true alebo false.
     *
     * @param true/false Pre koniec hry.
     */
    private boolean jeKoniec() {
        int celkoveSkore = this.skoreHraca + this.zabityEnemaci;
        
        if (this.zivotyHraca != 0) {
            if (this.pozbieralHracVsetkyCoiny()) {
                //System.out.println("Hrac pozbieral vsetko coiny a jeho skore je : " + celkoveSkore);
                this.hracVyhral = true;
                return true;
            }
            return false;
        } else {
            //System.out.println("Dosli ti zivoty :/ Tvoje skore : " + celkoveSkore);
            this.hracVyhral = false;
            return true;
        }
    }
    
    /**
     * Metóda ktorá nastavý inštanciu tejto triedy na null a vytvorí 
     * inštanciu triedyMenuHry.
     */
    private void prepniSaDoMenu() {
        this.hrac.skryHraca();
        this.enemak.skryEnemaka();
        this.skryCoiny();
        this.skryMapu();
        Hra.instancia = null;
        this.menu = new MenuHry();
    }
    
    /**
     * Metóda ktorá vymaže coiny z plátna a vyčistí zoznamCoinov
     */
    private void skryCoiny() {
        for (Coin aktCoin : this.zoznamCoinov) {
            aktCoin.skryCoin();
        }
        this.zoznamCoinov.clear();
    }
    
    /**
     * Metóda ktorá vymaže mapu z plátna a vyčistí zoznamElementovMapy
     */
    private void skryMapu() {
        for (ElementMapy aktElement : this.zoznamElementovMapy) {
            aktElement.skryElement();
        }
        this.zoznamElementovMapy.clear();
    }
    
    /**
     * Metóda ktorá vyčistí plátno a zavolá metóda start s novým 
     * číslom mapy.
     */
    private void pokracujNaNovejMape(int cisloMapy) {
        this.skryCoiny();
        this.hrac.skryHraca();
        this.enemak.skryEnemaka();
        this.displejZivotov.skryDisplej();
        this.skryMapu();
        this.start(cisloMapy);
    }
    
    /**
     * Metóda ktorá vymaže dáta uložené pre výpočet celkového skóre po prejdení hry.
     */
    private void vymazDataSkore() {
        try {
            this.skoreNaKonciHry.vymazData();
        } catch (Exception e) { }
    }
}
