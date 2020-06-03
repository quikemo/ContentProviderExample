package com.example.carteracliente;

//Aplicaci�n Android que implementa una base de datos SQLite,
//para posteriormente definir un Content Provider para la creaci�n, consulta, actualizaci�n y borrado
//de registros.


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*Clase BaseDatos, que hereda de la clase base SQLiteOpenHelper, encargada de 
sobrescribir los m�todos para la creaci�n, actualizaci�n y consultas a una Base de Datos.*/
public class BaseDatos extends SQLiteOpenHelper{

	/*Se declara e inicializa una variable encargada de controlar el tipo de dato
	de cada columna de la tabla.*/
	private static final String TEXT_TYPE = " TEXT";
	
	/*Se declara e inicializa una consulta Transact-Sql para la creaci�n 
	de una tabla formada por tres campos o columnas.*/
	private static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + EstructuraDatos.TABLE_NAME + " (" +
	    EstructuraDatos._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	    EstructuraDatos.COLUMN_NAME_CLIENTE + TEXT_TYPE + ", " +
	    EstructuraDatos.COLUMN_NAME_TELEFONO + TEXT_TYPE + ", " +
	    EstructuraDatos.COLUMN_NAME_DESCRIPCION + TEXT_TYPE + ")";

	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + EstructuraDatos.TABLE_NAME;
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Clientes.sqlite";
	public BaseDatos(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/*M�todo que recibe la consulta Transact-SQL para para crear la Tabla.*/
	@Override
	public void onCreate(SQLiteDatabase db) 
	{		
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	/*M�todo que elimina la tabla y vuelve a invocar al m�todo que la crea.*/
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}
}
