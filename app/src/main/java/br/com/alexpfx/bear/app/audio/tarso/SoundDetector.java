package br.com.alexpfx.bear.app.audio.tarso;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;

/**
 * Created by alexandre on 21/07/15.
 */
public class SoundDetector implements AudioProcessor {
    private AudioDispatcher dispatcher;

    public SoundDetector(AudioDispatcher dispatcher) {
        this.dispatcher = dispatcher;

    }

    @Override
    public boolean process(AudioEvent audioEvent) {
        return false;
    }

    @Override
    public void processingFinished() {

    }
}
