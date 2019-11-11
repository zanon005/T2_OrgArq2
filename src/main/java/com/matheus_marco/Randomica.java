package com.matheus_marco;

import java.util.Random;

public class Randomica implements PoliticaSubstituicao{
    
    private int linhas;

    public Randomica(int linhas){
        this.linhas = linhas;
    }

    //@Override
    public int getIndex(int indexConj) {
        //Gerar num random  0..511
        int rand = new Random().nextInt(linhas-1) * (indexConj+1);
        System.out.println("Rand ->"+rand);
        return rand;
    }

    //@Override
    public void updateIndex(int indexConj, int index) {

    }

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