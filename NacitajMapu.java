import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Trieda NacitajMapu slúži pre načítanie dát z .txt súborov a poskytnutie dát pre triedu Hra.
 * 
 * @author Roman Lojko 
 * @version 19.12.2020
 */

public class NacitajMapu {
    
    private Random random;
    
    private ArrayList<Coin> zoznamCoinov;
    private ArrayList<ElementMapy> zoznamElementovMapy;
    
    private int enemakX;
    private int enemakY;
    
    /**
     * Parametrický konštruktor ktorý slúži na zavolanie jednotlivých metód pre načítanie dát
     * z .txt súborov. Vstupný parameter sa používa ako číslo mapy.
     * 
     * @param level Číslo levelu
     */
    public NacitajMapu(int level) throws IOException  {
        this.random = new Random();
        this.zoznamElementovMapy = new ArrayList<ElementMapy>();
        this.zoznamCoinov = new ArrayList<Coin>();
        this.nacitajDataMapy("levely/mapa_" + level + "/elementyMapy.txt");
        this.nacitajDataCoinov("levely/mapa_" + level + "/coiny.txt");
        this.nacitajDataEnemaka("levely/mapa_" + level + "/enemak.txt");
    }
    
    /**
     * Metóda pre načítanie dát ElementovMapy z elemtyMapy.txt a uloženie do ArrayListu,
     * ktorý je returnovaný pomocou metódy getMapu.
     * 
     * @param cestaKSuboru cesta k súboru pre File
     */
    public void nacitajDataMapy(String cestaKSuboru) throws IOException  {
        File subor = new File(cestaKSuboru);
        Scanner citac = new Scanner(subor);
        
        while (citac.hasNext()) {
            int pozX = citac.nextInt();
            int pozY = citac.nextInt();
            int sirka = citac.nextInt();
            int vyska = citac.nextInt();
            
            this.zoznamElementovMapy.add(new ElementMapy(pozX, pozY, sirka, vyska));
        }
        
        citac.close();
    }
    
    /**
     * Metóda pre načítanie dát Coinov z coiny.txt a uloženie do ArrayListu,
     * ktorý je returnovaný pomocou metódy getCoiny.
     * 
     * @param cestaKSuboru cesta k súboru pre File
     */
    public void nacitajDataCoinov(String cestaKSuboru) throws IOException  {
        File subor = new File(cestaKSuboru);
        Scanner citac = new Scanner(subor);
        
        while (citac.hasNext()) {
            int pozX = citac.nextInt();
            int pozXMax = citac.nextInt();
            int pozY = citac.nextInt();
            
            int pocet = this.random.nextInt(2) + 3;
        
            while (pocet > 0) {
                this.zoznamCoinov.add(new Coin(pozX, pozXMax, pozY));
                pocet--;
            }
        }
        
        citac.close();
    }
    
    /**
     * Metóda pre načítanie dát enemaka(jeho spawnovaciu pozíciu) z enemak.txt a 
     * uloženie do atribútov enemakX, enemakY, ktorý je returnovaný pomocou metód getXEnamaka,
     * getYEnemaka.
     * 
     * @param cestaKSuboru cesta k súboru pre File
     */
    public void nacitajDataEnemaka(String cestaKSuboru) throws IOException  {
        File subor = new File(cestaKSuboru);
        Scanner citac = new Scanner(subor);
        
        while (citac.hasNext()) {
            this.enemakX = citac.nextInt();
            this.enemakY = citac.nextInt();
        }
        
        citac.close();
    }
    
    /**
     * Metóda ktorá returnuje ArrayList zoznamElementovMapy cez vytvorenie kópie aby som 
     * neporušil zapúzdrenie.
     * 
     * @return kopia kópia ArrayListu zoznamElementovMapy
     */
    public ArrayList<ElementMapy> getMapu() {
        ArrayList<ElementMapy> kopia = new ArrayList<ElementMapy>(this.zoznamElementovMapy);
        return kopia;
    }
    
    /**
     * Metóda ktorá returnuje ArrayList zoznamCoinov cez vytvorenie kópie aby som 
     * neporušil zapúzdrenie.
     * 
     * @return kopia kópia ArrayListu zoznamElementovMapy
     */
    public ArrayList<Coin> getCoiny() {
        ArrayList<Coin> kopia = new ArrayList<Coin>(this.zoznamCoinov);
        return kopia;
    }
    
    /**
     * Metóda pre získanie X pozície Enemáka.
     */
    public int getXEnemaka() {
        return this.enemakX;
    }
    
    /**
     * Metóda pre získanie Y pozície Enemáka.
     */
    public int getYEnemaka() {
        return this.enemakY;
    }
}
