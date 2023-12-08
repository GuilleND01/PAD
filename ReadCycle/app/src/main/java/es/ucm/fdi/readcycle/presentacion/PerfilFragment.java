package es.ucm.fdi.readcycle.presentacion;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Map;

import es.ucm.fdi.readcycle.R;
import es.ucm.fdi.readcycle.integracion.CallBacks;
import es.ucm.fdi.readcycle.negocio.BookInfo;
import es.ucm.fdi.readcycle.negocio.SAUser;
import es.ucm.fdi.readcycle.negocio.UserInfo;
import kotlin.jvm.internal.Intrinsics;


public class PerfilFragment extends Fragment {

    private Button logout;
    private TextView nombre, correo, contacto, zona;
    private ImageButton opt, notificaciones;
    private EditText editZona, editContacto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);

        // Obtener el ViewModel de la MainActivity
        MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        // Actualizar el estado del fragmento actual en el ViewModel
        mainViewModel.setCurrentFragmentId(R.id.fragment_perfil);

        nombre = v.findViewById(R.id.nombreP);
        correo = v.findViewById(R.id.corrreoP);
        contacto = v.findViewById(R.id.contactoP);
        zona = v.findViewById(R.id.zonaP);

        notificaciones = v.findViewById(R.id.buttonNotification);
        opt = v.findViewById(R.id.perfilOpciones);


        SAUser saUser = new SAUser();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        saUser.infoUsuario(currentUser.getEmail().toString(), new CallBacks() {
            @Override
            public void onCallback(UserInfo u) {

                nombre.setText(u.getNombre());
                correo.setText(u.getCorreo());
                contacto.setText(u.getContacto().toString());
                zona.setText(u.getZona().toString());
            }
        });

        notificaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SAUser sa = new SAUser();
                sa.getNotificaciones(currentUser.getEmail(), new CallBacks() {
                    @Override
                    public void onCallback(ArrayList<Map<String,String>> n) {

                        NotificacionesFragment notif = new NotificacionesFragment();

                        // Montar la recycler view en otra ventana
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("notifHist", n);

                        // Asignar el Bundle al fragmento
                        notif.setArguments(bundle);

                        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameLayout, notif);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }

                });
            }
        });

        opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.EstiloMenu);
                PopupMenu popup = new PopupMenu(contextThemeWrapper, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.perfil_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();
                        if(id == R.id.editarPerfilMenu){
                            mostrarDialog();
                            return true;
                        }
                        else if(id == R.id.cerrarSesionMenu){
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        return false;
                    }
                });

                popup.show();

            }
        });

        return v;

    }

    public void mostrarDialog(){
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogView = inflater.inflate(R.layout.dialog_edit_profile, null);

        editContacto = dialogView.findViewById(R.id.editarFormaContacto);
        editZona = dialogView.findViewById(R.id.editarZona);

        editContacto.setText(contacto.getText().toString());
        editZona.setText(zona.getText().toString());
        Button btnCancelar = dialogView.findViewById(R.id.btnCancelar);
        Button btnAceptar = dialogView.findViewById(R.id.btnAceptar);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        AlertDialog dialog = builder.create();
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevaZona = editZona.getText().toString();
                String nuevaFormaContacto = editContacto.getText().toString();
                SAUser saUser = new SAUser();

                //si se ha editado solo la forma de contacto
                if(nuevaZona.equals("") && !nuevaFormaContacto.equals("")){
                    saUser.editarContacto(nuevaFormaContacto);
                    saUser.infoUsuario(currentUser.getEmail().toString(), new CallBacks() {
                        @Override
                        public void onCallback(UserInfo u) {

                            contacto.setText(u.getContacto().toString());
                        }
                    });
                }
                //si se ha editado solo la zona
                else if (!nuevaZona.equals("") && nuevaFormaContacto.equals("")) {
                    saUser.editarZona(nuevaZona);
                    saUser.infoUsuario(currentUser.getEmail().toString(), new CallBacks() {
                        @Override
                        public void onCallback(UserInfo u) {

                            zona.setText(u.getZona().toString());
                        }
                    });

                }
                //si se han editado ambas
                else if (!nuevaZona.equals("") && !nuevaFormaContacto.equals("")) {
                    saUser.editarZonaYContacto(nuevaZona, nuevaFormaContacto);
                    saUser.infoUsuario(currentUser.getEmail().toString(), new CallBacks() {
                        @Override
                        public void onCallback(UserInfo u) {

                            zona.setText(u.getZona().toString());
                            contacto.setText(u.getContacto().toString());
                        }
                    });
                }
                dialog.dismiss();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
