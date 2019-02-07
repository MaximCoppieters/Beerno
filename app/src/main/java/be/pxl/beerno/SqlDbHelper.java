package be.pxl.beerno;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlDbHelper extends SQLiteOpenHelper {
    private static final SqlDbHelper sqlHelper = new
    public static final String DATABASE_NAME = "BeernoDataBase";

    public static final String BEERS_TABLE_NAME = "Beers";
    public static final String BEERS_COLUMN_ID = "id";
    public static final String BEERS_COLUMN_NAME = "name";
    public static final String BEERS_COLUMN_IMAGE_ID = "Image_Id";
    public static final String BEERS_COLUMN_DESCRIPTION = "Description";

    public static final String ESTABLISHMENT_TABLE_NAME = "name";
    public static final String ESTABLISHMENT_COLUMN_ID = "id";
    public static final String ESTABLISHMENT_COLUMN_LONGTITUDE = "longtitude";
    public static final String ESTABLISHMENT_COLUMN_LATITUDE = "latitude";

    public static final String JOIN_TABLE_NAME = "BEER_PER_PUB";

    public SqlDbHelper(Context context){
        super(context,DATABASE_NAME,null,1);
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
                "\t" + ESTABLISHMENT_COLUMN_LATITUDE + " text NOT NULL,\n" +
                "\t" + ESTABLISHMENT_COLUMN_LONGTITUDE + " text NOT NULL UNIQUE)");

        database.execSQL("create table IF NOT EXISTS " + JOIN_TABLE_NAME + " ( " +
                "\t" + ESTABLISHMENT_COLUMN_ID + " integer PRIMARY KEY,\n" +
                "\t" + BEERS_COLUMN_ID + " text NOT NULL,\n" +
                "\t" + ESTABLISHMENT_COLUMN_LONGTITUDE + " text NOT NULL UNIQUE)");

    }
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        onCreate(database);

    }
}