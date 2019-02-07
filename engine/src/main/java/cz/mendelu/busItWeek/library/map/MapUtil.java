package cz.mendelu.busItWeek.library.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;

import cz.mendelu.busItWeek.library.R;


/**
 * The utilities for operations around the map.
 */
public class MapUtil {

    /**
     * Creates a colored circle marker and returns it.
     * @param context context
     * @param text the text of the marker
     * @param backgroundColor the background color of the marker
     */
    public static Icon createColoredCircleMarker(Context context,
                                                   String text,
                                                   int backgroundColor){

        IconGenerator iconGenerator = new IconGenerator(context);
        final Drawable clusterIcon = ContextCompat.getDrawable(context, R.drawable.marker_circle_background);
        clusterIcon.setColorFilter(context.getResources().getColor(backgroundColor), PorterDuff.Mode.SRC_ATOP);
        iconGenerator.setBackground(clusterIcon);
        iconGenerator.setTextAppearance(R.style.marker_text_style);
        Bitmap icon = iconGenerator.makeIcon(text);
        return IconFactory.getInstance(context).fromBitmap(icon);
    }



}
