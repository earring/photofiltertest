package ru.moondance.photofiltertest.photo;

import android.content.Context;

import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.GrayscaleTransformation;
import jp.wasabeef.picasso.transformations.gpu.SwirlFilterTransformation;

public class FilterSelector {

    public static final int EMPTY = 0;

    public static final int GRAY = 1;

    public static final int BLUR = 2;

    public static final int SWIRL = 3;

    private static FilterSelector sInstance;

    private final Context mContext;

    private FilterSelector(Context context) {
        mContext = context;
    }

    public static FilterSelector getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new FilterSelector(context);
        }
        return sInstance;
    }

    public Transformation getTransformation(int code) {
        switch (code) {
            case GRAY:
                return new GrayscaleTransformation();
            case BLUR:
                return new BlurTransformation(mContext);
            case SWIRL:
                return new SwirlFilterTransformation(mContext);
            case EMPTY:
            default:
                return new EmptyTransformation();
        }
    }
}
