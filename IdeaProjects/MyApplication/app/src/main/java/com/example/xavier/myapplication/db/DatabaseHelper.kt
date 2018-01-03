package com.example.xavier.myapplication.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.xavier.myapplication.model.Factura
import com.example.xavier.myapplication.model.FacturaView
import org.jetbrains.anko.db.*

class DatabaseHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "LibraryDatabase", null, 8) {
    companion object {
        private var instance: DatabaseHelper? = null

        @Synchronized
        fun Instance(context: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(context.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(Factura.TABLE_NAME, true,
                Factura.COLUMN_ID to INTEGER + PRIMARY_KEY,
                Factura.COLUMN_CODIGO to TEXT,
                Factura.COLUMN_FECHA to TEXT,
                Factura.COLUMN_FLUJO to TEXT,
                Factura.COLUMN_ENTREGA to TEXT)

        db.createTable(FacturaView.TABLE_NAME, true,
                FacturaView.COLUMN_ID to INTEGER + PRIMARY_KEY,
                FacturaView.COLUMN_CODIGO to TEXT,
                FacturaView.COLUMN_FECHA to TEXT,
                FacturaView.COLUMN_FLUJO to TEXT,
                FacturaView.COLUMN_ENTREGA to TEXT,
                FacturaView.COLUMN_PROVEEDOR to TEXT,
                FacturaView.COLUMN_TOTAL to TEXT
                )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Factura.TABLE_NAME, true)

        db.dropTable(FacturaView.TABLE_NAME, true)

        onCreate(db)
    }
}