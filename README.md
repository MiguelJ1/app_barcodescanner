# Install OpenCV in Android Studio

1. Add `applicationId` with `opencv.org` in `build.gradle`as:

```
defaultConfig {  
  applicationId "com.example.barcodecodescannerandroid_2"  
  minSdk 21  
  targetSdk 31  
  versionCode 1  
  versionName "1.0"  
  applicationId "opencv.org"  
  
  testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"  
}
```
2. Fix dependencies and sync the proyect
3. Download and unzip the OpenCV release from Android in [oficial site](https://opencv.org/releases/)
4. Copy folder `sdk` and paste in your application folder (same level with `app` folder)
   Optional: change the folder name from `skd`  to `opencv`
5. From your proyect edit the `build.gradle` of `sdk` folder with custom config (Select Java or Kotlin)
6. In `settings.gradle` add `include ':skd'` (Set the folder name, if changed it)
7. Sync the proyect
8. From `proyect structure` > `dependencies` select `app` and  add `module dependency`, select `skd` and `implementation`, apply changes.
9. From `proyect structure` > `modules`, set `build tools version` with the last version, apply changes.
10. Add this code in you `MainActivity` for test:
```
public class MainActivity extends AppCompatActivity {
    private static String TAG  = "MainActivity";
    static {
        if (OpenCVLoader.initDebug()) {
            Log.d(TAG, "OpenCv init successfully");
        } else {
            Log.d(TAG, "OpenCv not start");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
```
11. Open `Logcat`, select `edit filter configuration`, set `filter name` and `Log Tag` with `MainActivity`
12. Run the app in the device and check logs.
13. Enjoy Opencv.

# Notes

For debugging may be required install libncurses5, use this command:
`sudo apt install libncurses5`
