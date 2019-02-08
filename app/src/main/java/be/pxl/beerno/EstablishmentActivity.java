package be.pxl.beerno;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class EstablishmentActivity extends AppCompatActivity {
    private TextView establishmentNameTextView;
    private CardView resumeButton;
    private Establishment establishment;
    private List<Beer> beersOnMenu;

    private RecyclerView recyclerView;
    private BeerAdapter beerAdapter;
    private BeerRepository beerRepository;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        beerRepository = BeerRepository.getInstance();
        establishment = beerRepository.getEstablishmentVisiting();

        setContentView(R.layout.activity_establishment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        resumeButton = findViewById(R.id.resume);
        recyclerView = findViewById(R.id.beers);

        establishmentNameTextView = findViewById(R.id.establishment_name);
        establishmentNameTextView.setText(establishment.getName());

        beersOnMenu = establishment.getBeers();
        initializeBeerList();

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EstablishmentActivity.this, "On to the next pub!", Toast.LENGTH_LONG).show();
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
                    .inflate(R.layout.row_beer_establishment, viewGroup, false);
            return new BeerViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return beers.size();
        }

        @Override
        public void onBindViewHolder(@NonNull final BeerViewHolder beerViewHolder, final int position) {
            final Beer beer = EstablishmentActivity.this.beersOnMenu.get(position);

            beerViewHolder.beer_name.setText(beer.getName());
            beerViewHolder.beer_image.setImageResource(beer.getImageId());
            beerViewHolder.beer_count.setText("0");

            Picasso.get().load(beer.getImageId()).into(beerViewHolder.beer_image);

            Button incrementButton = beerViewHolder.itemView.findViewById(R.id.increment_button);
            Button decrementButton = beerViewHolder.itemView.findViewById(R.id.decrement_button);

            incrementButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Stats.incrementBeersDrank();
                    int count = Integer.parseInt(beerViewHolder.beer_count.getText().toString());
                    count++;
                    beerViewHolder.beer_count.setText(count);
                }
            });

            decrementButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Stats.decrementBeersDrank();
                    int count = Integer.parseInt(beerViewHolder.beer_count.getText().toString());
                    count--;
                    beerViewHolder.beer_count.setText(Math.max(0, count));
                }
            });
        }

        public class BeerViewHolder extends RecyclerView.ViewHolder {
            public TextView beer_name;
            public TextView beer_count;
            public ImageView beer_image;

            public BeerViewHolder(@NonNull View itemView) {
                super(itemView);
                beer_name = itemView.findViewById(R.id.beer_name);
                beer_image = itemView.findViewById(R.id.beer_image);
                beer_count = itemView.findViewById(R.id.beer_count);
            }
        }
    }
}
