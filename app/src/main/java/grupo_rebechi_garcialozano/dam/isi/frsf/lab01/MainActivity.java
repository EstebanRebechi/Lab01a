package grupo_rebechi_garcialozano.dam.isi.frsf.lab01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editTextCorreo;
    private EditText editTextCuit;
    private EditText editTextImporte;
    private SeekBar seekBarCantDias;
    private CheckBox chBoxRenovar;
    private Button btnHacerPlazoFijo;
    private TextView textViewCantDias;
    private TextView textViewRendimiento;
    private TextView textViewRendimientoMensaje;

    private Double importeIngresado = 0.0;
    private Integer plazoIngresado = 1;
    private String correo;
    private String cuit;
    private boolean quiereRenovar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCorreo = (EditText) findViewById(R.id.edittext_correo);
        editTextCuit = (EditText) findViewById(R.id.edittext_cuit);
        editTextImporte = (EditText) findViewById(R.id.edittext_importe);
        seekBarCantDias = (SeekBar) findViewById(R.id.seekbar_cant_dias);
        chBoxRenovar = (CheckBox) findViewById(R.id.chbox_renovar);
        btnHacerPlazoFijo = (Button) findViewById(R.id.btn_hacer_plazo_fijo);
        textViewCantDias = (TextView) findViewById(R.id.textview_cant_dias);
        textViewRendimiento = (TextView) findViewById(R.id.textview_rendimiento);
        textViewRendimientoMensaje = (TextView) findViewById(R.id.textview_rendimiento_msg);

        btnHacerPlazoFijo.setOnClickListener(new hacerPlazoFijoBtnListener());
        seekBarCantDias.setOnSeekBarChangeListener(new plazoSeekBarListener());
        editTextImporte.addTextChangedListener(new editTextTextChangedListener());
    }

    private double calcularRendimiento(Double capital, Integer plazo) {
        double tasa = getTasaInteres(capital, plazo);
        double interes = capital * (Math.pow(1+tasa/100, plazo/360.0) -1);
        double rendimiento = Math.round((capital+interes) * 100.00) / 100.00;
        textViewRendimiento.setText("$" + rendimiento);
        Log.d("Tasa", tasa+"");
        Log.d("Calculo Interes ", Math.pow(1+tasa/100, plazo/360)+"");
        Log.d("Interes", interes+"");
        return rendimiento;
    }

    private double getTasaInteres(Double capital, Integer plazo) {
        double tasa = 0.0;
        if (plazo < 30) {
            if (capital < 5000) tasa = Double.valueOf(getString(R.string.hasta_5000_corto));
            else if (capital < 99999) tasa = Double.valueOf(getString(R.string.de_5000_a_99999_corto)) ;
            else tasa = Double.valueOf(getString(R.string.desde_99999_corto));
        } else {
            if (capital < 5000) tasa = Double.valueOf(getString(R.string.hasta_5000_largo));
            else if (capital < 99999) tasa = Double.valueOf(getString(R.string.de_5000_a_99999_largo)) ;
            else tasa = Double.valueOf(getString(R.string.desde_99999_largo));
        }
        return tasa;
    }

    private boolean sonDatosValidos() {
        return strNotEmpty(correo) && strNotEmpty(cuit) && importeIngresado > 0;
    }

    private boolean strNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    private void getDatosIngresados() {
        correo = editTextCorreo.getText().toString();
        cuit =  editTextCuit.getText().toString();
        getImporteIngresado();
        quiereRenovar = chBoxRenovar.isChecked();
    }

    private void getImporteIngresado() {
        String strImporte = editTextImporte.getText().toString();
        importeIngresado = strNotEmpty(strImporte) ? Double.valueOf(strImporte) : 0.00;
    }

    private class plazoSeekBarListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            plazoIngresado = i;
            textViewCantDias.setText(plazoIngresado + " dias");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            textViewRendimientoMensaje.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            getImporteIngresado();
            if (importeIngresado > 0) calcularRendimiento(importeIngresado, plazoIngresado);
            else textViewRendimiento.setText("$0.00");
        }
    }

    private class editTextTextChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            textViewRendimientoMensaje.setVisibility(View.INVISIBLE);
            getImporteIngresado();
            if(importeIngresado > 0) calcularRendimiento(importeIngresado, plazoIngresado);
            else textViewRendimiento.setText("$0.00");
        }
    }

    private class hacerPlazoFijoBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            getDatosIngresados();
            if (sonDatosValidos()) {
                double rendimientoCalculado = calcularRendimiento(importeIngresado, plazoIngresado);
                textViewRendimientoMensaje.setTextColor(getResources().getColor( R.color.colorVerde));
                textViewRendimientoMensaje.setText(getString(R.string.texto_rendimiento_valido, rendimientoCalculado));
            } else {
                textViewRendimientoMensaje.setTextColor(getResources().getColor(R.color.colorRojo));
                textViewRendimientoMensaje.setText(getString(R.string.texto_rendimiento_invalido));
            }
            textViewRendimientoMensaje.setVisibility(View.VISIBLE);
        }
    }
}
