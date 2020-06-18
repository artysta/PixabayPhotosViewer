package pl.adriankurek.pixabayphotosviewer.models;

import android.content.Context;
import android.util.Log;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONHelper {
    private Context ctx;
    private RequestQueue queue;

    public JSONHelper(Context ctx) {
        this.ctx = ctx;
        queue = Volley.newRequestQueue(ctx);
    }

    // Get images from JSON URL.
    public List<PixabayPhoto> getImagesFromJSONURL(final String jsonURL) {
        String url = jsonURL;
        final List<PixabayPhoto> photos = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    Log.i("JSON", String.format("Response: %s", response.toString()));

                    try {
                        JSONArray array = response.getJSONArray("hits");

                        // Loop through the images and just log info.
                        for (int i = 0; i < array.length(); i++) {
                            // Get current JSON object.
                            JSONObject jsonPhotos = array.getJSONObject(i);

                            // Get the current image data.
                            String id = jsonPhotos.getString("id");
                            String previewURL = jsonPhotos.getString("previewURL");
                            String webformatURL = jsonPhotos.getString("webformatURL");
                            String tags = jsonPhotos.getString("tags");
                            String user = jsonPhotos.getString("user");

                            // Create photo and set data.
                            PixabayPhoto photo = new PixabayPhoto();
                            photo.setId(Integer.parseInt(id));
                            photo.setPreviewURL(previewURL);
                            photo.setWebformatURL(webformatURL);
                            photo.setTags(tags);
                            photo.setUser(user);

                            photos.add(photo);

                            // Log.
                            Log.i("JSON", photos.get(i).toString());
                        }
                    } catch (JSONException e) {
                        Log.i("JSON", "ERROR: " + e.getMessage());
                        // TODO catch exception
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("JSON", error != null ? error.getMessage() : "VolleyError == null");
                    }
                });

        queue.add(jsonObjectRequest);
        queue.start();

        return photos;
    }

    public void addRequestFinishedListener(RequestQueue.RequestFinishedListener<Object> listener) {
        queue.addRequestFinishedListener(listener);
    }
}