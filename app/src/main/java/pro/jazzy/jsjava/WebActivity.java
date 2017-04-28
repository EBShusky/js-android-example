package pro.jazzy.jsjava;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.xwalk.core.XWalkActivityDelegate;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkView;

public class WebActivity extends AppCompatActivity {
    private XWalkView mWebView;
    private XWalkActivityDelegate mActivityDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mWebView = (XWalkView) findViewById(R.id.webView);

        XWalkPreferences.setValue("enable-javascript", true);

        // Show permission request (if needed).
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 666);
        }
        else {
            // Granted!
            init();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivityDelegate.onResume();
        if (mWebView != null) {
            mWebView.resumeTimers();
            mWebView.onShow();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.pauseTimers();
            mWebView.onHide();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.onDestroy();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mWebView != null) {
            mWebView.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mWebView != null) {
            mWebView.onNewIntent(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 666: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Granted!
                    init();
                } else {
                    // permission denied, boo!
                    finish();
                }
                return;
            }
        }
    }

    private void init() {
        mActivityDelegate = new XWalkActivityDelegate(this, new Runnable() {
            @Override
            public void run() {
                WebActivity.this.finish();
            }
        }, new Runnable() {
            @Override
            public void run() {
                GpsManager manager = new GpsManager(WebActivity.this, mWebView);
                Math math = new Math();
                JsBridge bridge = new JsBridge(manager, math);

                mWebView.loadUrl("file:///android_asset/js-java-example-app/index.html");
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.addJavascriptInterface(bridge, "NativeApp");
            }
        });
    }
}
