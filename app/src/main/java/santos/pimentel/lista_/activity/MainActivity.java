package santos.pimentel.lista_.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import santos.pimentel.lista_.R;
import santos.pimentel.lista_.adapter.MyAdapter;
import santos.pimentel.lista_.model.MainActivityViewModel;
import santos.pimentel.lista_.model.MyItem;
import santos.pimentel.lista_.util.Util;

public class MainActivity extends AppCompatActivity {

    static int NEW_ITEM_REQUEST = 1;

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

        // obtencao do recycleview
        RecyclerView rvItens = findViewById(R.id.rvItens);

        // obtenção do viewmodel da main
        MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        // obtenção da lista de itens que agora é guardada na mainactivityviewmodel
        List<MyItem> itens = vm.getItens();

        // criacao do adapter, que recebe a lista de itens como parâmetro
        myAdapter = new MyAdapter(this,itens);
        // set do adapter no recyclerview
        rvItens.setAdapter(myAdapter);

        // metodo que indica para o recycleview que não há variação no tamanho do itens da lista
        rvItens.setHasFixedSize(true);

        // criacao de um gerenciador de layout que faz com que a lista seja mostrada no recycleview de forma linear e verticalmente
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvItens.setLayoutManager(layoutManager);
        // criacao de um decorador, uma linha para separar cada um dos itens
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
                Uri selectedPhotoURI = data.getData();

                try {
                    // uso da função bitmap, herdada do arquivo util.java
                    // essa função guarda a imagem dentro de um bitmap (como um adecsrição da imagem em pixels)
                    // o uso dessa função dispensa o uso do endereço Uri
                    Bitmap photo = Util.getBitmap(MainActivity.this, selectedPhotoURI, 100, 100);
                    myItem.photo = photo;
                } // exceção disparada caso a imagem não seja encontrada
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                // novamente o viewmodel é obtido para que o novo item seja adicionado
                MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
                List<MyItem> itens = vm.getItens();

                // adicao do novo item a arraylist de itens
                itens.add(myItem);

                // notificacao de que um novo item vindo de newitemactivity foi recebido pela main
                // essa notificação precisa ser feita para que o novo item seja mostrado no recycleview
                myAdapter.notifyItemInserted(itens.size()-1);
            }
        }
    }

}