package aakumykov.ru.fragmentsandviews.thread_show;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import aakumykov.ru.fragmentsandviews.R;
import aakumykov.ru.fragmentsandviews.models.Thread.Post;
import aakumykov.ru.fragmentsandviews.utils.DvachUtils;
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

        // Коммент в TXT
        String cleanComment = DvachUtils.preProcessComment(rawComment);
        viewHolder.commentView.setText(cleanComment);

        // Коммент в HTML
//        Spanned spannedComment;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            spannedComment = Html.fromHtml(rawComment, Html.FROM_HTML_MODE_COMPACT);
//        } else {
//            spannedComment = Html.fromHtml(rawComment);
//        }
//        viewHolder.commentView.setText(spannedComment);

        // Первая картинка из комментария
//        List<File> files = post.getFiles();
//        if (files.size() > 0) {
//            final ImageView imageView = viewHolder.commentImage;
//            String thumbnailPath = Constants.BASE_URL + files.get(0).getThumbnail();
//            Picasso.get().load(thumbnailPath).into(imageView, new Callback() {
//                @Override
//                public void onSuccess() {
//                    MyUtils.show(imageView);
//                }
//
//                @Override
//                public void onError(Exception e) {
//
//                }
//            });
//        }

        // Метаданные комментария
//        String commentInfo = "";
//        commentInfo += "#"+post.getNum().toString();
//        commentInfo += "\n";
//        commentInfo += post.getDate();
//        viewHolder.commentInfo.setText(commentInfo);

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