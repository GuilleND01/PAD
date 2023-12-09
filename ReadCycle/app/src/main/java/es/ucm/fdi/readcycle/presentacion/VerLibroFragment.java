package es.ucm.fdi.readcycle.presentacion;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.integracion.CallBacks;
import es.ucm.fdi.readcycle.integracion.SingletonDataBase;
import es.ucm.fdi.readcycle.negocio.BookInfo;
import es.ucm.fdi.readcycle.negocio.SABook;
import es.ucm.fdi.readcycle.negocio.SAUser;
import es.ucm.fdi.readcycle.negocio.UserInfo;
import io.grpc.okhttp.OkHttpChannelBuilder;
import io.grpc.okhttp.OkHttpChannelProvider;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Response;

public class VerLibroFragment extends Fragment {

    private  BookInfo bookInfo;
    private static final String ARG_BOOK_INFO = "book_info";

    private static final String BEARER_TOKEN = "Bearer AAAAabD_4J8:APA91bHFi2tlM3CUqssG1jqw0HqSsDbWyeCZHUWwLxQDGO_BKYHwlbUC2bISS6zEJ38P3cxVfWiNVWbU_XrXKU0RF2Z4nw0AwQBaxgHLrlajhWnyRk6bNzjwU-wlQf-WmWcEkWZc5oK1";

    private TextView textTitulo, textGenero, textAutor, textPag, textEstado, textResumen, titResumen;
    private ImageView img;

    private FrameLayout layout;
    private Button btnSolicitar, btnEliminar;

    private ImageButton btnVolver;

    private String nombre, contacto;

