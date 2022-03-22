package com.example.barcodecodescannerandroid_2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.barcode_scanner.IScanner;
import com.example.barcode_scanner.meja_scanner.MejaScanner;
import com.example.barcode_scanner.opencv_scanner.OpencvScanner;
import com.example.barcodecodescannerandroid_2.BarcodeScanner.BarcodeScanner;
import com.example.barcodecodescannerandroid_2.BarcodeScanner.Image;
import com.example.barcodecodescannerandroid_2.dao.IImagesDao;
import com.example.barcodecodescannerandroid_2.dao.ImagesDaoImp;
import com.example.barcodecodescannerandroid_2.dao.PathImageFactory;


import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private IImagesDao imagesDao;

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

        tvMessage = (TextView) findViewById(R.id.tv_message);
        btnScan = (Button) findViewById(R.id.btn_scan);
        btnLoadImage = (Button) findViewById(R.id.btn_load_img);
        ivImages = (ImageView) findViewById(R.id.iv_image);
        aSwitch =  (Switch) findViewById(R.id.swt_opencv);

        initDao();
        ivImages.setImageBitmap(imagesDao.getNextBitmapImage());

        mejaScanner = new MejaScanner();
        try {
            opencvScanner = new OpencvScanner();
        } catch (Exception ex){
            tvMessage.setText(ex.getMessage());
            btnLoadImage.setEnabled(false);
            btnScan.setEnabled(false);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initDao(){
        imagesDao = aSwitch.isChecked() ? null : new ImagesDaoImp();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void scanImage(View view) {
        Image image = new Image(((BitmapDrawable) ivImages.getDrawable()).getBitmap());

        Date start = new Date();

        BarcodeScanner barcodeScanner = new BarcodeScanner();
        String message = barcodeScanner.read(image);

        long duration = new Date().getTime() - start.getTime();
        tvMessage.setText(String.format("%s\n%s\n%s\n%s",
                "Duration = " + duration,
                "Message = " + message,
                "mejaScan = " + mejaScanner.scan(null),
                "opencvScan = " + opencvScanner.scan(null)
        ));
    }

    public void loadImage(View view) {
        String pathImage = PathImageFactory.getNextPathImage();
        IScanner scanner = getScanner();
        Bitmap imageBitmap = scanner.getImageBitmap(pathImage);
        ivImages.setImageBitmap(imageBitmap);
    }

    private IScanner getScanner(){
        return aSwitch.isChecked() ? opencvScanner : mejaScanner;
    }
}

/*
TODO:
1. implementar en majaScanner el metodo scan
2. Implementar con opencvScan el metodo scan
 */
