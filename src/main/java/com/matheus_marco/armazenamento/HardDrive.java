package com.matheus_marco.armazenamento;

import java.util.Random;

public class HardDrive {

    private int hitCounter;
    //private int missCounter;
    
    private int probabilityHit;
    private int missPenalty;

    public HardDrive(int probabilityHit, int missPenalty){
        this.probabilityHit = probabilityHit;
        this.missPenalty = missPenalty;
    }

    // Metodo que retorna se o endereco esta presente na memoria.
    public boolean getEndereco(){
        //int random = new Random().nextInt((100-1) + 1) + 1;
        boolean achou = true; /*SOH CONFIA random < probabilityHit;*/
        if(achou){
            this.hitCounter++;
            return true;
        }else{
            //missCounter++;
            return false;
        }
    }

    // Metodo que retorna a penalidade para o sistema, caso 'miss' nessa memoria.
    public int getPenalidade(){
        return missPenalty;
    }

    public int getHitCounter() {
        return this.hitCounter;
    }
}