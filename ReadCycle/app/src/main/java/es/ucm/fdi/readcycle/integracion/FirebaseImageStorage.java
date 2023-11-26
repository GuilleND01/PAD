package es.ucm.fdi.readcycle.integracion;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseImageStorage {

    private StorageReference storageRef;

    public FirebaseImageStorage () {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        this.storageRef = storage.getReferenceFromUrl("gs://readcycle-4f72f.appspot.com");
    }

    public StorageReference getStorageRef() {
        return storageRef;
    }
}
