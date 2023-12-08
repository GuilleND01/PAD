package es.ucm.fdi.readcycle.presentacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.integracion.CallBacks;
import es.ucm.fdi.readcycle.negocio.BookInfo;
import es.ucm.fdi.readcycle.negocio.SAUser;
import es.ucm.fdi.readcycle.negocio.UserInfo;
import kotlin.jvm.internal.Intrinsics;

public class MiBibliotecaFragment extends Fragment {


    ImageButton addBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater"); //comprobamos que lo que llega no es ulo
        View view = inflater.inflate(R.layout.fragment_biblioteca, container, false);

        // Obtener el ViewModel de la MainActivity
        MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        // Actualizar el estado del fragmento actual en el ViewModel
        mainViewModel.setCurrentFragmentId(R.id.fragment_biblioteca);

        BookAdapter b = new BookAdapter();
        ArrayList<BookInfo> biblioteca = new ArrayList<>();

        //AÑADIMOS EL BOOKADD, tal como esta implementada la recycler view coge el primer elemento del
        //arraylist de books y lo sobreescribe mostrando la card de añadir, asi que metemos un libro nulo
        //para que no sobrreescriba uno real de la biblioteca

        ArrayList<Integer> g = new ArrayList<Integer>();
        g.add(0);
        android.net.Uri selectedImage=null;
        BookInfo bookAdd = new BookInfo("",g, "",0,"", 0, selectedImage);

        biblioteca.add(bookAdd);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        SAUser saUser = new SAUser();
        saUser.getBiblioteca(currentUser.getEmail(), new CallBacks() {
            @Override
            public void onCallbackBooks(ArrayList<BookInfo> bs) {
                biblioteca.addAll(bs);
                b.setBookData(biblioteca, true);
                RecyclerView recyclerView = view.findViewById(R.id.reclyclerViewBook);
                recyclerView.setAdapter(b);
            }
        });

        return view;

    }


}
