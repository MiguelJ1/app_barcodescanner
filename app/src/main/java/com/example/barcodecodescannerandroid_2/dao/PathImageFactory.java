package com.example.barcodecodescannerandroid_2.dao;

import android.os.Environment;

import java.util.Arrays;
import java.util.List;

public class PathImageFactory {

    private static List<String> IMAGES = Arrays.asList(
            "barcode.jpg",
            "carnet.jpg",
            "equipo-1.jpg",
            "equipo-2.jpg",
            "equipo-3.jpg"
    );

    private static String PATH_FOLDER_IMAGES = String.format("%s/%s/%s",
            Environment.getExternalStorageDirectory().getAbsolutePath(),
            Environment.DIRECTORY_DCIM,
            "BarcodeImages"
    );

    private static int NEXT_IMAGE = 0;

    public static String getNextPathImage(){
        String pathImage = String.format("%s/%s", PATH_FOLDER_IMAGES, IMAGES.get(NEXT_IMAGE));
        NEXT_IMAGE = (NEXT_IMAGE + 1) % IMAGES.size();
        return pathImage;
    }
}
