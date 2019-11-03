package com.matheus_marco;

import java.util.ArrayList;

public class LRU implements PoliticaSubstituicao{

    private int qtdConjuntos;
    private int linhas;
    /*
    Ver com o zanon como crio isso:
     lista( lista(0), lista(1))

    */
    private ArrayList<ArrayList<Integer>> control;

    public LRU(int qtdConjuntos, int linhas){
        this.qtdConjuntos = qtdConjuntos;
        this.linhas = linhas;
        this.control = new ArrayList<>();
    }
    @Override
    public int getIndex(int indexConj) {
        int menorTempo = 0;
        // ver com o zanon nao manjo de java bem kkk
        // se eu der indexConj zero vai para arraylist zero?
        for(int i=1; i < linhas; i++){
            if (control.get(indexConj).get(i) < control.get(indexConj).get(i)){
                menorTempo = i;
            }
        }
        return menorTempo;
    }

    @Override
    public void updateIndex(int indexConj, int index) {
        // TODO Auto-generated method stub

    }

}