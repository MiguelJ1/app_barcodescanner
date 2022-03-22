package com.example.barcode_scanner;

import android.graphics.Bitmap;

public interface IScanner {
    String scan(Bitmap bitmap);
    Bitmap getImageBitmap(String pathImage);
}
