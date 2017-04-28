package pro.jazzy.jsjava;

import java.util.TimerTask;

/**
 * Created by Radoslaw Halski, Jazzy Innovations on 26/04/2017.
 */

public class Timer {
    private long mInterval;
    private java.util.Timer mTimer;
    private Runnable mRunnable;

    public Timer(Runnable runnable) {
        mRunnable = runnable;
    }

    public void setInterval(long intervalInSeconds) {
        mInterval = intervalInSeconds;

        // Restart timer with new interval :]
        if (mTimer != null) {
            stop();
            start();
        }
    }

    public long getInterval() {
        return mInterval;
    }

    public void start() {
        if (mTimer == null) {
            mTimer = new java.util.Timer();
            mTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    mRunnable.run();
                }
            }, 0, mInterval * 1000);
        }
    }

    public void stop() {
        if (mTimer != null) {
            mTimer.purge();
            mTimer.cancel();
            mTimer = null;
        }
    }
}
