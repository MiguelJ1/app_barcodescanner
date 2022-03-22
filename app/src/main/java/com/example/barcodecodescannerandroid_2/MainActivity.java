package com.example.barcodecodescannerandroid_2;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.barcode_scanner.IScanner;
import com.example.barcode_scanner.meja_scanner.MejaScanner;
import com.example.barcode_scanner.opencv_scanner.OpencvScanner;
import com.example.barcodecodescannerandroid_2.dao.PathImageFactory;

import java.util.Date;

public class MainActivity extends AppCompatActivity {


    private TextView tvMessage;
    private Button btnScan;
    private Button btnLoadImage;
    private ImageView ivImages;
    private Switch aSwitch;

    private IScanner mejaScanner;
    private IScanner opencvScanner;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgetsUI();
        initScanners();
        loadImage(null);
    }

    private void initScanners() {
        mejaScanner = new MejaScanner();
        try {
            opencvScanner = new OpencvScanner();
        } catch (Exception ex){
            tvMessage.setText(ex.getMessage());
            btnLoadImage.setEnabled(false);
            btnScan.setEnabled(false);
        }
    }

    private void initWidgetsUI() {
        tvMessage = (TextView) findViewById(R.id.tv_message);
        btnScan = (Button) findViewById(R.id.btn_scan);
        btnLoadImage = (Button) findViewById(R.id.btn_load_img);
        ivImages = (ImageView) findViewById(R.id.iv_image);
        aSwitch =  (Switch) findViewById(R.id.swt_opencv);
    }

    private IScanner getScanner(){
        return aSwitch.isChecked() ? opencvScanner : mejaScanner;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void scanImage(View view) {
        Bitmap bitmap = ((BitmapDrawable) ivImages.getDrawable()).getBitmap();
        IScanner scanner = getScanner();
        Object objetToScan = scanner.buildObjetToScan(bitmap);

        Date start = new Date();

        String responseScan = scanner.scan(objetToScan);

        long duration = new Date().getTime() - start.getTime();
        tvMessage.setText(String.format("%s\n%s",
                "Duration = " + duration,
                "Message = " + responseScan
        ));
    }

    public void loadImage(View view) {
        String pathImage = PathImageFactory.getNextPathImage();
        IScanner scanner = getScanner();
        Bitmap imageBitmap = scanner.getImageBitmap(pathImage);
        ivImages.setImageBitmap(imageBitmap);
    }
}

/*
TODO:
1. Implementar con opencvScan el metodo scan
 */
