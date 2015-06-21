/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakkaus.huffmanLogiikka;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pakkaus.pakkaus.Merkkijononlukija;
import pakkaus.pakkaus.Tiedostonlukija;

/**
 *
 * @author Markku
 */
public class BufferedBitStreamTest {

    public BufferedBitStreamTest() {
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
    @Test
    public void bufferedBitStreamTest() throws Exception {
        final String tiedostonimi = "testidata.txt";
        File lahdetiedosto = new File(tiedostonimi);
        FileInputStream syote = new FileInputStream(lahdetiedosto);
        BufferedInputStream psyote = new BufferedInputStream(syote);

        Tiedostonlukija tl = new Tiedostonlukija(psyote);
        BufferedBitStream bbs = new BufferedBitStream(tl, 0);
        String bittijono = "";
        for (int i = 0; i < 8; i++) {
            bittijono += "" + bbs.seuraava();
        }
        psyote.close();
        syote.close();
        lahdetiedosto = new File(tiedostonimi);
        syote = new FileInputStream(lahdetiedosto);
        psyote = new BufferedInputStream(syote);
        System.out.println(bittijono);
        int t = psyote.read();
        System.out.println("JoooO " + (char) t + t);
        assertEquals(t, Integer.parseInt(bittijono, 2));
        psyote.close();
        syote.close();

    }

    @Test
    public void bufferedBitStreamTest2() throws Exception {

        // Tiedoston sisältö on "bA7" eli tavut 98, 65, 55
        // Binääriesitys: 01100010 01000001 00110111
        Merkkijononlukija mjl = new Merkkijononlukija("bA7");
        BufferedBitStream bbs=new BufferedBitStream(mjl,0);

        int i;
        String tavu = "";
        for (i = 0; i < 8; i++) {
            tavu += bbs.seuraava();
            System.out.println(tavu);

        }
        int eka = Integer.parseInt(tavu, 2);
        tavu = "";
        for (i = 0; i < 8; i++) {
            tavu += bbs.seuraava();
            System.out.println(tavu);

        }
        int toka = Integer.parseInt(tavu, 2);
        tavu = "";
        for (i = 0; i < 8; i++) {
            tavu += bbs.seuraava();
            System.out.println(tavu);

        }
        int kolmas = Integer.parseInt(tavu, 2);

        assertEquals(98, eka);
        assertEquals(65, toka);
        assertEquals(55, kolmas);

        System.out.println("bittilukija testattu");
    }
}
