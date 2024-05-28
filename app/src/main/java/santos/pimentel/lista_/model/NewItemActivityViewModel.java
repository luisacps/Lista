package santos.pimentel.lista_.model;

import android.net.Uri;
import androidx.lifecycle.ViewModel;

public class NewItemActivityViewModel extends ViewModel {
    // codigo viewmodel da newitemactivity
    Uri selectPhotoLocation = null;

    public Uri getSelectPhotoLocation() {
        return selectPhotoLocation;
    }

    // método que guarda o Uri para que a imagem continua aparecendo com a rotação da tela
    public void setSelectPhotoLocation(Uri selectPhotoLocation) {
        this.selectPhotoLocation = selectPhotoLocation;
    }
}
