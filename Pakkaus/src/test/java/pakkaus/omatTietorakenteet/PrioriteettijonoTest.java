/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakkaus.omatTietorakenteet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pakkaus.huffmanLogiikka.HuffmanSolmu;

/**
 *
 * @author Markku
 */
public class PrioriteettijonoTest {

    public PrioriteettijonoTest() {
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
    public void PrioriteettijonoTest1() {
        HuffmanSolmu a = new HuffmanSolmu('a', 10, null, null);
        HuffmanSolmu b = new HuffmanSolmu('b', 15, null, null);
        HuffmanSolmu c = new HuffmanSolmu('c', 3, null, null);
        HuffmanSolmu d = new HuffmanSolmu('d', 4, null, null);
        HuffmanSolmu e = new HuffmanSolmu('e', 8, null, null);

        Prioriteettijono jono = new Prioriteettijono();
        jono.insert(a);
        jono.insert(b);
        jono.insert(c);
        jono.insert(d);
        jono.insert(e);

        jono.tulosta();
//        System.out.println(jono.delMin().getMerkki());
        assertEquals('c', jono.delMin().getMerkki());
        jono.tulosta();
        assertEquals('d', jono.delMin().getMerkki());
        assertEquals('e', jono.delMin().getMerkki());
        assertEquals('a', jono.delMin().getMerkki());
        assertEquals('b', jono.delMin().getMerkki());

    }

    @Test
    public void PrioriteettijonoTest2() {
        HuffmanSolmu a = new HuffmanSolmu('a', 158, null, null);
        HuffmanSolmu b = new HuffmanSolmu('b', 15, null, null);
        HuffmanSolmu c = new HuffmanSolmu('c', 555, null, null);
        HuffmanSolmu d = new HuffmanSolmu('d', 63, null, null);
        HuffmanSolmu e = new HuffmanSolmu('e', 554, null, null);

        Prioriteettijono jono = new Prioriteettijono();
        jono.insert(a);
        jono.insert(b);
        jono.insert(c);
        jono.insert(d);
        jono.insert(e);

        jono.tulosta();
//        System.out.println(jono.delMin().getMerkki());
        assertEquals('b', jono.delMin().getMerkki());
        jono.tulosta();
        assertEquals('d', jono.delMin().getMerkki());
        assertEquals('a', jono.delMin().getMerkki());
        assertEquals('e', jono.delMin().getMerkki());
        assertEquals('c', jono.delMin().getMerkki());

    }

}
