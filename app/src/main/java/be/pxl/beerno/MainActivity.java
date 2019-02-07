package be.pxl.beerno;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.github.jorgecastillo.FillableLoader;
import com.github.jorgecastillo.State;
import com.github.jorgecastillo.clippingtransforms.ClippingTransform;
import com.github.jorgecastillo.listener.OnStateChangeListener;

public class MainActivity extends AppCompatActivity {

    private String svgPath = SvgPathUtil.svgPath3;
    private FillableLoader fillableLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fillableLoader = findViewById(R.id.fillableLoader);
        fillableLoader.setSvgPath(svgPath);


     fillableLoader.setOnStateChangeListener(new OnStateChangeListener() {
           @Override
        public void onStateChange(int state) {
               if(state == State.FINISHED){
                startActivity(new Intent(MainActivity.this,
                         BeerSelectActivity.class));
              }
           }
        });

        fillableLoader.start();

        startActivity(new Intent(MainActivity.this, EstablishmentActivity.class));
    }

    private Context getActivity() {
        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//    public void Start(View view) {
//        Intent intent = new Intent(MainActivity.this, BeerSelectActivity.class);
//        startActivity(intent);
//    }
}
