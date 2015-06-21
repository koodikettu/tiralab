/**
 * Lukija-rajapinta mahdollistaa sekä Tiedostonlukija- että Merkkijononlukija-olioiden antamisen
 * parametreina pakkaustoiminnallisuuden toteuttamiseen osallistuville metodeille.
 */
package pakkaus.tiedostonhallinta;

/**
 *
 * @author Markku
 */
public interface Lukija {
    
    public int read() throws Exception;
    
    public int available() throws Exception;
    
}
