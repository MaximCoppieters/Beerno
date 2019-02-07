package be.pxl.beerno;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class EstablishmentActivity extends AppCompatActivity {

    private int drinkCount;
    private TextView establishmentName;
    private Button incrementButton;
    private Button decrementButton;
    private CardView resumeButton;
    private Establishment establishment;
    private ImageView beer_image;
    private List<Beer> beerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        incrementButton = findViewById(R.id.increment_button);
        decrementButton = findViewById(R.id.decrement_button);
        resumeButton = findViewById(R.id.resume);
        establishmentName = findViewById(R.id.establishment_name);
        //establishmentName.setText(establishment.getName());
        beerList = BeerRepository.getAllBeers();
        beer_image = findViewById(R.id.beer_image);
        for (Beer beer : beerList
             ) {
            beer_image.setImageResource(beer.getImageId());
            Picasso.get().load(beer.getImageId()).into(beer_image);
        }

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

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EstablishmentActivity.this, BeerRouteActivity.class));
            }
        });
    }
}
