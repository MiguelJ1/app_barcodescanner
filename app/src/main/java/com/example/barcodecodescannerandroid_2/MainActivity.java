package com.example.barcodecodescannerandroid_2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.barcodecodescannerandroid_2.BarcodeScanner.BarcodeScanner;
import com.example.barcodecodescannerandroid_2.BarcodeScanner.Image;
import com.example.barcodecodescannerandroid_2.dao.IImagesDao;
import com.example.barcodecodescannerandroid_2.dao.ImagesDaoImp;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private IImagesDao imagesDao;

    private TextView tvMessage;
    private Button btnScan;
    private Button btnLoadImage;
    private ImageView ivImages;
    private Switch aSwitch;

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
        tvMessage.setText(String.format("%s\n%s",
                "Duration = " + duration,
                "Message = " + message
        ));
    }

    public void loadImage(View view) {
        ivImages.setImageBitmap(imagesDao.getNextBitmapImage());
    }
}