import javax.swing.JOptionPane;

/**
 * Trieda MenuHry slúži ako spúštač levelov, či už výberom poďla čísla alebo od prvého levelu.
 * 
 * @author Roman Lojko 
 * @version 19.12.2020
 */

public class MenuHry {
    
    private Hra hra;
    private Obrazok obr;
    private Manazer manazer;
    
    /**
     * Bezparametrický konštruktor. Vytvorí inštanciu triedy Manazer a pošle správu pre
     * spravovanie objektu. Nakreslí menu, teda obrázok, ktorý toto menu predstavuje.
     */
    public MenuHry() {
        this.manazer = new Manazer();
        this.manazer.spravujObjekt(this);
        this.nakresliMenu();
    }
    
    /**
     * Metóda spustiHru, pošle správu triede Hra ktorá vráti inštanciu tejto triedy. Následne
     * zavolá jej public metódu start s parametrom mapa, ktorý reprezentuje číslo mapy.
     * 
     * @param mapa Číslo mapy.
     */
    public void spustiHru(int mapa) {
        this.hra = Hra.getInstancia();
        this.hra.start(mapa);
    }
    
    /**
     * Metóda vyskoc slúži na reakciu na správu od triedy manazer. Vyčistí plátno pomocou metódy
     * skryHru. Zavolá metódu spustiHru s parametrom 1, pretože pri stlačení space chceme spustiť prvú mapu.
     * A triede manazer posle spravu prestanSpravovatObjekt.
     */
    public void vyskoc() {
        this.skryMenu();
        this.spustiHru(1);
        this.manazer.prestanSpravovatObjekt(this);
    }
    
    /**
     * Metóda slúži na reakciu na správu od triedy manazer. Pri stlačení klávesy V manazer pošle správu
     * spravovanému objektu. Reakciou na správu je možnosť vybrania mapy podľa čísla, ktoré získavam
     * z triedy JOptionPane pomocou showInputDialog. Následne spustím mapu poďla zadaného čísla.
     */
    public void stlaceneV() {
        String text = JOptionPane.showInputDialog(null, "Zadaj číslo mapy");
        int moznost = Integer.parseInt(text);
        if (moznost <= 0) {
            JOptionPane.showMessageDialog(null, "Mapy s číslom < ako 0 niesú!");
        } else if (moznost >= 3) {
            JOptionPane.showMessageDialog(null, "Hra má zatiaľ len 2 levely :/");
        } else {
            this.skryMenu();
            this.spustiHru(moznost);
            this.manazer.prestanSpravovatObjekt(this);
        }
    }
    
    /**
     * Metóda ktorá slúži na nakreslenie obrázku na plátno
     * pomocou triedy Obrazok.
     */
    private void nakresliMenu() {
        this.obr = new Obrazok("menuFotky/menu.png");
        this.obr.zmenPolohu(825, 425);
        this.obr.zobraz();
    }
    
    /**
     * Metóda ktorá vymaže obrázok predstavujúci menu
     * z plátna.
     */
    private void skryMenu() {
        this.obr.skry();
    }
}
