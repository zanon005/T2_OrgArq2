package com.matheus_marco;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

public class LRU implements PoliticaSubstituicao{
    
    private int linhas;
    
    private LinkedList<HashMap<Integer,Integer>> mapEnderecos;
    private HierarquiaMem hm = HierarquiaMem.getInstance();
    
    public LRU(int linhas){
        this.linhas = linhas;
        this.mapEnderecos = new LinkedList<>();

        for(int i=0; i< hm.getNumConjuntosMemAssociativa();i++){
            HashMap<Integer,Integer> aux = new HashMap<>();
            System.out.println("TAM CONJUNTOS ->"+hm.getTamConjuntosMemAssociativa());
            for(int j=0; j<hm.getTamConjuntosMemAssociativa(); j++ ){
                //System.out.println("J ESCOLHIDO ->"+j);
                aux.put(j, 0);
            }
            this.mapEnderecos.add(aux);
        }
    }
    
    public void atualizaFrequencia(int conjunto, int indexEndereco){
        mapEnderecos.get(conjunto).put(indexEndereco, mapEnderecos.get(conjunto).get(indexEndereco)+1);
    }

    //@Override
    public int getIndex(int iConj) {
        int minFreq = -1;
        int linhaKey = -1;
        for (Entry<Integer,Integer> entry : mapEnderecos.get(iConj).entrySet()) {
            if(minFreq < entry.getValue().intValue()){
                minFreq = entry.getValue().intValue();
                linhaKey = entry.getKey().intValue();
            }
        }
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