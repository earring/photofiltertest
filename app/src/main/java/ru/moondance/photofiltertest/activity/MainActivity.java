package ru.moondance.photofiltertest.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.moondance.photofiltertest.photo.FilterSelector;
import ru.moondance.photofiltertest.R;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST_CODE = 1;

    private static final String URI_RESTORE_KEY = MainActivity.class.getName() + ".uri";

    private static final String FILTER_RESTORE_KEY = MainActivity.class.getName() + ".filter";

    private static final String IMAGE_TYPE_URI = "image/*";

    @BindView(R.id.imageView)
    ImageView mImageView;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    private Uri mSelectedImageUri;

    private int mFilterCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mSelectedImageUri = savedInstanceState.getParcelable(URI_RESTORE_KEY);
        mFilterCode = savedInstanceState.getInt(FILTER_RESTORE_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadImage();
    }

    private RequestCreator getImageCreator() {
        if (mSelectedImageUri != null) {
            return Picasso.with(this).load(mSelectedImageUri.toString());
        } else {
            return Picasso.with(this).load(R.drawable.sky);
        }
    }

    private void loadImage() {
        mProgressBar.setVisibility(View.VISIBLE);
        getImageCreator().transform(FilterSelector.getInstance(this).getTransformation(mFilterCode))
                         .fit()
                         .centerInside()
                         .into(mImageView, new Callback() {
                             @Override
                             public void onSuccess() {
                                 mProgressBar.setVisibility(View.GONE);
                             }

                             @Override
                             public void onError() {

                             }
                         });
    }

    @OnClick(R.id.button_select_image)
    void onButtonSelectImageClick() {
        Intent intent = new Intent();
        intent.setType(IMAGE_TYPE_URI);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), PICK_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            mSelectedImageUri = data.getData();
            loadImage();
        }
    }

    @OnClick(R.id.button_no_filter)
    void onButtonNoFilterClick() {
        mFilterCode = FilterSelector.EMPTY;
        loadImage();
    }

    @OnClick(R.id.button_gray_filter)
    void onButtonGrayFilterClick() {
        mFilterCode = FilterSelector.GRAY;
        loadImage();
    }

    @OnClick(R.id.button_blur_filter)
    void onButtonBlurFilterClick() {
        mFilterCode = FilterSelector.BLUR;
        loadImage();
    }

    @OnClick(R.id.button_swirl_filter)
    void onButtonSwirlFilterClick() {
        mFilterCode = FilterSelector.SWIRL;
        loadImage();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(URI_RESTORE_KEY, mSelectedImageUri);
        outState.putInt(FILTER_RESTORE_KEY, mFilterCode);
    }
}
