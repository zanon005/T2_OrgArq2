package com.matheus_marco;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Processador {

    private static Processador processador;
    private LinkedList<String> conteudoArq = new LinkedList<>();
    HierarquiaMem hm = HierarquiaMem.getInstance();

    private Processador(){}

    public static Processador getInstance(){
        if(processador == null){
            processador = new Processador();
            return processador;
        }else{
            return processador;
        }
    }

    public void leArquivo(){
        try {
            //Colocar para abrir pelo arquivo ver com o zanon
            FileReader arq = new FileReader("enderecos.txt");
            BufferedReader lerArq = new BufferedReader(arq);
       
            String linha = lerArq.readLine();
            conteudoArq.add(linha); 
            while (linha != null) {
              linha = lerArq.readLine();
              conteudoArq.add(linha);
            }
            arq.close();
          } catch (IOException e) {
              System.err.printf("Erro na abertura do arquivo: %s.\n",
                e.getMessage());
          }
    }

    public String start(){
        String result = " ";
        int demorou = 0;
        long inicio= System.currentTimeMillis();
        for(int i=0; i < conteudoArq.size(); i++){
            //nao sei o que é o enderecoDecimal
            demorou += hm.getEndereco(conteudoArq.get(i), enderecoDecimal);
        }
        long fim= System.currentTimeMillis();

        long time = fim - inicio;
        
    }

    
}