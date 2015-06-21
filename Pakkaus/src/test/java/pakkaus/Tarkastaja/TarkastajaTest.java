/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakkaus.Tarkastaja;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pakkaus.tiedostonhallinta.Merkkijononlukija;
import pakkaus.tarkastaja.Tarkastaja;

/**
 *
 * @author Markku
 */
public class TarkastajaTest {

    public TarkastajaTest() {
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
    public void tarkastajaTest() throws Exception {
        int a;
        Merkkijononlukija eka = new Merkkijononlukija("abcdefg");
        Merkkijononlukija toka = new Merkkijononlukija("abcdefg");
        a = Tarkastaja.vertaa(eka, toka);

        assertEquals(0, a);
        System.out.println("Tarkastajatesti1");
    }

    @Test
    public void tarkastajaTest2() throws Exception {
        int a;
        Merkkijononlukija eka = new Merkkijononlukija("abcdefgh");
        Merkkijononlukija toka = new Merkkijononlukija("abcdefg");
        a = Tarkastaja.vertaa(eka, toka);

        assertEquals(1, a);
        System.out.println("Tarkastajatesti2");
    }

    @Test
    public void tarkastajaTest3() throws Exception {
        int a;
        Merkkijononlukija eka = new Merkkijononlukija("abcdefg");
        Merkkijononlukija toka = new Merkkijononlukija("abcdefgh");
        a = Tarkastaja.vertaa(eka, toka);

        assertEquals(3, a);
        System.out.println("Tarkastajatesti3");
    }
    
        @Test
    public void tarkastajaTest4() throws Exception {
        int a;
        Merkkijononlukija eka = new Merkkijononlukija("abcdefg");
        Merkkijononlukija toka = new Merkkijononlukija("abccefg");
        a = Tarkastaja.vertaa(eka, toka);

        assertEquals(2, a);
        System.out.println("Tarkastajatesti4");
    }
}
