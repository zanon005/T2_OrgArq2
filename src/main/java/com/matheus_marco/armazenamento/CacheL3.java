package com.matheus_marco.armazenamento;

import java.util.Random;

public class CacheL3 {

    private int probabilityHit;
    private int missPenalty;
    
    public CacheL3(int probabilityHit, int missPenalty){
        this.probabilityHit = probabilityHit;
        this.missPenalty = missPenalty;
    }

    // Metodo que retorna se o endereco esta presente na memoria.
    public boolean getEndereco(){
        //r.nextInt((max - min) + 1) + min;
        int random = new Random().nextInt((100-1) + 1) + 1;
        return random < probabilityHit;
    }

    // Metodo que retorna a penalidade para o sistema, caso 'miss' nessa memoria.
    public int getPenalidade(){
        return missPenalty;
    }
}