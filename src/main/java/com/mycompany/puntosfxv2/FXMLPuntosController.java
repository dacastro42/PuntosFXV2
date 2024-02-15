/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.puntosfxv2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import modelo.Punto2D;

/**
 * FXML Controller class
 *
 * @author andres_gab.fernandez
 */
public class FXMLPuntosController implements Initializable {

    @FXML
    public Label idLabel1;
    @FXML
    public Canvas idCanvas;
    @FXML
    public TextArea idTextArea1;

    //Crear un contenido gráfico para poder "Pintar" en el canvas
    GraphicsContext g;

    LinkedList<Punto2D> listaPuntos;

    public static Stage s;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        listaPuntos = new LinkedList<>();

        //Pintar bordes del contexto gráfico para que el usuario
        //pueda visibilizar los limites del canvas
        g = idCanvas.getGraphicsContext2D();
        double h = idCanvas.getHeight();
        double w = idCanvas.getWidth();

        g.setStroke(Color.BLUE);
        g.setLineWidth(3);
        g.strokeRect(0, 0, w, h);
    }

    @FXML
    public void guardarArchivo(ActionEvent a) {

        // Convertir contenido de la LinkedList a String
        String contenidoLista = "";
        for (int i = 0; i < listaPuntos.size(); i++) {
            Punto2D get = listaPuntos.get(i);
            contenidoLista += get.toString() + "\n";
        }

        // Convertir el contenido de lista a archivo JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String archivoJson = gson.toJson(contenidoLista);

        // Pedir la ruta y nombre de archivo a guardar
        FileChooser ventanaGuardado = new FileChooser();
        ventanaGuardado.setTitle("Guardar Archivo");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos JSON (*.json)", "*.json");
        ventanaGuardado.getExtensionFilters().add(extFilter);
        File file = ventanaGuardado.showSaveDialog(s);

        // Si los datos de guardado se registraron correctamente
        if (file != null) {

            // Escribir Archivo en formato JSON
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(archivoJson);

                // Mostrar mensaje de éxito al usuario 
                Alert popUpAviso = new Alert(Alert.AlertType.INFORMATION);
                popUpAviso.setTitle("Guardado Exitoso");
                popUpAviso.setHeaderText(null);
                popUpAviso.setContentText("Archivo guardado correctamente en: " + file.getAbsolutePath());
                popUpAviso.showAndWait();

            } catch (IOException e) {
                System.out.println("Error al escribir archivo");
                e.printStackTrace();
            }
        }
    }

    //Metodos
    @FXML
    public void getCoordenada(MouseEvent m) {

        double coordX = m.getX();
        double coordY = m.getY();

        System.out.println("X: " + coordX + ", Y: " + coordY);

        listaPuntos.add(new Punto2D(coordX, coordY));

    }

    @FXML
    public void mostrarPunto(ActionEvent m) {

        String mostrar = "";
        for (int i = 0; i < listaPuntos.size(); i++) {
            Punto2D get = listaPuntos.get(i);
            mostrar += get.toString() + "\n";
        }

        idTextArea1.setText(mostrar);
    }

    @FXML
    public void guardarArchivoEnPC() {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("listaPuntos2D.txt"))) {
            for (Punto2D punto : listaPuntos) {
                bw.write(punto.toString());
                bw.newLine();

            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al escribir en el archivo.");
            e.printStackTrace();
        }
    }

}
