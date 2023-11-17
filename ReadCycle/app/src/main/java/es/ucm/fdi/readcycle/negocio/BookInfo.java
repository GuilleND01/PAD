package es.ucm.fdi.readcycle.negocio;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BookInfo {

    private String title, img, author, state, description;
    private ArrayList<String> genres;
    private Integer pages;

    public BookInfo(String title, ArrayList<String> genres, String author, String state, String description, String img, Integer pages){
        this.title=title;
        this.img=img;
        this.genres=genres; // Debería ser un array?
        this.author=author; // Debería ser un array?
        this.state=state;
        this.description=description;
        this.pages=pages;
    }

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
    }

    public ArrayList<String> getGenre() {
        return genres;
    }

    public String getAuthor() {
        return author;
    }

    public String getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPages() {
        return pages;
    }
}
