package be.pxl.beerno;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

public class SqlDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "BeernoDataBase";

    public static final String BEERS_TABLE_NAME = "Beers";
    public static final String BEERS_COLUMN_ID = "id";
    public static final String BEERS_COLUMN_NAME = "beer_name";
    public static final String BEERS_COLUMN_IMAGE_ID = "Image_Id";
    public static final String BEERS_COLUMN_DESCRIPTION = "Description";

    public static final String ESTABLISHMENT_TABLE_NAME = "name";
    public static final String ESTABLISHMENT_COLUMN_ID = "establishment_id";
    public static final String ESTABLISHMENT_COLUMN_NAME = "name";
    public static final String ESTABLISHMENT_COLUMN_LONGTITUDE = "longtitude";
    public static final String ESTABLISHMENT_COLUMN_LATITUDE = "latitude";

    public static final String JOIN_TABLE_NAME = "BEER_PER_PUB";

    public SqlDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL("DROP TABLE IF EXISTS " + BEERS_TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + ESTABLISHMENT_TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + JOIN_TABLE_NAME);
        database.execSQL("create table IF NOT EXISTS " + BEERS_TABLE_NAME + " ( " +
                "\t" + BEERS_COLUMN_ID + " integer PRIMARY KEY,\n" +
                "\t" + BEERS_COLUMN_NAME + " text NOT NULL,\n" +
                "\t" + BEERS_COLUMN_IMAGE_ID + " integer NOT NULL UNIQUE,\n" +
                "\t" + BEERS_COLUMN_DESCRIPTION + " text NOT NULL UNIQUE)");

        database.execSQL("create table IF NOT EXISTS " + ESTABLISHMENT_TABLE_NAME + " ( " +
                "\t" + ESTABLISHMENT_COLUMN_ID + " integer PRIMARY KEY,\n" +
                "\t" + ESTABLISHMENT_COLUMN_NAME + " text PRIMARY KEY,\n" +
                "\t" + ESTABLISHMENT_COLUMN_LATITUDE + " text NOT NULL,\n" +
                "\t" + ESTABLISHMENT_COLUMN_LONGTITUDE + " text NOT NULL UNIQUE)");

        database.execSQL("create table IF NOT EXISTS " + JOIN_TABLE_NAME + " ( " +
                "\t" + ESTABLISHMENT_COLUMN_ID + " integer PRIMARY KEY,\n" +
                "\t" + BEERS_COLUMN_ID + " integer PRIMARY KEY" +
                ")");
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        onCreate(database);

    }

    public List<Establishment> getAllEstablishments() {
        String selectStatement = "SELECT * FROM " + ESTABLISHMENT_TABLE_NAME;
        ArrayList<Establishment> establishments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(selectStatement, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            String nameOfEstablishment =
                    res.getString(res.getColumnIndex(ESTABLISHMENT_COLUMN_NAME));
            int latitude = res.getInt(res.getColumnIndex(ESTABLISHMENT_COLUMN_LATITUDE));
            int longtitude = res.getInt(res.getColumnIndex(ESTABLISHMENT_COLUMN_LONGTITUDE));
            LatLng latLng = new LatLng();
            latLng.setLongitude(longtitude);
            latLng.setLatitude(latitude);
            Establishment establishment = new Establishment(nameOfEstablishment, latLng);
            addAllBearsOfEstablishment(establishment, res.getInt(res.getColumnIndex(ESTABLISHMENT_COLUMN_ID)));
            res.moveToNext();
        }

        return establishments;
    }

    private void addAllBearsOfEstablishment(Establishment establishment, int establishmentId) {

        String selectStatement = "SELECT * FROM " + JOIN_TABLE_NAME +
                "WHERE " + ESTABLISHMENT_COLUMN_ID + "=" + establishmentId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(selectStatement, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            int beerId = res.getInt(res.getColumnIndex(BEERS_COLUMN_ID));
            establishment.addBeerToMenu(getBeerById(beerId));
        }
    }

    private Beer getBeerById(int beerId) {
        String selectStatement = "SELECT * FROM " + BEERS_TABLE_NAME +
                "WHERE " + BEERS_COLUMN_ID + "=" + beerId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(selectStatement, null);
        res.moveToFirst();
        String beerName = res.getString(res.getColumnIndex(BEERS_COLUMN_NAME));
        int imageId = res.getInt(res.getColumnIndex(BEERS_COLUMN_IMAGE_ID));
        String beerDescription = res.getString(res.getColumnIndex(BEERS_COLUMN_DESCRIPTION));
        Beer beer = new Beer(beerName, imageId);
        beer.setDescription(beerDescription);
        beer.setId(beerId);
        return beer;
    }

    public List<Beer> getAllBeers(){
        List<Beer> beers = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String select = "SELECT " + BEERS_COLUMN_ID + " FROM " + BEERS_TABLE_NAME;
        Cursor res = db.rawQuery(select, null);
        res.moveToFirst();
        while (!res.isAfterLast()){
            int idBeer = (res.getInt(res.getColumnIndex(BEERS_COLUMN_ID)));
            beers.add(getBeerById(idBeer));
        }

        return beers;
    }

    public void insertBeer(Beer beer) {
        String insert = "INSERT INTO " + BEERS_TABLE_NAME +
                " (" +
                BEERS_COLUMN_NAME + ", "
                + BEERS_COLUMN_DESCRIPTION + ", "
                + BEERS_COLUMN_IMAGE_ID +
                ") VALUES(" +
                beer.getName() + ", "
                + beer.getDescription() + ", " +
                beer.getImageId() + ")";
        insertData(insert);
    }

    private void insertData(String insert) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(insert);
    }

    public void insertEstablishment(Establishment establishment) {
        double longtitude = establishment.getLocation().getLongitude();
        double latitude = establishment.getLocation().getLatitude();

        String insert =  "INSERT INTO " + ESTABLISHMENT_TABLE_NAME +
                " (" +
                ESTABLISHMENT_COLUMN_NAME + ", " +
                ESTABLISHMENT_COLUMN_LATITUDE + ", " +
                ESTABLISHMENT_COLUMN_LONGTITUDE +
                ") VALUES(" +
                establishment.getName() + ", "
                + latitude + ", " +
                longtitude + ")";
        insertData(insert);
    }

    private void insertJoinGroup(int establishmentId, int beerId) {
       String insert = "INSERT INTO " + JOIN_TABLE_NAME +
                " (" +
                ESTABLISHMENT_COLUMN_ID + ", "
                + BEERS_COLUMN_ID +
                ") VALUES(" +
                establishmentId + ", " +
                beerId +
                ")";
       insertData(insert);
    }
}