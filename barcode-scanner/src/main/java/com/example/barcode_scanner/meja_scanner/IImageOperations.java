package com.example.barcode_scanner.meja_scanner;

public interface IImageOperations<V, T>{
    void execute(V src, V dts, T kernel);
}
