package com.dlsd.property.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.zhihu.matisse.engine.ImageEngine;


/**
 * Created by LSH ON 2019/11/20
 * Describe:
 */
public class GlideLoadEngine implements ImageEngine {

    /**
     * Load thumbnail of a static image resource.
     *
     * @param context     Context
     * @param resize      Desired size of the origin image
     * @param placeholder Placeholder drawable when image is not loaded yet
     * @param imageView   ImageView widget
     * @param uri         Uri of the loaded image
     */
    @Override
    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(uri)
                .override(resize, resize)
                .placeholder(placeholder)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void loadGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView,
                                 Uri uri) {
        Glide.with(context)
                .load(uri)
                .override(resize, resize)
                .placeholder(placeholder)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(uri)
                .override(resizeX, resizeY)
                .priority(Priority.HIGH)
                .fitCenter()
                .into(imageView);
    }

    @Override
    public void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(uri)
                .override(resizeX, resizeY)
                .priority(Priority.HIGH)
                .fitCenter()
                .into(imageView);
    }

    @Override
    public boolean supportAnimatedGif() {
        return true;
    }
}
