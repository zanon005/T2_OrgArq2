package com.matheus_marco;

import com.matheus_marco.armazenamento.CacheL1;
import com.matheus_marco.armazenamento.CacheL2;
import com.matheus_marco.armazenamento.CacheL3;
import com.matheus_marco.armazenamento.HardDrive;
import com.matheus_marco.armazenamento.MainMemory;

public class HierarquiaMem {
    
    /*Deve-se poder informar o tamanho em bytes da cache, 
    o número de palavras por bloco, o tamanho da palavra e o número de vias.*/
    private int penaltyL1;
    private int penaltyL2;
    private int penaltyL3;
    private int penaltyMainMemory;
    private int penaltyHD;
    private PoliticaSubstituicao politica;

    private int probabilityHitL2;
    private int probabilityHitL3;
    private int probabilityHitMainMemory;
    private int probabilityHitHD;

    private int tamBytesCache;
    private int numWordsByBlock;
    private int sizeBytesWord;
    private int numVias;
    private int numBitsEnderecos; //Sor definiu como 32 no pdf.

    private int tamBlocoMemDado;
    private int numLinhasMemDado;
    private int numConjuntosMemAssociativa;
    private int tamConjuntosMemAssociativa;

    private CacheL1 l1;
    private CacheL2 l2;
    private CacheL3 l3;
    private MainMemory mainMemory;
    private HardDrive hd;

    public HierarquiaMem(int numBitsEnderecos, int tamBytesCache, int numWordsByBlock, int sizeBytesWord, int numVias){
        this.numBitsEnderecos = numBitsEnderecos;
        this.tamBytesCache = tamBytesCache;
        this.numWordsByBlock = numWordsByBlock;
        this.sizeBytesWord = sizeBytesWord;
        this.numVias = numVias;
        this.numBitsEnderecos = 32;
        calculaBits();
        l1 = new CacheL1(penaltyL1, politica, numWordsByBlock, numLinhasMemDado, 
        numConjuntosMemAssociativa, tamConjuntosMemAssociativa);
        l2 = new CacheL2(probabilityHitL2, penaltyL2);
        l3 = new CacheL3(probabilityHitL3, penaltyL3);
        mainMemory = new MainMemory(probabilityHitMainMemory, penaltyMainMemory);
        hd = new HardDrive(probabilityHitHD, penaltyHD);
    }

    /*
    *   Dado tamanho em bytes da memoria de dados, numero de palavras por bloco(linha da memoria de dados),
    * tamanho em bytes de cada palavra armazenada, e o numero de vias da memoria associativa( quantas linhas 
    * cada linha da memoria associativa referencia da memoria de dados).  
    */
    private void calculaBits(){
        tamBlocoMemDado = sizeBytesWord * numWordsByBlock;
        numLinhasMemDado = tamBytesCache / tamBlocoMemDado;
        numConjuntosMemAssociativa = numLinhasMemDado / numVias;
        tamConjuntosMemAssociativa = numLinhasMemDado / numConjuntosMemAssociativa;
    }
}