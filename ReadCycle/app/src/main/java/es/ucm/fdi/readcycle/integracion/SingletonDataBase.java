package es.ucm.fdi.readcycle.integracion;
import com.google.firebase.firestore.FirebaseFirestore;

public abstract class SingletonDataBase {

    private static SingletonDataBase instance;

    public static SingletonDataBase getInstance() {
        if (instance == null)
            instance = new SingletonDBImp();
        return instance;
    }

    public abstract FirebaseFirestore getDB();
}
