package com.example.barcodecodescannerandroid_2.BarcodeScanner;

import android.graphics.Bitmap;

public class Kernel extends Image{
    public int offsetRow;
    public int offsetCol;

    public Kernel(Bitmap bitmap) {
        super(bitmap);
        offsetRow = this.rows/2;
        offsetCol = this.cols/2;
    }

    public Kernel(Rectangle rectangle) {
        super(rectangle);
        offsetRow = this.rows/2;
        offsetCol = this.cols/2;
    }

    public Kernel(short[][] pixels) {
        super(pixels);
        offsetRow = this.rows/2;
        offsetCol = this.cols/2;
    }
}
