package openwebgis_app.openwebgis;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
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

public class MainActivity extends AppCompatActivity {
    public static WebView mWebView;private ProgressDialog progressDialog;public String shtml;public String filenamesave;public String ntext;
    public Handler mHandler = new Handler();
   // public ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);


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
        String newUA= "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        webSettings.setUserAgentString(newUA);

        webSettings.setAllowUniversalAccessFromFileURLs(true);
        mWebView.addJavascriptInterface(new DJavaScriptInterface(this), "AndroidFunction");
        mWebView.setWebContentsDebuggingEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {final  ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

       @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.equals("http://opengis.dlinkddns.com/gis/opengis_eng.html")) {

                    Intent intentn = new Intent(Intent.ACTION_VIEW);
                    intentn.setData(Uri.parse(url));
                    startActivity(intentn);
                    return true;
                }
                else
                {
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
                 return true;}
            }

            public void onPageFinished(WebView view, String url) {
                  progressBar.setVisibility(ProgressBar.INVISIBLE);

            }


        });


        mWebView.setDownloadListener(new DownloadListener() {
            private boolean isExternalStorageReadOnly() {
                String extStorageState = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
                    return true;
                }                return false;            }
            private boolean isExternalStorageAvailable() {
                String extStorageState = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
                    return true;     }                return false;          }

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
        });

            mWebView.setWebChromeClient(new WebChromeClient() {
            WebView newView = new WebView(getApplicationContext());

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                final WebView newView = (WebView) findViewById(R.id.webview_hidden);
                newView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        Intent intent = new Intent(MainActivity.this, PopupActivity.class);
                        intent.putExtra("URL", url);
                        if(url.equals("https://www.facebook.com/openwebgisPage")||url.equals("http://openwebgisystem.blogspot.ru/")||url.equals("https://m.youtube.com/user/openwebgis?")||url.equals("http://www.mediawiki.org/")||url.equals("https://www.mediawiki.org/wiki/MediaWiki")||url.equals("https://www.mediawiki.org/")||url.equals("https://www.youtube.com/user/openwebgis")||url.equals("http://vk.com/openwebgis"))
                        {}
                        else{
                             startActivity(intent);

                        newView.destroy();}
                    }
                });

                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newView);
                resultMsg.sendToTarget();
                return true;
            }
        });
        mWebView.loadUrl("file:///android_asset/index.html");


    }

    public class DJavaScriptInterface {
        Context mContext;

        DJavaScriptInterface(Context c) {
            mContext = c;
        }
        @JavascriptInterface
        public void showToast(String textfile, String filename){shtml=textfile.toString(); filenamesave=filename.toString();
            // Toast.makeText(mContext, toast.toString(), Toast.LENGTH_SHORT).show();

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



