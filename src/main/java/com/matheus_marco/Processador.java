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

    private int atrasosTotais;
    private int hitsTotais;
    private int missTotais;

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
            System.out.println("P -> "+linha);
            conteudoArq.add(linha); 
            while (linha != null) {
              linha = lerArq.readLine();
              if(linha == null){System.out.println("PARADOXO");break;}
              System.out.println("P -> "+linha);
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
            System.out.println("Endereco processador ->"+endereco);
            String enderecoBinario = formatStr(strDecimalToBin(endereco));
            System.out.println("EnderecoBIN processador ->"+enderecoBinario);
            int auxAtraso = hm.getEndereco(enderecoBinario, endereco);
            if(0 == auxAtraso){
                resultLog.append(endereco+"Hit CacheL1, Atraso = 0"+"\n");
            }else if(cc.getPenaltyL2() == auxAtraso){
                resultLog.append(endereco+"Hit CacheL2, Atraso = "+auxAtraso+"\n");
            }else if(cc.getPenaltyL3() == auxAtraso){
                resultLog.append(endereco+"Hit CacheL3, Atraso = "+auxAtraso+"\n");
            }else if(cc.getPenaltyMainMemory() == auxAtraso){
                resultLog.append(endereco+"Hit MR, Atraso = "+auxAtraso+"\n");
            }else{
                resultLog.append(endereco+"Hit HD, Atraso = "+auxAtraso+"\n");
            }
            atrasosTotais+= auxAtraso;
        }

        long fim= System.currentTimeMillis();
        long time = fim - inicio;
        resultLog.append("**************************************\n");
        resultLog.append("Tamanho total da memoria: "+hm.getNumLinhasMemDados());
        resultLog.append("\nTamanho em Bytes da palavra: "+hm.getSizeBytesWord());
        resultLog.append("\nNúmero palavras p/ bloco: "+hm.getNumWordsByBlock());
        resultLog.append("\nTamanho em Bytes do bloco: "+hm.getTamBlocoMemDado());
        resultLog.append("\nQuantidade de conjuntos: "+hm.getNumVias());
        resultLog.append("\nPolitica de substituicao: "+hm.getNomePolitica());
        resultLog.append("\n:Tamanho dos conjuntos: "+hm.getTamConjuntosMemAssociativa());
        resultLog.append("\n:Quantidade de conjuntos: "+hm.getNumConjuntosMemAssociativa());
        resultLog.append("\n:Quantidade de enderecos buscados: "+conteudoArq.size());
        resultLog.append("\n:Quantidade de Hits totais: "+this.hitsTotais);
        resultLog.append("\n:Percentual de Hits: "+ ((this.hitsTotais/conteudoArq.size())*100)+"%");
        resultLog.append("\n:Quantidade de Miss totais: "+this.missTotais);
        resultLog.append("\n:Percentual de Miss: "+((this.missTotais/conteudoArq.size())*100)+"%");
        resultLog.append("\n:Atraso total gerado na execucao: "+this.atrasosTotais);
        resultLog.append("\n:Tempo medio de acesso: "+time/conteudoArq.size());
        resultLog.append("\n:Tempo total da execucao em segundos: "+time/1000);

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