package com.example.barcode_scanner.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Code128 extends BasicCodeType{

    private static final String START_CODE_A = buildChar("MSSXSM");
    private static final String START_CODE_B = buildChar("MSSMSX");
    private static final String START_CODE_C = buildChar("MSSMLM");
    private static final List<String> STARTS = Arrays.asList(START_CODE_A, START_CODE_B,START_CODE_C);

    private static final String CHANGE_TO_CODE_A = "CODE_A";
    private static final String CHANGE_TO_CODE_B = "CODE_B";
    private static final String CHANGE_TO_CODE_C = "CODE_C";
    private static final List<String> CHANGE_TO = Arrays.asList(CHANGE_TO_CODE_A, CHANGE_TO_CODE_B, CHANGE_TO_CODE_C);

    private static String END = buildChar("MLLSSSM");

    private List<String> keys;

    public Code128() {
        buildTableCharacters();
    }

    @Override
    protected void buildTableCharacters() {
        TABLE.put(buildChar("MSMMMM"), " , ,00");
        TABLE.put(buildChar("MMMSMM"), "!,!,01");
        TABLE.put(buildChar("MMMMMS"), "\",\",02");
        TABLE.put(buildChar("SMSMML"), "#,#,03");
        TABLE.put(buildChar("SMSLMM"), "$,$,04");
        TABLE.put(buildChar("SLSMMM"), "%,%,05");
        TABLE.put(buildChar("SMMMSL"), "&,&,06");
        TABLE.put(buildChar("SMMLSM"), "',',07");
        TABLE.put(buildChar("SLMMSM"), "(,(,08");
        TABLE.put(buildChar("MMSMSL"), "),),09");
        TABLE.put(buildChar("MMSLSM"), "*,*,10");
        TABLE.put(buildChar("MLSMSM"), "+,+,11");
        TABLE.put(buildChar("SSMMLM"), "coma,coma,12");
        TABLE.put(buildChar("SMMSLM"), "-,-,13");
        TABLE.put(buildChar("SMMMLS"), ".,.,14");
        TABLE.put(buildChar("SSLMMM"), "/,/,15");
        TABLE.put(buildChar("SMLSMM"), "0,0,16");
        TABLE.put(buildChar("SMLMMS"), "1,1,17");
        TABLE.put(buildChar("MMLMSS"), "2,2,18");
        TABLE.put(buildChar("MMSSLM"), "3,3,19");
        TABLE.put(buildChar("MMSMLS"), "4,4,20");
        TABLE.put(buildChar("MSLMSM"), "5,5,21");
        TABLE.put(buildChar("MMLSSM"), "6,6,22");
        TABLE.put(buildChar("LSMSLS"), "7,7,23");
        TABLE.put(buildChar("LSSMMM"), "8,8,24");
        TABLE.put(buildChar("LMSSMM"), "9,9,25");
        TABLE.put(buildChar("LMSMMS"), ":,:,26");
        TABLE.put(buildChar("LSMMSM"), ";,;,27");
        TABLE.put(buildChar("LMMSSM"), "<,<,28");
        TABLE.put(buildChar("LMMMSS"), "=,=,29");
        TABLE.put(buildChar("MSMSML"), ">,>,30");
        TABLE.put(buildChar("MSMLMS"), "?,?,31");
        TABLE.put(buildChar("MLMSMS"), "@,@,32");
        TABLE.put(buildChar("SSSLML"), "A,A,33");
        TABLE.put(buildChar("SLSSML"), "B,B,34");
        TABLE.put(buildChar("SLSLMS"), "C,C,35");
        TABLE.put(buildChar("SSMLSL"), "D,D,36");
        TABLE.put(buildChar("SLMSSL"), "E,E,37");
        TABLE.put(buildChar("SLMLSS"), "F,F,38");
        TABLE.put(buildChar("MSSLSL"), "G,G,39");
        TABLE.put(buildChar("MLSSSL"), "H,H,40");
        TABLE.put(buildChar("MLSLSS"), "I,I,41");
        TABLE.put(buildChar("SSMSLL"), "J,J,42");
        TABLE.put(buildChar("SSMLLS"), "K,K,43");
        TABLE.put(buildChar("SLMSLS"), "L,L,44");
        TABLE.put(buildChar("SSLSML"), "M,M,45");
        TABLE.put(buildChar("SSLLMS"), "N,N,46");
        TABLE.put(buildChar("SLLSMS"), "O,O,47");
        TABLE.put(buildChar("LSLSMS"), "P,P,48");
        TABLE.put(buildChar("MSSLLS"), "Q,Q,49");
        TABLE.put(buildChar("MLSSLS"), "R,R,50");
        TABLE.put(buildChar("MSLSSL"), "S,S,51");
        TABLE.put(buildChar("MSLLSS"), "T,T,52");
        TABLE.put(buildChar("MSLSLS"), "U,U,53");
        TABLE.put(buildChar("LSSSML"), "V,V,54");
        TABLE.put(buildChar("LSSLMS"), "W,W,55");
        TABLE.put(buildChar("LLSSMS"), "X,X,56");
        TABLE.put(buildChar("LSMSSL"), "Y,Y,57");
        TABLE.put(buildChar("LSMLSS"), "Z,Z,58");
        TABLE.put(buildChar("LLMSSS"), "[,[,59");
        TABLE.put(buildChar("LSXSSS"), "\\,\\,60");
        TABLE.put(buildChar("MMSXSS"), "],],61");
        TABLE.put(buildChar("XLSSSS"), "^,^,62");
        TABLE.put(buildChar("SSSMMX"), "_,_,63");
        TABLE.put(buildChar("SSSXMM"), "NUL,`,64");
        TABLE.put(buildChar("SMSSMX"), "SOH,a,65");
        TABLE.put(buildChar("SMSXMS"), "STX,b,66");
        TABLE.put(buildChar("SXSSMM"), "ETX,c,67");
        TABLE.put(buildChar("SXSMMS"), "EOT,d,68");
        TABLE.put(buildChar("SSMMSX"), "ENQ,e,69");
        TABLE.put(buildChar("SSMXSM"), "ACK,f,70");
        TABLE.put(buildChar("SMMSSX"), "BEL,g,71");
        TABLE.put(buildChar("SMMXSS"), "BS,h,72");
        TABLE.put(buildChar("SXMSSM"), "HT,i,73");
        TABLE.put(buildChar("SXMMSS"), "LF,j,74");
        TABLE.put(buildChar("MXSMSS"), "VT,k,75");
        TABLE.put(buildChar("MMSSSX"), "FF,l,76");
        TABLE.put(buildChar("XSLSSS"), "CR,m,77");
        TABLE.put(buildChar("MXSSSM"), "SO,n,78");
        TABLE.put(buildChar("SLXSSS"), "SI,o,79");
        TABLE.put(buildChar("SSSMXM"), "DLE,p,80");
        TABLE.put(buildChar("SMSSXM"), "DC1,q,81");
        TABLE.put(buildChar("SMSMXS"), "DC2,r,82");
        TABLE.put(buildChar("SSXMSM"), "DC3,s,83");
        TABLE.put(buildChar("SMXSSM"), "DC4,t,84");
        TABLE.put(buildChar("SMXMSS"), "NAK,u,85");
        TABLE.put(buildChar("XSSMSM"), "SYN,v,86");
        TABLE.put(buildChar("XMSSSM"), "ETB,w,87");
        TABLE.put(buildChar("XMSMSS"), "CAN,x,88");
        TABLE.put(buildChar("MSMSXS"), "EM,y,89");
        TABLE.put(buildChar("MSXSMS"), "SUB,z,90");
        TABLE.put(buildChar("XSMSMS"), "ESC,{,91");
        TABLE.put(buildChar("SSSSXL"), "FS,|,92");
        TABLE.put(buildChar("SSSLXS"), "GS,},93");
        TABLE.put(buildChar("SLSSXS"), "RS,~,94");
        TABLE.put(buildChar("SSXSSL"), "US,DEL,95");
        TABLE.put(buildChar("SSXLSS"), "FNC_3,FNC_3,96");
        TABLE.put(buildChar("XSSSSL"), "FNC_2,FNC_2,97");
        TABLE.put(buildChar("XSSLSS"), "SHIFT,SHIFT,98");
        TABLE.put(buildChar("SSLSXS"), "CODE_C,CODE_C,99");
        TABLE.put(buildChar("SSXSLS"), "CODE_B,FNC_4,CODE_B");
        TABLE.put(buildChar("LSSSXS"), "FNC_4,CODE_A,CODE_A");
        TABLE.put(buildChar("XSSSLS"), "FNC_1,FNC_1,FNC_1");

        loadKeys();
        loadLengthCharacters();
    }

    private void loadKeys() {
        this.keys = new ArrayList<>(TABLE.keySet());
        this.keys.addAll(STARTS);
    }

    @Override
    protected void loadLengthCharacters() {
        this.LENGTH_CHARACTERS = START_CODE_A.length();
    }

    @Override
    public String decode(String barcode) {
        byte isCode = isCode(barcode);
        if (isCode == 0){
            return null;
        } else if (isCode < 0){
            barcode = revertString(barcode);
        }

        int indexValues = getIndexValues(isCode);
        String startCodeType = getStartCodeType(indexValues);
        barcode = getBarcodeContent(barcode, startCodeType, END);
        String message = "";
        int checkSum = keys.indexOf(startCodeType);

        int length = barcode.length();

        String key;
        String character;
        for (int i = 0, j = 1; i < length; i += LENGTH_CHARACTERS, j++){
            key = barcode.substring(i, i + LENGTH_CHARACTERS);
            if (!TABLE.containsKey(key)){
                return null;
            }
            if (i + LENGTH_CHARACTERS >= length){
                if (keys.indexOf(key) != checkSum % 103){
                    return null;
                }
                break;
            }
            checkSum += keys.indexOf(key) * j;
            character = getCharacterFromValue(TABLE.get(key), indexValues);
            if (CHANGE_TO.contains(character)){
                indexValues = getIndexToValue(character);
            } else {
                message += character;
            }
        }
        if (message.isEmpty()){
            return null;
        }
        return message;
    }

    private int getIndexValues(byte isCode) {
        if (isCode < 0){
            isCode *= -1;
        }
        return isCode;
    }

    private String getCharacterFromValue(String value, int index){
        String character = value.split(",")[index - 1];
        return character.equals("coma") ? "," : character;
    }

    private int getIndexToValue(String change){
        if (CHANGE_TO_CODE_A.equals(change)){
            return 1;
        } else if (CHANGE_TO_CODE_B.equals(change)){
            return 2;
        } else if (CHANGE_TO_CODE_C.equals(change)){
            return 3;
        } else {
            return -1;
        }
    }

    @Override
    protected byte isCode(String barcode) {
        byte i = 1;
        for (String start : STARTS) {
            if (barcode.contains(start) && barcode.contains(END)){
                return i;
            } else if (barcode.contains(revertString(start)) && barcode.contains(revertString(END))){
                return (byte) (i * -1);
            }
            i ++;
        }
        return 0;
    }

    private String getStartCodeType(int indexValues){
        if (indexValues == 1){
            return START_CODE_A;
        } else if (indexValues == 2) {
            return START_CODE_B;
        } else if (indexValues == 3) {
            return START_CODE_C;
        } else {
            return null;
        }
    }

    @Override
    public void computeRanges(int init_value) {
        double rate_down = 0.6;
        double rate_up = 1.3;

        int min = (int) Math.round(init_value * rate_down);
        int max = (int) Math.round(init_value * rate_up);
        ranges.put(LINE_S, new limit(max, min));

        min = max + 1;
        max = (int)(init_value * rate_up * 2);
        ranges.put(LINE_M, new limit(max, min));

        min = max + 1;
        max = (int)(init_value * rate_up * 3);
        ranges.put(LINE_L, new limit(max, min));

        min = max + 1;
        max = (int)(init_value * rate_up * 4);
        ranges.put(LINE_X, new limit(max, min));
    }
}
