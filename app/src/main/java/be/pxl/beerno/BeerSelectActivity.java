package be.pxl.beerno;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BeerSelectActivity extends AppCompatActivity {
    private List<Beer> beers;
    private RecyclerView recyclerView;
    private BeerAdapter beerAdapter;

    private CardView cardView;
    private CardView beerCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_select);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        recyclerView = findViewById(R.id.beers);

        beerCardView = findViewById(R.id.beer_select);
        beerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beerCardView.setVisibility(View.VISIBLE);
            }
        });
        cardView = findViewById(R.id.GO);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BeerSelectActivity.this, BeerRouteActivity.class));
            }
        });
        initializeBeerList();
    }


    private void initializeBeerList() {
        beers = BeerRepository.getAllBeers();

        beerAdapter = new BeerAdapter(beers);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(beerAdapter);
    }

    private class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.BeerViewHolder> {

        private List<Beer> beers;
        private List<Integer> selectedBeerIndexes;

        public BeerAdapter(List<Beer> beers) {
            this.beers = beers;
        }


        @NonNull
        @Override
        public BeerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_beer_select, viewGroup, false);
            selectedBeerIndexes = new ArrayList<>();
            return new BeerViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return beers.size();
        }

        @Override
        public void onBindViewHolder(@NonNull final BeerViewHolder beerViewHolder, final int position) {
            final Beer selectedBeer = BeerSelectActivity.this.beers.get(position);

            beerViewHolder.beer_name.setText(selectedBeer.getName());
            beerViewHolder.beer_image.setImageURI(selectedBeer.getImageUri());

            beerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedBeerIndexes.contains(position)) {
                        selectedBeerIndexes.remove(position);
                    } else {
                        selectedBeerIndexes.add(position);
                    }
                    beerViewHolder.itemView.setSelected(selectedBeerIndexes.contains(position));
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
