package ec.edu.uce.optativa3.vistas;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import ec.edu.uce.optativa3.modelo.Usuario;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolderData> {

    private List<Usuario> usuarios;
    private Context context;
    private ItemClickListener itemClickListener;

    public DataAdapter(List<Usuario> usuarios, Context context) {
        this.usuarios = usuarios;
        this.context = context;
    }

    @Override
    public ViewHolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_data, parent, false);

        return new ViewHolderData(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderData holder, int position) {
        String datePattern = "dd MMMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);

        String numberPattern = "###,##0.00";
        NumberFormat nf = new DecimalFormat(numberPattern);

        holder.user.setText(usuarios.get(position).getUsuario());
        holder.mail.setText(usuarios.get(position).getCorreo());
        holder.celular.setText(usuarios.get(position).getCelular());
        holder.genero.setText(usuarios.get(position).getGenero());
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolderData extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView user;
        TextView mail;
        TextView celular;
        TextView genero;

        public ViewHolderData(View itemView) {
            super(itemView);


            user = itemView.findViewById(R.id.id_user);
            mail = itemView.findViewById(R.id.id_mail);
            celular = itemView.findViewById(R.id.id_celular);
            genero = itemView.findViewById(R.id.id_genero);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }
    }
}
