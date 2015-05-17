/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakkaus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

/**
 *
 * @author Markku
 */
public class Pakkaus {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        String tiedostonNimi;
        File lahde, kohde;
        FileInputStream fis, fisKohde;
        FileOutputStream fos;
        char merkki;

        int valinta;
        Scanner lukija = new Scanner(System.in);
        System.out.println("Anna pakattavan tiedoston nimi: ");
        tiedostonNimi = lukija.next();
        lahde = new File(tiedostonNimi);
        fis = new FileInputStream(lahde);
        fos = new FileOutputStream(tiedostonNimi + ".pakattu");
        System.out.println("Valitse käytettävä pakkausalgoritmi:");
        System.out.println("1. Ei pakkausta");
        System.out.println("2. Joka toinen merkki");
        valinta = lukija.nextInt();
        if (valinta == 1) {
            eiPakkausta(fis, fos);
        } else {
            jokaToinen(fis, fos);
        }
        fis.close();
        fos.close();
        fis = new FileInputStream(lahde);
        fisKohde = new FileInputStream(tiedostonNimi + ".pakattu");
        System.out.println("Lähde: " + fis.getChannel().size());
        System.out.println("Kohde: " + fisKohde.getChannel().size());


    }

    public static void eiPakkausta(FileInputStream fis, FileOutputStream fos) throws Exception {
        while (fis.available() > 0) {
            fos.write(fis.read());
        }

    }

    public static void jokaToinen(FileInputStream fis, FileOutputStream fos) throws Exception {
        int i = 0;
        int b;
        while (fis.available() > 0) {
            if (i == 0) {
                b = fis.read();
                fos.write(b);
                i = 1;

            } else {
                fis.read();
                i = 0;

            }
        }

    }

}
