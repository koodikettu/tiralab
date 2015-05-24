/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakkaus;

import java.util.PriorityQueue;

/**
 *
 * @author Markku
 */
public class HuffmanKoodaus {

    public static int[] muodostaTiheystaulu(String merkkijono) {

        int i;
        int[] taulukko = new int[256];

        for (i = 0; i < merkkijono.length(); i++) {
            taulukko[(int) (merkkijono.charAt(i))]++;

        }
        return taulukko;

    }

    public static HuffmanSolmu muodostaHuffmanPuu(int[] tiheystaulu) {
        int i;
        HuffmanSolmu vasen, oikea;
        PriorityQueue<HuffmanSolmu> prjono = muodostaPrioriteettijono(tiheystaulu);

        while (prjono.size() > 1) {
            vasen = prjono.remove();
            oikea = prjono.remove();
            System.out.println("Yhdistetään: " + vasen.getTiheys() + ", " + oikea.getTiheys());
            prjono.add(new HuffmanSolmu('\0', vasen.getTiheys() + oikea.getTiheys(), vasen, oikea));
        }
        return prjono.remove();

    }

    public static PriorityQueue<HuffmanSolmu> muodostaPrioriteettijono(int[] tiheystaulu) {
        int i;
        PriorityQueue<HuffmanSolmu> prjono = new PriorityQueue<HuffmanSolmu>();
        for (i = 0; i < tiheystaulu.length; i++) {
            if (tiheystaulu[i] > 0) {
                prjono.add(new HuffmanSolmu((char) i, tiheystaulu[i], null, null));
                System.out.println("Lisätty solmu " + (char) i);
            }
        }
        return prjono;
    }

    public static void muodostaKooditaulu(HuffmanSolmu solmu, String koodi, String[] kooditaulu) {
        if (solmu.onLehti()) {
            System.out.println("Löytyi solmu: " + solmu.getMerkki());
            kooditaulu[(int) (solmu.getMerkki())] = koodi;
            return;
        }

        muodostaKooditaulu(solmu.getVasen(), koodi + "0", kooditaulu);

        muodostaKooditaulu(solmu.getOikea(), koodi + "1", kooditaulu);

    }

}
