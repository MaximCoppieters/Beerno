package be.pxl.beerno;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

public class EstablishmentActivity extends AppCompatActivity {

    private int drinkCount;
    private String establishmentName;
    private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }



    public void IncrementDrinkCount() {
        drinkCount++;
    }

    public void DecrementDrinkCount() {
        if (drinkCount > 0) {
            drinkCount--;
        }
    }
}
