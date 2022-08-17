package com.example.pcbanalyserrepeat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
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
    public TextField nameText;
    public Label saturationLabel;
    public Label redLabel;
    public Label greenLabel;
    public Label blueLabel;
    public Label alphaLabel;
    public Label hueLabel;
    public Label brightnessLabel;
    public ComboBox<Object> partStore;
    private PixelReader reader;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageDrop.setImage(inputImage);

        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));


    }

    public void chosenSpot(MouseEvent mouseEvent) {
        imageDrop.setOnMouseClicked(e -> {
            int x = (int) e.getX();
            int y = (int) e.getY();

            reader = inputImage.getPixelReader();

            redLabel.setText(String.format("%.2f", reader.getColor(x, y).getRed()));
            greenLabel.setText(String.format("%.2f", reader.getColor(x, y).getGreen()));
            blueLabel.setText(String.format("%.2f", reader.getColor(x, y).getBlue()));
            alphaLabel.setText("" + reader.getColor(x, y).getOpacity());
            hueLabel.setText(String.format("%.2f", reader.getColor(x, y).getHue()));
            saturationLabel.setText(String.format("%.2f", reader.getColor(x, y).getSaturation()));
            brightnessLabel.setText(String.format("%.2f", reader.getColor(x, y).getBrightness()));

            Parts p;
            Statics.parts.addElement( p = new Parts(nameText.getText(),
                    Double.parseDouble(redLabel.getText()),
                    Double.parseDouble(greenLabel.getText()),
                    Double.parseDouble(blueLabel.getText()),
                    Integer.parseInt(alphaLabel.getText()),
                    Double.parseDouble(hueLabel.getText()),
                    Double.parseDouble(saturationLabel.getText()),
                    Double.parseDouble(brightnessLabel.getText()))
            );

            partStore.getItems().clear();
            for(int i = Statics.parts.size() - 1; i >= 0; i--){
                partStore.getItems().add(Statics.parts.get(i).getName());
            }
        });
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