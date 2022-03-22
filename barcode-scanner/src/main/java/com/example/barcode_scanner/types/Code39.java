package com.example.barcode_scanner.types;

public class Code39 extends BasicCodeType{
    private static final String START = buildChar("SLSSLSLSSS");
    private static final String END = buildChar("SLSSLSLSS");


    public Code39() {
        buildTableCharacters();
    }

    @Override
    protected void buildTableCharacters() {
        TABLE.put(buildChar("LSSSSLSSLS"),"A");
        TABLE.put(buildChar("SSLSSLSSLS"),"B");
        TABLE.put(buildChar("LSLSSLSSSS"),"C");
        TABLE.put(buildChar("SSSSLLSSLS"),"D");
        TABLE.put(buildChar("LSSSLLSSSS"),"E");
        TABLE.put(buildChar("SSLSLLSSSS"),"F");
        TABLE.put(buildChar("SSSSSLLSLS"),"G");
        TABLE.put(buildChar("LSSSSLLSSS"),"H");
        TABLE.put(buildChar("SSLSSLLSSS"),"I");
        TABLE.put(buildChar("SSSSLLLSSS"),"J");
        TABLE.put(buildChar("LSSSSSSLLS"),"K");
        TABLE.put(buildChar("SSLSSSSLLS"),"L");
        TABLE.put(buildChar("LSLSSSSLSS"),"M");
        TABLE.put(buildChar("SSSSLSSLLS"),"N");
        TABLE.put(buildChar("LSSSLSSLSS"),"O");
        TABLE.put(buildChar("SSLSLSSLSS"),"P");
        TABLE.put(buildChar("SSSSSSLLLS"),"Q");
        TABLE.put(buildChar("LSSSSSLLSS"),"R");
        TABLE.put(buildChar("SSLSSSLLSS"),"S");
        TABLE.put(buildChar("SSSSLSLLSS"),"T");
        TABLE.put(buildChar("LLSSSSSSLS"),"U");
        TABLE.put(buildChar("SLLSSSSSLS"),"V");
        TABLE.put(buildChar("LLLSSSSSSS"),"W");
        TABLE.put(buildChar("SLSSLSSSLS"),"X");
        TABLE.put(buildChar("LLSSLSSSSS"),"Y");
        TABLE.put(buildChar("SLLSLSSSSS"),"Z");
        TABLE.put(buildChar("SSSLLSLSSS"),"0");
        TABLE.put(buildChar("LSSLSSSSLS"),"1");
        TABLE.put(buildChar("SSLLSSSSLS"),"2");
        TABLE.put(buildChar("LSLLSSSSSS"),"3");
        TABLE.put(buildChar("SSSLLSSSLS"),"4");
        TABLE.put(buildChar("LSSLLSSSSS"),"5");
        TABLE.put(buildChar("SSLLLSSSSS"),"6");
        TABLE.put(buildChar("SSSLSSLSLS"),"7");
        TABLE.put(buildChar("LSSLSSLSSS"),"8");
        TABLE.put(buildChar("SSLLSSLSSS"),"9");
        TABLE.put(buildChar("SLLSSSLSSS")," ");
        TABLE.put(buildChar("SLSSSSLSLS"),"-");
        TABLE.put(buildChar("SLSLSLSSSS"),"$");
        TABLE.put(buildChar("SSSLSLSLSS"),"%");
        TABLE.put(buildChar("LLSSSSLSSS"),".");
        TABLE.put(buildChar("SLSLSSSLSS"),"/");
        TABLE.put(buildChar("SLSSSLSLSS"),"+");
        TABLE.put(buildChar("SLSSLSLSSS"),"*");

        loadLengthCharacters();
    }

    @Override
    protected void loadLengthCharacters() {
        this.LENGTH_CHARACTERS = START.length();
    }

    @Override
    public String decode(String barcode) {
        byte isCode = isCode(barcode);
        if (isCode == 0){
            return null;
        }
        else if(isCode == -1){
            barcode = revertString(barcode);
        }
        barcode = getBarcodeContent(barcode, START, END);
        int length = barcode.length();
        String message = "";
        String key;
        for (int i = 0; i < length; i += LENGTH_CHARACTERS){
            if (i + LENGTH_CHARACTERS > length){
                return null;
            }
            key = barcode.substring(i, i + LENGTH_CHARACTERS);
            if (!TABLE.containsKey(key)){
                return null;
            }
            message += TABLE.get(key);
        }
        if (message.isEmpty()){
            return null;
        }
        return message;
    }

    @Override
    protected byte isCode(String barcode) {
        if (barcode.contains(START) && barcode.contains(END)) {
            return 1;
        }
        else if (barcode.contains(revertString(START)) && barcode.contains(revertString(END))){
            return -1;
        }
        else {
            return  0;
        }
    }

    @Override
    public void computeRanges(int init_value) {
        int min = init_value / 2;
        int max = init_value * 2;
        ranges.put(LINE_S, new limit(max, min));
        ranges.put(LINE_L, new limit(max * 2, max + 1));
    }
}

