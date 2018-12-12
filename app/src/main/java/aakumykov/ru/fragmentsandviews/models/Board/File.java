package aakumykov.ru.fragmentsandviews.models.Board;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class File {

    @Expose
    private String displayname;
    @Expose
    private String fullname;
    @Expose
    private Long height;
    @Expose
    private String md5;
    @Expose
    private String name;
    @Expose
    private Long nsfw;
    @Expose
    private String path;
    @Expose
    private Long size;
    @Expose
    private String thumbnail;
    @SerializedName("tn_height")
    private Long tnHeight;
    @SerializedName("tn_width")
    private Long tnWidth;
    @Expose
    private Long type;
    @Expose
    private Long width;

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNsfw() {
        return nsfw;
    }

    public void setNsfw(Long nsfw) {
        this.nsfw = nsfw;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Long getTnHeight() {
        return tnHeight;
    }

    public void setTnHeight(Long tnHeight) {
        this.tnHeight = tnHeight;
    }

    public Long getTnWidth() {
        return tnWidth;
    }

    public void setTnWidth(Long tnWidth) {
        this.tnWidth = tnWidth;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

}
