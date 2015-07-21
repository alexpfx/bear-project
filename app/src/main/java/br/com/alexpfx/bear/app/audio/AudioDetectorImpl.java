package br.com.alexpfx.bear.app.audio;

import com.musicg.api.WhistleApi;
import com.musicg.wave.WaveHeader;

/**
 * Created by alexandre on 21/07/15.
 */
public class AudioDetectorImpl implements AudioDetector {

    private AudioRecorder audioRecorder;
    private WaveHeader waveHeader;
    private WhistleApi whistleApi;
    private boolean isRunning = true;


    public AudioDetectorImpl(AudioRecorder audioRecorder) {
        this.audioRecorder = audioRecorder;
        waveHeader = new WaveHeader();
        int channel = audioRecorder.getChannel();

        waveHeader.setChannels(channel);
        int bitsPerSample = audioRecorder.getBitsPerSample();

        waveHeader.setBitsPerSample(bitsPerSample);

        int sampleRate = audioRecorder.getSampleRate();

        waveHeader.setSampleRate(sampleRate);

        whistleApi = new WhistleApi(waveHeader);

    }

    @Override
    public void run() {
        byte[] buffer;
        while (isRunning){
            buffer = audioRecorder.getFrameBytes();

            if (buffer.length > 0){
                for (byte b : buffer){
                    System.out.print(b);
                }
                System.out.println();
                System.out.println(whistleApi.isWhistle(buffer));
            }
        }
    }

    @Override
    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
}
