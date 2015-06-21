/**
 * Merkkijononkirjoittaja on testausta helpottava luokka, jonka avulla voidaan antaa syöte
 * pakkausmetodeille tiedostojen sijaan merkkijonomuodossa.
 * 
 */
package pakkaus.tiedostonhallinta;

/**
 *
 * @author Markku
 */
public class Merkkijononkirjoittaja implements Kirjoittaja {
    private String merkkijono;
    
    public Merkkijononkirjoittaja() {
        this.merkkijono="";
    }
    
    public void write(int b) {
        this.merkkijono=this.merkkijono + (char) b;
    }
    
}
