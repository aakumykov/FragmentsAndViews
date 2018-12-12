package aakumykov.ru.fragmentsandviews.models.Thread;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OneThread {

    @SerializedName("advert_bottom_image")
    private String advertBottomImage;
    @SerializedName("advert_bottom_link")
    private String advertBottomLink;
    @SerializedName("advert_mobile_image")
    private String advertMobileImage;
    @SerializedName("advert_mobile_link")
    private String advertMobileLink;
    @SerializedName("advert_top_image")
    private String advertTopImage;
    @SerializedName("advert_top_link")
    private String advertTopLink;
    @SerializedName("Board")
    private String board;
    @SerializedName("board_banner_image")
    private String boardBannerImage;
    @SerializedName("board_banner_link")
    private String boardBannerLink;
    @SerializedName("BoardInfo")
    private String boardInfo;
    @SerializedName("BoardInfoOuter")
    private String boardInfoOuter;
    @SerializedName("BoardName")
    private String boardName;
    @SerializedName("bump_limit")
    private Long bumpLimit;
    @SerializedName("current_thread")
    private String currentThread;
    @SerializedName("default_name")
    private String defaultName;
    @SerializedName("enable_dices")
    private Long enableDices;
    @SerializedName("enable_flags")
    private Long enableFlags;
    @SerializedName("enable_icons")
    private Long enableIcons;
    @SerializedName("enable_images")
    private Long enableImages;
    @SerializedName("enable_likes")
    private Long enableLikes;
    @SerializedName("enable_names")
    private Long enableNames;
    @SerializedName("enable_oekaki")
    private Long enableOekaki;
    @SerializedName("enable_posting")
    private Long enablePosting;
    @SerializedName("enable_sage")
    private Long enableSage;
    @SerializedName("enable_shield")
    private Long enableShield;
    @SerializedName("enable_subject")
    private Long enableSubject;
    @SerializedName("enable_thread_tags")
    private Long enableThreadTags;
    @SerializedName("enable_trips")
    private Long enableTrips;
    @SerializedName("enable_video")
    private Long enableVideo;
    @SerializedName("files_count")
    private Long filesCount;
    @SerializedName("is_board")
    private Long isBoard;
    @SerializedName("is_closed")
    private Long isClosed;
    @SerializedName("is_index")
    private Long isIndex;
    @SerializedName("max_comment")
    private Long maxComment;
    @SerializedName("max_files_size")
    private Long maxFilesSize;
    @SerializedName("max_num")
    private Long maxNum;
    @SerializedName("news_abu")
    private List<NewsAbu> newsAbu;
    @SerializedName("posts_count")
    private Long postsCount;
    @Expose
    private List<Thread> threads;
    @Expose
    private String title;
    @Expose
    private List<Top> top;
    @SerializedName("unique_posters")
    private String uniquePosters;

    public String getAdvertBottomImage() {
        return advertBottomImage;
    }

    public void setAdvertBottomImage(String advertBottomImage) {
        this.advertBottomImage = advertBottomImage;
    }

    public String getAdvertBottomLink() {
        return advertBottomLink;
    }

    public void setAdvertBottomLink(String advertBottomLink) {
        this.advertBottomLink = advertBottomLink;
    }

    public String getAdvertMobileImage() {
        return advertMobileImage;
    }

    public void setAdvertMobileImage(String advertMobileImage) {
        this.advertMobileImage = advertMobileImage;
    }

    public String getAdvertMobileLink() {
        return advertMobileLink;
    }

    public void setAdvertMobileLink(String advertMobileLink) {
        this.advertMobileLink = advertMobileLink;
    }

    public String getAdvertTopImage() {
        return advertTopImage;
    }

    public void setAdvertTopImage(String advertTopImage) {
        this.advertTopImage = advertTopImage;
    }

    public String getAdvertTopLink() {
        return advertTopLink;
    }

    public void setAdvertTopLink(String advertTopLink) {
        this.advertTopLink = advertTopLink;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getBoardBannerImage() {
        return boardBannerImage;
    }

    public void setBoardBannerImage(String boardBannerImage) {
        this.boardBannerImage = boardBannerImage;
    }

    public String getBoardBannerLink() {
        return boardBannerLink;
    }

    public void setBoardBannerLink(String boardBannerLink) {
        this.boardBannerLink = boardBannerLink;
    }

    public String getBoardInfo() {
        return boardInfo;
    }

    public void setBoardInfo(String boardInfo) {
        this.boardInfo = boardInfo;
    }

    public String getBoardInfoOuter() {
        return boardInfoOuter;
    }

    public void setBoardInfoOuter(String boardInfoOuter) {
        this.boardInfoOuter = boardInfoOuter;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public Long getBumpLimit() {
        return bumpLimit;
    }

    public void setBumpLimit(Long bumpLimit) {
        this.bumpLimit = bumpLimit;
    }

    public String getCurrentThread() {
        return currentThread;
    }

    public void setCurrentThread(String currentThread) {
        this.currentThread = currentThread;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public Long getEnableDices() {
        return enableDices;
    }

    public void setEnableDices(Long enableDices) {
        this.enableDices = enableDices;
    }

    public Long getEnableFlags() {
        return enableFlags;
    }

    public void setEnableFlags(Long enableFlags) {
        this.enableFlags = enableFlags;
    }

    public Long getEnableIcons() {
        return enableIcons;
    }

    public void setEnableIcons(Long enableIcons) {
        this.enableIcons = enableIcons;
    }

    public Long getEnableImages() {
        return enableImages;
    }

    public void setEnableImages(Long enableImages) {
        this.enableImages = enableImages;
    }

    public Long getEnableLikes() {
        return enableLikes;
    }

    public void setEnableLikes(Long enableLikes) {
        this.enableLikes = enableLikes;
    }

    public Long getEnableNames() {
        return enableNames;
    }

    public void setEnableNames(Long enableNames) {
        this.enableNames = enableNames;
    }

    public Long getEnableOekaki() {
        return enableOekaki;
    }

    public void setEnableOekaki(Long enableOekaki) {
        this.enableOekaki = enableOekaki;
    }

    public Long getEnablePosting() {
        return enablePosting;
    }

    public void setEnablePosting(Long enablePosting) {
        this.enablePosting = enablePosting;
    }

    public Long getEnableSage() {
        return enableSage;
    }

    public void setEnableSage(Long enableSage) {
        this.enableSage = enableSage;
    }

    public Long getEnableShield() {
        return enableShield;
    }

    public void setEnableShield(Long enableShield) {
        this.enableShield = enableShield;
    }

    public Long getEnableSubject() {
        return enableSubject;
    }

    public void setEnableSubject(Long enableSubject) {
        this.enableSubject = enableSubject;
    }

    public Long getEnableThreadTags() {
        return enableThreadTags;
    }

    public void setEnableThreadTags(Long enableThreadTags) {
        this.enableThreadTags = enableThreadTags;
    }

    public Long getEnableTrips() {
        return enableTrips;
    }

    public void setEnableTrips(Long enableTrips) {
        this.enableTrips = enableTrips;
    }

    public Long getEnableVideo() {
        return enableVideo;
    }

    public void setEnableVideo(Long enableVideo) {
        this.enableVideo = enableVideo;
    }

    public Long getFilesCount() {
        return filesCount;
    }

    public void setFilesCount(Long filesCount) {
        this.filesCount = filesCount;
    }

    public Long getIsBoard() {
        return isBoard;
    }

    public void setIsBoard(Long isBoard) {
        this.isBoard = isBoard;
    }

    public Long getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Long isClosed) {
        this.isClosed = isClosed;
    }

    public Long getIsIndex() {
        return isIndex;
    }

    public void setIsIndex(Long isIndex) {
        this.isIndex = isIndex;
    }

    public Long getMaxComment() {
        return maxComment;
    }

    public void setMaxComment(Long maxComment) {
        this.maxComment = maxComment;
    }

    public Long getMaxFilesSize() {
        return maxFilesSize;
    }

    public void setMaxFilesSize(Long maxFilesSize) {
        this.maxFilesSize = maxFilesSize;
    }

    public Long getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Long maxNum) {
        this.maxNum = maxNum;
    }

    public List<NewsAbu> getNewsAbu() {
        return newsAbu;
    }

    public void setNewsAbu(List<NewsAbu> newsAbu) {
        this.newsAbu = newsAbu;
    }

    public Long getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(Long postsCount) {
        this.postsCount = postsCount;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Top> getTop() {
        return top;
    }

    public void setTop(List<Top> top) {
        this.top = top;
    }

    public String getUniquePosters() {
        return uniquePosters;
    }

    public void setUniquePosters(String uniquePosters) {
        this.uniquePosters = uniquePosters;
    }

}
