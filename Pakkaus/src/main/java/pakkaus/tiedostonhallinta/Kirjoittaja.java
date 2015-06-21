/**
 * Kirjoittaja-rajapinta mahdollistaa sekä Tiedostonkirjoittaja- että Merkkijononkirjoittaja-olioiden
 * antamisen parametreina pakkaustoiminnallisuuden toteuttamiseen osallistuville metodeille.
 */
package pakkaus.tiedostonhallinta;

/**
 *
 * @author Markku
 */
public interface Kirjoittaja {
    
    public void write(int b) throws Exception;
    
}
