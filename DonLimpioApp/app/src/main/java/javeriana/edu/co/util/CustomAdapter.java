package javeriana.edu.co.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import javeriana.edu.co.classes.Provider;
import javeriana.edu.co.donlimpio.R;

public class CustomAdapter extends BaseAdapter {

    ArrayList<Provider> arrayList;
    Activity mContext;
    static LayoutInflater inflater = null;

    public CustomAdapter(ArrayList<Provider> info, Activity context){
        //super(context, R.layout.provider_row_item, info);
        this.arrayList = info;
        this.mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;
        itemView = (itemView == null) ? inflater.inflate(R.layout.provider_row_item,null) : itemView;
        TextView categText = itemView.findViewById(R.id.category_textview);
        TextView descrText = itemView.findViewById(R.id.description_textview);
        TextView priceText = itemView.findViewById(R.id.price_textview);
        Provider p = arrayList.get(position);
        categText.setText(p.getCategory());
        descrText.setText(p.getDescription());
        priceText.setText(Integer.toString(p.getPrice()));

        return itemView;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
