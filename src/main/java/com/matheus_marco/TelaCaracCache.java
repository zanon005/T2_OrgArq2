package com.matheus_marco;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TelaCaracCache {

    private Stage mainStage;
    private Scene cenaCaracCache;

    final FileChooser fileChooser = new FileChooser();
    final Button openFileButton = new Button("Abrir arquivo");
    private File file;
    private int sizeBytesCache ;
    private int numPalavrasByBlock;
    private int sizePalavra;
    private int numVias;
    private int numBitsEndereco;
    private PoliticaSubstituicao politicaEscolhida; 

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

        Text sceneTitle = new Text("Definição da memória cache!");
        sceneTitle.setUnderline(true);
        sceneTitle.setId("welcome-text");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BLACK, 20));
        grid.add(sceneTitle, 0, 0); // 0,0,2,1

        /*
         * Deve-se poder informar o tamanho em bytes da cache, o numero de palavras por
         * bloco, o tamanho da palavra e o numero de vias.
         */

        Text helpPickFileMessage = new Text("Escolha o arquivo que caracterizará a hierarquia de memoria!");
        helpPickFileMessage.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        grid.add(helpPickFileMessage, 0, 1);
        
        HBox hbArquivo = new HBox();
        Label arquivo = new Label("Arquivo:");
        arquivo.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));

        openFileButton.setOnAction(e -> {
            this.file = fileChooser.showOpenDialog(mainStage);
        });
        hbArquivo.getChildren().addAll(arquivo, openFileButton);

        GridPane gridCamposTexto = new GridPane();
        gridCamposTexto.setAlignment(Pos.CENTER);
        gridCamposTexto.setHgap(10);
        gridCamposTexto.setVgap(10);
        gridCamposTexto.setPadding(new Insets(25, 25, 25, 25));

        gridCamposTexto.add(hbArquivo, 0, 0);
        Label bytesCache = new Label("Tamanho em bits da cache:");
        bytesCache.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        TextField fieldBytesCache = new TextField();
        gridCamposTexto.add(bytesCache, 0, 1);
        gridCamposTexto.add(fieldBytesCache, 1, 1);

        Label palavrasByBlock = new Label("Número de palavras por bloco:");
        palavrasByBlock.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        TextField fieldPalavrasByBlock = new TextField();
        gridCamposTexto.add(palavrasByBlock, 0, 2);
        gridCamposTexto.add(fieldPalavrasByBlock, 1, 2);

        Label sizeWord = new Label("Tamanho de cada palavra em bits:");
        sizeWord.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        TextField fieldSizeWord = new TextField();
        gridCamposTexto.add(sizeWord, 0, 3);
        gridCamposTexto.add(fieldSizeWord, 1, 3);

        Label nVias = new Label("Número de vias:");
        nVias.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        TextField fieldNumVias = new TextField();
        gridCamposTexto.add(nVias, 0, 4);
        gridCamposTexto.add(fieldNumVias, 1, 4);

        Label nBitsEnderecos = new Label("Número de bits do endereço");
        nBitsEnderecos.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        TextField fieldBitsEndereco = new TextField();
        gridCamposTexto.add(nBitsEnderecos, 0, 5);
        gridCamposTexto.add(fieldBitsEndereco, 1, 5);

        Label labelChoiceBox = new Label("Escolha uma politica:");
        labelChoiceBox.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        String[] opcoesPolitica = {"Politica de subs. Randomica", "Politica de subs. LRU"};
        ComboBox<String> boxPolitica = new ComboBox<>(FXCollections.observableArrayList(opcoesPolitica));
        boxPolitica.setValue("Politica de subs. Randomica");

        gridCamposTexto.add(labelChoiceBox, 0, 6);
        gridCamposTexto.add(boxPolitica, 1, 6);

        grid.add(gridCamposTexto, 0, 2);


        Button buttonGo = new Button("Go!");
        buttonGo.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        buttonGo.setPrefWidth(80);
        buttonGo.setAlignment(Pos.CENTER);
        buttonGo.setOnAction(e -> {
            if (this.file == null) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Arquivo invlálido!");
                alert.setHeaderText("O arquivo selecionado não é válido");
                alert.setContentText("Por favor, selecione um arquivo válido!");
                alert.showAndWait();
            }else{
                try {
                    sizeBytesCache = Integer.parseInt(fieldBytesCache.getText());
                    numPalavrasByBlock = Integer.parseInt(fieldPalavrasByBlock.getText());
                    sizePalavra = Integer.parseInt(fieldSizeWord.getText());
                    numVias = Integer.parseInt(fieldNumVias.getText());
                    numBitsEndereco = Integer.parseInt(fieldBitsEndereco.getText());

                    if(sizeBytesCache %2 != 0 ){throw new NumberFormatException();}
                    if(numPalavrasByBlock %2 != 0 && numPalavrasByBlock !=1 ){throw new NumberFormatException();}
                    if(sizePalavra %2 != 0 ){throw new NumberFormatException();}
                    if(numVias %2 != 0 ){throw new NumberFormatException();}
                    if(numBitsEndereco %2 != 0 ){throw new NumberFormatException();}
                    
                    CaracterizacaoCache caracterizacaoCache = CaracterizacaoCache.getInstance();
                    caracterizacaoCache.setFile(file);
                    caracterizacaoCache.leArquivo();
                    
                    HierarquiaMem hierarquiaMem = HierarquiaMem.getInstance();
                    hierarquiaMem.carregaHierarquiaMem(numBitsEndereco, sizeBytesCache, numPalavrasByBlock, sizePalavra, numVias);
    
                    String politica = boxPolitica.getValue();
                    if(politica.equals("Politica de subs. Randomica")){
                        politicaEscolhida = new Randomica(hierarquiaMem.getNumLinhasConjMemAssociativa());
                        hierarquiaMem.setPolitica(politicaEscolhida);
                    }else{
                        politicaEscolhida = new LFU();
                        politicaEscolhida.init(hierarquiaMem.getNumConjuntosMemAssociativa(), 
                        hierarquiaMem.getNumLinhasConjMemAssociativa());
                        //System.out.println("POLITICA CRIADA... ATUALIZANDO TESTE");
                        //politicaEscolhida.atualizaFrequencia(0, 0);
                        //System.out.println("POLITICA CRIADA... ATUALIZADA!!!!!!!!!!!!!");
    
                        hierarquiaMem.setPolitica(politicaEscolhida);
                    }
                    hierarquiaMem.carregaMemorias();
                    
                    TelaLog telaLog = new TelaLog(mainStage);
                    Scene scene = telaLog.getTelaCaracProgram();
                    mainStage.setScene(scene);
                } catch (FileNotInCorrectFormatException e2) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Arquivo invlálido!");
                    alert.setHeaderText("O arquivo selecionado não é válido");
                    alert.setContentText("Por favor, selecione um que esteja no formato válido!\n"+
                    "CL2:10:20\n"+
                    "CL3:30:20\n"+
                    "MR:100:40\n"+
                    "HD:1000:100");
                    alert.showAndWait();
                } catch (NumberFormatException e3){
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Números inválidos!");
                    alert.setHeaderText("Pelo menos um dos números digitados foi inválido!");
                    alert.setContentText("Por favor, digite números válidos!\n"+
                    "Números PARES >0 pelo menos!\n"+
                    "É possível ter somente uma palavra, digite '1' no  cambo bits palavra para isso!\n"+
                    "Tamanho em bis da cache: 1024\n"+
                    "Número de palavras no bloco: 4\n"+
                    "Tamanho em bits de cada palavra: 32\n"+
                    "Número de vias: 8\n"+
                    "Tamanho em bits do endereco: 32");
                }
            }
        });
            
        grid.add(buttonGo, 0, 3);

        cenaCaracCache = new Scene(grid);
        return this.cenaCaracCache;
    }
}