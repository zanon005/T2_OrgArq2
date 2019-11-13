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
    //private int tamConjuntosMemAssociativa;
    private int numLinhasConjMemAssociativa;
    
    private int bitsForEndereco;
    private int bitsForPalavra;
    private int bitsForConjunto;
    //private int bitsForTag;

    private String[][][] conjuntoAssociativo; //Conj.MemAssociativa,linhaMemAssociativa, tag e bit validade
    private String[][] memoriaDados;
    private int[] listIndexProxPosDisponivelConjuntos;

    public CacheL1(int missPenalty, PoliticaSubstituicao politicaSubstituicao,int qtdPalavrasNoBloco, 
    int numLinhasMemDado, int numConjuntosMemAssociativa, int tamConjuntosMemAssociativa, int bitsEndereco,
    int numLinhasConjMemAssociativa ){
        this.missPenalty = missPenalty;
        this.politicaSubstituicao = politicaSubstituicao;
        this.qtdPalavrasNoBloco = qtdPalavrasNoBloco;
        this.numLinhasMemDado = numLinhasMemDado;
        this.numConjuntosMemAssociativa = numConjuntosMemAssociativa;
        //this.tamConjuntosMemAssociativa = tamConjuntosMemAssociativa;
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
        
        //Achar tag na mem associativa e gritar a linha correspondente na mem dados
        int iHitTag=-1;
        //System.out.printf("Procurando tag(%s)(%s) para checar hit!\n",bitsTag,enderecoDecimal);
        for(int i=0; i < numLinhasConjMemAssociativa; i++){
            //[QualConjunto][QualLinhaDoConjunto][tag], [0] = tag, [1] = validade
            //System.out.printf("Comparando tag(%s)palavra(%s) com %s\n",bitsTag,memoriaDados[(qualConjunto+1)*i][0],conjuntoAssociativo[qualConjunto][i][0]);
            if(conjuntoAssociativo[qualConjunto][i][0].equals(bitsTag)){
                iHitTag = i;
                //System.out.printf("Achei hit de tag na linha %d\n",iHitTag);
                break;
            }
        }
        //Se iHitTag == -1 entao nao achou a tag!.
        if(iHitTag != -1){
            //Achou tag na mem associativa, acessar memDados com o indice fornecido
            //System.out.println("iHitTag != -1 !!!!!!!");
            //System.out.println("'QualPalavra'-> "+qualPalavra);
            //System.out.println("Palavra armazenada -> "+memoriaDados[iHitTag][qualPalavra]);
            //System.out.println("Palavra que procuro -> "+enderecoDecimal);
            if(memoriaDados[iHitTag+(qualConjunto*numLinhasConjMemAssociativa)][qualPalavra].equals(enderecoDecimal)){
                hitCounter++;
                //System.out.println("DEU HIT");
                politicaSubstituicao.atualizaFrequencia(qualConjunto, iHitTag);
                //System.out.println("PASSOU DO METODO DE ATUALIZAR FREQ");
                return true;
            }
        }
        //System.out.println("DEU MISS");
        missCounter++;
        //Como deu miss entao tem que escrever o dado na memoria, magico overpower
        escreveEndereco(endereco, enderecoDecimal);
        return false;
    }

    //  Se der miss na cacheL1, quando der hit nos outros niveis da hierarquia, entao "write throught" 
    //de volta na cache l1 o dado que foi requisitado.
    public void escreveEndereco(String endereco, String enderecoDecimal){
        //Endereco 50 -> 32 + 16 + 2
        // 00,01,10,11 0->3
        //Num combinacoes  = 2^(numBitsPalavra)
        // 000, 001, 010, 011, 100, 101, 110, 111 0->7
        //000000000000000000000000001100  10
        //000000000000000000000000001100  00 
        //String bitsPalavra = endereco.substring(bitsForEndereco-bitsForPalavra, bitsForEndereco);
        String bitsConjunto = endereco.substring(bitsForEndereco-bitsForPalavra-bitsForConjunto, bitsForEndereco-bitsForPalavra);
        String bitsTag = endereco.substring(0, bitsForEndereco-bitsForPalavra-bitsForConjunto);

        //Para tratar caso soh se tenha 1 conjunto associativo, entao eh sempre o conjunto 'zero'.
        int qualConjunto=0;
        if(bitsConjunto.length() != 0){
            qualConjunto = Integer.parseInt(bitsConjunto, 2);
        }

        //Verificar se mem associativa ta cheia, se tiver, usa politica, se nao tiver escreve prox pos disponivel
        //Se nao ta cheio... entao escreve na prox pos disponivel
        int indexLinhaEscolhida = 0;
        if(listIndexProxPosDisponivelConjuntos[qualConjunto] < numLinhasConjMemAssociativa){
            //System.out.printf("AINDA TEM ESPACO NO CONJUNTO %d\n",qualConjunto);
            //System.out.printf("ESCREVENDO A TAG %s\n",bitsTag);
            conjuntoAssociativo[qualConjunto][listIndexProxPosDisponivelConjuntos[qualConjunto]][0] = bitsTag;
            conjuntoAssociativo[qualConjunto][listIndexProxPosDisponivelConjuntos[qualConjunto]][1] = "1"; //bit validade
            indexLinhaEscolhida = listIndexProxPosDisponivelConjuntos[qualConjunto];
            listIndexProxPosDisponivelConjuntos[qualConjunto]++;
        }else{
            //Ta cheio... usar politica de substituicao
            int indexLinhaASubstituir = politicaSubstituicao.getIndex(qualConjunto);
            //System.out.printf("NAO TEM ESPACO NO CONJUNTO %d\n",qualConjunto);
            //System.out.printf("GUARDANDO TAG NA LINHA %d \n",indexLinhaASubstituir);
            //System.out.printf("ESCREVENDO A TAG %s\n",bitsTag);
            conjuntoAssociativo[qualConjunto][indexLinhaASubstituir][0] = bitsTag;
            conjuntoAssociativo[qualConjunto][indexLinhaASubstituir][1] = "1"; //bit validade
            indexLinhaEscolhida = indexLinhaASubstituir;
        }

        //Primeira palavra que vai na primeira coluna
        // enderecoQueVeio - bitsPalavra(ultimos)
        //Concatenar nesse endereco numBitsForPalavra 0's, se 8 palavras endereco+"000"
        //System.out.println("Endereco recebido para armazenar -> "+endereco);
        //System.out.println("EnderecoDecimal recebido para armazenar -> "+enderecoDecimal);
        //int aux = bitsForPalavra; if(bitsForPalavra == 0){aux = 1;}
        String enderecoSemPalavra = endereco.substring(0, bitsForEndereco-bitsForPalavra);
        //Concatear 'bitsForPalavra' * '0',  se 8 palavras endereco+"000"
        for(int i=0; i<bitsForPalavra;i++){enderecoSemPalavra+="0";}
        
        //Transformar a nova palavra no formato StringDecimal
        enderecoSemPalavra = String.valueOf(Integer.parseInt(enderecoSemPalavra, 2));

        //Escreve o dado na memoria de dados na coluna
        for(int i=0; i<qtdPalavrasNoBloco; i++){
            memoriaDados[indexLinhaEscolhida+(qualConjunto*numLinhasConjMemAssociativa)][i] = enderecoSemPalavra;
            politicaSubstituicao.atualizaFrequencia(qualConjunto, indexLinhaEscolhida);
            enderecoSemPalavra = String.valueOf(Integer.parseInt(enderecoSemPalavra) + 1);
        }
        //System.out.println("Memoria: ");
        //System.out.println("Linhas Memoria: "+numLinhasMemDado);
        //System.out.println("Conjunto -> : "+qualConjunto);
        /*for(int i=0; i<numLinhasMemDado; i++){
            System.out.printf("linhaMem %d -> enderecoArmazenado->%s, freq-> %d\n",i,memoriaDados[i][0], politicaSubstituicao.getFreq(qualConjunto, i));
        }*/
        /*for(int i=0; i<numConjuntosMemAssociativa; i++){
            for(int j=0; j<numLinhasConjMemAssociativa; j++){
                System.out.printf("linhaMem %d -> enderecoArmazenado->%s, freq-> %d\n",((i*numLinhasConjMemAssociativa)+j),memoriaDados[((i*numLinhasConjMemAssociativa)+j)][0],politicaSubstituicao.getFreq(i, j) );
            }
        }*/
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