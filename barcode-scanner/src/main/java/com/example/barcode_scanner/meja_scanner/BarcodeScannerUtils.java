package com.example.barcode_scanner.meja_scanner;

import com.example.barcode_scanner.meja_scanner.ImageUtils.Image;
import com.example.barcode_scanner.meja_scanner.ImageUtils.Kernel;
import com.example.barcode_scanner.types.BasicCodeType;
import com.example.barcode_scanner.types.Code128;
import com.example.barcode_scanner.types.Code39;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BarcodeScannerUtils {

    private static final int SAMPLES_PER_IMAGE = 24;

    public static List<Integer> getRowIndexesEvaluate(Image image){
        Integer step = image.rows / SAMPLES_PER_IMAGE;
        List<Integer> rowIndexesEvaluate = new ArrayList<>();
        for (int i = 1; i < SAMPLES_PER_IMAGE; i++){
            rowIndexesEvaluate.add(step * i);
        }
        return rowIndexesEvaluate;
    }

    public static final IBooleanOperations<Short> AND = (a, b) -> (short) (a & b);

    public static final IBooleanOperations<Short> OR = (a, b) -> (short) (a | b);

    public static final IBooleanOperations<Short> OR_NOT_A = (a, b) -> (short) ((255 - a) | b);

    public static final IImageOperations<Image, Kernel> OPEN_IMAGE = DigitalImageProcessing::open;

    public static final IImageOperations<Image, Kernel> DO_NOTHING = (src, dts, kernel) -> {};

    public static final List<BasicCodeType> barcodesAvailable = Arrays.asList(
            new Code39(),
            new Code128()
    );

}
