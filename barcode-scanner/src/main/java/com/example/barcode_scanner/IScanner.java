package com.example.barcode_scanner;

import android.graphics.Bitmap;

public interface IScanner {
    String scan(Object imageObject);
    Object buildObjetToScan(Bitmap bitmap);
    Bitmap getImageBitmap(String pathImage);
}
