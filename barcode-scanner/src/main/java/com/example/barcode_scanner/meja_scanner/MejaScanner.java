package com.example.barcode_scanner.meja_scanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.barcode_scanner.IScanner;
import com.example.barcode_scanner.meja_scanner.ImageUtils.Image;
import com.example.barcode_scanner.meja_scanner.ImageUtils.Rectangle;
import com.example.barcode_scanner.types.BasicCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MejaScanner implements IScanner {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public String scan(Object imageObject) {
        String message;

        Image image = (Image) imageObject;

        List<Integer> indexesAlreadyProcessed = new ArrayList<>();
        List<Integer> rowIndexesEvaluate = BarcodeScannerUtils.getRowIndexesEvaluate(image);

        List<ProcessImage> processImages = Arrays.asList(
                new ProcessImage(BarcodeScannerUtils.DO_NOTHING, null),
                new ProcessImage(BarcodeScannerUtils.OPEN_IMAGE, DigitalImageProcessing.VERTICAL_SIZE_5),
                new ProcessImage(BarcodeScannerUtils.OPEN_IMAGE, DigitalImageProcessing.FULL_SIZE_5),
                new ProcessImage(BarcodeScannerUtils.OPEN_IMAGE, DigitalImageProcessing.FULL_SIZE_7)
        );

        for (ProcessImage process : processImages) {
            message = scanImageByProcess(image, rowIndexesEvaluate, indexesAlreadyProcessed, process);
            if (message != null){
                return message;
            }
        }
        return null;
    }

    @Override
    public Object buildObjetToScan(Bitmap bitmap) {
        return new Image(bitmap);
    }

    @Override
    public Bitmap getImageBitmap(String pathImage) {
        return BitmapFactory.decodeFile(pathImage);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String scanImageByProcess(Image image, List<Integer> rowIndexesEvaluate, List<Integer> indexesAlreadyProcessed, ProcessImage process){
        String message;
        for (Integer index : rowIndexesEvaluate) {
            if (!indexesAlreadyProcessed.contains(index)) {
                DigitalImageProcessing.setThresholdImageByIndex(image, index);
                indexesAlreadyProcessed.add(index);
            }
            message = processSubImage(image, index, process);
            if (message != null){
                return message;
            }
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String processSubImage(Image image, Integer rowIndex, ProcessImage process){
        Rectangle rectangle = new Rectangle(
                rowIndex - DigitalImageProcessing.OFF_SET_ROWS,
                rowIndex + DigitalImageProcessing.OFF_SET_ROWS + 1,
                0,
                image.cols);
        Image imgSrc = DigitalImageProcessing.getSubImage(image, rectangle);
        Image imgDst = imgSrc.clone();

        process.operation.execute(imgSrc, imgDst, process.kernel);
        List<Integer> widthSegment = getWidthSegment(imgDst, imgDst.rows/2);
        Integer mode = getMode(widthSegment);
        String message = null;
        for (BasicCodeType barcodeType : BarcodeScannerUtils.barcodesAvailable) {
            barcodeType.computeRanges(mode);
            String possibleBarcode = getPossibleBarcode(widthSegment, barcodeType.ranges);
            System.out.println("Mode = " + mode);
            System.out.println(possibleBarcode);
            message = barcodeType.decode(possibleBarcode);
            if (message != null) {
                break;
            }
        }
        imgSrc = null;
        imgDst = null;
        System.gc();
        return message;
    }

    private List<Integer> getWidthSegment(Image subImage, Integer rowIndex){
        ArrayList<Integer> widthSegment = new ArrayList<>();
        short previous = -1;
        int width = 0;
        for (int col = 0; col < subImage.cols; col++){
            if (previous == -1){
                previous = subImage.pixels[rowIndex][col];
            }
            else if (previous != subImage.pixels[rowIndex][col]){
                previous = subImage.pixels[rowIndex][col];
                widthSegment.add(width);
                width = 0;
            }
            width ++;
        }
        widthSegment.add(width);
        return widthSegment;
    }

    private Integer getMode(List<Integer> widthSegment){
        int size = widthSegment.size();
        Integer valueTmp;
        int countTmp;
        Integer mode = -1;
        int count = 0;

        for(int i = 0; i < size; i++){
            countTmp = 0;
            valueTmp = widthSegment.get(i);
            for (int j = 0; j < size; j ++){
                if (valueTmp.equals(widthSegment.get(j))){
                    countTmp ++;
                }
            }

            if (count < countTmp){
                mode = valueTmp;
                count = countTmp;
            }
            else if (count == countTmp && mode.compareTo(valueTmp) == -1){
                mode = valueTmp;
            }
        }
        return mode;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getPossibleBarcode(List<Integer> widths, Map<String, BasicCodeType.limit> ranges){
        String possibleBarcode = "";
        for (Integer width : widths) {
            for (Map.Entry<String, BasicCodeType.limit> entry : ranges.entrySet()) {
                String lineType = entry.getKey();
                BasicCodeType.limit limit = entry.getValue();
                if (limit.min <= width && width <= limit.max) {
                    possibleBarcode += lineType;
                }
            }
        }
        return possibleBarcode;
    }
}
