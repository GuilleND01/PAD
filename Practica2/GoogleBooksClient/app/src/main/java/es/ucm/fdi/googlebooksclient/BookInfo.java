package es.ucm.fdi.googlebooksclient;

import static android.provider.Settings.System.getString;


import android.content.Context;
import android.content.res.Resources;

import org.json.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookInfo {
    private String title;
    private String authors;
    private URL infoLink;

    private String imagen;

    private String printType;



    public BookInfo(String title, String authors, URL infoLink, String printType, String imagen) {
        this.title = title;
        this.authors = authors;
        this.infoLink = infoLink;
        this.printType = printType;
        this.imagen = imagen;
    }

    public static List<BookInfo> fromJsonResponse(String s, Context ctx) throws JSONException, MalformedURLException {
        List<BookInfo> book_info = new ArrayList<>();

        JSONObject json = new JSONObject(s);
        if(json.getInt("totalItems") == 0){
            return book_info;
        }

        JSONArray jar = json.getJSONArray("items");

        for (int i = 0; i < jar.length(); i++) {
            JSONObject object = jar.getJSONObject(i);
            JSONObject clave = object.getJSONObject("volumeInfo");



            String type = clave.getString("printType");
            String aut = "";

            if(type.equals("BOOK") && clave.has("authors")){
                aut = clave.getJSONArray("authors").toString();
            } else if (type.equals("BOOK")) {
                aut = ctx.getString(R.string.noAut);
            }

            String tit = "";
            if(clave.has("title")){
                tit = clave.getString("title");
            }
            else{
                tit = ctx.getString(R.string.noAut);
            }

            if(type.equals("BOOK")){
                type = ctx.getString(R.string.libro);
            }
            else{
                type = ctx.getString(R.string.revista);
            }

            String img = "";
            if(clave.has("imageLinks") && clave.getJSONObject("imageLinks").has("thumbnail")){
                img = clave.getJSONObject("imageLinks").getString("thumbnail");
            }



            URL info = new URL(clave.getString("infoLink"));

            book_info.add(new BookInfo(tit, aut, info, type, img));
        }
        return book_info;
    }

    public String getTitle(){
        return title;
    }
    public String getAuthors(){
        return authors;
    }
    public URL getinfoLink(){ return infoLink; }

    public String getImagen(){return imagen;}

    public String getPrintType(){
        return printType;
    }
}
