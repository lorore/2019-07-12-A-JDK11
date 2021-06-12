/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Food2;
import it.polito.tdp.food.model.Model;
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

    @FXML // fx:id="txtPorzioni"
    private TextField txtPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalorie"
    private Button btnCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="boxFood"
    private ComboBox<Food> boxFood; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...");
    	String porzioni=this.txtPorzioni.getText();
    	if(porzioni.isEmpty()) {
    		this.txtResult.setText("Nessun numero inserito");
    		return;
    	}
    	Integer p;
    	try {
    		p=Integer.parseInt(porzioni);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Non hai inserito un numero intero");
    		return;
    	}
    	
    	if(p<0) {
    		this.txtResult.setText("Numero non può essere negativo!");
    		return;
    	}
    	
    	String result=this.model.creaGrafo(p);
    	this.txtResult.appendText("\n");
    	this.txtResult.appendText(result);
    	this.boxFood.getItems().addAll(this.model.getVertici());
    	this.btnSimula.setDisable(false);
    	this.btnCalorie.setDisable(false);

    }
    
    @FXML
    void doCalorie(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Analisi calorie...");
    	if(this.boxFood.getValue()==null) {
    		this.txtResult.setText("Inserire un cibo!");
    		return;
    	}
    	
    	Food f=this.boxFood.getValue();
    	List<Food2> result=this.model.getTop5(f);
    	this.txtResult.appendText("\n");
    	if(result.size()>=5) {
    	for(int i=0; i<5; i++){
    		this.txtResult.appendText(result.get(i).toString()+"\n");
	  	
    	}
    	}else {
    		this.txtResult.appendText("Sono disponibili meno di 5 vicini"+"\n");
    		for(int i=0; i<result.size(); i++){
        		this.txtResult.appendText(result.get(i).toString()+"\n");
    	  	
        	}
    	}
    	}

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Simulazione...");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCalorie != null : "fx:id=\"btnCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.btnSimula.setDisable(true);
    	this.btnCalorie.setDisable(true);
    }
}
