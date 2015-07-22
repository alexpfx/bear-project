package br.com.alexpfx.bear.app.audio.audiog;

/**
 * Created by alexandre on 21/07/15.
 */
public interface AudioRecorder extends Runnable{

    void startRecording ();
    void stopRecording ();

    byte [] getFrameBytes ();

    boolean isRecording ();

    int getBitsPerSample ();

    int getChannel ();

    int getSampleRate ();



}
