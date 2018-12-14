
package aakumykov.ru.fragmentsandviews.models.Thread;

import java.util.List;
import com.google.gson.annotations.Expose;

public class Thread {

    @Expose
    private List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

}
