package santos.pimentel.lista_.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.net.Uri;

import santos.pimentel.lista_.R;

public class NewItemActivity extends AppCompatActivity {

    static int PHOTO_PICKER_REQUEST = 1;
    // uri -> endereco de um dado que esta fora da app, o atributo vai guardar o endereco da imagem selecionada
    Uri photoSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // obtencao do botao que vai abrir a galeria do celular
        ImageButton imgCI = findViewById(R.id.imgCI);
        imgCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // criacao da intent de abertura de documentos
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                // especificacao de que a intent vai procurar elementos do tipo image
                photoPickerIntent.setType("image/*");
                // execucao da intent, o resultado esperado e a imagem escolhida pelo usuario
                startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST);
            }
        });


        Button btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // condicional executada caso nenhuma imagem seja selecionada
                if (photoSelected == null) {
                    // exibicao de uma mensagem de erro
                    Toast.makeText(NewItemActivity.this, "E necessario selecionar uma imagem", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etTitle = findViewById(R.id.etTitle);
                String title = etTitle.getText().toString();

                // condicional executada caso um titulo nao tenha sido fornecido
                if (title.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "E necessario inserir um titulo", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etDesc = findViewById(R.id.etDesc);
                String description = etDesc.getText().toString();

                // condicional acionada caso nao tenha uma descricao
                if (description.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "E necessario inserir uma descricao", Toast.LENGTH_LONG).show();
                    return;
                }

                // como a activity retorna os dados para a outra:
                // criacao de uma intent que guarda as informacoes que vao para a main
                Intent i = new Intent();
                // set dos dados na intent
                i.setData(photoSelected);
                i.putExtra("title", title);
                i.putExtra("description", description);
                // metodo que indica que todos os dados foram setados para serem enviados
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });
    }

    @Override
    // o resquest e o codigo que indica a chamada, o result e o codigo que diz se a newitemactivity retornou dados corretamente, data é o dado retornado
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // identificacao se o request e o mesmo que foi fornecido
        if (requestCode == PHOTO_PICKER_REQUEST) {
            // condicional que ve se houve mesmo o retorno de dados
            if (resultCode == Activity.RESULT_OK) {
                // obtencao do uri da imagem selecionada
                photoSelected = data.getData();
                // variavel que coleta o imageview da activity
                ImageView imgPhotoPreview = findViewById(R.id.imgPhotoPreview);
                // set da imagem selecionada do imageview da pagina
                imgPhotoPreview.setImageURI(photoSelected);
            }
        }
    }
}