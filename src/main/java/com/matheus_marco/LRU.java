package com.matheus_marco;

import java.util.ArrayList;

public class LRU implements PoliticaSubstituicao{

    private int conjuntoEscolhido;
    private int linhas;
    private ArrayList<ArrayList<Integer>> control;

    public LRU(int linhas){
        this.linhas = linhas;
        this.control = new ArrayList<>();
    }

    //@Override
    public int getIndex(int indexConj) {
        int menorTempo = 0;
        for(int i=1; i < linhas; i++){
            if (control.get(indexConj).get(i) < control.get(indexConj).get(menorTempo)){
                menorTempo = i;
            }
        }
        return menorTempo;
    }

    //@Override
    public void updateIndex(int indexConj, int index) {
        // TODO Auto-generated method stub

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

    public String toString(){
        return "LRU";
    }

}