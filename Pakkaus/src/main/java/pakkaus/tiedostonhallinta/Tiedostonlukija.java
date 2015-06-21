/**
 * Tiedostonlukija-luokka kapseloi sisäänsä tiedostojen lukemiseen tarvittavat toiminnallisuudet.
 * Se toteuttaa Lukija-rajapinnan.
 * 
 */
package pakkaus.tiedostonhallinta;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 *
 * @author Markku
 */
public class Tiedostonlukija implements Lukija {

    private File lahdetiedosto;
    private FileInputStream syote;
    private BufferedInputStream bis;
    private boolean tiedostonhallinta;

    public Tiedostonlukija(BufferedInputStream b) {
        this.bis = b;
        tiedostonhallinta = false;
    }

    public Tiedostonlukija(String tiedostoNimi) throws Exception {
        this.lahdetiedosto = new File(tiedostoNimi);
        this.syote = new FileInputStream(lahdetiedosto);
        this.bis = new BufferedInputStream(syote);
        tiedostonhallinta = true;
    }

    @Override
    public int read() throws Exception {
        return this.bis.read();
    }

    @Override
    public int available() throws Exception {
        return this.bis.available();
    }

    public void close() throws Exception {
        if (tiedostonhallinta == false) {
            return;
        }
        bis.close();
        syote.close();

    }
    
    /**
     * Merkitsee tiedoston alkukohdan siihen myöhempää palaamista varten.
     * @param a Luettavien tavujen maksimimäärä, jonka jälkeen kirjanmerkki ei enää toimi.
     * @throws Exception 
     */

    public void mark(int a) throws Exception {
        if (tiedostonhallinta == false) {
            return;
        }
        this.bis.mark(a);
    }
    
    /**
     * Palaa luettevassa tiedostossa edellisellä mark()-metodilla merkittyyn kohtaan (alkuun).
     * @throws Exception 
     */

    public void reset() throws Exception {
        if (tiedostonhallinta == false) {
            return;
        }
        this.bis.reset();
    }

}
