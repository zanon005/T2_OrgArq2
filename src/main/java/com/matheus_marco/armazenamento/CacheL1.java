package com.matheus_marco.armazenamento;

import com.matheus_marco.PoliticaSubstituicao;

public class CacheL1{

    private int missPenalty;
    private PoliticaSubstituicao politicaSubstituicao;
    private int qtdPalavrasNoBloco;
    private int numLinhasMemDado;
    private int numConjuntosMemAssociativa;
    private int tamConjuntosMemAssociativa;

    private String[][][] conjuntoAssociativo; //Conj.MemAssociativa,linhaMemAssociativa, tag e bit validade
    private String[][] memoriaDados;

    public CacheL1(int missPenalty, PoliticaSubstituicao politicaSubstituicao,int qtdPalavrasNoBloco, 
    int numLinhasMemDado, int numConjuntosMemAssociativa, int tamConjuntosMemAssociativa ){
        this.missPenalty = missPenalty;
        this.politicaSubstituicao = politicaSubstituicao;
        this.qtdPalavrasNoBloco = qtdPalavrasNoBloco;
        this.numLinhasMemDado = numLinhasMemDado;
        this.numConjuntosMemAssociativa = numConjuntosMemAssociativa;
        this.tamConjuntosMemAssociativa = tamConjuntosMemAssociativa;

        this.conjuntoAssociativo = new String[numConjuntosMemAssociativa][tamConjuntosMemAssociativa][2];
        this.memoriaDados = new String[numLinhasMemDado][qtdPalavrasNoBloco];
    }

    // Metodo que retorna se o endereco esta presente na memoria.
    public boolean getEndereco(String endereco){
        return false;
    }

    // Metodo que retorna a penalidade para o sistema, caso 'miss' nessa memoria.
    public int getPenalidade(){
        return missPenalty;
    }
}