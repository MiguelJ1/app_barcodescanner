package com.example.barcode_scanner.meja_scanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.barcode_scanner.IScanner;

public class MejaScanner implements IScanner {
    @Override
    public String scan(Bitmap bitmap) {
        return null;
    }

    @Override
    public Bitmap getImageBitmap(String pathImage) {
        return BitmapFactory.decodeFile(pathImage);
    }
}
