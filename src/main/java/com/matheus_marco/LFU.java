package com.matheus_marco;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map.Entry;

public class LFU implements PoliticaSubstituicao{
    
    private int linhas;
    
    private LinkedList<HashMap<Integer,Integer>> mapEnderecos;
    private LinkedList<ArrayList<Integer>> enderecos;
    private HierarquiaMem hm = HierarquiaMem.getInstance();
    
    public LFU(int linhas){
        this.linhas = linhas;
        this.mapEnderecos = new LinkedList<>();
        this.enderecos = new LinkedList<>();

        for(int i=0; i< hm.getNumConjuntosMemAssociativa();i++){
            //HashMap<Integer,Integer> aux = new HashMap<>();
            ArrayList<Integer> aux = new ArrayList<>(hm.getNumLinhasConjMemAssociativa());
            System.out.println("TAM lihhas CONJUNTOS ->"+hm.getNumLinhasConjMemAssociativa());
            for(int j=0; j<hm.getNumLinhasConjMemAssociativa(); j++ ){
                System.out.println("FOR LFU");
                aux.add(0);
            }
            System.out.println("CRIOU ARRAYLIST");
            this.enderecos.add(aux);
            System.out.println("ADICIONOU ARRAYLIST");
        }

    }
    
    public void atualizaFrequencia(int conjunto, int indexEndereco){
        //mapEnderecos.get(conjunto).put(indexEndereco, (mapEnderecos.get(conjunto).get(indexEndereco)+1) );
        System.out.println("indexEndereco"+ indexEndereco);
        System.out.println(enderecos.get(conjunto).get(indexEndereco));
        int aux =  enderecos.get(conjunto).get(indexEndereco)+1;
        enderecos.get(conjunto).add(indexEndereco, aux);
        System.out.println(enderecos.get(conjunto).get(indexEndereco));
       
    }

    //@Override
    public int getIndex(int iConj) {
        int minFreq = -1;
        int linhaKey = -1;
        System.out.println("POLITIA****************************************************");
        for(int i=0; i< enderecos.get(iConj).size(); i++){
            if(minFreq < enderecos.get(iConj).get(i)  ){
                minFreq = enderecos.get(iConj).get(i);
                linhaKey = i;
            }
        }
        System.out.println("LinhaEscolhida->"+linhaKey+" ,Freq->"+minFreq);
        System.out.println("POLITIA****************************************************");
        return linhaKey;
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