package com.matheus_marco.armazenamento;

import com.matheus_marco.PoliticaSubstituicao;

public class CacheL1U{

    private int hitCounter;
    private int missCounter;

    private int missPenalty;
    private PoliticaSubstituicao politicaSubstituicao;
    private int qtdPalavrasNoBloco;

    private int numLinhasConjMemAssociativa;
    
    private int bitsForEndereco;
    private int bitsForPalavra;
    private int bitsForConjunto;
    //private int bitsForTag;

    private String[][][] conjuntoAssociativo; //Conj.MemAssociativa,linhaMemAssociativa, tag e bit validade
    private String[][] memoriaDados;
    private int[] listIndexProxPosDisponivelConjuntos;

    public CacheL1U(int missPenalty, PoliticaSubstituicao politicaSubstituicao,int qtdPalavrasNoBloco, 
    int numLinhasMemDado, int numConjuntosMemAssociativa, int tamConjuntosMemAssociativa, int bitsEndereco,
    int numLinhasConjMemAssociativa ){
        this.missPenalty = missPenalty;
        this.politicaSubstituicao = politicaSubstituicao;
        this.qtdPalavrasNoBloco = qtdPalavrasNoBloco;
        this.bitsForEndereco = bitsEndereco;
        this.numLinhasConjMemAssociativa = numLinhasConjMemAssociativa;

        this.conjuntoAssociativo = new String[numConjuntosMemAssociativa][numLinhasConjMemAssociativa][2];
        this.memoriaDados = new String[numLinhasMemDado][qtdPalavrasNoBloco];
        this.listIndexProxPosDisponivelConjuntos = new int[numConjuntosMemAssociativa];

        //Inicializendo as listas
        for(int i=0; i<numConjuntosMemAssociativa; i++){
            for(int j=0; j<numLinhasConjMemAssociativa; j++){
                conjuntoAssociativo[i][j][0] = "";
                conjuntoAssociativo[i][j][1] = "";
            }
        }
        for(int i=0; i<numLinhasMemDado; i++){
            for(int j=0; j<qtdPalavrasNoBloco; j++){
                memoriaDados[i][j] = "";
            }
        }
        for(int i=0; i<numConjuntosMemAssociativa; i++){
            listIndexProxPosDisponivelConjuntos[i] = 0;
        }
        this.bitsForPalavra = numBitsToRepresent(qtdPalavrasNoBloco);
        this.bitsForConjunto = numBitsToRepresent(numConjuntosMemAssociativa);
        //this.bitsForTag = bitsEndereco - bitsForPalavra - bitsForConjunto;
    }

    // Metodo que retorna se o endereco esta presente na memoria.
    public boolean getEndereco(String endereco, String enderecoDecimal){

        String bitsPalavra = endereco.substring(bitsForEndereco-bitsForPalavra, bitsForEndereco);
        String bitsConjunto = endereco.substring(bitsForEndereco-bitsForPalavra-bitsForConjunto, bitsForEndereco-bitsForPalavra);
        String bitsTag = endereco.substring(0, bitsForEndereco-bitsForPalavra-bitsForConjunto);

        //Verificar se a tag existe no conjunto associativo
        //Para tratar caso soh se tenha 1 palavra por linha, entao eh sempre a palavra 'zero'.
        int qualPalavra = 0;
        if(bitsPalavra.length() != 0){
            qualPalavra = Integer.parseInt(bitsPalavra,2);
        }
        //Para tratar caso soh se tenha 1 conjunto associativo, entao eh sempre o conjunto 'zero'.
        int qualConjunto=0;
        if(bitsConjunto.length() != 0){
            qualConjunto = Integer.parseInt(bitsConjunto, 2);
        }

        System.out.println("Bits de conjunto -> "+bitsConjunto+", valor ->"+qualConjunto);

        int linhaHitNoConjunto = -1;

        System.out.println("Procurando tag -> "+bitsTag+", Do endereco -> "+endereco);

        for(int i=0; i< numLinhasConjMemAssociativa; i++){
            //Se a tag armazenada em conjunto 'qualConjunto' na linha 'i' for igual a tag do endereco recebido...
            System.out.println("Comparando com -> "+conjuntoAssociativo[qualConjunto][i][0]);
            if(conjuntoAssociativo[qualConjunto][i][0].equals(bitsTag)){
                System.out.println("Achou!, linha -> "+i);
                linhaHitNoConjunto = i;
                break;
            }
        }

        //Se eu achei a tag do endereco no conjunto associativo...
        if(linhaHitNoConjunto != -1){
            //Verificar se a palavra armazenada na mem dados eh de fato
            if(memoriaDados[linhaHitNoConjunto+(qualConjunto*numLinhasConjMemAssociativa)][0].equals(bitsTag)){
                //sucesso
                hitCounter++;
                politicaSubstituicao.atualizaFrequencia(qualConjunto, linhaHitNoConjunto);
                return true;
            }
        }else{
            //Nao achei a tag, nao deu hit
            missCounter++;
            escreverTagAndPalavra(endereco,enderecoDecimal, bitsTag, qualConjunto);
            return false;
        }
        return false;
    }

