package es.ucm.fdi.readcycle.negocio;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

import es.ucm.fdi.readcycle.R;

public class BookInfo implements Serializable {

    private String title, author, description, propietario, id;
    private int state;
    private ArrayList<Integer> genres;
    private Integer pages;

    private android.net.Uri selectedImage;


    public BookInfo(String title, ArrayList<Integer> genres, String author, int state, String description, Integer pages, android.net.Uri selectedImage)
    {
        this.title=title;
        this.genres=genres;
        this.author=author;
        this.state=state;
        this.description=description;
        this.pages=pages;
        this.selectedImage=selectedImage;
    }
    public BookInfo(){};

    public android.net.Uri getSelectedImage() {
        return selectedImage;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Integer> getGenre() {
        return genres;
    }

    public String getAuthor() {
        return author;
    }

    public int getState() { return state; }

    public String getDescription() {
        return description;
    }

    public Integer getPages() {
        return pages;
    }

    public String getPropietario(){return propietario; }


    public String getId(){return id;}

    public void setId(String id){ this.id=id; }

    public void setPropietario(String propietario){
        this.propietario=propietario;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public void setAuthor(String author){
        this.author=author;
    }
}
