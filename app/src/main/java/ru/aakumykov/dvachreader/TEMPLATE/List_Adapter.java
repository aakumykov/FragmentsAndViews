package ru.aakumykov.dvachreader.TEMPLATE;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ru.aakumykov.dvachreader.models.Element;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.aakumykov.fragmentsandviews.R;

public class List_Adapter extends ArrayAdapter<Element> {

    private LayoutInflater inflater;
    private int layout;
    private List<Element> list;

    List_Adapter(Context context, int resource, List<Element> list) {
        super(context, resource, list);
        this.list = list;
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

        Element element = list.get(position);

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