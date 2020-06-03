package com.example.carteracliente;

//Aplicaci�n Android que implementa una base de datos SQLite,
//para posteriormente definir un Content Provider para la creaci�n, consulta, actualizaci�n y borrado
//de registros.


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/*Clase CompartirDatos, que hereda de la clase base ContentProvider, encargada de
establecer los mecanismos necesarios para intercambiar informaci�n con el resto de aplicaciones.*/
public class CompartirDatos extends ContentProvider {
	
	private static final String AUTORIDAD = "com.example.carteracliente.CompartirDatos";
	/*Se declara e inicializa una constante de la clase Uri, que recoger� la URI
	que identificar� de manera �nica el Content Provider .*/
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTORIDAD + "/" + EstructuraDatos.TABLE_NAME);
	private static final int CLIENTES = 1;
	private static final int CLIENTES_ID = 2;
	private SQLiteDatabase db;
	BaseDatos datos;
	private static final UriMatcher uriMatcher;
	
	/*Se inicializa la clase UriMatcher, para definir los tipos de URI,
	y poder devolvernos su tipo.*/
	static 
	{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTORIDAD, EstructuraDatos.TABLE_NAME, CLIENTES);
		uriMatcher.addURI(AUTORIDAD, EstructuraDatos.TABLE_NAME + "/#", CLIENTES_ID);
	}
	
	/*Array de Strings, con todos los campos existentes en la tabla Clientes.*/
	public static String[] columnas = new String[]{EstructuraDatos._ID,
											EstructuraDatos.COLUMN_NAME_CLIENTE,
											EstructuraDatos.COLUMN_NAME_TELEFONO, 
											EstructuraDatos.COLUMN_NAME_DESCRIPCION};
	
	/*M�todo que procesar� las solicitudes de eliminaci�n de datos, recibiendo entre sus par�metros 
	la URI del Content Provider, y los argumentos de selecci�n y orden definidos. 
	Devolver� el n�mero de filas afectadas.*/
	@Override
	public int delete(Uri uri, String selection, String[] arg2) {
		int id;
		
		String where = selection;
		if(uriMatcher.match(uri) == CLIENTES_ID)
		{
			/*Se establece la cl�usula WHERE con la condici�n del campo y valor como condici�n de eliminaci�n.*/
			where = EstructuraDatos._ID + "=" + uri.getLastPathSegment();
		}
		db = datos.getReadableDatabase();
		/*Se invoca al m�todo delete(), indicando entre sus argumentos la tabla, y la cl�usula WHERE con la condici�n
		que debe cumplir el registro a eliminar.*/
		id = db.delete(EstructuraDatos.TABLE_NAME, where, arg2);
		
		return id;
	}

	@Override
	public String getType(Uri uri) {
	     return null;
	}

	/*M�todo que posibilita la inserci�n de datos, 
	devolviendo la URI que hace referencia al registro introducido.*/
	@Override
	public Uri insert(Uri uri, ContentValues values) {	
		long regId = 1;		
		SQLiteDatabase db = datos.getWritableDatabase();	 
	    regId = db.insert(EstructuraDatos.TABLE_NAME, null, values);	 
	    Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);	 
	    return newUri;
	}

	/*M�todo d�nde se inicializa la base de datos SQLite utilizada para el Content Provider.*/
	@Override
	public boolean onCreate() {
		datos = new BaseDatos(getContext());
		return true;
	}
	
	
	/*M�todo que permite consultar los datos almacenados. 
	Este m�todo devuelve un objeto Cursor con los datos solicitados al Content Provider.*/
	@Override
	public Cursor query(Uri uri, String[] columnas, String arg2, String[] arg3,
			String arg4) {
		
		datos = new BaseDatos(getContext());
		/*Se establecen permisos de lectura.*/
		db = datos.getReadableDatabase();
		/*Se invoca al m�todo rawQuery() con la consulta Transact-SQL a procesar.*/
		Cursor c = db.rawQuery("select * from NuevosClientes", null);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		return 0;
	}

}
