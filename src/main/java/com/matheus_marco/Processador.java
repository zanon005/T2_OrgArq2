package com.matheus_marco;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Processador {

    private static Processador processador;
    private LinkedList<String> conteudoArq = new LinkedList<>();
    private HierarquiaMem hm = HierarquiaMem.getInstance();
    private CaracterizacaoCache cc = CaracterizacaoCache.getInstance();

    private double atrasosTotais;
    private double hitsTotais;
    private double missTotais;

    private Processador(){}

    public static Processador getInstance(){
        if(processador == null){
            processador = new Processador();
            return processador;
        }else{
            return processador;
        }
    }

    //Lendo os enderecos gerados!
    public void leArquivo(){
        try {
            FileReader arq = new FileReader("enderecos.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            conteudoArq.add(linha); 
            while (linha != null) {
              linha = lerArq.readLine();
              if(linha == null){System.out.println("PARADOXO");break;}
              conteudoArq.add(linha);
            }
            arq.close();
          } catch (IOException e) {
              System.err.printf("Erro na abertura do arquivo: %s.\n",
                e.getMessage());
          }
    }

    public String start(){
        StringBuilder resultLog = new StringBuilder();
        long inicio= System.currentTimeMillis();
        //Para cada endereco no arquivo de enderecos gerados...
        for (String endereco : conteudoArq) {
            //(EnderecoBinario, EndercoNormal)
            String enderecoBinario = formatStr(strDecimalToBin(endereco));
            int auxAtraso = hm.getEndereco(enderecoBinario, endereco);
            if(0 == auxAtraso){
                resultLog.append("Endereco: "+endereco+" -> Hit CacheL1, Atraso = 0"+"\n");
            }else if(cc.getPenaltyL2() == auxAtraso){
                resultLog.append("Endereco: "+endereco+" -> Hit CacheL2, Atraso = "+auxAtraso+"\n");
            }else if(cc.getPenaltyL3() == auxAtraso){
                resultLog.append("Endereco: "+endereco+" -> Hit CacheL3, Atraso = "+auxAtraso+"\n");
            }else if(cc.getPenaltyMainMemory() == auxAtraso){
                resultLog.append("Endereco: "+endereco+" -> Hit MR, Atraso = "+auxAtraso+"\n");
            }else{
                resultLog.append("Endereco: "+endereco+" -> Hit HD, Atraso = "+auxAtraso+"\n");
            }
            atrasosTotais+= auxAtraso;
        }

        long fim= System.currentTimeMillis();
        long time = fim - inicio;

        this.hitsTotais = hm.getHitsL1()+hm.getHitsL2()+hm.getHitsL3()+hm.getHitsMR()+hm.getHitsHD();
        this.missTotais = hm.getMissL1()+hm.getMissL2()+hm.getMissL3()+hm.getMissMR();
        resultLog.append("************************************************************************");
        resultLog.append("\nTamanho total da memoria cache em bits: "+hm.getTamBitsCache());
        resultLog.append("\nTamanho total da memoria cache em linhas: "+hm.getNumLinhasMemDados());
        resultLog.append("\nTamanho em bits da palavra: "+hm.getSizeBytesWord());
        resultLog.append("\nNúmero palavras por bloco: "+hm.getNumWordsByBlock());
        resultLog.append("\nTamanho em bits do bloco: "+hm.getTamBlocoMemDado());
        resultLog.append("\nQuantidade de vias: "+hm.getNumVias());
        resultLog.append("\nPolitica de substituicao: "+hm.getNomePolitica());
        resultLog.append("\nTamanho dos conjuntos associativos em bits: "+hm.getTamConjuntosMemAssociativa());
        resultLog.append("\nQuantidade de conjuntos associativos: "+hm.getNumConjuntosMemAssociativa());
        resultLog.append("\nQuantidade de enderecos buscados: "+conteudoArq.size());
        resultLog.append("\nQuantidade de Hits totais: "+this.hitsTotais);
        resultLog.append("\nPercentual de Hits: "+ ((this.hitsTotais/conteudoArq.size())*100)+"%");
        resultLog.append("\nQuantidade de Miss totais: "+this.missTotais);
        resultLog.append("\nPercentual de Miss: "+((this.missTotais/conteudoArq.size())*100)+"%");
        resultLog.append("\nAtraso total gerado na execucao: "+this.atrasosTotais);
        resultLog.append("\nTempo medio de acesso: "+(time/1000.0) / (conteudoArq.size()+0.0) );
        resultLog.append("\nTempo total da execucao em segundos: "+time/1000.0);

        return resultLog.toString();
    }

    /*
        Informar o número de conjuntos associativos
         O formato de interpretação do endereço (tag, conjunto, bloco)
         O número/taxa de acerto por nível hierárquico
         O número/taxa de falha por nível hierárquico
         O tempo médio de acesso -> tempoTotal / numeroAcessosTotais
         O tempo total de execução -> tempoTotal
    */

    //PODE DAR PROBLEMA CASO NUMERO STRDECIMAL FOR MAIOR QUE UM INTEIRO!
    public String strDecimalToBin(String strDecimal){
        String strBin = Integer.toBinaryString(Integer.parseInt(strDecimal));
        return strBin;
    }

    public String formatStr(String strUnformated){
        int strSize = strUnformated.length();
        int zerosToComplete = hm.getNumBitsEndereco() - strSize;
        if(zerosToComplete < 0){/*ERRO*/}
        StringBuilder str = new StringBuilder(strUnformated);
        for(int i=0; i<zerosToComplete; i++){
            str.insert(0, "0");
        }
        return str.toString();
    }

    public String intToBinary(int n){
        String s = "";
        while (n > 0){
            s =  ( (n % 2 ) == 0 ? "0" : "1") +s;
            n = n / 2;
        }
        return formatStr(s);
    }

    
}