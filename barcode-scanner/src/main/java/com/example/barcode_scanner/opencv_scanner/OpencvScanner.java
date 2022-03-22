package com.example.barcode_scanner.opencv_scanner;

import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.barcode_scanner.IScanner;
import com.example.barcode_scanner.types.BasicCodeType;
import com.example.barcode_scanner.types.Code128;
import com.example.barcode_scanner.types.Code39;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OpencvScanner implements IScanner {

    private static final int SAMPLES_PER_IMAGE = 24;
    private static final int OFFSET_SUBIMAGE = 3;
    private static final int BLOCKS_PER_IMAGE = 10;

    public OpencvScanner() throws Exception {
        if (!OpenCVLoader.initDebug()){
            throw new Exception("Error init Opencv");
        }
    }

    @Override
    public Object buildObjetToScan(Bitmap bitmap) {
        Mat image = new Mat(bitmap.getWidth(), bitmap.getHeight(), CvType.CV_8UC1);
        Utils.bitmapToMat(bitmap, image);
        Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY);
        return image;
    }

    @Override
    public Bitmap getImageBitmap(String pathImage) {
        Mat matImage = Imgcodecs.imread(pathImage);
        Bitmap bitmap = Bitmap.createBitmap(matImage.width(), matImage.height(), Bitmap.Config.ARGB_8888);
        Imgproc.cvtColor(matImage, matImage, Imgproc.COLOR_BGR2RGB);
        Utils.matToBitmap(matImage, bitmap);
        return bitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public String scan(Object imageObject) {
        String message;
        Mat image = (Mat) imageObject;
        List<Mat> subImages = getSubImages(image);
        List<IImageOperations<Mat>> processes = getProcesses();

        for (Mat subImage : subImages) {
            for (IImageOperations<Mat> process : processes) {
                Mat processImage = process.execute(subImage);
                List<Integer> widths = getWidths(processImage);
                Integer mode = getMode(widths);
                message = getMessageDecode(widths, mode);
                if (message != null) {
                    return message;
                }
            }
        }

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getMessageDecode(List<Integer> width, Integer mode) {
        String message;
        for (BasicCodeType barcodeType : Arrays.asList(new Code39(), new Code128())) {
            barcodeType.computeRanges(mode);
            String possibleBarcode = getPossibleBarcode(width, barcodeType.ranges);
            message = barcodeType.decode(possibleBarcode);
            if (message != null) {
                return message;
            }
        }
        return null;
    }

    private List<Integer> getWidths(Mat image) {
        List<Integer> widths = new ArrayList<>();

        int cols = image.cols();
        int evalRowIndex = image.rows() / 2;

        double previousPixel = -1;
        int lastIndexCol = cols - 1;
        int width = 0;

        for (int col = 0; col < cols; col++) {
            double pixel = image.get(evalRowIndex, col)[0];
            if (col == 0) {
                previousPixel = pixel;
            } else if (previousPixel != pixel) {
                previousPixel = pixel;
                widths.add(width);
                width = 0;
            } else if (col == lastIndexCol){
                widths.add(width + 1);
            }
            width++;
        }
        return widths;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Integer getMode(List<Integer> widths) {
        Map<Integer, Long> histogram = widths.stream()
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));
        Long frequencyMode = histogram.values().stream()
                .max(Long::compareTo)
                .orElse(0L);
        return histogram.entrySet().stream()
                .filter(set -> frequencyMode.equals(set.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(0);
    }

    private List<Mat> getSubImages(Mat image) {
        List<Mat> subImages = new ArrayList<>();
        int cols = image.cols();
        int rows = image.rows();
        int step = rows / SAMPLES_PER_IMAGE;

        int minRow, maxRow;
        int blockSize = cols / BLOCKS_PER_IMAGE;
        blockSize = blockSize % 2 == 0 ? blockSize + 1 : blockSize;

        for(int s = step; s < rows; s+=step) {
            minRow = s - OFFSET_SUBIMAGE;
            maxRow = s + OFFSET_SUBIMAGE;
            minRow = minRow >= 0 ? minRow : 0;
            maxRow = maxRow <= rows ? maxRow : rows - 1;

            Mat subImage = image.clone().submat(minRow, maxRow, 0, cols);
            Imgproc.adaptiveThreshold(subImage, subImage, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, blockSize, 0);

            subImages.add(subImage);
        }
        return  subImages;
    }

    private List<IImageOperations<Mat>> getProcesses() {
        return Arrays.asList(
                this::doNothing,
                this::doSpecialProcess,
                this::doOpenVertical3,
                this::doOpenOnes7
        );
    }

    private Mat doNothing(Mat image) {
        return image.clone();
    }

    private Mat doSpecialProcess(Mat image) {
        Mat kernelErode = Mat.ones(7, 7, CvType.CV_8U);
        Mat kernelDilate = new Mat(3,9, CvType.CV_8U);
        kernelDilate.put(0,0,
                new byte[]{
                        0, 0, 0, 0, 0, 0, 0, 0, 0,
                        1, 1, 1, 1, 1, 1, 1, 1, 1,
                        0, 0, 0, 0, 0, 0, 0, 0, 0
                });
        Mat result = new Mat();
        Imgproc.morphologyEx(image, result, Imgproc.MORPH_ERODE, kernelErode);
        Imgproc.morphologyEx(image, result, Imgproc.MORPH_DILATE, kernelDilate);
        return result;
    }

    private Mat doOpenVertical3(Mat image) {
        Mat result = new Mat();
        Mat kernel = new Mat(3,3, CvType.CV_8U);
        kernel.put(0,0,
                new byte[]{
                        0,1,0,
                        0,1,0,
                        0,1,0
                });
        Imgproc.morphologyEx(image, result, Imgproc.MORPH_OPEN, kernel);
        return result;
    }

    private Mat doOpenOnes7(Mat image) {
        Mat kernel = Mat.ones(7, 7, CvType.CV_8U);
        Mat result = new Mat();
        Imgproc.morphologyEx(image, result, Imgproc.MORPH_OPEN, kernel);
        return result;
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
