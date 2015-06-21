/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakkaus.tiedostonhallinta;

/**
 *
 * @author Markku
 */
public class Merkkijononkirjoittaja implements Kirjoittaja {
    private String merkkijono;
    
    public Merkkijononkirjoittaja() {
        this.merkkijono="";
    }
    
    public void write(int b) {
        this.merkkijono=this.merkkijono + (char) b;
    }
    
}
