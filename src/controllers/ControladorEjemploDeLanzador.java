package controllers;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperPrint;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import javafx.scene.control.TextArea;

public class ControladorEjemploDeLanzador {

    @FXML
    private TextField codMedico;

    @FXML
    private TextField dirPaciente;

    @FXML
    private TextField espMedico;

    @FXML
    private TextField nomMedico;

    @FXML
    private TextField nomPaciente;

    @FXML
    private TextField numPaciente;

    @FXML
    private TextArea tratamiento;

    @FXML
    void exit(ActionEvent event) {
    	Platform.exit();
    }

    @FXML
    void generar(ActionEvent event) {

    	//si no necesitamos conexión a bbdd
    	//ConexionDB con = new ConexionDB();
    	
    	
    	ArrayList<String> errors = new ArrayList<>();

    	
    	HashMap<String, Object> parameters = new HashMap<String, Object>();
    	if (!nomMedico.getText().isBlank())
    		parameters.put("nomMedico", nomMedico.getText());
    	else
    		errors.add("El nombre del médico debe ser rellenado");
                
        if (!espMedico.getText().isBlank())
        	parameters.put("espMedico", espMedico.getText());
    	else
    		errors.add("El nombre del médico debe ser rellenado");
        
        try {
        	Integer.parseInt(codMedico.getText());
        	parameters.put("numMedico", codMedico.getText());
        } catch (NumberFormatException e) {
        	errors.add("El codigo del médico debe ser un numero");
        }
        try {
        	Integer.parseInt(numPaciente.getText());
        	parameters.put("numPaciente", numPaciente.getText());
        } catch (NumberFormatException e) {
        	errors.add("El numero del paciente debe ser un numero");
        }
        
        if (!nomPaciente.getText().isBlank())
            parameters.put("nomPaciente", nomPaciente.getText());
    	else
    		errors.add("El nombre del paciente debe ser rellenado");
        
        if (!dirPaciente.getText().isBlank())
        	parameters.put("dirPaciente", dirPaciente.getText());
        else
    		errors.add("La direccion del paciente debe ser rellenada");
        
        if (!tratamiento.getText().isBlank())
        	parameters.put("tratamiento", tratamiento.getText());
    	else
    		errors.add("El tratamiento del paciente debe ser rellenado");
    	
        if (!errors.isEmpty()) {
        	showErrorDialog(errors);
        	return;
        }
        
        
    	//llamada con parametros y bbdd
    	//JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/reports/informe.jasper"));
		try {
			JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("informe.jasper"));
	        JasperPrint jprint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
	        JasperViewer viewer = new JasperViewer(jprint, false);
	        viewer.setVisible(true);
		} catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("ERROR");
            alert.setContentText("Ha ocurrido un error");
            alert.showAndWait();
            e.printStackTrace();
        }
        

    }

    @FXML
    void limpiar(ActionEvent event) {
    	codMedico.setText("");
    	dirPaciente.setText("");
    	espMedico.setText("");
    	nomMedico.setText("");
    	nomPaciente.setText("");
    	numPaciente.setText("");
    	tratamiento.setText("");
    	
    }
    
    private void showErrorDialog(ArrayList<String> errors) {
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("ERROR");
    	alert.setHeaderText("Faltan datos por rellenar o están mal rellenados.");
    	String err="";
    	for (String e : errors)
    		err += e + "\n";
    	
    	alert.setContentText(err);

    	alert.showAndWait();
    }

}

