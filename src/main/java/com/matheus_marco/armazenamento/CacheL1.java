package com.matheus_marco.armazenamento;

import com.matheus_marco.PoliticaSubstituicao;

public class CacheL1{

    private int hitCounter;
    private int missCounter;

    private int missPenalty;
    private PoliticaSubstituicao politicaSubstituicao;
    private int qtdPalavrasNoBloco;
    private int numLinhasMemDado;
    private int numConjuntosMemAssociativa;
    private int tamConjuntosMemAssociativa;
    
    private int bitsForEndereco;
    private int bitsForPalavra;
    private int bitsForConjunto;
    private int bitsForTag;

    private String[][][] conjuntoAssociativo; //Conj.MemAssociativa,linhaMemAssociativa, tag e bit validade
    private String[][] memoriaDados;

    public CacheL1(int missPenalty, PoliticaSubstituicao politicaSubstituicao,int qtdPalavrasNoBloco, 
    int numLinhasMemDado, int numConjuntosMemAssociativa, int tamConjuntosMemAssociativa, int bitsEndereco ){
        this.missPenalty = missPenalty;
        this.politicaSubstituicao = politicaSubstituicao;
        this.qtdPalavrasNoBloco = qtdPalavrasNoBloco;
        this.numLinhasMemDado = numLinhasMemDado;
        this.numConjuntosMemAssociativa = numConjuntosMemAssociativa;
        this.tamConjuntosMemAssociativa = tamConjuntosMemAssociativa;
        this.bitsForEndereco = bitsEndereco;
        this.conjuntoAssociativo = new String[numConjuntosMemAssociativa][tamConjuntosMemAssociativa][2];
        this.memoriaDados = new String[numLinhasMemDado][qtdPalavrasNoBloco];

        this.bitsForPalavra = numBitsToRepresent(qtdPalavrasNoBloco);
        this.bitsForConjunto = numBitsToRepresent(numConjuntosMemAssociativa);
        this.bitsForTag = bitsEndereco - bitsForPalavra - bitsForConjunto;
    }

    // Metodo que retorna se o endereco esta presente na memoria.
    public boolean getEndereco(String endereco, String enderecoDecimal){
        String bitsPalavra = endereco.substring(bitsForEndereco-bitsForPalavra+1, bitsForEndereco+1);
        String bitsConjunto = endereco.substring(bitsForEndereco-bitsForPalavra-bitsForConjunto+1, bitsForEndereco-bitsForPalavra+1);
        String bitsTag = endereco.substring(0, bitsForEndereco-bitsForPalavra-bitsForConjunto+1);

        //Verificar se a tag existe no conjunto associativo
        int qualPalavra = Integer.parseInt(bitsPalavra,2);
        int qualConjunto = Integer.parseInt(bitsConjunto, 2);

        for (String[] linhaMemAssociativa : conjuntoAssociativo[qualConjunto]) {
            //Se achar a tag, entao
            if(linhaMemAssociativa[0].equals(bitsTag)){
                //Talvez verificar bit de validade...?
                //Se a palavra realmente estiver la entao achou HIT!.
                if(memoriaDados[qualConjunto+1 * tamConjuntosMemAssociativa][qualPalavra].equals(enderecoDecimal)){
                    hitCounter++;
                    return true;
                }
            }
        }
        missCounter++;
        return false;
    }

    //Descobrir quantos bits eu preciso para representar certo numero
    private  int numBitsToRepresent(int numero){
        int numBits=0;
        while(true){
            if(Math.pow(2, numBits) >= numero){
                return numBits;
            }else{numBits++;}
        }
    }

    // Metodo que retorna a penalidade para o sistema, caso 'miss' nessa memoria.
    public int getPenalidade(){
        return missPenalty;
    }
}