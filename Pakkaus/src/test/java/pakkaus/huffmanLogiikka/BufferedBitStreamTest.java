/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakkaus.huffmanLogiikka;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pakkaus.tiedostonhallinta.Merkkijononlukija;
import pakkaus.tiedostonhallinta.Tiedostonlukija;
import pakkaus.huffmanLogiikka.BufferedBitStream;

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
        Merkkijononlukija m = new Merkkijononlukija("bA7");
        BufferedBitStream bbs = new BufferedBitStream(m);
        String bittijono = "";
        int tavu;
        for (int i = 0; i < 8; i++) {
            bittijono += "" + bbs.seuraava();
        }

        tavu = Integer.parseInt(bittijono, 2);
        bittijono = "";
        assertEquals((int) 'b', tavu);
        for (int i = 0; i < 8; i++) {
            bittijono += "" + bbs.seuraava();
        }
        tavu = Integer.parseInt(bittijono, 2);
        bittijono = "";
        assertEquals((int) 'A', tavu);
        for (int i = 0; i < 8; i++) {
            bittijono += "" + bbs.seuraava();
        }
        tavu = Integer.parseInt(bittijono, 2);
        assertEquals((int) '7', tavu);

    }


}
