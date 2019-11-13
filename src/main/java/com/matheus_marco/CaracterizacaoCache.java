package com.matheus_marco;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CaracterizacaoCache {

    private File file;
    private Scanner scanner;
    private int penaltyL2;
    private int penaltyL3;
    private int penaltyMainMemory;
    private int penaltyHD;

    private int probabilityHitL2;
    private int probabilityHitL3;
    private int probabilityHitMainMemory;
    private int probabilityHitHD;

    private static CaracterizacaoCache caracterizacaoCache;

    private CaracterizacaoCache(){}

    public static CaracterizacaoCache getInstance(){
        if(caracterizacaoCache == null){
            caracterizacaoCache = new CaracterizacaoCache();
            return caracterizacaoCache;
        }else{
            return caracterizacaoCache;
        }
    }

    public void setFile(File file){
        this.file = file;
    }
    
    public void leArquivo() throws FileNotInCorrectFormatException {
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
	    String line[] =  scanner.nextLine().trim().split(":"); //primeira linha, com "ep: 100" por exemplo.
	    if(!line[0].equalsIgnoreCase("CL2")){
            /*formato errado de arquivo;*/
            //System.out.println("ERRO CL2");
            throw new FileNotInCorrectFormatException("Arquivo de entrada nao esta no formato correto!");
        }else{
            penaltyL2 = Integer.parseInt(line[1]);
            probabilityHitL2 = Integer.parseInt(line[2]);
            //Linha CL3
            line = scanner.nextLine().trim().split(":");
            if(!line[0].equalsIgnoreCase("CL3")){
                /*formato errado de arquivo;*/
                //System.out.println("ERRO CL3");
                throw new FileNotInCorrectFormatException("Arquivo de entrada nao esta no formato correto!");
            }else{
                penaltyL3 = Integer.parseInt(line[1]);
                probabilityHitL3 = Integer.parseInt(line[2]);
                //Linha memoria principal
                line = scanner.nextLine().trim().split(":");
                if(!line[0].equalsIgnoreCase("MR")){
                    /*formato errado de arquivo;*/
                    //System.out.println("ERRO MR");
                    throw new FileNotInCorrectFormatException("Arquivo de entrada nao esta no formato correto!");
                }else{
                    penaltyMainMemory = Integer.parseInt(line[1]);
                    probabilityHitMainMemory = Integer.parseInt(line[2]);
                    line = scanner.nextLine().trim().split(":");
                    if(!line[0].equalsIgnoreCase("HD")){
                        /*formato errado de arquivo;*/
                        //System.out.println("ERRO HD");
                        throw new FileNotInCorrectFormatException("Arquivo de entrada nao esta no formato correto!");
                    }else{
                        penaltyHD = Integer.parseInt(line[1]);
                        probabilityHitHD = Integer.parseInt(line[2]);
                    }
                }
            }
        }
    }

    public int getPenaltyL2() {return penaltyL2;}

    public int getPenaltyL3() {return penaltyL3;}

    public int getPenaltyMainMemory() {return penaltyMainMemory;}

    public int getPenaltyHD() {return penaltyHD;}

    public int getProbabilityHitL2() {return probabilityHitL2;}

    public int getProbabilityHitL3() {return probabilityHitL3;}

    public int getProbabilityHitMainMemory() {return probabilityHitMainMemory;}

    public int getProbabilityHitHD() {return probabilityHitHD;}
}