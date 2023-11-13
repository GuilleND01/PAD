package es.ucm.fdi.readcycle.integracion;

import com.google.firebase.firestore.FirebaseFirestore;

public class SingletonDBImp extends SingletonDataBase{
    private FirebaseFirestore db;

    public SingletonDBImp() {
        if (db == null) {
            db = FirebaseFirestore.getInstance();
        }
    }

    @Override
    public FirebaseFirestore getDB() {
        return db;
    }
}
