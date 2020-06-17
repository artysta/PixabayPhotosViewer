package pl.adriankurek.pixabayphotosviewer.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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

    private GridView gridView;
    private BaseAdapter adapter;
    private PhotoViewModel photoViewModel;
    private Button btn;
    private Button btnFavorites;
    private EditText editSearch;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);
        photoViewModel.init();

        btn = findViewById(R.id.btn);
        editSearch = findViewById(R.id.edit_search);
        progressBar = findViewById(R.id.progress_bar);

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
                initGridView();
                photoViewModel.getPhotos().observe(this, u -> adapter.notifyDataSetChanged());
                progressBar.setVisibility(View.GONE);
            });
        });

        btnFavorites = findViewById(R.id.btn_favorites);
        btnFavorites.setOnClickListener((v) -> {
            Intent intent = new Intent(getApplicationContext(), FavoritesActivity.class);
            startActivity(intent);
        });
    }

    // Initialize GridView and add on item click listener.
    private void initGridView() {
        gridView = findViewById(R.id.grid_photos);
        adapter = new PhotosAdapter(this, photoViewModel.getPhotos().getValue());
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
}