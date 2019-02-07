package be.pxl.beerno;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import java.util.List;

public class EstablishmentActivity extends AppCompatActivity {

    private int drinkCount;
    private String establishmentName;
    private Button incrementButton;
    private Button decrementButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        incrementButton = findViewById(R.id.increment_button);
        decrementButton = findViewById(R.id.decrement_button);

        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drinkCount++;
            }
        });
        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drinkCount--;
            }
        });
    }
}
