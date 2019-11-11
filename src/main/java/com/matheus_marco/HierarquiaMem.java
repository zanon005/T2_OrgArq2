package com.matheus_marco;

import com.matheus_marco.armazenamento.CacheL1;
import com.matheus_marco.armazenamento.CacheL2;
import com.matheus_marco.armazenamento.CacheL3;
import com.matheus_marco.armazenamento.HardDrive;
import com.matheus_marco.armazenamento.MainMemory;

public class HierarquiaMem {
    
    /*Deve-se poder informar o tamanho em bytes da cache, 
    o número de palavras por bloco, o tamanho da palavra e o número de vias.*/
    private PoliticaSubstituicao politica;

    private int tamBytesCache;
    private int numWordsByBlock;
    private int sizeBitsWord;
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

    private static HierarquiaMem hierarquiaMem;

    private HierarquiaMem(){}

    public static HierarquiaMem getInstance(){
        if(hierarquiaMem == null){
            hierarquiaMem = new HierarquiaMem();
            return hierarquiaMem;
        }else{
            return hierarquiaMem;
        }
    }

    public void carregaHierarquiaMem(int numBitsEnderecos, int tamBytesCache, int numWordsByBlock, int sizeBitsWord, int numVias){
        this.numBitsEnderecos = numBitsEnderecos;
        this.tamBytesCache = tamBytesCache;
        this.numWordsByBlock = numWordsByBlock;
        this.sizeBitsWord = sizeBitsWord;
        this.numVias = numVias;
        this.numBitsEnderecos = 32;
        calculaBits();
    }

    public void carregaMemorias(){
        CaracterizacaoCache caracCache = CaracterizacaoCache.getInstance();
        l1 = new CacheL1(0, politica, numWordsByBlock, numLinhasMemDado, 
        numConjuntosMemAssociativa, tamConjuntosMemAssociativa, numBitsEnderecos);
        l2 = new CacheL2(caracCache.getProbabilityHitL2(), caracCache.getPenaltyL2());
        l3 = new CacheL3(caracCache.getProbabilityHitL3(), caracCache.getPenaltyL3());
        mainMemory = new MainMemory(caracCache.getProbabilityHitMainMemory(), caracCache.getPenaltyMainMemory());
        hd = new HardDrive(caracCache.getProbabilityHitHD(), caracCache.getPenaltyHD());
        //Special thanks to "Cops. pai" <3 (tambem conhecido como bernardo cops.)
    }

    /*
    *   Dado tamanho em bytes da memoria de dados, numero de palavras por bloco(linha da memoria de dados),
    * tamanho em bytes de cada palavra armazenada, e o numero de vias da memoria associativa( quantas linhas 
    * cada linha da memoria associativa referencia da memoria de dados).  
    */
    private void calculaBits(){
        tamBlocoMemDado = sizeBitsWord * numWordsByBlock;
        numLinhasMemDado = tamBytesCache / tamBlocoMemDado;
        numConjuntosMemAssociativa = numLinhasMemDado / numVias;
        tamConjuntosMemAssociativa = numLinhasMemDado / numConjuntosMemAssociativa;
    }

    //Retorna o quanto demorou para devolver o endereco
    public int getEndereco(String endereco, String enderecoDecimal){
        int atraso = 0;
        //Procura enderco na l1... caso miss, procura na hierarquia de baixo.
        if(l1.getEndereco(endereco, enderecoDecimal)){
            return atraso;
        }else if(l2.getEndereco()){
            atraso+= l2.getPenalidade();
            return atraso;
        }else if(l3.getEndereco()){
            atraso+= l3.getPenalidade();
            return atraso;
        }else if(mainMemory.getEndereco()){
            atraso+= mainMemory.getPenalidade();
            return atraso;
        }else{
            //Considerando hd com 100% probabilidade
            hd.getEndereco();
            atraso+= hd.getPenalidade();
            return atraso;
        }
    }

    public int getNumBitsEndereco(){ return this.numBitsEnderecos;}

    public int getHitsL1(){return this.l1.getHitCounter();}

    public int getMissL1(){return this.l1.getMissCounter();}

    public int getHitsL2(){return this.l2.getHitCounter();}

    public int getMissL2(){return this.l2.getMissCounter();}

    public int getHitsL3(){return this.l3.getHitCounter();}

    public int getMissL3(){return this.l3.getMissCounter();}

    public int getHitsMR(){return this.mainMemory.getHitCounter();}

    public int getMissMR(){return this.mainMemory.getMissCounter();}

    public int getHitsHD(){return this.hd.getHitCounter();}

    public int getNumLinhasMemDados(){return this.numLinhasMemDado;}

    public int getTamBytesCache() {return tamBytesCache;}

    public void setTamBytesCache(int tamBytesCache) {
        this.tamBytesCache = tamBytesCache;
    }

    public int getNumWordsByBlock() {return numWordsByBlock;}

    public void setNumWordsByBlock(int numWordsByBlock) {this.numWordsByBlock = numWordsByBlock;}

    public int getSizeBytesWord() {return sizeBitsWord;}

    public void setSizeBytesWord(int sizeBitsWord) {this.sizeBitsWord = sizeBitsWord;}

    public int getNumVias() {return numVias;}

    public void setNumVias(int numVias) {this.numVias = numVias;}

    public int getNumBitsEnderecos() {return numBitsEnderecos;}

    public void setNumBitsEnderecos(int numBitsEnderecos) {this.numBitsEnderecos = numBitsEnderecos;}

    public int getTamBlocoMemDado() {return tamBlocoMemDado;}

    public void setTamBlocoMemDado(int tamBlocoMemDado) {this.tamBlocoMemDado = tamBlocoMemDado;}

    public int getNumLinhasMemDado() {return numLinhasMemDado;}

    public void setNumLinhasMemDado(int numLinhasMemDado) {this.numLinhasMemDado = numLinhasMemDado;}

    public int getNumConjuntosMemAssociativa() {return numConjuntosMemAssociativa;}

    public void setNumConjuntosMemAssociativa(int numConjuntosMemAssociativa) {
        this.numConjuntosMemAssociativa = numConjuntosMemAssociativa;
    }

    public int getTamConjuntosMemAssociativa() {return tamConjuntosMemAssociativa;}

    public void setTamConjuntosMemAssociativa(int tamConjuntosMemAssociativa) {
        this.tamConjuntosMemAssociativa = tamConjuntosMemAssociativa;
    }

    public String getNomePolitica(){return this.politica.toString();}

    public PoliticaSubstituicao getPolitica() {return politica;}

    public void setPolitica(PoliticaSubstituicao politica) {this.politica = politica;}
}