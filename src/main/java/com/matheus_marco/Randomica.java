package com.matheus_marco;

import java.util.Random;

public class Randomica implements PoliticaSubstituicao{

    private int conjuntoEscolhido;
    private int linhas;

    public Randomica(){}

    //@Override
    public int getIndex(int indexConj) {
        //Gerar num random  0..511
        return new Random().nextInt(linhas-1) * (conjuntoEscolhido+1);
    }

    //@Override
    public void updateIndex(int indexConj, int index) {

    }

    public int getConjuntoEscolhido() {
        return conjuntoEscolhido;
    }

    public void setConjuntoEscolhido(int conjuntoEscolhido) {
        this.conjuntoEscolhido = conjuntoEscolhido;
    }

    public int getLinhas() {
        return linhas;
    }

    public void setLinhas(int linhas) {
        this.linhas = linhas;
    }

}