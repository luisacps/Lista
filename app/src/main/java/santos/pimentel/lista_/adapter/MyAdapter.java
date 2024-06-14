package santos.pimentel.lista_.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import santos.pimentel.lista_.R;
import santos.pimentel.lista_.activity.MainActivity;
import santos.pimentel.lista_.model.MyItem;

public class MyAdapter extends RecyclerView.Adapter {

    MainActivity mainActivity;
    List<MyItem> itens;

    public MyAdapter(MainActivity mainActivity, List<MyItem> itens) {
        this.mainActivity = mainActivity;
        this.itens = itens;
    }

    @NonNull
    @Override
    // metodo que cria os elementos de interface para um item
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // criacao de um layoutinflater, que le o arquivo xml do item
        LayoutInflater inflater = LayoutInflater.from(mainActivity);
        // uso do layoutinflater para criar os elementos referentes a um item
        View v = inflater.inflate(R.layout.item_list, parent, false);
        // guarda a view num objeto viewholder e retorna ele
        return new MyViewHolder(v);
    }

    @Override
    // metodo que recebe o viewholder criado no metodo oncreateviewholder e preenche os elementos de interface com os dados
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // obtencao do item que vai ser usado
        MyItem myItem = itens.get(position);
        // obtencao do objeto view guardado dentro do viewholder
        View v = holder.itemView;

        // obtencao dos dados e set nos elementos de interface
        ImageView imvPhoto = v.findViewById(R.id.imvPhoto);
        imvPhoto.setImageBitmap(myItem.photo);

        TextView tvTitle = v.findViewById(R.id.tvTitle);
        tvTitle.setText(myItem.title);

        TextView tvDesc = v.findViewById(R.id.tcDesc);
        tvDesc.setText(myItem.description);
    }

    @Override
    // metodo que retorna a quantidade de itens que a lista possui
    public int getItemCount() {
        return itens.size();
    }
}




