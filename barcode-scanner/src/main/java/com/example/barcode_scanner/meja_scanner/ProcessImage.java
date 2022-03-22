package com.example.barcode_scanner.meja_scanner;

import com.example.barcode_scanner.meja_scanner.ImageUtils.Image;
import com.example.barcode_scanner.meja_scanner.ImageUtils.Kernel;

public class ProcessImage {
    public IImageOperations<Image, Kernel> operation;
    public Kernel kernel;

    public ProcessImage(IImageOperations<Image, Kernel> operation, Kernel kernel) {
        this.operation = operation;
        this.kernel = kernel;
    }
}
