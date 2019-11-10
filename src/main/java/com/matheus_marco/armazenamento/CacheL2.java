package com.matheus_marco.armazenamento;

import java.util.Random;

public class CacheL2{

    private int hitCounter;
    private int missCounter;

    private int probabilityHit;
    private int missPenalty;

    public CacheL2(int probabilityHit, int missPenalty){
        this.probabilityHit = probabilityHit;
        this.missPenalty = missPenalty;
    }

    // Metodo que retorna se o endereco esta presente na memoria.
    public boolean getEndereco(){
        //r.nextInt((max - min) + 1) + min;
        int random = new Random().nextInt((100-1) + 1) + 1;
        boolean achou = random < probabilityHit;
        if(achou){
            hitCounter++;
            return true;
        }else{
            missCounter++;
            return false;
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