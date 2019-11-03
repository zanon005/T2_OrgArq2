package com.matheus_marco;

import java.util.ArrayList;

public class Memory{

    private PoliticaSubstituicao pol;
    private int tamMemTotal;
    private int tamBloco;
    private int tamPalavra;
    private int qtdConjuntos;
    
    private int bitsBloco;
    private int acessos;
    private int hit;
    private int miss;

    private ArrayList<ArrayList<String>> cache;
    private ArrayList<ArrayList<String>> conj;
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
        //tem que ajeitar essa pol nao sei qual a instancia correta
        //randomica ou LRU
        this.pol = pol;
        //bitsBloco localiza qual bloco o dado pertence! 
        this.bitsBloco = tamPalavra * tamBloco + 1; //bitsBloco gera quantidade de bits por bloco
        this.acessos =0;
        this.hit=0;
        this.miss=0;
        this.cache = new ArrayList<>();
        this.conj = new ArrayList<>();
        this.linhas = 1;
        this.tempoMedio = 0;
        
    }

    //quantos bits tem para representar a tag,i.e, o que sobrar é da tag
    public int bitsTag(int repBloco, int repOutro){
        return 32 - repBloco - repOutro;

    }
    

    //Metodo diz quantos bits vai ser utilizado
    public int repBits(Double bits){
        return (int) Math.floor(Math.log(bits));
    }

    public void start(){
        //Verificar '8' magico, calcular bytes por linha da mem corretamente
        //'8' não é magico so faço um conversao para bits de bytes
        // nLinhasMem = SizeMemBytes / tamBytesLinha
        
        // QUANTOS CONJUNTOS ASSOCIATIVOS TEM ?  COMO CALCULAR ??
        // Não sei responder tem que ver com o sor, msm

   
        while((bitsBloco * linhas) <= (tamMemTotal * 8)){
            linhas *= 2;
        }

        linhas /= 2;
        //tem que ver se fiz isso certo foi de cabeça
        //ta ok!
        tamCache = (bitsBloco * linhas) / 8;
        utilCache = tamCache / tamMemTotal * 100;

        //como calcula a tag é asssim?
        //é desse jeito mesmo
        //Fiz gambiarra para converte para double
        qtdTag = bitsTag(repBits(tamBloco*1.00), repBits(qtdConjuntos*1.00));

        //Como anteriormento ver como cria uma lista de lista em java
        //inicializar zerando tudo na cache
        //Quero fazer isso ver com o zanon
        /*
        cache = [[] for i in range(linhas)]
        */
        for(int i=0; i<linhas;i++){
            String aux = "";
            int cont = 0;
            //Concaternar zeros 
            while(cont < tamPalavra){
                aux += "0";
                cont++;
            }
            cache.get(i).add(aux);
        }
        
        int offSet = linhas / qtdConjuntos;


        //ver como vai receber a politica LRU OR Random
        // nao lembro como faz essa merda de operar com interface
        // é alguma coisa parecido com a linha de baixo
        pol = new Randomica(qtdConjuntos, offSet);

        //Como anteriormento ver como cria uma lista de lista em java
        //inicializer zerando tudo em conj
       /*
        self.conj = [[] for i in range(self.qtdConjuntos)]
       */
       for(int i=0; i < qtdConjuntos; i++){
            //FICOU DO JEITO BURRO RESOLVER MAIS TARDE 
            String aux = "";
            int cont = 0;
            //Concaternar zeros 
            while(cont < qtdConjuntos){ 
                aux += "0";
                cont++;
            }
           for(int j=0; j<offSet; j++){
               conj.get(i).add(aux);
           }
       }
    }

    //Ver como implementar um complemento de 2
    //inteiro para binario
    private String int2bs(String s, int n){
        return " ";
    }

    public void acessoMemoria(String endereco){
        String conteudo = endereco;
        String end = int2bs(endereco, 32);
        
        //Ver se pega o ultimo bits é incluso ou nao !!
        String tag = end.substring(0, qtdTag);
        int cont = qtdTag;
        //Parei aqui realmente tenho que ver com o sor se entendi corretamente essa parte !!!
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