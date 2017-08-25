package grupo_rebechi_garcialozano.dam.isi.frsf.lab01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText importeIngresado;
    private Integer importeIngresadoInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button boton = (Button) findViewById(R.id.boton_hacer_plazo_fijo);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatosIngresados();
                if(sonDatosValidos()) {
                    boton.setTextColor(getColor(R.color.colorVerde));
                    boton.setText(getString(R.string.texto_rendimiento_valido, rendimiento));
                } else {
                    boton.setTextColor(getColor(R.color.colorRojo));
                    boton.setText(getString(R.string.texto_rendimiento_invalido));
                }
            }
        });
    }

    private boolean sonDatosValidos() {
        // Hacer otras validaciones de ser necesario
        return importeIngresadoInt>0;
    }

    private void getDatosIngresados() {
        // Obtener mail, CUIT y checkbox de ser necesario
        importeIngresado = (EditText) findViewById(R.id.importe_ingreso);
        importeIngresadoInt = Integer.valueOf(importeIngresado.getText().toString());

    }
}
