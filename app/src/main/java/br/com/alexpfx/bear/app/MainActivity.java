package br.com.alexpfx.bear.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import br.com.alexpfx.android.lib.popcorntime.PopcornTimeMainActivity;
import br.com.alexpfx.bear.app.audio.audiog.AudioDetector;
import br.com.alexpfx.bear.app.audio.audiog.AudioDetectorImpl;
import br.com.alexpfx.bear.app.audio.audiog.AudioRecorder;
import br.com.alexpfx.bear.app.audio.audiog.AudioRecorderImpl;
import br.com.alexpfx.bear.app.popcorn.RemoteControlPopcornTime;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements AudioDetector.OnSignalsDetectedListener {
    AudioRecorder audioRecorder;
    AudioDetector audioDetector;
    Thread recordThread;
    Thread detectorThread;

    @Bind(R.id.edtDetections)
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        audioRecorder = new AudioRecorderImpl();
        audioDetector = new AudioDetectorImpl(this, audioRecorder);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnStartRecord)
    void btnStartRecordClick() {
        recordThread = new Thread(audioRecorder);
        detectorThread = new Thread(audioDetector);
        recordThread.start();
        detectorThread.start();


    }

    @OnClick(R.id.btnStopRecord)
    void btnStopRecordClick() {
        audioRecorder.stopRecording();

    }


    @OnClick(R.id.btnLock)
    void btnLockClick() {
//        startActivity(new Intent(this, PopcornTimeMainActivity.class));
    }

    @OnClick(R.id.btnPopConnect)
    void bntPopConnectOnClick (){
//        new RemoteControlPopcornTime().connect();
        Intent i = new Intent(getApplicationContext(), PopcornTimeMainActivity.class);


        startActivity(i);
    }

    @Override
    public void onClapDetected() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editText.append(" Palmas! ");

            }
        });
    }

    @Override
    public void onWhistleDetected() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editText.append(" Apito! ");
            }
        });
    }

}
