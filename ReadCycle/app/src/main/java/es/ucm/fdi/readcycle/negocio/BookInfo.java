package es.ucm.fdi.readcycle.negocio;
public class BookInfo {

    private String title;
    private String img;

    public BookInfo(String title, String img){
        this.title=title;
        this.img=img;
    }

    public String getTitle(){
        return title;
    }

    public String getImg(){
        return img;
    }

}