    public void escreverTagAndPalavra(String endereco, String enderecoDecimal, String bitsTag, int qualConjunto){

        //Verificar em que linha vamos colocar essa nova tag, se tiver espaco livre ou usando politica de subts.
        //Se ainda tem espaco livre, escolher proxima linha disponivel!.
        int linhaConjEscolhida = -1;
        if(listIndexProxPosDisponivelConjuntos[qualConjunto] < numLinhasConjMemAssociativa){
            System.out.println("Nao ta cheio...");
            int linha = listIndexProxPosDisponivelConjuntos[qualConjunto];
            conjuntoAssociativo[qualConjunto][linha][0] = bitsTag;
            conjuntoAssociativo[qualConjunto][linha][1] = "1";
            //Incrementar contador de posicao livre para proxima...
            listIndexProxPosDisponivelConjuntos[qualConjunto]++;
            linhaConjEscolhida = linha;
        }else{
            //Se nao tem espaco livre, escolher uma linha do conjunto de acordo com a politica!
            System.out.println("Ta cheio...");
            int linha = politicaSubstituicao.getIndex(qualConjunto);
            conjuntoAssociativo[qualConjunto][linha][0] = bitsTag;
            conjuntoAssociativo[qualConjunto][linha][1] = "1";
            linhaConjEscolhida = linha;
        }

        System.out.printf("Botei a tag %s na linha %d\n",bitsTag, linhaConjEscolhida);

        //Agora escrever o dado na linha da memoria associada a linha escolhida para por a tag no conj associativo.
        //Tirar os nBits de palavra do fim da palavra e colocar zeros no lugar.
        String enderecoSemBitsPalavra = endereco.substring(0, bitsForEndereco-bitsForPalavra);
        //Concatenar os nBits de zero no fim....
        while(enderecoSemBitsPalavra.length() < 32){enderecoSemBitsPalavra+= "0";}
        
        //Converter o endereco ajustado para formato decimal
        enderecoSemBitsPalavra = String.valueOf(Integer.parseInt(enderecoSemBitsPalavra, 2));

        //Colocar essa palavra ajustada comecando na coluna 0, e indo incrementando conforme numero de palavras na bloco.
        for(int i=0; i<qtdPalavrasNoBloco; i++){
            memoriaDados[linhaConjEscolhida+(qualConjunto*numLinhasConjMemAssociativa)][i] = enderecoSemBitsPalavra;
            //Incrementar o edenreco para explorar localidade espacial e pegar os amiguinhos proximos a ele :)
            enderecoSemBitsPalavra= String.valueOf(Integer.parseInt(enderecoSemBitsPalavra) + 1);
        }
        System.out.println("Memoria: ");
        for(int i=0; i<qtdPalavrasNoBloco; i++){
            System.out.printf("linhaMem %d, -> %s\n",linhaConjEscolhida+(qualConjunto*numLinhasConjMemAssociativa),memoriaDados[linhaConjEscolhida+(qualConjunto*numLinhasConjMemAssociativa)][i]);
        }
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

    public int getHitCounter() {
        return hitCounter;
    }

    public int getMissCounter() {
        return missCounter;
    }
}