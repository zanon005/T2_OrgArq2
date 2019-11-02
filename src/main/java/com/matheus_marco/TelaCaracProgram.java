package com.matheus_marco;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class TelaCaracProgram{

    private Stage mainStage;
    private Scene cenaCaracProgram;

    final FileChooser fileChooser = new FileChooser();
    final Button openFileButton = new Button("Abrir arquivo");
    private File file;

    public TelaCaracProgram(Stage anStage){
        this.mainStage = anStage;
    }

    public void setFile(File file){this.file = file;}

    public Scene getTelaCaracProgram(){
        GridPane grid =  new GridPane();
        grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setGridLinesVisible(true);
        
        Text sceneTitle = new Text("Caracterizacao do programa!");
        sceneTitle.setId("welcome-text");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0); //0,0,2,1

        Text helpPickFileMessage = new Text("Escolha o arquivo que caracterizara o programa!");
        grid.add(helpPickFileMessage, 0, 2);

        Label arquivo = new Label("Arquivo:");
        grid.add(arquivo, 0, 3);

        openFileButton.setOnAction(e ->{
            this.file = fileChooser.showOpenDialog(mainStage);
        });
        grid.add(openFileButton, 1, 3);

        Button generateAdresses = new Button("Gerar enderecos!");
        grid.add(generateAdresses, 0, 4);

        cenaCaracProgram = new Scene(grid);
        return cenaCaracProgram;
    }

}