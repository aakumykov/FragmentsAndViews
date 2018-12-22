package ru.aakumykov.dvachreader.models.BoardsList;

public class BoardsTOCItem {

    public static final String USER_CATEGORY_NAME = "Пользовательские";

    private String id;
    private String name;
    private String category;
    private String default_name;
    private Object[] icons;

    private int bump_limit;
    private int pages;

    private Boolean sage;
    private Boolean tripcodes;

    private Boolean enable_dices;
    private Boolean enable_flags;
    private Boolean enable_icons;
    private Boolean enable_likes;
    private Boolean enable_names;
    private Boolean enable_oekaki;
    private Boolean enable_posting;
    private Boolean enable_sage;
    private Boolean enable_shield;
    private Boolean enable_subject;
    private Boolean enable_thread_tags;
    private Boolean enable_trips;

    BoardsTOCItem(){}

    // Методы, добавленные вручную
    public boolean isUserCategory() {
        return category.equals(USER_CATEGORY_NAME);
    }

    // Сгенерированные геттеры-сеттеры
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDefault_name() {
        return default_name;
    }

    public void setDefault_name(String default_name) {
        this.default_name = default_name;
    }

    public Object[] getIcons() {
        return icons;
    }

    public void setIcons(Object[] icons) {
        this.icons = icons;
    }

    public int getBump_limit() {
        return bump_limit;
    }

    public void setBump_limit(int bump_limit) {
        this.bump_limit = bump_limit;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Boolean getSage() {
        return sage;
    }

    public void setSage(Boolean sage) {
        this.sage = sage;
    }

    public Boolean getTripcodes() {
        return tripcodes;
    }

    public void setTripcodes(Boolean tripcodes) {
        this.tripcodes = tripcodes;
    }

    public Boolean getEnable_dices() {
        return enable_dices;
    }

    public void setEnable_dices(Boolean enable_dices) {
        this.enable_dices = enable_dices;
    }

    public Boolean getEnable_flags() {
        return enable_flags;
    }

    public void setEnable_flags(Boolean enable_flags) {
        this.enable_flags = enable_flags;
    }

    public Boolean getEnable_icons() {
        return enable_icons;
    }

    public void setEnable_icons(Boolean enable_icons) {
        this.enable_icons = enable_icons;
    }

    public Boolean getEnable_likes() {
        return enable_likes;
    }

    public void setEnable_likes(Boolean enable_likes) {
        this.enable_likes = enable_likes;
    }

    public Boolean getEnable_names() {
        return enable_names;
    }

    public void setEnable_names(Boolean enable_names) {
        this.enable_names = enable_names;
    }

    public Boolean getEnable_oekaki() {
        return enable_oekaki;
    }

    public void setEnable_oekaki(Boolean enable_oekaki) {
        this.enable_oekaki = enable_oekaki;
    }

    public Boolean getEnable_posting() {
        return enable_posting;
    }

    public void setEnable_posting(Boolean enable_posting) {
        this.enable_posting = enable_posting;
    }

    public Boolean getEnable_sage() {
        return enable_sage;
    }

    public void setEnable_sage(Boolean enable_sage) {
        this.enable_sage = enable_sage;
    }

    public Boolean getEnable_shield() {
        return enable_shield;
    }

    public void setEnable_shield(Boolean enable_shield) {
        this.enable_shield = enable_shield;
    }

    public Boolean getEnable_subject() {
        return enable_subject;
    }

    public void setEnable_subject(Boolean enable_subject) {
        this.enable_subject = enable_subject;
    }

    public Boolean getEnable_thread_tags() {
        return enable_thread_tags;
    }

    public void setEnable_thread_tags(Boolean enable_thread_tags) {
        this.enable_thread_tags = enable_thread_tags;
    }

    public Boolean getEnable_trips() {
        return enable_trips;
    }

    public void setEnable_trips(Boolean enable_trips) {
        this.enable_trips = enable_trips;
    }
}
