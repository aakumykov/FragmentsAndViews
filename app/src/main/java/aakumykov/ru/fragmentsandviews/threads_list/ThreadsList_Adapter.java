package aakumykov.ru.fragmentsandviews.threads_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import aakumykov.ru.fragmentsandviews.R;
import aakumykov.ru.fragmentsandviews.models.Board.Thread;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ThreadsList_Adapter extends ArrayAdapter<Thread> {

    private LayoutInflater inflater;
    private int layout;
    private List<Thread> list;

    ThreadsList_Adapter(Context context, int resource, List<Thread> list) {
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

        Thread item = list.get(position);

        viewHolder.titleView.setText(item.getName());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.titleView) TextView titleView;
        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}