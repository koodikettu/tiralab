/**
 * Merkkijononlukija on testausta helpottava luokka, jonka avulla voidaan antaa syöte
 * pakkausmetodeille tiedostojen sijaan merkkijonomuodossa.
 * 
 */
package pakkaus.tiedostonhallinta;

/**
 *
 * @author Markku
 */
public class Merkkijononlukija implements Lukija {
    
    String merkkijono;
    int index;
    
    public Merkkijononlukija(String s) {
        merkkijono=s;
        int index=0;
    }

    @Override
    public int read() {
        return (int) merkkijono.charAt(index++);
    }

    @Override
    public int available() {
        return merkkijono.length()-index;
    }
    
}
