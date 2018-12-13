package aakumykov.ru.fragmentsandviews.thread_show;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import aakumykov.ru.fragmentsandviews.R;
import aakumykov.ru.fragmentsandviews.models.Thread.Post;
import aakumykov.ru.fragmentsandviews.utils.DvachUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ThreadShow_Adapter extends ArrayAdapter<Post> {

    private LayoutInflater inflater;
    private int layout;
    private List<Post> list;

    ThreadShow_Adapter(Context context, int resource, List<Post> list) {
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

        Post post = list.get(position);

        String comment = DvachUtils.processComment(post.getComment());

        viewHolder.threadComment.setText(comment);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.threadComment) TextView threadComment;
        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}