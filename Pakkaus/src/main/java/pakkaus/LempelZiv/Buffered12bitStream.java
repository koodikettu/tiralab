/**
 * Buffered12bitStream-luokan olio toteuttaa Lempel-Ziv -koodauksen purkamisessa tarvittavan
 * mahdollisuuden lukea pakattua tiedostoa siten, että luetaan kerralla 12-bittiä, joista muodostetaan
 * kokonaisluku.
 * 
 */
package pakkaus.LempelZiv;

import pakkaus.tiedostonhallinta.Lukija;

/**
 *
 * @author Markku
 */
public class Buffered12bitStream {

    Lukija bis;
    String puskuri;
    int puskurissaJaljella;

    public Buffered12bitStream(Lukija bis) throws Exception {
        this.bis = bis;
        puskuri = "";
        if (bis.available() > 2) {
            puskuri = taydenna(Integer.toBinaryString(bis.read()));
            puskuri += taydenna(Integer.toBinaryString(bis.read()));
            puskuri += taydenna(Integer.toBinaryString(bis.read()));
            puskurissaJaljella = 2;
        }

    }
    
    /**
     * Metodi palauttaa kokonaisluvun, joka saadaan kun luetaan seuraavat 12 bittiä.
     * @return 12 bitin jonosta muodostettu kokonaisluku
     * @throws Exception 
     */

    public int next() throws Exception {
        String apu;
        int tulos = 4095;
        if (puskurissaJaljella == 2) {
            apu = puskuri.substring(0, 12);
            tulos = Integer.parseInt(apu, 2);
//            System.out.println(apu + ": " + tulos);
            puskuri = puskuri.substring(12, 24);
            puskurissaJaljella--;
        }
        else if (puskurissaJaljella == 1) {
            apu = puskuri;
            tulos = Integer.parseInt(apu, 2);
//            System.out.println(apu + ": " + tulos);
            puskuri = "";
            if (bis.available() > 2) {
                puskuri += taydenna(Integer.toBinaryString(bis.read()));
                puskuri += taydenna(Integer.toBinaryString(bis.read()));
                puskuri += taydenna(Integer.toBinaryString(bis.read()));
                puskurissaJaljella = 2;
            } else {
                puskurissaJaljella = 0;
            }

        }

        return tulos;
    }
    
    /**
     * Metodi kertoo, onko 12 bitin jaksoja vielä luettavissa.
     * @return true, jos voidaan lukea uusi 12 bitin jakso ja siitä muodostettu kokonaisluku
     */
    
    public boolean available() {
        if(puskurissaJaljella>0)
            return true;
        return false;
    }
    
    /**
     * Metodi täydentää bittijonon merkkijonoesityksen 12 merkin mittaiseksi lisäämällä alkuun
     * nollia, jos alkuperäinen merkkijono on lyhyempi kuin 12 merkkiä.
     * 
     * @param bittijono täydennettävä bittijonon merkkijonoesitys
     * @return parametrinä saadun bittijonon täydennetty 12-merkkinen bittijonoesitys
     */
    
    public String taydenna(String bittijono) {
        String jono=bittijono;
        while(jono.length()<8)
            jono="0"+jono;
        return jono;
    }

}
