package com.matheus_marco;

public class Processador{

    private static Processador processador;

    private Processador(){}

    public static Processador getInstance(){
        if(processador == null){
            processador = new Processador();
            return processador;
        }else{
            return processador;
        }
    }

    
}