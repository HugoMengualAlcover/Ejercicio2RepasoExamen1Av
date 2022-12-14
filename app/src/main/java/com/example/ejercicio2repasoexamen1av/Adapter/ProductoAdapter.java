package com.example.ejercicio2repasoexamen1av.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejercicio2repasoexamen1av.MainActivity;
import com.example.ejercicio2repasoexamen1av.Modelos.Producto;
import com.example.ejercicio2repasoexamen1av.R;


import java.text.NumberFormat;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoVH> {

    private List<Producto> objects;
    private int resource;
    private Context context;
    private MainActivity mainActivity;
    NumberFormat nf;

    public ProductoAdapter(List<Producto> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
        nf = NumberFormat.getCurrencyInstance();
        mainActivity = (MainActivity) context;
    }

    @NonNull
    @Override
    public ProductoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productoFilaView = LayoutInflater.from(context).inflate(resource, null);
        productoFilaView.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new ProductoVH(productoFilaView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoVH holder, int position) {
        Producto producto = objects.get(position);
        holder.lblNombre.setText(producto.getNombre());
        holder.lblCantidad.setText(String.valueOf(producto.getCantidad()));
        holder.lblPrecio.setText(nf.format(producto.getPrecio()));
        holder.btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete(producto, holder.getAdapterPosition());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProducto(producto,holder.getAdapterPosition()).show();
            }
        });

    }

    private AlertDialog confirmDelete (Producto producto, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Seguro?!");
        builder.setCancelable(false);
        builder.setNegativeButton("Cancelar", null);
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                objects.remove(producto);
                notifyItemRemoved(position);
            }
        });
        return  builder.create();
    }

    private AlertDialog updateProducto(Producto p, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(p.getNombre());
        builder.setCancelable(false);

        View cuerpoAlert = LayoutInflater.from(context).inflate(R.layout.activity_anyadir_alista_compra, null);
        EditText txtNombre = cuerpoAlert.findViewById(R.id.txtNombreCrear);
        txtNombre.setVisibility(View.GONE);

        Button btnCrear = cuerpoAlert.findViewById(R.id.btnAnyadirCrear);
        btnCrear.setVisibility(View.GONE);

        EditText txtCantidad = cuerpoAlert.findViewById(R.id.txtCantidadCrear);
        txtCantidad.setText(String.valueOf(p.getCantidad()));
        EditText txtPrecio =  cuerpoAlert.findViewById(R.id.txtPrecioCrear);
        txtPrecio.setText(String.valueOf(p.getPrecio()));
        builder.setView(cuerpoAlert);

        builder.setNegativeButton("CANCELAR",null);
        builder.setNegativeButton("MODIFICAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!txtCantidad.getText().toString().isEmpty() && !txtPrecio.getText().toString().isEmpty()){
                    p.setCantidad(Integer.parseInt(txtCantidad.getText().toString()));
                    p.setPrecio(Float.parseFloat(txtPrecio.getText().toString()));
                    notifyItemChanged(position);
                    mainActivity.calculaValoresFinales();
                }
            }
        });

        return builder.create();
    }


    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ProductoVH extends RecyclerView.ViewHolder{

        TextView lblNombre;
        TextView lblPrecio;
        TextView lblCantidad;
        Button btnBorrar;

        public ProductoVH(@NonNull View itemView) {
            super(itemView);
            lblNombre = itemView.findViewById(R.id.lblNombreProductoCard);
            lblPrecio = itemView.findViewById(R.id.lblPrecioProductoCard);
            lblCantidad = itemView.findViewById(R.id.lblCantidadProductoCard);
            btnBorrar = itemView.findViewById(R.id.btnBorrarProductoCard);
        }

    }
}
