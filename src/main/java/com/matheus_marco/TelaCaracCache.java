package com.matheus_marco;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TelaCaracCache {

    private Stage mainStage;
    private Scene cenaCaracCache;

    public TelaCaracCache(Stage anStage, Scene scena) {
        this.mainStage = anStage;
        this.cenaCaracCache =  scena;
    }

    public Scene getTelaCaracCache() {
        GridPane grid = new GridPane();
        grid.setFocusTraversable(true);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        // grid.setGridLinesVisible(true);

        Text sceneTitle = new Text("Definição da memória cache!");
        sceneTitle.setId("welcome-text");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0); // 0,0,2,1

        /*
         * Deve-se poder informar o tamanho em bytes da cache, o numero de palavras por
         * bloco, o tamanho da palavra e o numero de vias.
         */

        int sizeBytesCache = 0;
        int numPalavrasByBlock = 0;
        int sizePalavra = 0;
        int numVias = 0;

        Text onlyNumbers = new Text("Digite somente números ou não irá poder prosseguir!");
        onlyNumbers.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(onlyNumbers, 0, 1);

        Label BytesCache = new Label("Tamanho em bytes da cache:");
        TextField textBytesCache = new TextField();
        grid.add(BytesCache, 0, 2);
        grid.add(textBytesCache, 1, 2);

        Label palavrasByBlock = new Label("Numero de palavras por bloco:");
        TextField textpalavrasByBlock = new TextField();
        grid.add(palavrasByBlock, 0, 3);
        grid.add(textpalavrasByBlock, 1, 3);

        Label sizeWord = new Label("Tamanho de cada palavra:");
        TextField textsizeWord = new TextField();
        grid.add(sizeWord, 0, 4);
        grid.add(textsizeWord, 1, 4);

        Label nVias = new Label("Numero de vias:");
        TextField textnVias = new TextField();
        grid.add(nVias, 0, 5);
        grid.add(textnVias, 1, 5);

        Button buttonGo = new Button("Go!");
        grid.add(buttonGo, 0, 6);

        cenaCaracCache = new Scene(grid);
        return this.cenaCaracCache;
    }
}