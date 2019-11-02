package com.matheus_marco;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class TelaCaracProgram {

    private Stage mainStage;
    private Scene cenaCaracProgram;

    final FileChooser fileChooser = new FileChooser();
    final Button openFileButton = new Button("Abrir arquivo");
    private File file;

    public TelaCaracProgram(Stage anStage) {
        this.mainStage = anStage;
        this.file = null;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Scene getTelaCaracProgram() {
        GridPane grid = new GridPane();
        grid.setFocusTraversable(true);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        // grid.setGridLinesVisible(true);

        Text sceneTitle = new Text("Caracterizacao do programa!");
        sceneTitle.setId("welcome-text");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0); // 0,0,2,1

        Text helpPickFileMessage = new Text("Escolha o arquivo que caracterizara o programa!");
        grid.add(helpPickFileMessage, 0, 2);

        HBox hbArquivo = new HBox();
        hbArquivo.setAlignment(Pos.CENTER);

        Label arquivo = new Label("Arquivo:");
        // grid.add(arquivo, 0, 3);

        openFileButton.setOnAction(e -> {
            this.file = fileChooser.showOpenDialog(mainStage);
        });
        // grid.add(openFileButton, 1, 3);
        hbArquivo.getChildren().addAll(arquivo, openFileButton);
        grid.add(hbArquivo, 0, 3);

        Button generateAdresses = new Button("Gerar enderecos!");
        grid.add(generateAdresses, 0, 4);
        GridPane.setHalignment(generateAdresses, HPos.CENTER);
        generateAdresses.setOnAction(e -> {
            if (this.file == null) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Arquivo invlálido!");
                alert.setHeaderText("O arquivo selecionado não é válido");
                alert.setContentText("Por favor, selecione um arquivo válido!");
                alert.showAndWait();
            } else {
                // Se o file esta ok, entao tenta gerar a saida!.
                CaracterizacaoPrograma carac = new CaracterizacaoPrograma(file);
                try {
                    carac.leArquivo();
                    TelaCaracCache telaCarac = new TelaCaracCache(mainStage, cenaCaracProgram);
                    Scene scene = telaCarac.getTelaCaracCache();
				    mainStage.setScene(scene);
                } catch (FileNotInCorrectFormatException e1) {
                    e1.printStackTrace();
                }
            }
        });

        cenaCaracProgram = new Scene(grid);
        return this.cenaCaracProgram;
    }

}