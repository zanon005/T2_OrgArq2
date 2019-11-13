package com.matheus_marco;

import java.util.Random;

public class Randomica implements PoliticaSubstituicao{
    
    private int linhas;

    public Randomica(int linhas){
        System.out.println("LINHAS RECEBIDAS ->"+linhas);
        this.linhas = linhas;
    }

    //@Override
    public int getIndex(int indexConj) {
        //Gerar num random  0..511
        int rand = new Random().nextInt(linhas-1);
        return rand;
    }

    public void atualizaFrequencia(int conjunto, int indexEndereco){
        //mapEnderecos.get(conjunto).put(indexEndereco, mapEnderecos.get(conjunto).get(indexEndereco)+1);
    }

    public void init(int numeroDeConjuntos, int numLinhasNosConjuntos){}

    public int getFreq(int qualConjunto, int endereco){return 0;}

    public int getLinhas() {
        return linhas;
    }

    public void setLinhas(int linhas) {
        this.linhas = linhas;
    }

    public String toString(){
        return "Randomica";
    }

}