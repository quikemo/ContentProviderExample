package com.example.carteracliente;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    /*Se declaran los diferentes componentes y clases, que permitan rellenar, enviar y almacenar datos.*/
    private EditText edCliente, edTelefono, edDescripcion;
    private TextView txtTotalRegistros;
    private Cursor c;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Se enlazan los componentes declarados con sus recursos a nivel de layout.*/
        edCliente = (EditText)findViewById(R.id.edCliente);
        edTelefono = (EditText)findViewById(R.id.edTelefono);
        edDescripcion = (EditText)findViewById(R.id.edDescripcion);
        txtTotalRegistros = (TextView)findViewById(R.id.txtTotalRegistros);

        ContentResolver resolver = getContentResolver();
        c = resolver.query(CompartirDatos.CONTENT_URI, CompartirDatos.columnas, null, null, null);

        int totalRegistros = c.getCount();

        txtTotalRegistros.setText(String.valueOf(totalRegistros));
    }

    /*Evento onClick para insertar los datos introducidos en los campos EditText.*/
    public void guardarDatos(View view)
    {
        String cliente = edCliente.getText().toString();
        String telefono = edTelefono.getText().toString();
        String descripcion = edDescripcion.getText().toString();

        if(cliente.equals("") || telefono.equals("") || descripcion.equals(""))
        {
            Toast.makeText(this, "Es necesario que introduzca todos los datos.", Toast.LENGTH_LONG).show();
        }else
        {
			/*Declaramos e inicializamos la clase ContentValues, para almacenar los datos del
			nuevo registro.*/
            ContentValues values = new ContentValues();
            values.put(EstructuraDatos.COLUMN_NAME_CLIENTE, cliente);
            values.put(EstructuraDatos.COLUMN_NAME_TELEFONO, telefono);
            values.put(EstructuraDatos.COLUMN_NAME_DESCRIPCION, descripcion);
			/*Se invoca al m�todo insert(), indicando la URI definida y los valores a
			insertar entre sus argumentos.*/
            getContentResolver().insert(CompartirDatos.CONTENT_URI, values);
            refrescarPantalla();
            Toast.makeText(this, "Se ha almacenado un nuevo cliente: " + cliente, Toast.LENGTH_LONG).show();
            edCliente.setText("");
            edTelefono.setText("");
            edDescripcion.setText("");
        }
    }


    public void refrescarPantalla()
    {
        onCreate(bundle);
    }
}
