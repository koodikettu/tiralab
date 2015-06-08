/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakkaus.omatTietorakenteet;

import pakkaus.huffmanLogiikka.HuffmanSolmu;

/**
 *
 * @author Markku
 */
public class Prioriteettijono {
    int koko;
    HuffmanSolmu[] taulukko;
    
    public Prioriteettijono() {
        taulukko= new HuffmanSolmu[256+256];
        koko=0;
    }
    
    public void insert(HuffmanSolmu lisattava) {
        if(koko==0) {
            taulukko[1]=lisattava;
            koko++;
            return;
        }
        koko++;
        int i=koko;
        while(i>1 && taulukko[parent(i)].compareTo(lisattava)>0) {
            taulukko[i]=taulukko[parent(i)];
            i=parent(i);
        }
        taulukko[i]=lisattava;
        
    }
    
    public void heapify(int i) {
        int max;
        HuffmanSolmu temp;
        int left=left(i);
        int right=right(i);
        if(right<=koko) {
            if(taulukko[left].compareTo(taulukko[right])<0)
                max=left;
            else max=right;
            if(taulukko[i].compareTo(taulukko[max])>0) {
                temp=taulukko[i];
                taulukko[i]=taulukko[max];
                taulukko[max]=temp;
                heapify(max);
            }
                
        }
        else if (left==koko && taulukko[i].compareTo(taulukko[left])>0) {
            temp=taulukko[i];
            taulukko[i]=taulukko[left];
            taulukko[left]=temp;
        }
    }
    
    public HuffmanSolmu delMin() {
        HuffmanSolmu min=taulukko[1];
        taulukko[1]=taulukko[koko];
        koko--;
        heapify(1);
        return min;
    }
    
    public int left(int i) {
        return i*2;
    }
    
    public int right(int i) {
        return i*2+1;
    }
    
    public int parent(int i) {
        if (i%2==1) {
            return (i-1)/2;
            
        }
        else
            return i/2;
    }
    

    public void tulosta() {
        for(int i=1;i<=koko;i++) {
            System.out.println(i+ ": " + taulukko[i].getMerkki() + ", " + taulukko[i].getTiheys());
        }
    }
    
    public int koko() {
        return this.koko;
    }
    
}
