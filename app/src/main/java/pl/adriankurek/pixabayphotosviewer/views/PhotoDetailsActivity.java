package pl.adriankurek.pixabayphotosviewer.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.List;

import pl.adriankurek.pixabayphotosviewer.BuildConfig;
import pl.adriankurek.pixabayphotosviewer.R;
import pl.adriankurek.pixabayphotosviewer.models.JSONHelper;
import pl.adriankurek.pixabayphotosviewer.models.PixabayPhoto;

public class PhotoDetailsActivity extends AppCompatActivity {
    private final String API_KEY = BuildConfig.PIXABAY_API;

    private PixabayPhoto photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Get selected photo id.
        Intent intent = getIntent();
        String photoId = intent.getStringExtra("PHOTO_ID");

        String photoURL = MessageFormat.format("https://pixabay.com/api/?key={0}&id={1}", API_KEY, photoId);

        JSONHelper helper = new JSONHelper(this);
        List<PixabayPhoto> photos = helper.getImagesFromJSONURL(photoURL);

        helper.addRequestFinishedListener(r -> {
            photo = photos.get(0);
            setPhotoDetails();
        });
    }

    public void setPhotoDetails() {
        ImageView imgView = findViewById(R.id.imgView);
        TextView txtId = findViewById(R.id.txt_photo_id);
        TextView txtWebviewURL = findViewById(R.id.txt_webview_url);
        TextView txtUser = findViewById(R.id.txt_user);
        TextView txtTags = findViewById(R.id.txt_tags);

        Picasso.get().load(photo.getWebformatURL()).into(imgView);
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
}
