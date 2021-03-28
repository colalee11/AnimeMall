package com.dlsd.property.weight;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dlsd.property.view.BannerImageView;
import com.youth.banner.loader.ImageLoader;

public class ImgBanner extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
        BannerImageView img = new BannerImageView(context);
        return img;
    }
}
