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
        BufferedBitStream bbs=new BufferedBitStream(psyote, 0);
        String bittijono="";
        for(int i=0;i<8;i++)
            bittijono+=""+bbs.seuraava();
        psyote.close();
        syote.close();
                lahdetiedosto = new File(tiedostonimi);
        syote = new FileInputStream(lahdetiedosto);
        psyote = new BufferedInputStream(syote);
        System.out.println(bittijono);
        int t=psyote.read();
        System.out.println("JoooO " + (char) t + t);
        assertEquals(t,Integer.parseInt(bittijono,2));
        psyote.close();
        syote.close();
        
    }
}
