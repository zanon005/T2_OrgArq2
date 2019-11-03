package com.matheus_marco;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
        sceneTitle.setUnderline(true);
        sceneTitle.setId("welcome-text");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BLACK, 20));
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
        onlyNumbers.setFont(Font.font("Tahoma", FontWeight.BLACK, 16));
        grid.add(onlyNumbers, 0, 1);

        GridPane gridCamposTexto = new GridPane();
        gridCamposTexto.setAlignment(Pos.CENTER);
        gridCamposTexto.setHgap(10);
        gridCamposTexto.setVgap(10);
        gridCamposTexto.setPadding(new Insets(25, 25, 25, 25));

        Label bytesCache = new Label("Tamanho em bytes da cache:");
        bytesCache.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        TextField fieldBytesCache = new TextField();
        gridCamposTexto.add(bytesCache, 0, 0);
        gridCamposTexto.add(fieldBytesCache, 1, 0);

        Label palavrasByBlock = new Label("Número de palavras por bloco:");
        palavrasByBlock.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        TextField fieldPalavrasByBlock = new TextField();
        gridCamposTexto.add(palavrasByBlock, 0, 1);
        gridCamposTexto.add(fieldPalavrasByBlock, 1, 1);

        Label sizeWord = new Label("Tamanho de cada palavra:");
        sizeWord.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        TextField fieldSizeWord = new TextField();
        gridCamposTexto.add(sizeWord, 0, 2);
        gridCamposTexto.add(fieldSizeWord, 1, 2);

        Label nVias = new Label("Número de vias:");
        nVias.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        TextField fieldNumVias = new TextField();
        gridCamposTexto.add(nVias, 0, 3);
        gridCamposTexto.add(fieldNumVias, 1, 3);
        
        grid.add(gridCamposTexto, 0, 2);

        Button buttonGo = new Button("Go!");
        buttonGo.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        buttonGo.setPrefWidth(80);
        buttonGo.setAlignment(Pos.CENTER);
        grid.add(buttonGo, 0, 3);

        cenaCaracCache = new Scene(grid);
        return this.cenaCaracCache;
    }
}