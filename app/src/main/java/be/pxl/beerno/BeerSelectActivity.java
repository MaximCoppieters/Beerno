package be.pxl.beerno;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BeerSelectActivity extends AppCompatActivity {
    private List<Beer> beers;
    private RecyclerView recyclerView;
    private BeerAdapter beerAdapter;
    private CardView resumeButtonCard;
    private FrameLayout loading_layout;
    private LinearLayout main_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_select);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.beers);
        main_layout = findViewById(R.id.main_layout);
        loading_layout = findViewById(R.id.loading_layout);
        resumeButtonCard = findViewById(R.id.GO);
        resumeButtonCard.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                int selectCounted = 0;
                for (Beer beer : beers) {
                    if (beer.getSelected()) {
                        selectCounted++;
                    }
                }

                if (selectCounted == 0) {
                    Toast.makeText(BeerSelectActivity.this, "Please select a beer",
                            Toast.LENGTH_SHORT).show();
                }


                main_layout.setAlpha((float) 0.1);
                loading_layout.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(BeerSelectActivity.this, BeerRouteActivity.class));
                    }
                }, 3000);
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select the beers you want to drink");
        initializeBeerList();

    }

    @Override
    public void onBackPressed() {
    }

    private void initializeBeerList() {
        BeerRepository beerRepository = BeerRepository.getInstance();
        beers = beerRepository.getAllBeers();

        beerAdapter = new BeerAdapter(beers);
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
            final Beer selectedBeer = BeerSelectActivity.this.beers.get(position);

            beerViewHolder.beer_name.setText(selectedBeer.getName());
            beerViewHolder.beer_image.setImageResource(selectedBeer.getImageId());

            Picasso.get().load(selectedBeer.getImageId()).into(beerViewHolder.beer_image);

            beerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView checkmark = v.findViewById(R.id.check_mark);
                    if (selectedBeer.getSelected()) {
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
