package com.example.ejercicio2repasoexamen1av;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.ejercicio2repasoexamen1av.Adapter.ProductoAdapter;
import com.example.ejercicio2repasoexamen1av.Modelos.Producto;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ejercicio2repasoexamen1av.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> launcherCrear;
    private ArrayList<Producto> productosList;

    private ProductoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        inicializarLaunchers();

        View productoView = LayoutInflater.from(this).inflate(R.layout.producto_card, null);

        adapter = new ProductoAdapter(productosList, R.layout.producto_card, this);
        layoutManager = new GridLayoutManager(this, 1);
        binding.contentMain.contenedor.setAdapter(adapter);
        binding.contentMain.contenedor.setLayoutManager(layoutManager);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcherCrear.launch(new Intent(MainActivity.this, AnyadirAListaCompraActivity.class));
            }
        });

        productoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarProducto();
            }
        });


    }


    private AlertDialog editarProducto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Producto");
        builder.setCancelable(false);

        View productoAlertView = LayoutInflater.from(this).inflate(R.layout.editar_producto_alert, null);
        builder.setView(productoAlertView);

        EditText txtNombre = productoAlertView.findViewById(R.id.txtNombreProductoAlert);
        EditText txtPrecio = productoAlertView.findViewById(R.id.txtPrecioProductoAlert);
        EditText txtCantidad = productoAlertView.findViewById(R.id.txtCantidadProductoAlert);

        builder.setNegativeButton("Cancelar", null);
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!txtNombre.getText().toString().isEmpty() && !txtPrecio.getText().toString().isEmpty() &&
                        !txtCantidad.getText().toString().isEmpty()) {
                    Producto producto = new Producto(
                            txtNombre.getText().toString(), Float.parseFloat(txtPrecio.getText().toString()),
                            Integer.parseInt(txtCantidad.getText().toString())
                    );
                    productosList.add(0, producto);
                    adapter.notifyItemInserted(0);
                }else{
                    Toast.makeText(MainActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return builder.create();
    }

    private void inicializarLaunchers() {
        launcherCrear = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        if (result.getData().getExtras() != null) {
                            if (result.getData().getExtras().getSerializable("PRODUCTO") != null) {

                                Producto producto = (Producto) result.getData().getExtras().getSerializable("PRODUCTO");
                                productosList.add(producto);

                            } else {
                                Toast.makeText(MainActivity.this, "NO HAY DATOS", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "NO HAY BUNDLE EN EL INTENT", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "NO HAY INTENT", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "VENTANA CANCELADA", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}