package pro.jazzy.jsjava;

import org.xwalk.core.JavascriptInterface;

import java.util.List;

/**
 * Created by Radoslaw Halski, Jazzy Innovations on 25/04/2017.
 */

public class Math {
    @JavascriptInterface
    public int sum(Object[] array) {
        // array is null ;(
        return -1;
    }
}
