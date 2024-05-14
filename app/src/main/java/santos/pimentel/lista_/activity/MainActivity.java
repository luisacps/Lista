package santos.pimentel.lista_.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import santos.pimentel.lista_.R;
import santos.pimentel.lista_.adapter.MyAdapter;
import santos.pimentel.lista_.model.MyItem;

public class MainActivity extends AppCompatActivity {

    static int NEW_ITEM_REQUEST = 1;
    List<MyItem> itens = new ArrayList<MyItem>();

    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // variavel que obtem o botao fab
        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem);
        // registro de um clicklistener para a acao de click no botao
        fabAddItem.setOnClickListener(new View.OnClickListener() {

            @Override
            // funcao executada com a acao de click no botao
            public void onClick(View v) {
                // criacao de uma intent que navega da main para a newitem
                Intent i = new Intent(MainActivity.this, NewItemActivity.class);
                // execucao do metodo que assume que, em algum momento, a newitem vai retornar algum dado para a main
                // i -> intent que vai ser executada, new_item_request -> numero que identifica a chamada da intent
                startActivityForResult(i, NEW_ITEM_REQUEST);
            }
        });

        RecyclerView rvItens = findViewById(R.id.rvItens);
        myAdapter = new MyAdapter(this,itens);
        rvItens.setAdapter(myAdapter);

        rvItens.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvItens.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(), DividerItemDecoration.VERTICAL);
        rvItens.addItemDecoration(dividerItemDecoration);

    }

    @Override
    // funcao que verifica se todas as condicoes de retorno de dados foram cumpridas
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ITEM_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                // caso esteja tudo certo, uma nova instancia e criada para guardar os novos dados
                MyItem myItem = new MyItem();
                myItem.title = data.getStringExtra("title");
                myItem.description = data.getStringExtra("description");
                myItem.photo = data.getData();
                // adicao do novo item a arraylist de itens
                itens.add(myItem);

                myAdapter.notifyItemInserted(itens.size()-1);
            }
        }
    }

}