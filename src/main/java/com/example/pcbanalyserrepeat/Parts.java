package com.example.pcbanalyserrepeat;

public class Parts {

    private String name;

    private double red;

    private double green;

    private double blue;

    private int alpha;

    private double hue;

    private double saturation;

    private double brightness;

    public Parts(String name, double red, double green, double blue, int alpha, double hue, double saturation, double brightness) {
        this.name = name;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRed() {
        return red;
    }

    public void setRed(double red) {
        this.red = red;
    }

    public double getGreen() {
        return green;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    public double getBlue() {
        return blue;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public double getHue() {
        return hue;
    }

    public void setHue(double hue) {
        this.hue = hue;
    }

    public double getSaturation() {
        return saturation;
    }

    public void setSaturation(double saturation) {
        this.saturation = saturation;
    }

    public double getBrightness() {
        return brightness;
    }

    public void setBrightness(double brightness) {
        this.brightness = brightness;
    }

}