    public static VerLibroFragment newInstance(BookInfo bookInfo) {
        VerLibroFragment fragment = new VerLibroFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK_INFO, bookInfo);
        fragment.setArguments(args);
        return fragment;
    }

    FirebaseUser currentUser = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View v = inflater.inflate(R.layout.fragment_verlibro, container, false);

        Bundle args = getArguments();
        if (args != null) {
            this.bookInfo = (BookInfo) args.getSerializable(ARG_BOOK_INFO);
            Log.d("JULIA", bookInfo.getTitle());

            textTitulo =  v.findViewById(R.id.nombre_usuario);
            textGenero = v.findViewById(R.id.libro_genero);
            textAutor  = v.findViewById(R.id.libro_autor);
            textPag  = v.findViewById(R.id.libro_numpaginas);
            textEstado  = v.findViewById(R.id.libro_estado);
            textResumen = v.findViewById(R.id.libro_resumen);

            img = v.findViewById(R.id.libro_imagen);
            layout = v.findViewById(R.id.libro_layout);

            //asiganamos los valores
            textTitulo.setText(bookInfo.getTitle());
            textAutor.setText(bookInfo.getAuthor());
            textPag.setText(bookInfo.getPages().toString());




            //Genero un string con los generos
            String[] generoArray = getResources().getStringArray(R.array.genero_array);
            ArrayList<Integer> generos = bookInfo.getGenre();
            String g = "";
            for(int i = 0; i<generos.size();i++){
                g += generoArray[generos.get(i)];
                if( generos.size()>1 && i != generos.size()-1 ){ //Si no es el útimo elmento añado la coma
                    g += ", ";
                }
            }
            textGenero.setText(g);

            //Asignamos el estado dependiendo del valor
            String[] booksArray = getResources().getStringArray(R.array.books_array);
            textEstado.setText(booksArray[bookInfo.getState()]);

            //Si no hay descripcion no muestro el campo
            if(bookInfo.getDescription().equals("")){
                titResumen = v.findViewById(R.id.libro_resumen_tit);
                titResumen.setVisibility(View.GONE);
                textResumen.setVisibility(View.GONE);
            }else{
                textResumen.setText(bookInfo.getDescription());
            }

            Glide.with(layout)
                    .load(bookInfo.getSelectedImage())
                    .placeholder(R.drawable.libro_2)
                    .into(img);


            //Damos funcionalidad a los botones
            btnSolicitar = v.findViewById(R.id.btn_solicitar);
            btnEliminar = v.findViewById(R.id.btn_eliminar);
            btnVolver = v.findViewById(R.id.btn_back_user_biblioteca);

            btnVolver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            });

            //Si el propietario del libro es el usuario registrado mostramos el boton de eliminar, si no el de solicitar
            //De esta manera podemos reutilizar la vista
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
            //Cojo los parametros del usuario q voy a necesitar luego
            SAUser saUser = new SAUser();
            saUser.infoUsuario(currentUser.getEmail(), new CallBacks() {
                @Override
                public void onCallback(UserInfo u) {
                    nombre = u.getNombre();
                    contacto = u.getContacto();
                }
            });

            if(bookInfo.getPropietario().equals(currentUser.getEmail())){
                btnSolicitar.setVisibility(View.GONE);
               btnEliminar.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                       builder.setTitle(R.string.confirmar);
                       builder.setPositiveButton(R.string.eliminar, new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                              SABook saBookInfo = new SABook();
                              saBookInfo.eliminarLibro(bookInfo, new CallBacks() {
                                   @Override
                                   public void onCallbackExito(Boolean exito) {
                                       dialog.dismiss();
                                       if(exito){
                                           //redirigir a la biblioteca si se ha podido eliminar el libro
                                           MiBibliotecaFragment miBibliotecaFragment = new MiBibliotecaFragment();
                                           FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                                           transaction.replace(R.id.frameLayout, miBibliotecaFragment);
                                           transaction.addToBackStack(null);
                                           transaction.commit();

                                       }else {
                                           Toast.makeText(v.getContext(), getResources().getString(R.string.MSG_ERROR_ELIMINAR), Toast.LENGTH_LONG).show();
                                       }

                                   }

                               });
                           }
                       });
                       builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               dialog.dismiss();
                           }
                       });
                       AlertDialog dialog = builder.create();
                       dialog.show();


                   }
               });
            }else{
                btnEliminar.setText(getResources().getString(R.string.bibliotecOtro));
                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //redirigir a la biblioteca del usuario
                        UsuarioBibliotecaFragment usuarioBibliotecaFragment = new UsuarioBibliotecaFragment();

                        // Crear un Bundle para pasar el correo
                        Bundle bundle = new Bundle();
                        bundle.putString("propietario", bookInfo.getPropietario());

                        // Asignar el Bundle al fragmento
                        usuarioBibliotecaFragment.setArguments(bundle);

                        // Iniciar la transacción del fragmento
                        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameLayout, usuarioBibliotecaFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    }
                });

                btnSolicitar.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        /* Recupero el USER_TOKEN de la persona */

                        // TODO mover a DAO
                        SingletonDataBase.getInstance().getDB().collection("Usuarios").whereEqualTo("correo",
                                bookInfo.getPropietario()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    QuerySnapshot querySnapshot = task.getResult();
                                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                        DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                                        String userToken = document.getString("token");
                                        realizar_Https(userToken);
                                    }
                                }
                            }
                        });

                    }
                });


            }


        }
        return v;
    }

    /* Para realizar la comunicación device-to-device utilizamos peticiones https de tipo POST a la
    * api de Firebase Cloud Messaging. Se pasa como parámetro el token del dispositivo al que se va
    * a mandar la notificación. También, para realizar la llamada es necesario auntenticarse por eso
    * el Bearer, que he sacado del proyecto de la web de Firebase.*/
    public void realizar_Https (String USER_TOKEN){
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject jsonObject = null;

        try{
            jsonObject  = new JSONObject();

            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title", getString(R.string.noti_solicitud));
            String bodyNotification = getString(R.string.body_noti, nombre, bookInfo.getTitle(), bookInfo.getAuthor(), contacto);
            notificationObj.put("body", bodyNotification);
            notificationObj.put("tag",  currentUser.getEmail());
            jsonObject.put("notification",notificationObj);
            jsonObject.put("to", USER_TOKEN);

        }catch (Exception e){
            Log.d("error", e.toString());
        }


        OkHttpClient client = new OkHttpClient();
        String url = "https://fcm.googleapis.com/fcm/send";
        RequestBody body = RequestBody.create(jsonObject.toString(), mediaType);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization", BEARER_TOKEN)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), getString(R.string.exito_noti), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }
}
