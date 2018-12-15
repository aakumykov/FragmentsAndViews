package aakumykov.ru.fragmentsandviews.thread_show;


import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import aakumykov.ru.fragmentsandviews.Constants;
import aakumykov.ru.fragmentsandviews.R;
import aakumykov.ru.fragmentsandviews.models.Thread.File;
import aakumykov.ru.fragmentsandviews.models.Thread.Post;
import aakumykov.ru.fragmentsandviews.utils.MyUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ThreadShow_Adapter extends ArrayAdapter<Post> {

    public static final String TAG = "ThreadShow_Adapter";
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
        String rawComment = post.getComment();

//        String cleanComment = DvachUtils.processComment(rawComment);
//        viewHolder.commentView.setText(cleanComment);

        Spanned spannedComment;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            spannedComment = Html.fromHtml(rawComment, Html.FROM_HTML_MODE_COMPACT);
        } else {
            spannedComment = Html.fromHtml(rawComment);
        }
        viewHolder.commentView.setText(spannedComment);

        List<File> files = post.getFiles();
        if (files.size() > 0) {
            final ImageView imageView = viewHolder.commentImage;
            String thumbnailPath = Constants.BASE_URL + files.get(0).getThumbnail();
            Picasso.get().load(thumbnailPath).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    MyUtils.show(imageView);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }

        String commentInfo = "";
        commentInfo += "#"+post.getNum().toString();
        commentInfo += "\n";
        commentInfo += post.getDate();

        viewHolder.commentInfo.setText(commentInfo);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.commentText) TextView commentView;
        @BindView(R.id.commentImage) ImageView commentImage;
        @BindView(R.id.commentInfo) TextView commentInfo;

        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}