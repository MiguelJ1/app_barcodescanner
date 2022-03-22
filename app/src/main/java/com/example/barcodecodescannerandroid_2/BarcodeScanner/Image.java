package com.example.barcodecodescannerandroid_2.BarcodeScanner;

import android.graphics.Bitmap;


public class Image {
    public short[][] pixels;
    public int rows;
    public int cols;

    public Image(Bitmap bitmap) {
        rows = bitmap.getHeight();
        cols = bitmap.getWidth();
        pixels = new short[rows][cols];
        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                int pixel = bitmap.getPixel(col, row);
                pixels[row][col] = (short) (
                        (((pixel >> 16)&0xff) + ((pixel >> 8)&0xff) + (pixel&0xff)
                        )/3
                );
            }
        }
    }

    public Image(Rectangle rectangle){
        rows = rectangle.row_2 - rectangle.row_1;
        cols = rectangle.col_2 - rectangle.col_1;
        pixels = new short[rows][cols];
    }

    public Image(short[][] pixels){
        this.pixels = pixels;
        rows = pixels.length;
        cols = pixels[0].length;
    }

    public Image clone(){
        short[][] pixelsClone = new short[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                pixelsClone[i][j] = this.pixels[i][j];
            }
        }
        return new Image(pixelsClone);
    }
}
