package grupo_rebechi_garcialozano.dam.isi.frsf.lab01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editTextImporte;
    private Integer importeIngresado;
    private Integer plazoIngresado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnHacerPlazoFijo = (Button) findViewById(R.id.btn_hacer_plazo_fijo);
        btnHacerPlazoFijo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatosIngresados();
                if(sonDatosValidos()) {
                    double rendimientoCalculado = calcularRendimiento(importeIngresado, plazoIngresado);
                    btnHacerPlazoFijo.setTextColor(getColor(R.color.colorVerde));
                    btnHacerPlazoFijo.setText(getString(R.string.texto_rendimiento_valido, rendimientoCalculado));
                } else {
                    btnHacerPlazoFijo.setTextColor(getColor(R.color.colorRojo));
                    btnHacerPlazoFijo.setText(getString(R.string.texto_rendimiento_invalido));
                }
            }
        });
    }

    private double calcularRendimiento(Integer capital, Integer plazo) {
        return 0.0;
    }

    private boolean sonDatosValidos() {
        // Hacer otras validaciones de ser necesario
        return importeIngresado >0;
    }

    private void getDatosIngresados() {
        // Obtener mail, CUIT y checkbox de ser necesario
        editTextImporte = (EditText) findViewById(R.id.edittext_importe);
        importeIngresado = Integer.valueOf(editTextImporte.getText().toString());

    }
}
