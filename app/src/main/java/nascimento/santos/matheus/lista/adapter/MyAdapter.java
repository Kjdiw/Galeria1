package nascimento.santos.matheus.lista.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.zip.Inflater;

import nascimento.santos.matheus.lista.R;
import nascimento.santos.matheus.lista.activity.MainActivity;
import nascimento.santos.matheus.lista.model.MyItem;

public class MyAdapter extends RecyclerView.Adapter {
    MainActivity mainActivity;
    List<MyItem> itens;

    public MyAdapter(MainActivity mainActivity, List<MyItem> itens) {
        this.mainActivity = mainActivity;
        this.itens = itens;//construtor do myadapter
    }
    @NonNull
    @Override
    public int getItemCount() {
        return itens.size();
    }//manda o tamanho da lista para o recycleview

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mainActivity);
        View v = inflater.inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(v);
    }//cria o item com os elementos

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyItem myItem = itens.get(position);//pega o elemento na posição
        View v = holder.itemView;//define uma variável como o elemento construído mas sem itens

        ImageView imvfoto = v.findViewById(R.id.imvfoto);
        imvfoto.setImageURI(myItem.photo);

        TextView tvTitle = v.findViewById(R.id.tvTitle);
        tvTitle.setText(myItem.title);

        TextView tvdesc = v.findViewById(R.id.tvdesc);
        tvdesc.setText(myItem.description);//coloca os elementos
    }
}
