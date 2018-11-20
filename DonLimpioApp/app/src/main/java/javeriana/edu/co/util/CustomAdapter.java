package javeriana.edu.co.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import javeriana.edu.co.classes.Provider;
import javeriana.edu.co.donlimpio.R;

public class CustomAdapter extends ArrayAdapter<Provider> {

    ArrayList<Provider> arrayList;
    Context mContext;
    int lastPosition = -1;

    private static class ViewHolder{
        TextView categText;
        TextView descrText;
        TextView priceText;
    }

    public CustomAdapter(ArrayList<Provider> info, Context context){
        super(context, R.layout.provider_row_item, info);
        this.arrayList = info;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Provider p = getItem(position);
        ViewHolder viewHolder;
        final View result;

        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.provider_row_item, parent, false);
            viewHolder.categText = (TextView) convertView.findViewById(R.id.category_textview);
            viewHolder.descrText = (TextView) convertView.findViewById(R.id.description_textview);
            viewHolder.priceText = (TextView) convertView.findViewById(R.id.price_textview);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.categText.setText(p.getCategory());
        viewHolder.descrText.setText(p.getDescription());
        viewHolder.priceText.setText(Double.toString(p.getPrice()));

        return convertView;
    }
}
