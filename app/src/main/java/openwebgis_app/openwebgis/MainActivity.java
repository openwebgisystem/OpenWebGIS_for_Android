package openwebgis_app.openwebgis;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.app.AlertDialog;
import android.webkit.DownloadListener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    public static WebView mWebView;private ProgressDialog progressDialog;public String shtml;public String filenamesave;public String ntext;
    public Handler mHandler = new Handler();
    public SensorManager sensorManagerA;	//
    public SensorManager sensorManagerM;
    public SensorManager sensorManagerL;
    private SensorManager sensorManagerT;
    private SensorManager sensorManagerG;
    private SensorManager sensorManagerO;
    private SensorManager sensorManagerP;
    public DJavaScriptInterface JSInterface;
    private long lastUpdate;
    public String stringSensorM=""; public String stringSensorL="";public String stringSensorT="";
   private String stringSensorG="";private String stringSensorR="";private String stringSensorA="";String stringSensorP="";
public String strsenGPS = "";
    // public ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
    private  LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private float[] orientationVals=new float[3];
    private float[] mRotationMatrix=new float[16];
    private WifiManager wifi;    private List<ScanResult> wifi_results;private int sizeWF = 0;
    private AlertDialog DialogWF;


    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
    public void MessageBox (String msg)
    {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(msg);
        builder.setPositiveButton("OK", null);
        AlertDialog d = builder.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        /*registerReceiver(new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context c, Intent intent)
            {
                wifi_results = wifi.getScanResults();
                sizeWF = wifi_results.size();
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));*/

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
              mLocationListener = new LocationListener() {
                public void onStatusChanged(String provider, int status,
                                            Bundle extras) {
                    switch (status) {
                        case LocationProvider.OUT_OF_SERVICE:
                            Log.v("GPS demo",
                                    "Status changed: " + provider + " provider"
                                            + " out of service");
                            break;
                        case LocationProvider.TEMPORARILY_UNAVAILABLE:
                            Log.v("GPS demo",
                                    "Status changed: " + provider + " provider"
                                            + " temporarily unavailable");
                            break;
                        case LocationProvider.AVAILABLE:
                            Log.v("GPS demo",
                                    "Status changed: " + provider + " provider"
                                            + " available");
                            break;
                    }
                }

                public void onProviderEnabled(String provider) {
                    //Log.v("GPS demo", "Enabled new provider: " + provider);
                }

                public void onProviderDisabled(String provider) {
                    //Log.v("GPS demo", "Disabled provider: " + provider);
                }

                public void onLocationChanged(Location location) {
                    //Log.v("GPS demo", "Location changed");
                    float lat = (float) (location.getLatitude());  float lon = (float) (location.getLongitude()); float alt = (float) (location.getAltitude());float sp = location.getSpeed();
                    float tm = location.getTime();
                    SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                    String ntm; ntm=format1.format(tm);
                   strsenGPS = "" +Float.toString(lat) + ","+Float.toString(lon)+"," + Float.toString(alt) +","+Float.toString(sp)+"," + ntm;

                }
            };

           // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setBuiltInZoomControls(true);
       ////

        //webSettings.setGeolocationDatabasePath(getFilesDir().getPath());
        //webSettings.setGeolocationEnabled(true);

        ////
        String newUA= "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        webSettings.setUserAgentString(newUA);
        sensorManagerA = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManagerM = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManagerL = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManagerG = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManagerO=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManagerP=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManagerT=(SensorManager) getSystemService(SENSOR_SERVICE);
        //reg class of the sensor

        sensorManagerA.registerListener(this, sensorManagerA.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManagerM.registerListener(this, sensorManagerM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManagerL.registerListener(this, sensorManagerL.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManagerG.registerListener(this,sensorManagerG.getDefaultSensor(Sensor.TYPE_GYROSCOPE),SensorManager.SENSOR_DELAY_NORMAL);
        sensorManagerO.registerListener(this,sensorManagerO.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),SensorManager.SENSOR_DELAY_NORMAL);
        sensorManagerP.registerListener(this,sensorManagerP.getDefaultSensor(Sensor.TYPE_PRESSURE),SensorManager.SENSOR_DELAY_NORMAL);
        sensorManagerT.registerListener(this,sensorManagerT.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),SensorManager.SENSOR_DELAY_NORMAL);

        lastUpdate = System.currentTimeMillis();

        webSettings.setAllowUniversalAccessFromFileURLs(true);
        mWebView.addJavascriptInterface(new DJavaScriptInterface(this), "AndroidFunction");
        mWebView.setWebContentsDebuggingEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.equals("http://opengis.dlinkddns.com/gis/opengis_eng.html")) {

                    Intent intentn = new Intent(Intent.ACTION_VIEW);
                    intentn.setData(Uri.parse(url));
                    startActivity(intentn);
                    return true;
                } else {
                    new Thread(new Runnable() {
                        public void run() {

                            mHandler.post(new Runnable() {
                                public void run() {
                                    //mProgress.setProgress(mProgressStatus);

                                    progressBar.setVisibility(ProgressBar.VISIBLE);
                                }
                            });
                        }
                        //}
                    }).start();


                    view.loadUrl(url);
                    return true;
                }
            }

            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);

            }


        });


        mWebView.setDownloadListener(

                new DownloadListener() {
                                private boolean isExternalStorageReadOnly() {
                String extStorageState = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
                    return true;
                }
                return false;
            }

            private boolean isExternalStorageAvailable() {
                String extStorageState = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
                    return true;
                }
                return false;
            }

            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype, long contentLength) {


///////////////////////////
                String filename = filenamesave;
                String string = shtml;
                FileOutputStream outputStream;
                String baseDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                File file = new File(baseDir, filename);
                FileWriter writer = null;
                try {
                    writer = new FileWriter(file);
                    writer.write(string.toString());
                    writer.close();

                    Toast.makeText(MainActivity.this, "File's saved in downloads folder", Toast.LENGTH_LONG).show();
                    MessageBox("File's saved in downloads folder");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });mWebView.getSettings().setGeolocationEnabled(true);

       //lm.addGpsStatusListener(lGPS);
        mWebView.setWebChromeClient(new WebChromeClient() {

            //WebView newView = new WebView(getApplicationContext());
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);

            }


            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                final WebView newView = (WebView) findViewById(R.id.webview_hidden);
                newView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        Intent intent = new Intent(MainActivity.this, PopupActivity.class);
                        intent.putExtra("URL", url);
                        if (url.equals("https://www.facebook.com/openwebgisPage") || url.equals("http://openwebgisystem.blogspot.ru/") || url.equals("https://m.youtube.com/user/openwebgis?") || url.equals("http://www.mediawiki.org/") || url.equals("https://www.mediawiki.org/wiki/MediaWiki") || url.equals("https://www.mediawiki.org/") || url.equals("https://www.youtube.com/user/openwebgis") || url.equals("http://vk.com/openwebgis")) {
                        } else {
                            startActivity(intent);

                            newView.destroy();
                        }
                    }
                });

                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newView);
                resultMsg.sendToTarget();
                return true;
                }
            });

        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        mWebView.loadUrl("file:///android_asset/index.html");


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float[] valuesM = event.values;
            double lengthVector=Math.sqrt(valuesM[0]*valuesM[0]+valuesM[1]*valuesM[1]+valuesM[2]*valuesM[2]);
            stringSensorM=""+String.format(Locale.US, "%.2f", lengthVector)+";"+String.format(Locale.US, "%.2f",valuesM[0])+";"+String.format(Locale.US, "%.2f",valuesM[1])+";"+String.format(Locale.US, "%.2f",valuesM[2]);
              }
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float[] valuesL = event.values;
            stringSensorL=""+String.format(Locale.US, "%.2f", valuesL[0]);
             }
        else{        }
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            float[] valuesT = event.values;
            stringSensorT=""+String.format(Locale.US, "%.2f", valuesT[0]);
            }
        else{        }
        if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            float[] valuesP = event.values;
            stringSensorP=""+String.format(Locale.US, "%.2f", valuesP[0]);

        }
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float[] valuesG = event.values;
            stringSensorG=""+String.format(Locale.US, "%.2f", (valuesG[0] * 180) / Math.PI) + ";" + String.format(Locale.US, "%.2f", (valuesG[1] * 180) / Math.PI) + ";" + String.format(Locale.US, "%.2f", (valuesG[2] * 180) / Math.PI);

        }
           if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            float[] valuesR = event.values;
          // Convert the rotation-vector to a 4x4 matrix.

            sensorManagerO.getRotationMatrixFromVector(mRotationMatrix, event.values);
            sensorManagerO.remapCoordinateSystem(mRotationMatrix, sensorManagerO.AXIS_X, sensorManagerO.AXIS_Z, mRotationMatrix);
            sensorManagerO.getOrientation(mRotationMatrix, orientationVals);

            // Optionally convert the result from radians to degrees
            orientationVals[0] = (float) Math.toDegrees(orientationVals[0]);
            if(orientationVals[0]<0){orientationVals[0]+=360;}
            orientationVals[1] = (float) Math.toDegrees(orientationVals[1]);
            orientationVals[2] = (float) Math.toDegrees(orientationVals[2]);

               stringSensorR=""+String.valueOf(orientationVals[0]) + ";" + String.valueOf(orientationVals[1]) + ";" + String.valueOf( orientationVals[2]);


        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float[] values = event.values;
            // the projection of the acceleration on the axis of the coordinate system
            float x = values[0];
            float y = values[1];
            float z = values[2];
            stringSensorA=""+String.format(Locale.US, "%.2f", x)+";"+String.format(Locale.US, "%.2f", y)+";"+String.format(Locale.US, "%.2f", y)+";";
            //phone acceleration module square, divided by the square of the gravity acceleration
            float accelationSquareRoot = (x * x + y * y + z * z)/(SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
            //current time
            long actualTime = System.currentTimeMillis();

               }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        //sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // unregister listener

        try{ sensorManagerA.unregisterListener(this);}catch(Exception e){}
        try{sensorManagerL.unregisterListener(this);}catch(Exception e){}
      try{sensorManagerT.unregisterListener(this);}catch(Exception e){}
        try{sensorManagerG.unregisterListener(this);}catch(Exception e){}
        try{sensorManagerO.unregisterListener(this);}catch(Exception e){}
        try{sensorManagerP.unregisterListener(this);}catch(Exception e){}
        checkPermission("android.permission.ACCESS_FINE_LOCATION", 1, 0);
      try{ mLocationManager.removeUpdates(mLocationListener);}catch(Exception e){}
        mLocationManager=null;mLocationListener=null;
        //mLocationManager.
        super.onPause();

    }

    public class DJavaScriptInterface {
        Context mContext;

        DJavaScriptInterface(Context c) {
            mContext = c;
        }
        @JavascriptInterface
        public void showToast(String textfile, String filename)
        {shtml=textfile.toString();
            filenamesave=filename.toString();
            if (Environment.getExternalStorageState() == null) {
                //create new file directory object
                File directory = new File(Environment.getDataDirectory() + "/OpenWebGIS/");
                // if no directory exists, create new directory
                if (!directory.exists()) { directory.mkdir();  }
                File file = new File(directory, filename);
                FileWriter writer = null;
                try {
                    writer = new FileWriter(file);
                    writer.write(directory.toString());
                    writer.close();

                    Toast.makeText(MainActivity.this, "File's saved in OpenWebGIS folder", Toast.LENGTH_LONG).show();
                    MessageBox("File's saved in OpenWebGIS folder");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                filename = filenamesave;
                String string = shtml;
                FileOutputStream outputStream;
                String baseDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                File file = new File(baseDir, filename);
                FileWriter writer = null;
                try {
                    writer = new FileWriter(file);
                    writer.write(string.toString());
                    writer.close();

                    Toast.makeText(MainActivity.this, "File's saved in downloads folder", Toast.LENGTH_LONG).show();
                    MessageBox("File's saved in downloads folder");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        @JavascriptInterface
        public String GetForced_GPSdata() {

            //mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            checkPermission("android.permission.ACCESS_FINE_LOCATION", 1, 0);
            final Location currentLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


           if (currentLocation != null) {
                float lat = (float) (currentLocation.getLatitude());
                float lon = (float) (currentLocation.getLongitude());
                float alt = (float) (currentLocation.getAltitude());
                float sp = currentLocation.getSpeed();
                               float tm = currentLocation.getTime();
                 SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
               String ntm; ntm=format1.format(tm);
               strsenGPS = "" +Float.toString(lat) + ","+Float.toString(lon)+"," + Float.toString(alt) +","+Float.toString(sp)+"," + ntm;
            } else {
                strsenGPS = "No location info,No location info,No location info,No location info,No location info,";

            }
            /*mLocationListener = new LocationListener() {
                public void onStatusChanged(String provider, int status,
                                            Bundle extras) {
                    switch (status) {
                        case LocationProvider.OUT_OF_SERVICE:
                            Log.v("GPS demo",
                                    "Status changed: " + provider + " provider"
                                            + " out of service");
                            break;
                        case LocationProvider.TEMPORARILY_UNAVAILABLE:
                            Log.v("GPS demo",
                                    "Status changed: " + provider + " provider"
                                            + " temporarily unavailable");
                            break;
                        case LocationProvider.AVAILABLE:
                            Log.v("GPS demo",
                                    "Status changed: " + provider + " provider"
                                            + " available");
                            break;
                    }
                }

                public void onProviderEnabled(String provider) {
                    //Log.v("GPS demo", "Enabled new provider: " + provider);
                }

                public void onProviderDisabled(String provider) {
                    //Log.v("GPS demo", "Disabled provider: " + provider);
                }

                public void onLocationChanged(Location location) {
                    //Log.v("GPS demo", "Location changed");
                    float lat = (float) (location.getLatitude());  float lon = (float) (location.getLongitude()); float alt = (float) (location.getAltitude());float sp = location.getSpeed();
                    float tm = location.getTime();
                    SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                    String ntm; ntm=format1.format(tm);
                   strsenGPS = "" +Float.toString(lat) + ","+Float.toString(lon)+"," + Float.toString(alt) +","+Float.toString(sp)+"," + ntm;

                }
            };*/

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
            if (mLocationManager != null) {

                final Location currentLocationN = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (currentLocationN != null) {
                    float lat = (float) (currentLocationN.getLatitude());
                    float lon = (float) (currentLocationN.getLongitude());
                    float alt = (float) (currentLocationN.getAltitude());
                    float sp = currentLocationN.getSpeed();
                    float tm = currentLocationN.getTime();
                    SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                    String ntm; ntm=format1.format(tm);
                    strsenGPS = "" +Float.toString(lat) + ","+Float.toString(lon)+"," + Float.toString(alt) +","+Float.toString(sp)+"," + ntm;
                    } else {
                    strsenGPS = "No location info,No location info,No location info,No location info,No location info,";

                }


            }
            return strsenGPS;
        }
        @JavascriptInterface
        public String dataSensorsM() {return stringSensorM;}
        @JavascriptInterface
        public String dataSensorsL()
        {
            return stringSensorL;
        }
        @JavascriptInterface
        public String dataSensorsT() { return stringSensorT; }
        @JavascriptInterface
        public String dataSensorsG() { return stringSensorG; }
        @JavascriptInterface
        public String dataSensorsR() { return stringSensorR; }
        @JavascriptInterface
        public String dataSensorsA() { return stringSensorA; }
        @JavascriptInterface
        public String dataSensorsP() { return stringSensorP; }

        @JavascriptInterface
        public String getWiFi()
        {
            checkPermission("android.permission.CHANGE_WIFI_STATE", 1, 0);
            if (wifi.isWifiEnabled() == false)
            {
                AlertDialog.Builder builderWF = new AlertDialog.Builder(MainActivity.this);
                builderWF.setMessage("WiFi is disabled. Make it enabled?");
                builderWF.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        wifi.setWifiEnabled(true);
                    }
                });
                builderWF.setNegativeButton("Cancel", null);
                if( DialogWF != null && DialogWF.isShowing() ){}
                else{DialogWF = builderWF.show();}

                //wifi.setWifiEnabled(true);

            }
            wifi_results = wifi.getScanResults();
            sizeWF = wifi_results.size();
            //wifi.startScan();
            String FullWiFI_string=""; String SSIDw="";String BSSIDw="";String capabilitiesw="";String centerFreq0w="";
            String centerFreq1w=""; String channelWidthw=""; String frequencyw=""; String levelw=""; String operatorFriendlyNamew=""; String timestampw="";
            String venueNamew="";String levelwPers="";

            while (sizeWF > 0) {
                SSIDw+=wifi_results.get(sizeWF-1).SSID.toString()+"_;_";
                BSSIDw+=wifi_results.get(sizeWF-1).BSSID.toString()+"_;_";
                capabilitiesw+=wifi_results.get(sizeWF-1).capabilities.toString()+"_;_";
                if(Build.VERSION.SDK_INT>=23 ){centerFreq0w+=wifi_results.get(sizeWF-1).centerFreq0+"_;_";}else{centerFreq0w+=" "+"_;_";}
                if(Build.VERSION.SDK_INT>=23 ){centerFreq1w+=wifi_results.get(sizeWF-1).centerFreq1+"_;_";}else{centerFreq1w+=" "+"_;_";}
                if(Build.VERSION.SDK_INT>=23)
                {channelWidthw+=wifi_results.get(sizeWF-1).channelWidth+"_;_";}
                else{channelWidthw+=" "+"_;_";}
                frequencyw+=wifi_results.get(sizeWF-1).frequency+"_;_";
                levelw+=wifi_results.get(sizeWF-1).level+"_;_";
                levelwPers+=WifiManager.calculateSignalLevel(wifi_results.get(sizeWF - 1).level, 100)+"_;_";
                if(Build.VERSION.SDK_INT>=23){operatorFriendlyNamew+=wifi_results.get(sizeWF-1).operatorFriendlyName+"_;_";}
                else{operatorFriendlyNamew+=" "+"_;_";}
                SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                float tm =System.currentTimeMillis() - SystemClock.elapsedRealtime() + (wifi_results.get(sizeWF-1).timestamp/1000);
                String ntm; ntm=format1.format(tm);
                timestampw+=ntm+"_;_";
                if(Build.VERSION.SDK_INT>=23)
                {venueNamew+=wifi_results.get(sizeWF-1).venueName+"_;_";}
                else{venueNamew+=" "+"_;_";}


                //MessageBox(SSIDw);
                sizeWF = sizeWF - 1;
            }
            FullWiFI_string=SSIDw+"_//_"+BSSIDw+"_//_"+capabilitiesw+"_//_"+centerFreq0w+"_//_"+centerFreq1w+"_//_"+channelWidthw+"_//_"+frequencyw+"_//_"+levelw+"_//_"+operatorFriendlyNamew+"_//_"+timestampw+"_//_"+venueNamew+"_//_"+levelwPers;
            if(wifi_results.size()==0)
            {String nw="no WiFi_;_";FullWiFI_string=nw+"_//_"+nw+"_//_"+nw+"_//_"+nw+"_//_"+nw+"_//_"+nw+"_//_"+nw+"_//_"+nw+"_//_"+nw+"_//_"+nw+"_//_"+nw+"_//_"+nw;}
            return FullWiFI_string;

        }


        @JavascriptInterface
        public String   setAndroidfileZIPand()
        {
                       return ntext;
        }

        @JavascriptInterface
        public void AndroidFileChooser() {
            new FileChooser(MainActivity.this).setFileListener(new FileChooser.FileSelectedListener() {
                @Override
                public void fileSelected(final File file) {
                    // do something with the file
                    String line = "";
                    StringBuilder text = new StringBuilder();

                   try {
                        FileReader fReader = new FileReader(file);
                        BufferedReader bReader = new BufferedReader(fReader);
                       //FileInputStream in;
                       //BufferedInputStream buf;
                       //MessageBox(bReader.readLine());
                       final String filename=file.getName();
                       String filenameArray[] = filename.split("\\.");
                       String extension = filenameArray[filenameArray.length-1];
                       if(extension.equals("jpg")||extension.equals("png")||extension.equals("jpeg")||extension.equals("gif")||extension.equals("bmp"))
                       {
                           //in = new FileInputStream(file);
                           //buf = new BufferedInputStream(in);
                           int size = (int) file.length();
                           byte[] bytes = new byte[size];
                           try {
                               BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                               buf.read(bytes, 0, bytes.length);
                               String base64 = Base64.encodeToString(bytes, Base64.DEFAULT);
                               if(extension.equals("jpg"))
                               {text.append("data:image/jpg;base64,"+base64);}
                               if(extension.equals("png"))
                               {text.append("data:image/png;base64,"+base64);}
                               if(extension.equals("jpeg"))
                               {text.append("data:image/jpeg;base64,"+base64);}
                               if(extension.equals("gif"))
                               {text.append("data:image/gif;base64,"+base64);}
                               if(extension.equals("bmp"))
                               {text.append("data:image/bmp;base64,"+base64);}
                               buf.close();
                           } catch (FileNotFoundException e) {
                               // TODO Auto-generated catch block
                               e.printStackTrace();
                           } catch (IOException e) {
                               // TODO Auto-generated catch block
                               e.printStackTrace();
                           }

                          /* while( (line = bReader.readLine()) != null  ){byte[] data = line.getBytes("UTF-8");String base64 = Base64.encodeToString(data, Base64.DEFAULT);
                               text.append(base64 + "\n");*/
                           //}
                       }
                       else
                       { while( (line = bReader.readLine()) != null  ){
                           text.append(line + "\n");
                        } }

                      ntext=text.toString();
                       mWebView.post(new Runnable() {
                           @Override
                           public void run() {String g=ntext;
                             mWebView.loadUrl("javascript:setAndroidfileZIPvar2()");

                           }
                       });
                       mWebView.post(new Runnable() {
                           @Override
                           public void run() {String name=filename;
                               mWebView.loadUrl("javascript:setAndroidfileZIPvarName('"+name+"')");
                           }
                       });
                   } catch (IOException e) {
                       // e.printStackTrace();
                    }
                }
           }).showDialog();
        }

    }

}
class WebViewClientD extends WebViewClient  {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        	    view.loadUrl(url);
        return true;
    }

}



