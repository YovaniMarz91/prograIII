package com.example.sales;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Registros extends SQLiteOpenHelper {
    static String nameDB = "db_Usuarios";
    static String tblRegistros ="CREATE TABLE Registros(idUsuario integer primary key autoincrement, nombre text,  apellido text, email text, contraseña text)";

    public Registros(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nameDB, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tblRegistros);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public Cursor mantenimientoProducto(String accion, String[] data) {
        SQLiteDatabase sqLiteDatabaseReadable = getReadableDatabase();
        SQLiteDatabase sqLiteDatabaseWritable = getWritableDatabase();
        Cursor cursor = null;
        switch (accion) {
            case "Consultar":
                cursor = sqLiteDatabaseReadable.rawQuery("SELECT * FROM Registros ORDER BY nombre ASC", null);
                break;

            case "Nuevo":
                sqLiteDatabaseReadable.execSQL("INSERT INTO Registros (nombre,apellido,email,contraseña) VALUES('" + data[1] + "', '" + data[2] + "', '" + data[3] + "', '" + data[4] + "',)");
                break;
        }
        return cursor;
    }
}
