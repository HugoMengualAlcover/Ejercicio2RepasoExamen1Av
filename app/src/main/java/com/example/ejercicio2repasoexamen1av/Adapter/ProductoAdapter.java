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

import com.example.ejercicio2repasoexamen1av.Modelos.Producto;
import com.example.ejercicio2repasoexamen1av.R;


import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoVH> {

    private List<Producto> objects;
    private int resource;
    private Context context;

    public ProductoAdapter(List<Producto> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productoFilaView = LayoutInflater.from(context).inflate(resource, null);
        productoFilaView.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoVH holder, int position) {
        Producto producto = objects.get(position);
        holder.lblNombre.setText(producto.getNombre());
        holder.lblCantidad.setText(producto.getCantidad());
        holder.lblPrecio.setText(String.valueOf(producto.getPrecio()));
        holder.btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objects.remove(producto);
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });

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
