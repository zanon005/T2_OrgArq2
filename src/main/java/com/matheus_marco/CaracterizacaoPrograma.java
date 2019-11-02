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
    private int[] enderecos;
    

    public CaracterizacaoPrograma(File file) {
        this.file = file;
    }

    public void leArquivo() {
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
	    String line[] =  scanner.nextLine().split(":"); //primeira linha, com "ep: 100" por exemplo.
	    if(line[0] != "ep"){/*formato errado de arquivo;*/}
	    // trow error, lidar na tela!
	    qtdEnderecos = Integer.parseInt(line[1]);
        enderecos = new int[qtdEnderecos];
        LinkedList<String> arquivo = new LinkedList<>();
	    while(scanner.hasNextLine()){
            String lineX = scanner.nextLine();
            arquivo.add(lineX);
        }
        escreveArquivo(arquivo);
    }
    
    public void escreveArquivo(LinkedList<String> arquivo) {
        File file = new File("enderecos.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            int auxI = 0;
            while(auxI != qtdEnderecos){
                fr.write(String.valueOf(auxI));
                fr.write("\n");
                for (String line : arquivo) {
                    String linha[] = line.split(":");
                    //System.out.println(line);
                    //Ver se da match no endereco
                    if(Integer.parseInt(linha[1]) == auxI){
                        //Ver qual eh o tipo de instrucao, 'BI' or 'JI'
                        //System.out.println("Linha = "+linha[0]);
                        if(linha[0].equals("ji")){
                            //System.out.println("Eh JI");
                            auxI = Integer.parseInt(linha[2]);
                            fr.write(String.valueOf(auxI));
                            fr.write("\n");
                        }else{
                            //System.out.println("Eh BI");
                            int valor = new Random().nextInt(100) + 1;
                            if(valor <= Integer.parseInt(linha[3])){
                                auxI = Integer.parseInt(linha[2]);
                                fr.write(String.valueOf(auxI));
                                fr.write("\n");
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