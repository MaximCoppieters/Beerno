package be.pxl.beerno;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class EstablishmentActivity extends AppCompatActivity {

    private int drinkCount;
    private TextView establishmentNameTextView;
    private Button incrementButton;
    private Button decrementButton;
    private CardView resumeButton;
    private Establishment establishment;
    private ImageView beerImageView;
    private List<Beer> beersOnMenu;

    private RecyclerView recyclerView;
    private BeerAdapter beerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        establishment = (Establishment) getIntent().getSerializableExtra("establishment");

        setContentView(R.layout.activity_establishment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        incrementButton = findViewById(R.id.increment_button);
        decrementButton = findViewById(R.id.decrement_button);
        resumeButton = findViewById(R.id.resume);


        recyclerView = findViewById(R.id.beers);

        establishmentNameTextView = findViewById(R.id.establishment_name);
        establishmentNameTextView.setText(establishment.getName());

        beersOnMenu = establishment.getBeers();
        beerImageView = findViewById(R.id.beer_image);
        for (Beer beer : beersOnMenu) {
            beerImageView.setImageResource(beer.getImageId());
            Picasso.get().load(beer.getImageId()).into(beerImageView);
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

    @Override
    public void onBackPressed() {
    }

    private void initializeBeerList() {
        beersOnMenu = establishment.getBeers();

        beerAdapter = new BeerAdapter(beersOnMenu);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(beerAdapter);
    }

    private class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.BeerViewHolder> {

        private List<Beer> beers;

        public BeerAdapter(List<Beer> beers) {
            this.beers = beers;
        }


        @NonNull
        @Override
        public BeerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_beer_select, viewGroup, false);
            return new BeerViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return beers.size();
        }

        @Override
        public void onBindViewHolder(@NonNull final BeerViewHolder beerViewHolder, final int position) {
            final Beer selectedBeer = EstablishmentActivity.this.beersOnMenu.get(position);

            beerViewHolder.beer_name.setText(selectedBeer.getName());
            beerViewHolder.beer_image.setImageResource(selectedBeer.getImageId());

            Picasso.get().load(selectedBeer.getImageId()).into(beerViewHolder.beer_image);

            beerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView checkmark = v.findViewById(R.id.check_mark);
                    if (selectedBeer.GetSelected()) {
                        selectedBeer.setSelected(false);
                        checkmark.setVisibility(View.GONE);
                    } else {
                        selectedBeer.setSelected(true);
                        checkmark.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        public class BeerViewHolder extends RecyclerView.ViewHolder {

            public TextView beer_name;
            public ImageView beer_image;

            public BeerViewHolder(@NonNull View itemView) {
                super(itemView);
                beer_name = itemView.findViewById(R.id.beer_name);
                beer_image = itemView.findViewById(R.id.beer_image);
            }
        }
    }

}
