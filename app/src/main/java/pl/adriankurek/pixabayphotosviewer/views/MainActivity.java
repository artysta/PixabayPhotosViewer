package pl.adriankurek.pixabayphotosviewer.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.MessageFormat;

import pl.adriankurek.pixabayphotosviewer.BuildConfig;
import pl.adriankurek.pixabayphotosviewer.R;
import pl.adriankurek.pixabayphotosviewer.adapters.PhotosAdapter;
import pl.adriankurek.pixabayphotosviewer.models.NetworkChecker;
import pl.adriankurek.pixabayphotosviewer.models.PixabayPhoto;
import pl.adriankurek.pixabayphotosviewer.viewmodels.PhotoViewModel;

public class MainActivity extends AppCompatActivity {
    private final String API_KEY = BuildConfig.PIXABAY_API;
    private static final int TIME_DELAY = 2000;
    private static long BACK_PRESSED;

    private GridView gridView;
    private PhotosAdapter adapter;
    private PhotoViewModel photoViewModel;
    private EditText editSearch;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGridView();

        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);
        photoViewModel.init();
        photoViewModel.getPhotos().observe(this, u -> adapter.updatePhotosList(photoViewModel.getPhotos().getValue()));

        editSearch = findViewById(R.id.edit_search);
        progressBar = findViewById(R.id.progress_bar);

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener((v) -> {
            String query = editSearch.getText().toString();

            // Make toast and return if the search query is empty.
            if (query.trim().equals("")) {
                Toast.makeText(this, R.string.empty_search_field, Toast.LENGTH_SHORT).show();
                return;
            }

            // Make toast and return if there is no internet connection.
            if (!NetworkChecker.isNetworkAvailable(this)) {
                Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            // {0} stands for API_KEY, {1} for search query.
            String requestURL = MessageFormat.format("https://pixabay.com/api/?key={0}&q={1}&image_type=photo", API_KEY, query);

            // Realoads photos basing on a search query.
            photoViewModel.reloadPhotos(this, requestURL, (r) -> {
                progressBar.setVisibility(View.GONE);
                Log.i("MVVM", "reloadPhotos MainActivity [CALLBACK]");
            });
        });

        Button btnFavorites = findViewById(R.id.btn_favorites);
        btnFavorites.setOnClickListener((v) -> {
            Intent intent = new Intent(getApplicationContext(), FavoritesActivity.class);
            startActivity(intent);
        });
    }

    // Initialize GridView and add on item click listener.
    private void initGridView() {
        adapter = new PhotosAdapter(this);
        gridView = findViewById(R.id.grid_photos);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            // Make toast and return if there is no internet connection.
            if (!NetworkChecker.isNetworkAvailable(this)) {
                Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                return;
            }

            PixabayPhoto photo = (PixabayPhoto) gridView.getAdapter().getItem(position);

            Intent intent = new Intent(getApplicationContext(), PhotoDetailsActivity.class);
            intent.putExtra("PHOTO_ID", String.valueOf(photo.getId()));
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        if (BACK_PRESSED + TIME_DELAY > System.currentTimeMillis()) {
            System.exit(0);
        } else {
            Toast.makeText(getBaseContext(), "Naciśnij jeszcze raz by wyjść z aplikacji.",
                    Toast.LENGTH_SHORT).show();
        }

        BACK_PRESSED = System.currentTimeMillis();
    }
}