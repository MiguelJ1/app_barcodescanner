package com.example.barcodecodescannerandroid_2.dao;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImagesDaoImp implements IImagesDao {

    @Override
    public Bitmap getNextBitmapImage() {
        String pathImage = PathImageFactory.getNextPathImage();
        return BitmapFactory.decodeFile(pathImage);
    }
}
