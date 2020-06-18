package pl.adriankurek.pixabayphotosviewer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import pl.adriankurek.pixabayphotosviewer.R;
import pl.adriankurek.pixabayphotosviewer.models.PixabayPhoto;

public class PhotosAdapter extends BaseAdapter {
    private List<PixabayPhoto> photos;
    private Context ctx;
    private LayoutInflater inflater;

    public PhotosAdapter(Context ctx, List<PixabayPhoto> photos){
        this.ctx = ctx;
        this.photos = photos;
        this.inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount(){
        return photos.size();
    }

    public Object getItem(int position){
        return photos.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.photos_grid_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.image = convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get current photo.
        PixabayPhoto current = photos.get(position);

        Picasso.get().load(current.getWebformatURL()).into(viewHolder.image);

        return convertView;
    }

    public void updatePhotosList(List<PixabayPhoto> photos) {
        this.photos.clear();
        this.photos.addAll(photos);
        this.notifyDataSetChanged();
    }

    private static class ViewHolder {
        ImageView image;
    }

}