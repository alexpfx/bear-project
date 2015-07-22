package br.com.alexpfx.bear.app.audio.audiog;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by alexandre on 21/07/15.
 */
public class AudioRecorderImpl implements AudioRecorder {
    private AudioRecord audioRecord;
    private boolean isRecording;
    private int channelConfiguration = AudioFormat.CHANNEL_IN_MONO;
    private int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    private int sampleRate = 44100;
    private int frameByteSize = 2048; // for 1024 fft size (16bit sample size)
    private byte[] buffer;
    public static final LinkedBlockingDeque<Runnable> WORK_QUEUE = new LinkedBlockingDeque<Runnable>();
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 120, TimeUnit.SECONDS, WORK_QUEUE);
    private boolean running = false;

    public AudioRecorderImpl() {
        int recBufSize = AudioRecord.getMinBufferSize(sampleRate, channelConfiguration, audioEncoding);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfiguration, audioEncoding, recBufSize);
        buffer = new byte[frameByteSize];
    }

    @Override
    public void startRecording() {
        running = true;
        audioRecord.startRecording();

    }

    @Override
    public void stopRecording() {
        audioRecord.stop();
        running = false;
    }

    @Override
    public byte[] getFrameBytes() {
        audioRecord.read(buffer, 0, frameByteSize);
        final int averageAbsValue = getAverageAbsValue(buffer);
        return averageAbsValue > 30 ? buffer : new byte[0];
    }

    private int getAverageAbsValue(byte[] buffer) {
        int totalAbsValue = 0;
        short sample = 0;
        float averageAbsValue = 0.0f;

        for (int i = 0; i < frameByteSize; i += 2) {
            sample = (short) ((buffer[i]) | buffer[i + 1] << 8);
            totalAbsValue += Math.abs(sample);
        }
        return totalAbsValue / frameByteSize / 2;
    }

    @Override
    public void run() {
        startRecording();
    }

    @Override
    public boolean isRecording() {
        return Thread.currentThread().isAlive() && isRecording;
    }

    @Override
    public int getBitsPerSample() {
        switch (audioRecord.getAudioFormat()){
            case AudioFormat.ENCODING_PCM_16BIT: return 16;
            case AudioFormat.ENCODING_PCM_8BIT: return 8;
        }
        return 0;
    }

    @Override
    public int getChannel() {
        switch (audioRecord.getChannelConfiguration()){
            case AudioFormat.CHANNEL_IN_MONO: return 1;
        }
        return 0;
    }

    @Override
    public int getSampleRate() {
        return audioRecord.getSampleRate();
    }
}
