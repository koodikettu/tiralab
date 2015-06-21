/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakkaus.LempelZiv;

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
public class Buffered12bitStreamTest {

    public Buffered12bitStreamTest() {
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
    public void bitinlukuTest() throws Exception {
        
        // Tiedoston sisältö on "bA7" eli tavut 98, 65, 55
        // Binääriesitys: 01100010 01000001 00110111
        
        File lahdetiedosto = new File("bittienkasittelytesti.txt");
        FileInputStream syote = new FileInputStream(lahdetiedosto);
        BufferedInputStream psyote = new BufferedInputStream(syote);
        Buffered12bitStream b=new Buffered12bitStream(psyote);
        int eka = b.next();
        int toka = b.next();
        
        assertEquals(1572, eka);
        assertEquals(311, toka);
        
        psyote.close();
        syote.close();
        System.out.println("12bittilukija testattu");
    }
}
