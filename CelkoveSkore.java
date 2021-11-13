import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Táto trieda slúži na ukladanie skóre do celkoveSkore.txt po prejdení levelu a pre
 * následné načítanie týchto dát a ich sčítanie pre vypís celkového skóre po prejdení
 * všetkých levelov.
 * 
 * @author Roman Lojko
 * @version 20.12.2020
 */

public class CelkoveSkore {
    
    private ArrayList<Integer> skore;
    private int celkoveSkore;
    
    /**
     * Konštruktor ktorý vytvorí inštanciu triedy ArrayList a načíta celkoveSkore zo súboru kvôli
     * tomu aby tam boli hodnoty zo všetých máp.
     */
    public CelkoveSkore() {
        this.skore = new ArrayList<Integer>();
        try {
            this.nacitajZoSuboru("levely/celkoveSkore.txt");
        } catch (Exception e) { }
    }
    
    /**
     * Metóda ktorá pridá skore z parametru konštruktora do zozonamu skore.
     * 
     * @param cislo Hodnota skore Hráča
     */
    public void pridajSkoreDoZoznamu(int cislo) {
        this.skore.add(cislo);
    }
    
    /**
     * Uloží celkové skóre do súboru .txt.
     */
    public void ulozCelkoveSkore() throws IOException {
        File subor = new File("levely/celkoveSkore.txt");
        PrintWriter zapisovac = new PrintWriter(subor);
        if (this.skore !=  null) {
            if (!this.skore.isEmpty()) {
                for (Integer aktInt : this.skore) {
                    zapisovac.println(aktInt);
                }
            } else {
                System.out.println("Nemam co ulozit pretoze zoznam je prazdny.");
            }
        } else {
            System.out.println("Zoznam sa nenasiel. Pred zapisom do suboru vytvorte zoznam.");
        }
        
        zapisovac.close();
    }
    
    /**
     * Načíta hodnoty zo súboru celkoveSkore.txt a uloží ich do zoznamu skore.
     * 
     * @param cestaKSuboru
     */
    public void nacitajZoSuboru(String cestkaKSuboru) throws IOException {
        File subor = new File(cestkaKSuboru);
        Scanner citac = new Scanner(subor);
        int skorePoKole = 0;
        
        if (this.skore.isEmpty()) {
            while (citac.hasNext()) {
                skorePoKole = citac.nextInt();
                this.skore.add(skorePoKole);
            }
        }
        
        citac.close();
    }
    
    /**
     * Metóda ktorá spočíta celkové skóre na konci hry zo súboru celkoveSkore.txt
     * 
     * @param cestaKSuboru
     */
    public void spocitajSkore(String cestkaKSuboru) throws IOException {
        File subor = new File(cestkaKSuboru);
        Scanner citac = new Scanner(subor);
        int cislo;
        
        this.skore.clear();
        if (this.skore.isEmpty()) {
            while (citac.hasNext()) {
                cislo = citac.nextInt();
                this.skore.add(cislo);
            }
        }
        
        for (Integer aktSkore : this.skore) {
            this.celkoveSkore += aktSkore;
        }
        citac.close();
    }
    
    /**
     * Metóda ktorá vymaže dáta v súbore celkoveSkore.txt pri ukončení levelu pred prejdením
     * celej mapy.
     */
    public void vymazData() throws IOException {
        File subor = new File("levely/celkoveSkore.txt");
        PrintWriter zapisovac = new PrintWriter(subor);
        
        zapisovac.close();
    }
    
    /**
     * Vráti hodnotu atribútu celkoveSkore.
     * 
     * @return celkoveSkore Skore hráča na konci hry.
     */
    public int getSkore() {
        return this.celkoveSkore;
    }
}
