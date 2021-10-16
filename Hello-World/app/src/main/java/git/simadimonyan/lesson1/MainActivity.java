package git.simadimonyan.lesson1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences pref; //preferences for saving enters and exits
    private int enters = 0; //how many times user entered the app
    private int exits = 0; //how many times user exited the app

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getSharedPreferences("Storage", MODE_PRIVATE);
        enters = pref.getInt("enters", 0); //get enters
        exits = pref.getInt("exits", 0); //get exits
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();
        enters += 1; // +1 enter per start
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.enters);
        textView.setText("Enters: " + enters); //enters

        TextView textView2 = findViewById(R.id.exits);
        textView2.setText("Exits: " + exits); //exits
    }

    @Override
    protected void onPause() {
        super.onPause();
        exits += 1; // +1 per exit
        load(); //load preferences in storage
    }

    void load() {
       SharedPreferences.Editor edit = pref.edit();
       edit.putInt("enters", enters);
       edit.apply();
    }

}