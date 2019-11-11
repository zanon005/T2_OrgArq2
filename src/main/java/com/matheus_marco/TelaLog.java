package com.matheus_marco;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TelaLog {

    private Stage mainStage;
    private Scene cenaTelaLog;

    private int tamBytesCache;
    private int numWordsByBlock;
    private int sizeBytesWord;
    private int numVias;
    private int numBitsEnderecos; //Sor definiu como 32 no pdf.
    private Processador processador;

    public TelaLog(Stage anStage) {
        this.mainStage = anStage;
    }

    public Scene getTelaCaracProgram() {
        
        GridPane grid = new GridPane();
        grid.setFocusTraversable(true);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Log da execução do programa!");
        sceneTitle.setUnderline(true);
        sceneTitle.setId("welcome-text");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BLACK, 20));
        grid.add(sceneTitle, 0, 0); // 0,0,2,1

        Text log = new Text();

        //Desculpa cops pai
        processador = Processador.getInstance();
        processador.leArquivo();
        log.setText(processador.start());
        
        grid.add(log, 0, 1);

        cenaTelaLog = new Scene(grid);
        return this.cenaTelaLog;
    }
    

}