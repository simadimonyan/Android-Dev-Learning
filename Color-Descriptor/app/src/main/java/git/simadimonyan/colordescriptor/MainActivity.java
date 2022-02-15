package git.simadimonyan.colordescriptor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinner);
        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(v -> {
            int position = spinner.getSelectedItemPosition();
            String description = getDescription(position);
            textView.setText(description);
        });
    }

    public String getDescription(int position) {
        String[] descriptions = getResources().getStringArray(R.array.descriptions);
        return descriptions[position];
    }

}