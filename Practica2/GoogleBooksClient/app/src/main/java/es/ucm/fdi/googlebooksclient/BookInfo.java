package es.ucm.fdi.googlebooksclient;

import org.json.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookInfo {
    private String title;
    private String authors;
    private URL infoLink;

    public BookInfo(String title, String authors, URL infoLink) {
        this.title = title;
        this.authors = authors;
        this.infoLink = infoLink;
    }

    public static List<BookInfo> fromJsonResponse(String s) throws JSONException, MalformedURLException {
        List<BookInfo> book_info = new ArrayList<>();

        JSONObject json = new JSONObject(s);
        JSONArray jar = json.getJSONArray("items");

        for (int i = 0; i < jar.length(); i++) {
            JSONObject object = jar.getJSONObject(i);
            JSONObject clave = object.getJSONObject("volumeInfo");

            String tit = clave.getString("title");
            String aut = clave.getJSONArray("authors").toString();
            URL info = new URL(clave.getString("infoLink"));

            book_info.add(new BookInfo(tit, aut, info));
        }
        return book_info;
    }

    public String getTitle(){
        return title;
    }
    public String getAuthors(){
        return authors;
    }
    public URL getinfoLink(){
        return infoLink;
    }
}
