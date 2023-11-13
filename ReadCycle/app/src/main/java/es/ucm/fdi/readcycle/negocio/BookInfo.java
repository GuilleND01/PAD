package es.ucm.fdi.readcycle.negocio;
public class BookInfo {

    private String title, img, genre, author, state, description;
    private Integer pages;

    public BookInfo(String title, String genre, String author, String state, String description, String img, Integer pages){
        this.title=title;
        this.img=img;
        this.genre=genre; // Debería ser un array?
        this.author=author; // Debería ser un array?
        this.state=state;
        this.description=description;
        this.pages=pages;
    }

    public String getTitle(){
        return title;
    }

    public String getImg(){
        return img;
    }

}
