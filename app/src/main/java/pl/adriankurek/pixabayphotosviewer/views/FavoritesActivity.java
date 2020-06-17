package pl.adriankurek.pixabayphotosviewer.views;

import pl.adriankurek.pixabayphotosviewer.R;
import pl.adriankurek.pixabayphotosviewer.adapters.PhotosAdapter;
import pl.adriankurek.pixabayphotosviewer.models.NetworkChecker;
import pl.adriankurek.pixabayphotosviewer.models.PixabayPhoto;
import pl.adriankurek.pixabayphotosviewer.viewmodels.FavoritesViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

public class FavoritesActivity extends AppCompatActivity {
    private GridView gridView;
    private BaseAdapter adapter;
    private FavoritesViewModel favoritesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        favoritesViewModel.init();
        favoritesViewModel.reloadPhotos(this);

        initGridView();

        favoritesViewModel.getAllFavoritePhotos().observe(this, u -> adapter.notifyDataSetChanged());
    }

    // Initialize GridView and add on item click listener.
    private void initGridView() {
        gridView = findViewById(R.id.grid_photos);
        Toast.makeText(this, "" + favoritesViewModel.getAllFavoritePhotos().getValue().size(), Toast.LENGTH_SHORT).show();
        adapter = new PhotosAdapter(this, favoritesViewModel.getAllFavoritePhotos().getValue());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            // Make toast and return if there is no internet connection.
            if (!NetworkChecker.isNetworkAvailable(this)) {
                Toast.makeText(this, "Brak połączenia z siecią!!", Toast.LENGTH_SHORT).show();
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