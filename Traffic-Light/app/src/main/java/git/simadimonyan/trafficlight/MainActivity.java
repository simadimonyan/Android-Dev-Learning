package git.simadimonyan.trafficlight;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private LinearLayout block1, block2, block3;
    private boolean start = false; //start button mode
    private Button button;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        block1 = findViewById(R.id.block1); //init blocks
        block2 = findViewById(R.id.block2);
        block3 = findViewById(R.id.block3);
        button = findViewById(R.id.button); //init button
    }

    @SuppressLint("SetTextI18n")
    public void onClickButton(View view) {
        if (!start) { //if button is pressed yet
            start = true;
            button.setText("Stop"); //change mode on stop
            new Thread((Runnable) () -> {
                while(start) {
                    counter++;
                    switch (counter) {
                        case 1:
                            block3.setBackgroundColor(getResources().getColor(R.color.grey));
                            block1.setBackgroundColor(getResources().getColor(R.color.green)); //first block
                            break;
                        case 2:
                            block1.setBackgroundColor(getResources().getColor(R.color.grey));
                            block2.setBackgroundColor(getResources().getColor(R.color.yellow)); //second block
                            break;
                        case 3:
                            block2.setBackgroundColor(getResources().getColor(R.color.grey));
                            block3.setBackgroundColor(getResources().getColor(R.color.red)); //third block
                            counter = 0;
                            break;
                    }
                    try {
                        Thread.sleep(1000); //per 3 seconds

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        else {
            start = false; //change start button mode
            button.setText("Start"); //change mode on start
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        start = false; //kill thread on destroy
    }
}