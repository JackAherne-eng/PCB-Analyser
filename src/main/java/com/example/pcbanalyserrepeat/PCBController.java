package com.example.pcbanalyserrepeat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PCBController implements Initializable {

    public static Image inputImage;
    public ImageView imageDrop;
    public MenuItem open;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageDrop.setImage(inputImage);

        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));


    }

    public void chosenSpot(MouseEvent mouseEvent) {
    }


    public void chooseFile(ActionEvent actionEvent) {

        Stage stage = (Stage) ((MenuItem) actionEvent.getTarget()).getParentPopup().getOwnerWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Image Chooser");

        String userDir = System.getProperty("user.home")
                + File.separator + "Google Drive"
                + File.separator + "Waterford"
                + File.separator + "Year 2"
                + File.separator + "Semester 2"
                + File.separator + "PCB-Analyser-Repeat"
                + File.separator + "images";
        File userDirectory = new File(userDir);

        if(!userDirectory.canRead()){
            userDirectory = new File("c:/");
        }   else {
            fileChooser.setInitialDirectory(userDirectory);
        }

        File filePath = new File(fileChooser.showOpenDialog(stage).getAbsolutePath());

        Image image = new Image( "file:///" + filePath, 512, 512, false, false);

        imageDrop.setImage(image);
        inputImage = imageDrop.getImage();
    }

}