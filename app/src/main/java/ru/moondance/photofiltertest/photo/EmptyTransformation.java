package ru.moondance.photofiltertest.photo;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

public class EmptyTransformation implements Transformation {

    @Override
    public Bitmap transform(Bitmap source) {
        return source;
    }

    @Override
    public String key() {
        return "empty_transformation";
    }
}
