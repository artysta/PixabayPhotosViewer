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
    public List<PixabayImage> getImagesFromJSONURL(final String jsonURL) {
        String url = jsonURL;
        final List<PixabayImage> images = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("JSON", String.format("Response: %s", response.toString()));

                        try {
                            JSONArray array = response.getJSONArray("hits");

                            // Loop through the images and just log info.
                            for (int i = 0; i < array.length(); i++) {
                                // Get current JSON object.
                                JSONObject photos = array.getJSONObject(i);

                                // Get the current image data.
                                String id = photos.getString("id");
                                String previewURL = photos.getString("previewURL");

                                PixabayImage image = new PixabayImage();
                                image.setId(Integer.parseInt(id));
                                image.setPreviewURL(previewURL);

                                images.add(image);

                                // Log.
                                Log.i("JSON", images.get(i).toString());
                            }
                        } catch (JSONException e) {
                            // TODO catch exception
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("JSON", error != null ? error.getMessage() : "VolleyError == null");
                    }
                });

        queue.add(jsonObjectRequest);
        queue.start();

        return images;
    }

    public void addRequestFinishedListener(RequestQueue.RequestFinishedListener listener) {
        queue.addRequestFinishedListener(listener);
    }
}