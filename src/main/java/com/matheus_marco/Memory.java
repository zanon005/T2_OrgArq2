package com.matheus_marco;

import java.util.ArrayList;

public class Memory{

    private int tamMemTotal;
    private int tamBloco;
    private int tamPalavra;
    private int qtdConjuntos;
    private PoliticaSubstituicao pol;
    
    private int bitsBloco;
    private int acessos;
    private int hit;
    private int miss;

    private ArrayList<String> cache;
    private ArrayList<String> conj;
    private int linhas;

    private long tempoMedio;
    private int utilCache;
    private int tamCache;
    private int qtdTag;

    public Memory(int tamMemTotal, int tamBloco, int tamPalavra, int qtdConjuntos, PoliticaSubstituicao pol){
        this.tamMemTotal =tamMemTotal;
        this.tamBloco = tamBloco;
        this.tamPalavra = tamPalavra;
        this.qtdConjuntos = qtdConjuntos;
        this.pol = pol;
        this.bitsBloco = tamPalavra * tamBloco + 1;
        this.acessos =0;
        this.hit=0;
        this.miss=0;
        this.cache = new ArrayList<>();
        this.conj = new ArrayList<>();
        this.linhas = 1;
        this.tempoMedio = 0;
        
    }


    public int bitsTag(int repBloco, int repOutro){
        return 32 - repBloco - repOutro;
    }

    public int repBits(Double bits){
        return (int) Math.floor(Math.log(bits));
    }
    public void start(){
        while((bitsBloco * linhas) <= (tamMemTotal * 8)){
            linhas *= 2;
        }

        linhas /= 2;
        //tem que ver se fiz isso certo foi de cabeça
        tamCache = (bitsBloco * linhas) / 8;
        utilCache = tamCache / tamMemTotal * 100;

        //como calcula a tag é asssim?
        qtdTag = bitsTag(repBits(tamBloco*1.00), repBits(qtdConjuntos*1.00));

        //Como anteriormento ver como cria uma lista de lista em java
        //inicializar zerando tudo na cache


        int offSet = linhas / qtdConjuntos;


        //ver como vai receber a politica LRU OR Random
        // nao lembro como faz essa merda de operar com interface
        // é alguma coisa parecido com a linha de baixo
        pol = new Randomica(qtdConjuntos, offSet);

        //Como anteriormento ver como cria uma lista de lista em java
        //inicializer zerando tudo em conj
    }

    public String toString(){
        return "============= Resultado ============="+
               "\nTamanho total:"+ tamMemTotal +
               "\nBytes por Tamanho palavra:" + tamPalavra+
               "\nBits por Tamanho bloco:"+ tamBloco+
               "\nQtd conjuntos:"+qtdConjuntos+
               "\nPolitica: "+pol+
               "\nBits por Bloco:"+repBits(tamBloco*1.00)+
               "\nBits por Conjunto:"+repBits(qtdConjuntos*1.00)+
               "\nBits tag:"+qtdTag+
               "\nTamanho da Cache:"+tamCache+
               "\nPercentual de dados utilizados na cache:"+utilCache+
               "\nQuantidade de Acessos:"+acessos+
               "\nQuantidade de Hit's:"+hit+
               "\nPercentual de Hit's:"+ (hit/acessos * 100) +
               "\nQuantide de Miss:"+miss+
               "\nPercentual de Miss:"+ miss/acessos +
               "\nTempo medio: "+tempoMedio/acessos+"\n";
    }
}