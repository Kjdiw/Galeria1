package nascimento.santos.matheus.lista.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import nascimento.santos.matheus.lista.R;
import nascimento.santos.matheus.lista.model.NewItemActivityViewModel;

public class NewItemActivity extends AppCompatActivity {

    static int PHOTO_PICKER_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class);//pega o view model do new item activity

        Uri selectPhotoLocation = vm.getSelectPhotoLocation();//pega o uri e verifica se é nulo
        if(selectPhotoLocation!=null) {
            ImageView imvPhotoPreview = findViewById(R.id.imvPhotoPreview);
            imvPhotoPreview.setImageURI(selectPhotoLocation);
        }

        ImageButton imgCI = findViewById(R.id.imbCI);
        imgCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT); //cria intent de abrir a galeria
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST); //manda a Intent
            }
        });

        Button btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri photoSelected = vm.getSelectPhotoLocation();

                if (photoSelected == null) {
                    Toast.makeText(NewItemActivity.this, "É necessário selecionar uma imagem!", Toast.LENGTH_SHORT).show();
                    return;
                }
                EditText etTitle = findViewById(R.id.etTitle);
                String title = etTitle.getText().toString();
                if (title.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir um título", Toast.LENGTH_SHORT).show();
                    return;
                }
                EditText etDesc = findViewById(R.id.etDesc);
                String description = etDesc.getText().toString();
                if (description.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir uma descrição", Toast.LENGTH_SHORT).show();
                    return;
                }//confere se o usuário fez tudo certo
                Intent i = new Intent();
                i.setData(photoSelected);
                i.putExtra("title", title);
                i.putExtra("description", description);//guarda a foto, título e descrição na intent para mandar para main activity
                setResult(Activity.RESULT_OK, i);//guarda os resultados
                finish();//termina a tela e manda os resultados
            }
        }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//recebe a intent de ir para outra tela
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTO_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {//verifica se é o seletor de fotos que chamou
                Uri photoSelected = data.getData();//pega uri
                ImageView imvPhotoPreview = findViewById(R.id.imvPhotoPreview);

                imvPhotoPreview.setImageURI(photoSelected);//bota a imagem com a do URI

                NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class);//pega viewmodel
                vm.setSelectPhotoLocation(photoSelected);//guarda no view model o uri
            }


        }
    }
}