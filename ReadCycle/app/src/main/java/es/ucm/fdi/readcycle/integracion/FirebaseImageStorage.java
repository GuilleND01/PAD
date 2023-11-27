package es.ucm.fdi.readcycle.integracion;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseImageStorage {

    private StorageReference storageRef;
    private final String URL_TO_STORAGE = "gs://readcycle-4f72f.appspot.com";

    public FirebaseImageStorage () {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        this.storageRef = storage.getReferenceFromUrl(URL_TO_STORAGE);
    }

    public StorageReference getStorageRef() {
        return storageRef;
    }
}
