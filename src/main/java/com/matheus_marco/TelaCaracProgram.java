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
import javafx.scene.control.TextField;
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

        Text sceneTitle = new Text("Caracterizacao do programa!");
        sceneTitle.setUnderline(true);
        sceneTitle.setId("welcome-text");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0); // 0,0,2,1

        Text helpPickFileMessage = new Text("Escolha o arquivo que caracterizara o programa!");
        helpPickFileMessage.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        grid.add(helpPickFileMessage, 0, 2);


        HBox hbMaxEnderecos = new HBox();
        hbMaxEnderecos.setAlignment(Pos.CENTER);
        Label nMaxEnderecos = new Label("Número máximo de endereços:");
        nMaxEnderecos.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        TextField tfMaxEnderecos = new TextField();
        tfMaxEnderecos.setPrefWidth(80);
        hbMaxEnderecos.getChildren().addAll(nMaxEnderecos, tfMaxEnderecos);
        grid.add(hbMaxEnderecos, 0, 3); 

        HBox hbArquivo = new HBox();
        hbArquivo.setAlignment(Pos.CENTER);
        Label arquivo = new Label("Arquivo:");
        arquivo.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));

        openFileButton.setOnAction(e -> {
            this.file = fileChooser.showOpenDialog(mainStage);
        });
        hbArquivo.getChildren().addAll(arquivo, openFileButton);
        grid.add(hbArquivo, 0, 4);

        Button generateAdresses = new Button("Gerar enderecos!");
        generateAdresses.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        grid.add(generateAdresses, 0, 5);
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
                try {
                    //Tenta converter o campo de texto com o numero max de enderecos p/ gerar.
                    int numMaxEnderecos = Integer.parseInt(tfMaxEnderecos.getText());
                    if(numMaxEnderecos < 1){throw new NumberFormatException();}
                    CaracterizacaoPrograma carac = new CaracterizacaoPrograma(file, numMaxEnderecos);
                    carac.leArquivo();
                    TelaCaracCache telaCarac = new TelaCaracCache(mainStage, cenaCaracProgram);
                    Scene scene = telaCarac.getTelaCaracCache();
				    mainStage.setScene(scene);
                } catch (FileNotInCorrectFormatException e1) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Arquivo invlálido!");
                    alert.setHeaderText("O arquivo selecionado não é válido");
                    alert.setContentText("Por favor, selecione um que esteja no formato válido!\n"+
                    "exemplo:\n"+
                    "ep:200\n"+
                    "ji:20:50\n"+
                    "bi:55:25:20\n"+
                    "ji:90:100\n"+
                    "ji:100:110\n"+
                    "bi:85:121:10\n"+
                    "ji:115:80\n"+
                    "   ...     ");
                    alert.showAndWait();
                } catch (NumberFormatException e2) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Número máximo de endereços inválido!");
                    alert.setHeaderText("Digite um número válido para o máximo de endereços gerados");
                    alert.setContentText("Digite valores maiores que zero!, 'nMaxEnderecos >0' ");
                    alert.showAndWait();
                }
            }
        });

        cenaCaracProgram = new Scene(grid);
        return this.cenaCaracProgram;
    }

}