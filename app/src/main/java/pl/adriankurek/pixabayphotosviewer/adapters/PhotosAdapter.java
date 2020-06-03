package pl.adriankurek.pixabayphotosviewer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
            viewHolder.txtId = convertView.findViewById(R.id.txt_photo_id);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get current photo.
        PixabayPhoto current = photos.get(position);
        viewHolder.txtId.setText(String.valueOf(current.getId()));
        return convertView;
    }

    private static class ViewHolder{
        TextView txtId;
    }
}