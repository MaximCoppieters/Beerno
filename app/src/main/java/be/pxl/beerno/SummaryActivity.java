package be.pxl.beerno;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {

    private CardView finishCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        finishCardView = findViewById(R.id.finish);
        finishCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SummaryActivity.this, BeerSelectActivity.class));
            }
        });

        TextView beerDrinkTextView = findViewById(R.id.total_beer_count);

        beerDrinkTextView.setText(String.valueOf(Stats.getTotalBeersDrank()));
    }
}
