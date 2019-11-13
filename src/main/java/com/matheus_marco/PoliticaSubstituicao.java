package com.matheus_marco;

public interface PoliticaSubstituicao{

    public int getIndex(int indexConj);

    public String toString();

    public void init(int numeroDeConjuntos, int numLinhasNosConjuntos);

    public void atualizaFrequencia(int conjunto, int indexEndereco);

    public int getFreq(int conjunto, int indexEndereco);
    
}