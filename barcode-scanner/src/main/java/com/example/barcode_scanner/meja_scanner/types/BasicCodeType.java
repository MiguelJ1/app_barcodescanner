package com.example.barcode_scanner.meja_scanner.types;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BasicCodeType {

     protected static final String LINE_S = "1";
     protected static final String LINE_M = "2";
     protected static final String LINE_L = "3";
     protected static final String LINE_X = "4";

     protected Map<String, String> TABLE = new LinkedHashMap<>();

     protected int LENGTH_CHARACTERS;

     public Map<String, limit> ranges = new HashMap<>();

     public class limit{
          public int max;
          public int min;

          public limit(int max, int min) {
               this.max = max;
               this.min = min;
          }
     }

     private static String createLine(String size){
          if (size.equals("S")){
               return LINE_S;
          }
          else if (size.equals("M")){
               return LINE_M;
          }
          else if (size.equals("L")){
               return LINE_L;
          }
          else if (size.equals("X")){
               return LINE_X;
          }
          else {
               return "";
          }
     }

     protected static String buildChar(String characterEncoded){
          String characterDecoded = "";
          for (int i = 0; i < characterEncoded.length(); i++){
               characterDecoded += createLine(characterEncoded.substring(i, i + 1));
          }
          return characterDecoded;
     }

     protected static String revertString(String src){
          String res = "";
          for (int i = src.length() - 1; i >= 0; i--){
               res += src.charAt(i);
          }
          return res;
     }

     protected String getBarcodeContent(String barcode, String startCharacter, String endCharacter) {
          int beginIndex = barcode.indexOf(startCharacter) + LENGTH_CHARACTERS;
          int endIndex = barcode.lastIndexOf(endCharacter);

          if (beginIndex <= endIndex){
               return barcode.substring(beginIndex, endIndex);
          }
          else {
               return "";
          }
     }

     protected abstract void buildTableCharacters();

     protected abstract void loadLengthCharacters();

     public abstract String decode(String barcode);

     protected abstract byte isCode(String barcode);

     public abstract void computeRanges(int init_value);

}
