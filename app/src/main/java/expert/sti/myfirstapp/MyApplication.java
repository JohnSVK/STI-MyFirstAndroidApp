package expert.sti.myfirstapp;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import expert.sti.myfirstapp.helpers.ImagesCache;

public class MyApplication extends Application {

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    ImagesCache mLruBitmapCache;

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() { return mInstance; }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
        {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null)
        {
            getLruBitmapCache();
            mImageLoader = new ImageLoader(this.mRequestQueue, mLruBitmapCache);
        }

        return this.mImageLoader;
    }

    public ImagesCache getLruBitmapCache() {
        if (mLruBitmapCache == null)
        {
            mLruBitmapCache = new ImagesCache();
        }

        return this.mLruBitmapCache;
    }
}
