package com.example.barcodecodescannerandroid_2.BarcodeScanner;

public interface IImageOperations<V, T>{
    void execute(V src, V dts, T kernel);
}
