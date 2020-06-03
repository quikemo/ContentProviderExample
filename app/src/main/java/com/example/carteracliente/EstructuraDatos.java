package com.example.carteracliente;

//Aplicaci�n Android que implementa una base de datos SQLite,
//para posteriormente definir un Content Provider para la creaci�n, consulta, actualizaci�n y borrado
//de registros.

import android.provider.BaseColumns;

/*Clase EstructuraDatos, que implementa la interfaz BaseColumns, que permite declarar e inicializar
las variables que definen el nombre de la tabla y los campos creados.*/
public class EstructuraDatos implements BaseColumns{
	
	public static final String TABLE_NAME = "NuevosClientes";
    public static final String COLUMN_NAME_CLIENTE = "cliente";
    public static final String COLUMN_NAME_TELEFONO = "telefono";
    public static final String COLUMN_NAME_DESCRIPCION = "descripcion";
}
