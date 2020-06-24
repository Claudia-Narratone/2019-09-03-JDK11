/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.PorzioneAdiacente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	
    	String porzione=boxPorzioni.getValue();
    	if(porzione==null) {
    		txtResult.appendText("ERRORE: Devi selezionare una porzione\n");
    		return;
    	}
    	
    	Integer N;
    	try {
			N=Integer.parseInt(txtPassi.getText());
		} catch (NumberFormatException e) {
			txtResult.appendText("ERRORE: Devi inserire un numero intero\n");
			return;
		}
    	
    	model.cercaCammino(porzione, N);
    	if(model.getCamminoMax()==null) {
    		txtResult.appendText("Non ho trovato un cammino di questa lunghezza\n");
			return;
    	}
    		
    	txtResult.appendText(String.format("trovato un cammino di peso %f", model.getPesoMax()));
    	for(String vertice:model.getCamminoMax()) {
    		txtResult.appendText(vertice+"\n");
    	}
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	String porzione=boxPorzioni.getValue();
    	if(porzione==null) {
    		txtResult.appendText("ERRORE: Devi selezionare una porzione\n");
    		return;
    	}
    	List<PorzioneAdiacente> adiacenti=model.getAdiacenti(porzione);
    	for(PorzioneAdiacente p:adiacenti) {
    		txtResult.appendText(String.format("%s %f\n", p.getPorzione(),p.getPeso()));
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Integer c;
    	try {
			String cString=txtCalorie.getText();
			c=Integer.parseInt(cString);
		} catch (NumberFormatException e) {
			txtResult.appendText("ERRORE: Devi inserire un numero intero\n");
			return;
		}
    	String messaggio=model.creaGrafo(c);
    	txtResult.appendText(messaggio+"\n");
    	
    	boxPorzioni.getItems().clear();
    	boxPorzioni.getItems().addAll(model.getVerticiGrafo());
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
