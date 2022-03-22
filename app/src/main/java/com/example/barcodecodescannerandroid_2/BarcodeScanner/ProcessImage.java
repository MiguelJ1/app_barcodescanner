package com.example.barcodecodescannerandroid_2.BarcodeScanner;

public class ProcessImage {
    public IImageOperations<Image, Kernel> operation;
    public Kernel kernel;

    public ProcessImage(IImageOperations<Image, Kernel> operation, Kernel kernel) {
        this.operation = operation;
        this.kernel = kernel;
    }
}
