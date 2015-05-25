/**
 * Tämä testiluokka testaa huffman-Logiikka -pakkauksen metodeja.
 */
package pakkaus.huffmanLogiikka;

import java.util.PriorityQueue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pakkaus.huffmanLogiikka.HuffmanKoodaus;
import pakkaus.huffmanLogiikka.HuffmanSolmu;

/**
 *
 * @author Markku
 */
public class HuffmanKoodausTest {

    public HuffmanKoodausTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    /**
     * Testataan, muodostetaanko tiheystaulu oikein.
     */
    
    @Test
    public void muodostaTiheystauluTest1() {
        String mjono = "kissa";
        int[] taulukko;
        taulukko = HuffmanKoodaus.muodostaTiheystaulu(mjono);
        assertEquals(taulukko[(int) 'k'], 1);
        assertEquals(taulukko[(int) 'i'], 1);
        assertEquals(taulukko[(int) 's'], 2);
        assertEquals(taulukko[(int) 'a'], 1);
        assertEquals(taulukko[(int) 'f'], 0);
        assertEquals(taulukko[(int) '.'], 0);
    }
    
    /**
     * Testataan muodostetaanko tiheystaulu oikein, kun merkkijono on tyhjä.
     */

    @Test
    public void muodostaTiheystauluTest2() {
        String mjono = "";
        int[] taulukko;
        taulukko = HuffmanKoodaus.muodostaTiheystaulu(mjono);
        for (int i = 0; i < taulukko.length; i++) {
            assertEquals(taulukko[i], 0);
        }
    }
    
    /**
     * Testataan, muodostuuko prioriteettijono oikein ja saadaanko sieltä
     * poistettavat merkit oikeassa järjestyksessä.
     */

    @Test
    public void muodostaPrioriteettijonoTest() {
        String mjono = " abbddddeeeeeccc";
        PriorityQueue<HuffmanSolmu> prjono;
        int[] taulukko;
        taulukko = HuffmanKoodaus.muodostaTiheystaulu(mjono);
        prjono = HuffmanKoodaus.muodostaPrioriteettijono(taulukko);
        assertEquals(prjono.remove().getMerkki(), ' ');
        assertEquals(prjono.remove().getMerkki(), 'a');
        assertEquals(prjono.remove().getMerkki(), 'b');
        assertEquals(prjono.remove().getMerkki(), 'c');
        assertEquals(prjono.remove().getMerkki(), 'd');
        assertEquals(prjono.remove().getMerkki(), 'e');

    }
    
    /**
     * Testataan, muodostetaanko Huffman-puu oikein.
     */

    @Test
    public void muodostaHuffmanPuuTest() {
        HuffmanSolmu puu;
        String mjono = "aaddddbbb";
        PriorityQueue<HuffmanSolmu> prjono;
        int[] taulukko;
        taulukko = HuffmanKoodaus.muodostaTiheystaulu(mjono);
        puu = HuffmanKoodaus.muodostaHuffmanPuu(taulukko);
        assertEquals(puu.getTiheys(), 9);
        assertEquals(puu.getVasen().getTiheys(), 4);
        assertEquals(puu.getOikea().getTiheys(), 5);
    }
    
    /**
     * Testataan, toimiiko kooditaulun muodostaminen Huffman-puusta oikein.
     */

    @Test
    public void muodostaKooditauluTest() {
        HuffmanSolmu puu;
        String mjono = "aaddddbbb";
        String[] kooditaulu = new String[256];
        PriorityQueue<HuffmanSolmu> prjono;
        int[] taulukko;
        taulukko = HuffmanKoodaus.muodostaTiheystaulu(mjono);
        puu = HuffmanKoodaus.muodostaHuffmanPuu(taulukko);
        HuffmanKoodaus.muodostaKooditaulu(puu, "", kooditaulu);
        assertEquals(kooditaulu[(int) 'a'].equals("10"), true);
        assertEquals(kooditaulu[(int) 'b'].equals("11"), true);
        assertEquals(kooditaulu[(int) 'd'].equals("0"), true);
    }
}
