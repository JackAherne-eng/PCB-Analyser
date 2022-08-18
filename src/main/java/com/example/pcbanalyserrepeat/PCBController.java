package com.example.pcbanalyserrepeat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
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
    public int[] imageSize;
    public Button process;
    public ImageView processedImage;

    private PixelReader reader;

    ArrayList<Integer> roots = new ArrayList<>();
    private disjointSet S;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageDrop.setImage(inputImage);

        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));


    }

    /*
     * Exits the program
     * */
    public void exitProg(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void chosenSpot(MouseEvent mouseEvent) {
        imageDrop.setOnMouseClicked(e -> {
            int x = (int) e.getX();
            int y = (int) e.getY();

            reader = inputImage.getPixelReader();

            redLabel.setText(String.format("%.2f", reader.getColor(x, y).getRed()));
            greenLabel.setText(String.format("%.2f", reader.getColor(x, y).getGreen()));
            blueLabel.setText(String.format("%.2f", reader.getColor(x, y).getBlue()));
            alphaLabel.setText(String.valueOf(reader.getArgb(x, y)));
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
                + File.separator + "OneDrive"
                + File.separator + "Documents"
                + File.separator + "GitHub"
                + File.separator + "PCB-Analyser"
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

    public void processing(MouseEvent mouseEvent) {
        inputImage = imageDrop.getImage();

        WritableImage writableImage = new WritableImage(reader,(int) inputImage.getWidth(),(int) inputImage.getHeight());

        int width = (int) inputImage.getWidth();
        int height = (int) inputImage.getHeight();
        PixelWriter writer = writableImage.getPixelWriter();

        imageSize = new int[width * height];

        Parts p = Statics.parts.search(parts -> parts.getName().equals(partStore.getValue().toString()));
        int id = 0;

        for(int ht = 0; ht < height; ht++){
            for(int wh = 0; wh < width; wh++){

                Color color = reader.getColor(wh,ht);
                double red = p.getRed();
                double green = p.getGreen();
                double blue = p.getBlue();
                double hue = p.getHue();

                double red1 = color.getRed();
                double green1 = color.getGreen();
                double blue1 = color.getBlue();
                double hue1 = color.getHue();

                if(Limitations(red, green, blue, hue, red1, green1, blue1, hue1)){
                    writer.setColor(wh, ht, Color.color(0,0,0));
                    imageSize[id] = id;
                }
                else {
                    writer.setColor(wh, ht, Color.color(1,1,1));
                    imageSize[id] = 0;
                    }
                id++;
                }
            }
            processedImage.setImage(writableImage);

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++){
                    if (imageSize[i * width + j] != 0 && imageSize[i * width + j + 1] != 0) {
                        Statics.disjointSet.union(imageSize, i * width + j, i * width + j + 1);
                    }
                }
            }

            for(int i = 0; i < imageSize.length; i++){
                S = Statics.disjointSet;
                if(imageSize[i] != 0 && !roots.contains(Statics.disjointSet.find(imageSize, i))){
                    roots.add(Statics.disjointSet.find(imageSize, i));
                }
            }
    }

    /*
    Limitations weren't working properly with red values, so I had to use the following code to get it to work. This was achieved by getting
    help from Moses Ugwulo.
    */

    private static boolean Limitations(double red, double green, double blue, double hue, double red1, double green1, double blue1, double hue1) {
        return red > red1 - 0.15 && red < red1 + 0.15 && green > green1 - 0.15 && green < green1 + 0.15 && blue > blue1 - 0.15 && blue < blue1 + 0.15 && hue > hue1 - 2 && hue < hue1 + 2;
    }

}