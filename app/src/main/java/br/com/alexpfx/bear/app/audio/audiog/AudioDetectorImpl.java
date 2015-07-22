package br.com.alexpfx.bear.app.audio.audiog;

import com.musicg.api.ClapApi;
import com.musicg.api.WhistleApi;
import com.musicg.wave.Wave;
import com.musicg.wave.WaveHeader;
import com.musicg.wave.WaveTypeDetector;

/**
 * Created by alexandre on 21/07/15.
 */
public class AudioDetectorImpl implements AudioDetector {

    private AudioRecorder audioRecorder;
    private WaveHeader waveHeader;
    private WhistleApi whistleApi;
    private ClapApi clapApi;
    private boolean isRunning = true;
    private OnSignalsDetectedListener onSignalsDetectedListener;


    WaveTypeDetector typeDetector;

    public AudioDetectorImpl(OnSignalsDetectedListener listener, AudioRecorder audioRecorder) {
        this.onSignalsDetectedListener = listener;
        this.audioRecorder = audioRecorder;
        waveHeader = new WaveHeader();
        int channel = audioRecorder.getChannel();

        waveHeader.setChannels(channel);
        int bitsPerSample = audioRecorder.getBitsPerSample();

        waveHeader.setBitsPerSample(bitsPerSample);

        int sampleRate = audioRecorder.getSampleRate();

        waveHeader.setSampleRate(sampleRate);

        whistleApi = new WhistleApi(waveHeader);

        clapApi = new ClapApi(waveHeader);

        typeDetector = new WaveTypeDetector(new Wave(waveHeader, new byte[2048]));


    }

    @Override
    public void run() {
        byte[] buffer;
        while (isRunning){
            buffer = audioRecorder.getFrameBytes();

            if (buffer.length > 0){
                if (whistleApi.isWhistle(buffer)){
                    onSignalsDetectedListener.onWhistleDetected();
                }
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
