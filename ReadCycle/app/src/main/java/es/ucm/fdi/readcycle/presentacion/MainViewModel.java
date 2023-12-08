package es.ucm.fdi.readcycle.presentacion;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private int currentFragmentId;

    public void setCurrentFragmentId(int fragmentId) {
        currentFragmentId = fragmentId;
    }

    public int getCurrentFragmentId() {
        return currentFragmentId;
    }
}
