package pro.jazzy.jsjava;

import android.content.Context;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.webkit.WebView;

import org.xwalk.core.JavascriptInterface;
import org.xwalk.core.XWalkView;

import java.util.Locale;
import java.util.TimerTask;

/**
 * Created by Radoslaw Halski, Jazzy Innovations on 17/04/2017.
 */

public class GpsManager {
    private Timer mTimer;
    private XWalkView mWebView;
    private Context mContext;
    private LocationManager mLocationManager;
    private String mJsOnUpdateMethodCallback;
    private PointF mLastKnownPosition;


    public GpsManager(Context context, XWalkView webView) {
        mWebView = webView;
        mContext = context;

        mTimer = new Timer(mTimerTask);
        mTimer.setInterval(5);

        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        mLastKnownPosition = new PointF(0f, 0f);
    }

    @JavascriptInterface
    public void start() {
//        mJsOnStartMethodCallback = method;
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);

        mTimer.start();
    }

    @JavascriptInterface
    public void stop() {
//        mJsOnStopMethodCallback = method;
        mLocationManager.removeUpdates(mLocationListener);

        mTimer.stop();
    }

    @JavascriptInterface
    public void onUpdate(String method) {
        mJsOnUpdateMethodCallback = method;
    }

    @JavascriptInterface
    public void setDelay(String interval) {
        mTimer.setInterval(Long.parseLong(interval));
    }

    @JavascriptInterface
    public long getDelay() {
        return mTimer.getInterval();
    }

    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            final String call = String.format(Locale.ENGLISH,
                    "%s(%f,%f);",
                    mJsOnUpdateMethodCallback,
                    mLastKnownPosition.x,
                    mLastKnownPosition.y);

            mWebView.post(new Runnable() {
                @Override
                public void run() {
                    mWebView.evaluateJavascript(call, null);
                }
            });
        }
    };

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mLastKnownPosition.set((float) location.getLatitude(), (float) location.getLongitude());
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
}



