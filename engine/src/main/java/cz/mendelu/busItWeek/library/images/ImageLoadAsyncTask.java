package cz.mendelu.busItWeek.library.images;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by jaromirlanda on 28/03/17.
 */

public class ImageLoadAsyncTask extends AsyncTask<Object, Void, Bitmap> {

    private ImageView imageView;
    private int resourceID;
    private Resources resources;

    private int imageWidth;
    private int imageHeight;


    public ImageLoadAsyncTask(Resources resources, ImageView imv, int resourceID) {
        this.imageView = imv;
        this.resourceID = resourceID;
        this.resources = resources;
        imageWidth = imv.getMaxWidth();
        imageHeight = imv.getMaxHeight();
    }

    @Override
    protected Bitmap doInBackground(Object... params) {
        Bitmap bitmap = null;
        bitmap = ImageUtils.decodeSampledBitmapFromResource(resources, resourceID, imageWidth, imageHeight);
        return bitmap;
    }
    @Override
    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            imageView.setImageBitmap(result);
        }
    }

}