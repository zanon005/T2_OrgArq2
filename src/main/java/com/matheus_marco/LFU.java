package com.matheus_marco;

public class LFU implements PoliticaSubstituicao{

    //private static LFU LFU; 
    private int numLinhasNosConjuntos;
    //Lista de conjuntos, lista de (enderecos,frequencia de acesso)
    private int[][] listEnderecos;

    public LFU(){}

    /*public static LFU getInstance(){
        if(LFU == null){
            LFU = new LFU();
            return LFU;
        }else{
            return LFU;
        }
    }*/

    public void init(int numeroDeConjuntos, int numLinhasNosConjuntos){
        this.numLinhasNosConjuntos = numLinhasNosConjuntos;
        this.listEnderecos = new int[numeroDeConjuntos][numLinhasNosConjuntos];
        //Criando a lista
        //Para cada conjunto...
        for(int i=0; i<numeroDeConjuntos; i++){
            //Inicializando a lista de enderecos
            for(int j=0; j<numLinhasNosConjuntos; j++){
                //Frequencia do conjunto 'i', do endereco 'j' inicilizada com zero.
                listEnderecos[i][j] = 0;
            }
        }
    }

    //Metodo que atualiza a frequencia de um certo endereco de um certo conjunto.
    public void atualizaFrequencia(int qualConjunto, int endereco){
        //System.out.println("METODO POLITICA ATUALIZA!!");
        //System.out.printf("Incrementando endereco %d, conjunto %d, com freq %d\n", endereco, qualConjunto, listEnderecos[qualConjunto][endereco]);
        listEnderecos[qualConjunto][endereco]++;
    }

    //Metodo que retorna o endereco que tiver a menor frequencia de acesso de um certo conjunto.
    public int getIndex(int qualConjunto){
        int endereco = -1, minFreq = 999999;
        //Percorrer todo o conjunto procurando o endereco com menor frequencia
        for(int i=0; i< numLinhasNosConjuntos; i++){
            //Se a frequencia do endereco q estou iterando for menor que a antiga...
            if(listEnderecos[qualConjunto][i] < minFreq){
                endereco = i; minFreq = listEnderecos[qualConjunto][i];
            }
        }
        //Retorna o endereco com a menor frequencia
        //System.out.printf("Endereco com menor frequencia(%d) do conjunto %d eh: %d\n",minFreq ,qualConjunto, endereco);
        return endereco;
    }

    public int getFreq(int qualConjunto, int endereco){
        //System.out.println("OLAAAA");
        return listEnderecos[qualConjunto][endereco];
    }

    public String toString(){
        return "LFU";
    }
}