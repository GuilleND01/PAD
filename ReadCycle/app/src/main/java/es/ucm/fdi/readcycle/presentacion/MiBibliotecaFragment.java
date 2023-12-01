package es.ucm.fdi.readcycle.presentacion;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.integracion.DAOBook;
import es.ucm.fdi.readcycle.negocio.BookInfo;
import es.ucm.fdi.readcycle.negocio.SABook;
import es.ucm.fdi.readcycle.negocio.SAUser;
import kotlin.jvm.internal.Intrinsics;

public class MiBibliotecaFragment extends Fragment {


    ImageButton addBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater"); //comprobamos que lo que llega no es ulo
        View view = inflater.inflate(R.layout.fragment_biblioteca, container, false);

        //PRUEBA PARA LAS CARDS


        ArrayList<Integer> g = new ArrayList<Integer>();
        g.add(0);
        g.add(1);
        g.add(2);
        g.add(3);
        g.add(4);

        android.net.Uri selectedImage2 =null;
        android.net.Uri selectedImage = Uri.parse("https://books.google.com/books/content?id=e8DwuncELaoC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api");;

        BookInfo bookAdd = new BookInfo("",g, "",0,"", 0, selectedImage);
        BookInfo book1 = new BookInfo("Soy un libro libro libro libro",g, "Nombre autor",1,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi volutpat enim a viverra dapibus. Ut ante dui, posuere in ante vitae, dapibus aliquam lectus. Etiam eu sapien sit amet ante pellentesque pretium ut id urna. Quisque mollis erat quis justo luctus, eget tempor quam commodo. Suspendisse sit amet condimentum nisi. Nullam ante libero, tempor eu viverra vel, tempus at augue. Praesent et vulputate ligula, eu sagittis velit.", 1, selectedImage);
        book1.setPropietario("juliaflu@ucm.es");
        BookInfo book2 = new BookInfo("Jonas",g, "Nick",0,"mu bonito el libro ooooooo ooo ooo oo  oo oooooo ooo o oo oo o oo o o oooooooo o oo oo oo oo oo o oo ooo",100, selectedImage);
        book2.setPropietario("");
        BookInfo book3 = new BookInfo("PRUEBA",g, "antonio",3,"", 1, selectedImage2);
        book3.setPropietario("");
        ArrayList<BookInfo> a = new ArrayList<>();
        a.add(bookAdd);
        a.add(book1);
        a.add(book2);
        a.add(book3);

        BookAdapter b = new BookAdapter();
        b.setBookData(a);
        RecyclerView recyclerView = view.findViewById(R.id.reclyclerViewBook);
        recyclerView.setAdapter(b);

        //CLAUDIA PROBANDO COSAS CARGANDOSE LA JERARQUIA DE PAQUETES

        /*
        SABook sa = new SABook();
        BookInfo bookGet = sa.getLibroById("vHnr1YEWPjhwHxyIDxpA");
        if(bookGet != null) Log.d("JUL", bookGet.getTitle());*/



        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        SAUser saUser = new SAUser();
        //ArrayList<BookInfo> books = saUser.getBiblioteca(currentUser.getEmail());
        //if(books != null) Log.d("JUL", books.get(0).getTitle());



        return view;

    }
}
