package pl.adriankurek.pixabayphotosviewer.views;

import pl.adriankurek.pixabayphotosviewer.R;
import pl.adriankurek.pixabayphotosviewer.adapters.PhotosAdapter;
import pl.adriankurek.pixabayphotosviewer.models.NetworkChecker;
import pl.adriankurek.pixabayphotosviewer.models.PixabayPhoto;
import pl.adriankurek.pixabayphotosviewer.viewmodels.FavoritesViewModel;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class FavoritesActivity extends AppCompatActivity {
    private GridView gridView;
    private PhotosAdapter adapter;
    private FavoritesViewModel favoritesViewModel;

    @Override
    protected void onResume() {
        super.onResume();
        // Reload photos to check if any of them has been removed from favorites.
        favoritesViewModel.reloadPhotos(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        setTitle(R.string.activity_favorites);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        favoritesViewModel.init(this);
        favoritesViewModel.getAllFavoritePhotos().observe(this, o -> {
            adapter.updatePhotosList(favoritesViewModel.getAllFavoritePhotos().getValue());
            checkIfThereArePhotos();
        });

        initGridView();
    }

    // Hides or shows text information about favorite photos,
    public void checkIfThereArePhotos() {
        TextView txtFavorite = findViewById(R.id.txt_favorites);

        if (favoritesViewModel.getAllFavoritePhotos().getValue().size() == 0) {
            txtFavorite.setVisibility(View.VISIBLE);
        } else {
            txtFavorite.setVisibility(View.INVISIBLE);
        }
    }

    // Initialize GridView and add on item click listener.
    private void initGridView() {
        gridView = findViewById(R.id.grid_photos);
        adapter = new PhotosAdapter(this);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}