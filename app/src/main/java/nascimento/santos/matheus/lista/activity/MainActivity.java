package nascimento.santos.matheus.lista.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import nascimento.santos.matheus.lista.R;
import nascimento.santos.matheus.lista.adapter.MyAdapter;
import nascimento.santos.matheus.lista.model.MainActivityViewModel;
import nascimento.santos.matheus.lista.model.MyItem;
import nascimento.santos.matheus.lista.model.Util;

public class MainActivity extends AppCompatActivity {

    MyAdapter myAdapter;
    static int NEW_ITEM_REQUEST =1;
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_ITEM_REQUEST) {
            if(resultCode == Activity.RESULT_OK) {//vê se tá tudo certo
                MyItem myItem = new MyItem();//cria a lista myitem
                myItem.title = data.getStringExtra("title");
                myItem.description = data.getStringExtra("description");
                Uri selectedPhotoBitmap = data.getData();//coloca as coisas na lista



                try {
                    Bitmap photo = Util.getBitmap(MainActivity.this, selectedPhotoBitmap, 100, 100);
                    myItem.photo = photo;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class); //pega o view model
                List<MyItem> itens = vm.getItens(); //pega os itens desse view model
                itens.add(myItem);//guarda em uma lista no main view model
                myAdapter.notifyItemInserted(itens.size()-1);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem);
        fabAddItem.setOnClickListener(new View.OnClickListener() { //registra quando clica no botão
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NewItemActivity.class);
                startActivityForResult(i, NEW_ITEM_REQUEST);//realiza a intent da NewItemActivity
            }
        });

        RecyclerView rvItens = findViewById(R.id.rvItens);//pega a lista

        MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        List<MyItem> itens = vm.getItens();

        myAdapter = new MyAdapter(this, itens);
        rvItens.setAdapter(myAdapter);//seta o adaptador na lista para que ela saiba como adicionar itens

        rvItens.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvItens.setLayoutManager(layoutManager);//seta a forma a lista vai estar disposta
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(), DividerItemDecoration.VERTICAL);
        rvItens.addItemDecoration(dividerItemDecoration);//separa os itens
    }
}