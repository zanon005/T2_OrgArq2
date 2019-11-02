
package com.matheus_marco;
import java.io.File;
public class Main {

    
	public static void main(String args[]){
        File file = new File("caracterizacaoProgram.txt");
        CaracterizacaoPrograma carac = new CaracterizacaoPrograma(file);
        carac.leArquivo();
    }
}