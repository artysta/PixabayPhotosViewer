package pl.adriankurek.pixabayphotosviewer.views;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.List;

import pl.adriankurek.pixabayphotosviewer.BuildConfig;
import pl.adriankurek.pixabayphotosviewer.R;
import pl.adriankurek.pixabayphotosviewer.database.DbController;
import pl.adriankurek.pixabayphotosviewer.models.JSONHelper;
import pl.adriankurek.pixabayphotosviewer.models.PixabayPhoto;

public class PhotoDetailsActivity extends AppCompatActivity {
    private PixabayPhoto photo;
    private DbController controller;
    private ImageView imgFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);

        setTitle(R.string.activity_details);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        // Get selected photo id.
        Intent intent = getIntent();
        String photoId = intent.getStringExtra("PHOTO_ID");

        String photoURL = MessageFormat.format("{0}{1}&id={2}", BuildConfig.PIXABAY_MAIN_URL, BuildConfig.PIXABAY_API, photoId);

        JSONHelper helper = new JSONHelper(this);
        List<PixabayPhoto> photos = helper.getImagesFromJSONURL(photoURL);

        imgFavorite = findViewById(R.id.img_favorite);

        controller = new DbController(this);

        imgFavorite.setOnClickListener((v) -> {
            if (controller.isOnFavoriteList(photo.getId())) {
                // Remove photo from database if it is favorite.
                controller.removePhotoFromFavorites(photo);
                imgFavorite.setImageResource(R.drawable.ic_favorite_off);
                Toast.makeText(this, R.string.removed_from_favorites, Toast.LENGTH_SHORT).show();
            } else {
                // Add photo to database if it is not favorite.
                controller.addNewFavoritePhoto(photo);
                imgFavorite.setImageResource(R.drawable.ic_favorite_on);
                Toast.makeText(this, R.string.added_to_favorites, Toast.LENGTH_SHORT).show();
            }
        });

        helper.addRequestFinishedListener(r -> {
            photo = photos.get(0);
            setPhotoDetails();
        });
    }

    public void setPhotoDetails() {
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        CardView cardDetails = findViewById(R.id.card_details);
        ImageView imgView = findViewById(R.id.imgView);
        TextView txtId = findViewById(R.id.txt_photo_id);
        TextView txtWebviewURL = findViewById(R.id.txt_webview_url);
        TextView txtUser = findViewById(R.id.txt_user);
        TextView txtTags = findViewById(R.id.txt_tags);

        if (controller.isOnFavoriteList(photo.getId())) {
            // Remove photo from database if it is favorite.
            imgFavorite.setImageResource(R.drawable.ic_favorite_on);
        } else {
            // Add photo to database if it is not favorite.
            imgFavorite.setImageResource(R.drawable.ic_favorite_off);
        }

        Picasso.get().load(photo.getWebformatURL()).into(imgView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
                cardDetails.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.GONE);
                showErrorMessage();
            }
        });

        txtId.setText(String.valueOf(photo.getId()));
        txtWebviewURL.setText(photo.getWebformatURL());
        txtUser.setText(photo.getUser());
        txtTags.setText(photo.getTags());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showErrorMessage() {
        TextView txtError = findViewById(R.id.txt_error);
        txtError.setVisibility(View.VISIBLE);
    }
}
