import java.util.ArrayList;

/**
 * Trieda DisplejZivotov slúži pre zobrazenie stavu životov hráča pomocou triedy Zivot.
 * 
 * @author Roman Lojko 
 * @version 19.12.2020
 */

public class DisplejZivotov {
    
    private ArrayList<Zivot> zoznamZivotov;
    
    /**
     * Metóda pre pridanie nového objektu Zivot do ArrayListu zoznamZivotov s parametrami pozície
     * Zivota.
     * 
     * @param pocetZivotov Hráčov počet životov.
     */
    public DisplejZivotov(int pocetZivotov) {
        this.zoznamZivotov = new ArrayList<Zivot>();
        int pozXSrdca = 0;
        while (pocetZivotov > 0) {
            this.zoznamZivotov.add(new Zivot(75 + pozXSrdca, 75));
            pocetZivotov--;
            pozXSrdca += 50;
        }
    }
    
    /**
     * Metóda pre odstránenie Displeju z plátna pomocou metódy skryZivot v triede Zivot.
     */
    public void skryDisplej() {
        for (Zivot aktZiv : this.zoznamZivotov) {
            aktZiv.skryZivot();
        }
    }
}
