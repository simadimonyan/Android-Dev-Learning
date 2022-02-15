package git.simadimonyan.voicerecorder;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaRecorder recorder;
    private Button start;
    private Button stop;
    private Button play;
    private String output = null;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            boolean recordPerm = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean readPerm = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            boolean writePerm = grantResults[2] == PackageManager.PERMISSION_GRANTED;
            if (!recordPerm) MainActivity.super.finish();
            if (!writePerm) MainActivity.super.finish();
            if (!readPerm) MainActivity.super.finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE}, 10);

        try {
            output = Environment.getStorageDirectory().getCanonicalPath() + "/lastrecording.3gp";
        } catch (IOException e) {
            e.printStackTrace();
        }

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        assert output != null;
        recorder.setOutputFile(new File(output));

        start = (Button) findViewById(R.id.button3);
        stop = (Button) findViewById(R.id.button2);
        play = (Button) findViewById(R.id.button);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        play.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.button3:
                    start(v);
                    break;
                case R.id.button2:
                    stop(v);
                    break;
                case R.id.button:
                    play(v);
                    break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(View v) throws IOException {
        recorder.prepare();
        recorder.start();
        start.setEnabled(false);
        stop.setEnabled(true);
        play.setEnabled(false);

        Toast.makeText(getApplicationContext(), "Recording started!",
                Toast.LENGTH_SHORT).show();
    }

    public void stop(View v) {
        recorder.stop();
        recorder.release();
        recorder = null;
        stop.setEnabled(false);
        start.setEnabled(true);
        play.setEnabled(true);

        Toast.makeText(getApplicationContext(), "Recording finished!",
                Toast.LENGTH_SHORT).show();
    }

    public void play(View v) throws IOException {
        MediaPlayer player = new MediaPlayer();
        player.setDataSource(output);
        player.prepare();
        player.start();
        stop.setEnabled(false);

        Toast.makeText(getApplicationContext(), "Audio is playing!",
                Toast.LENGTH_SHORT).show();
    }

}