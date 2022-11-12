package com.example.ejercicio2repasoexamen1av;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ejercicio2repasoexamen1av.Modelos.Producto;
import com.example.ejercicio2repasoexamen1av.databinding.ActivityAnyadirAlistaCompraBinding;

public class AnyadirAListaCompraActivity extends AppCompatActivity {
    private ActivityAnyadirAlistaCompraBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_alista_compra);

        binding = ActivityAnyadirAlistaCompraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAnyadirCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Producto producto = crearProducto();
                if (producto != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("PRODUCTO", producto);
                    Intent intent = getIntent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(AnyadirAListaCompraActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private Producto crearProducto() {
        if (binding.txtCantidadCrear.getText().toString().isEmpty() ||
                binding.txtPrecioCrear.getText().toString().isEmpty() ||
                binding.txtNombreCrear.getText().toString().isEmpty()) {
            return null;
        }
        return new Producto(binding.txtNombreCrear.getText().toString(),
                Float.parseFloat(binding.txtPrecioCrear.getText().toString()),
                Integer.parseInt(binding.txtCantidadCrear.getText().toString()));
    }
}