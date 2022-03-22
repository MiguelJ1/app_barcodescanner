package com.example.barcodecodescannerandroid_2.dao;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ImagesDaoImp implements IImagesDao {

    private final String pathImages;

    private final List<String> imageNames;

    private final List<Bitmap> bitmapImagesList;

    private Integer nextImage = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ImagesDaoImp() {
        this.pathImages = getPathImages();
        this.imageNames = getImageNamesList();
        this.bitmapImagesList = loadImages();
    }


    private String getPathImages(){
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String directoryDcim = Environment.DIRECTORY_DCIM;
        return absolutePath + "/" + directoryDcim + "/BarcodeImages";
    }

    private List<String> getImageNamesList(){
        return Arrays.asList(
                "/barcode.jpg",
                "/carnet.jpg",
                "/equipo-1.jpg",
                "/equipo-2.jpg",
                "/equipo-3.jpg"
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Bitmap> loadImages() {
        return imageNames.stream()
                .map(imgName -> BitmapFactory.decodeFile(pathImages + imgName))
                .collect(Collectors.toList());
    }

    @Override
    public Bitmap getNextBitmapImage() {
        Integer size = bitmapImagesList.size();
        Bitmap bitmap = bitmapImagesList.get(nextImage);
        nextImage = (nextImage + 1) % size;
        return bitmap;
    }
}
