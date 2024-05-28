package santos.pimentel.lista_.model;

import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    // codigo viewmodel da mainactivity
    // mas o que é um viewmodel? um container que guarda os dados de uma activity
    List<MyItem> itens = new ArrayList<>();

    public List<MyItem> getItens() {
        return itens;
    }
}
