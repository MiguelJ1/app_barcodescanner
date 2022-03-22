package com.example.barcode_scanner.opencv_scanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.barcode_scanner.IScanner;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OpencvScanner implements IScanner {

    public OpencvScanner() throws Exception {
        if (!OpenCVLoader.initDebug()){
            throw new Exception("Error init Opencv");
        }
    }

    @Override
    public String scan(Object imageObject) {
        return null;
    }

    @Override
    public Object buildObjetToScan(Bitmap bitmap) {
        return null;
    }

    @Override
    public Bitmap getImageBitmap(String pathImage) {
        Mat matImage = Imgcodecs.imread(pathImage);
        Bitmap bitmap = Bitmap.createBitmap(matImage.width(), matImage.height(), Bitmap.Config.ARGB_8888);
        Imgproc.cvtColor(matImage, matImage, Imgproc.COLOR_BGR2RGB);
        Utils.matToBitmap(matImage, bitmap);
        return bitmap;
    }
}
