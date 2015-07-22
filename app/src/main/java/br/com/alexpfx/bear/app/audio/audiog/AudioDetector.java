package br.com.alexpfx.bear.app.audio.audiog;

/**
 * Created by alexandre on 21/07/15.
 */
public interface AudioDetector extends Runnable {

    void setRunning (boolean running);
    boolean isRunning ();

    interface OnSignalsDetectedListener {
        void onClapDetected ();
        void onWhistleDetected ();
    }

}
