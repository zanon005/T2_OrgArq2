package com.matheus_marco;

import java.util.Random;

public class Randomica extends PoliticaSubstituicao{

    private int qtdConjuntos;
    private int linhas;

    public Randomica(int qtdConjuntos, int linhas){
        this.qtdConjuntos = qtdConjuntos;
        this.linhas = linhas;
    }

    @Override
    public int getIndex(int indexConj) {
        return new Random().nextInt(linhas-1);
    }

    @Override
    public void updateIndex(int indexConj, int index) {

    }

}