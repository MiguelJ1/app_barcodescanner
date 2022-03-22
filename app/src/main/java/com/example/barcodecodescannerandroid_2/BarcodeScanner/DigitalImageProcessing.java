package com.example.barcodecodescannerandroid_2.BarcodeScanner;

public class DigitalImageProcessing {

    private static final int IMAGE_SEGMENTS = 20;

    public static final int OFF_SET_ROWS = 3;

    public static final Kernel VERTICAL_SIZE_5 = buildVerticalSize5();

    public static final Kernel FULL_SIZE_5 = buildFullSize5();

    public static final Kernel FULL_SIZE_7 = buildFullSize7();

    private static Kernel buildVerticalSize5(){
        return new Kernel(new short[][]{
                new short[] {0, 0, 255, 0, 0},
                new short[] {0, 0, 255, 0, 0},
                new short[] {0, 0, 255, 0, 0},
                new short[] {0, 0, 255, 0, 0},
                new short[] {0, 0, 255, 0, 0},
        });
    }

    private static Kernel buildFullSize5(){
        return new Kernel(new short[][]{
                new short[] {255, 255, 255, 255, 255},
                new short[] {255, 255, 255, 255, 255},
                new short[] {255, 255, 255, 255, 255},
                new short[] {255, 255, 255, 255, 255},
                new short[] {255, 255, 255, 255, 255},
        });
    }

    private static Kernel buildFullSize7(){
        return new Kernel(new short[][]{
                new short[] {255, 255, 255, 255, 255, 255, 255},
                new short[] {255, 255, 255, 255, 255, 255, 255},
                new short[] {255, 255, 255, 255, 255, 255, 255},
                new short[] {255, 255, 255, 255, 255, 255, 255},
                new short[] {255, 255, 255, 255, 255, 255, 255},
                new short[] {255, 255, 255, 255, 255, 255, 255},
                new short[] {255, 255, 255, 255, 255, 255, 255},
        });
    }

    private static int getAverage(int sum, int elements){
        return sum/elements;
    }

    public static void setThresholdImageByIndex(Image image, Integer index){
        int step = image.cols / IMAGE_SEGMENTS;
        int lastStep = step * IMAGE_SEGMENTS;
        int sum;
        int average;

        for(int sweepStart=0, sweepEnd=step; sweepEnd < image.cols; sweepStart+=step, sweepEnd+=step){
            if (lastStep == sweepEnd){
                sweepEnd = image.cols;
            }

            sum = 0;
            for (int col=sweepStart; col < sweepEnd; col++){
                sum += image.pixels[index][col];
            }
            average = getAverage(sum, sweepEnd - sweepStart);

            for(int row = index - OFF_SET_ROWS; row <= index + OFF_SET_ROWS; row++){
                for (int col=sweepStart; col < sweepEnd; col++){
                    image.pixels[row][col] = (short) (image.pixels[row][col] >= average ? 255 : 0);
                }
            }
        }
    }

    public static Image getSubImage(Image src, Rectangle rect) {
        Image subImage = new Image(rect);
        for (int row = rect.row_1; row < rect.row_2; row++){
            for (int col = rect.col_1; col < rect.col_2; col++){
                subImage.pixels[row - rect.row_1][col - rect.col_1] = src.pixels[row][col];
            }
        }
        return  subImage;
    }

    public static short convolution(Image image, short initialValue, Point position, Kernel kernel,
                             IBooleanOperations<Short> spOp, IBooleanOperations<Short> gnOp){
        short pixelEval = image.pixels[position.row][position.col];
        short negativeInitialValue = (short) (255 - initialValue);
        if (negativeInitialValue == pixelEval){
            return pixelEval;
        }
        short valueConvolution = initialValue;

        short valueKernel;
        short valueImage;

        int rowImage;
        int colImage;

        boolean useRowImage;
        boolean useColImage;

        for (int rowKernel = 0; rowKernel < kernel.rows; rowKernel++){
            for (int colKernel = 0; colKernel < kernel.cols; colKernel ++){
                valueKernel = kernel.pixels[rowKernel][colKernel];

                rowImage = rowKernel - kernel.offsetRow + position.row;
                useRowImage = 0 <= rowImage && rowImage < image.rows;

                colImage = colKernel - kernel.offsetCol + position.col;
                useColImage = 0 <= colImage && colImage < image.cols;

                valueImage = useRowImage && useColImage ? image.pixels[rowImage][colImage] : initialValue;

                valueConvolution = gnOp.execute(valueConvolution,
                        spOp.execute(valueKernel, valueImage));

                if (valueConvolution == negativeInitialValue){
                    return valueConvolution;
                }
            }
        }
        return valueConvolution;
    }

    public static void dilate(Image src, Image dst, Kernel kernel){
        short initialValue = 0;
        for (int row = 0; row < src.rows; row++){
            for (int col = 0; col < src.cols; col++){
                dst.pixels[row][col] = convolution(src, initialValue, new Point(row, col),kernel,
                        BarcodeScannerUtils.AND, BarcodeScannerUtils.OR);
            }
        }
    }

    public static void erode(Image src, Image dst, Kernel kernel){
        short initialValue = 255;
        for (int row = 0; row < src.rows; row++){
            for (int col = 0; col < src.cols; col++){
                dst.pixels[row][col] = convolution(src, initialValue, new Point(row, col),kernel,
                        BarcodeScannerUtils.OR_NOT_A, BarcodeScannerUtils.AND);
            }
        }
    }

    public static void open(Image src, Image dst, Kernel kernel){
        erode(src, dst, kernel);
        Image dstAux = getSubImage(dst, new Rectangle(0, dst.rows, 0, dst.cols));
        dilate(dstAux, dst,kernel);
    }

    public static void close(Image src, Image dst, Kernel kernel){
        dilate(src, dst,kernel);
        Image dstAux = getSubImage(dst, new Rectangle(0, dst.rows, 0, dst.cols));
        erode(dstAux, dst, kernel);
    }
}
