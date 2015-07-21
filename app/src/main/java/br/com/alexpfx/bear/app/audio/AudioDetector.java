package br.com.alexpfx.bear.app.audio;

/**
 * Created by alexandre on 21/07/15.
 */
public interface AudioDetector extends Runnable {

    void setRunning (boolean running);
    boolean isRunning ();

}
