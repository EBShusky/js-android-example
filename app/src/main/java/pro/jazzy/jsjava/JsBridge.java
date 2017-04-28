package pro.jazzy.jsjava;


import org.xwalk.core.JavascriptInterface;

/**
 * Created by Radoslaw Halski, Jazzy Innovations on 17/04/2017.
 */

public class JsBridge {
    private GpsManager mGpsManager;
    private Math mMath;

    public JsBridge(GpsManager manager, Math math) {
        mGpsManager = manager;
        mMath = math;
    }

    @JavascriptInterface
    public GpsManager getGps() {
        return mGpsManager;
    }

    @JavascriptInterface
    public Math getMath() {
        return mMath;
    }
}

