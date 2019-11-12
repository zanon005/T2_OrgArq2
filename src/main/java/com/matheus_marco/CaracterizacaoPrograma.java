package com.matheus_marco;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class CaracterizacaoPrograma {

    private File file;
    Scanner scanner;
    private int qtdEnderecos;
    private int numMaxEnderecos;
    
    public CaracterizacaoPrograma(File file, int numMaxEnderecos) {
        this.file = file;
        this.numMaxEnderecos = numMaxEnderecos;
    }

    public void leArquivo() throws FileNotInCorrectFormatException {
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
	    String line[] =  scanner.nextLine().split(":"); //primeira linha, com "ep: 100" por exemplo.
	    if(!line[0].equals("ep")){
            /*formato errado de arquivo;*/
            throw new FileNotInCorrectFormatException("Arquivo de entrada nao esta no formato correto!");
        }
	    // trow error, lidar na tela!
	    qtdEnderecos = Integer.parseInt(line[1]);
        LinkedList<String> arquivo = new LinkedList<>();
	    while(scanner.hasNextLine()){
            String lineX = scanner.nextLine();
            String aux[] = lineX.split(":");
            if(aux[0].equals("ep")){
                System.out.println("ERRO EP DUAS VEZES");
                throw new FileNotInCorrectFormatException("Arquivo de entrada nao esta no formato correto!");
            }else if(!aux[0].equals("ji") && !aux[0].equals("bi")){
                System.out.println("ERRO NAO EH JI NEM BI");
                throw new FileNotInCorrectFormatException("Arquivo de entrada nao esta no formato correto!");
            }
            arquivo.add(lineX);
        }
        escreveArquivo(arquivo);
    }
    
    public void escreveArquivo(LinkedList<String> arquivo) {
        File file = new File("enderecos2.txt");
        FileWriter fr = null;
        try {
            int numTotalLinhas = 0;
            fr = new FileWriter(file);
            int auxI = 0;
            while(auxI != qtdEnderecos){
                fr.write(String.valueOf(auxI));
                if(auxI+1 != qtdEnderecos){ fr.write("\n");}
                numTotalLinhas++;
                //Verificacao se numero max de enderecos nao foi extrapolado!.
                if(numTotalLinhas >= numMaxEnderecos){break;}
                for (String line : arquivo) {
                    String linha[] = line.split(":");
                    //Ver se da match no endereco
                    if(Integer.parseInt(linha[1]) == auxI){
                        //Ver qual eh o tipo de instrucao, 'BI' or 'JI'
                        if(linha[0].equals("ji")){
                            auxI = Integer.parseInt(linha[2]);
                            fr.write(String.valueOf(auxI));
                            if(auxI+1 != qtdEnderecos){ fr.write("\n");}
                            numTotalLinhas++;
                            //Verificacao se numero max de enderecos nao foi extrapolado!.
                            if(numTotalLinhas >= numMaxEnderecos){break;}
                        }else{
                            int valor = new Random().nextInt(100) + 1;
                            if(valor <= Integer.parseInt(linha[3])){
                                auxI = Integer.parseInt(linha[2]);
                                fr.write(String.valueOf(auxI));
                                if(auxI+1 != qtdEnderecos){ fr.write("\n");}
                                numTotalLinhas++;
                                //Verificacao se numero max de enderecos nao foi extrapolado!.
                                if(numTotalLinhas >= numMaxEnderecos){break;}
                            }
                        }
                    }
                }
                auxI++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}