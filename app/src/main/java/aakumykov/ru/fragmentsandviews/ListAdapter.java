package aakumykov.ru.fragmentsandviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import aakumykov.ru.fragmentsandviews.models.Element;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends ArrayAdapter<Element> {

    private LayoutInflater inflater;
    private int layout;
    private List<Element> Elements;

    ListAdapter(Context context, int resource, List<Element> elements) {
        super(context, resource, elements);
        this.Elements = elements;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Element element = Elements.get(position);

        viewHolder.titleView.setText(element.getName());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.titleView) TextView titleView;
        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}