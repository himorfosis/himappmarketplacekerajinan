package himorfosis.kerajinanaceh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String id = "id";
    private static final String idproduk = "idproduk";
    private static final String nama_produk = "nama_produk";
    private static final String harga_produk = "harga_produk";
    private static final String gambar_produk = "gambar_produk";
    private static final String jumlah = "jumlah";


    private static final String DatabaseName = "Keranjang";
    private static final int DatabaseVersion = 2;

    public Database(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE tabelkeranjang ( id INTEGER PRIMARY KEY AUTOINCREMENT, idproduk TEXT NOT NULL, nama_produk TEXT NOT NULL, harga_produk TEXT NOT NULL, gambar_produk TEXT NOT NULL, jumlah TEXT NOT NULL ); ");

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS tabelkeranjang");

        onCreate(db);
    }

    public void insertKeranjang(KeranjangClassData classData) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(id, classData.getId());
        cv.put(idproduk, classData.getIdproduk());
        cv.put(nama_produk, classData.getNama_produk());
        cv.put(harga_produk, classData.getHarga_produk());
        cv.put(gambar_produk, classData.getGambar_produk());
        cv.put(jumlah, classData.getJumlah());

        db.insert("tabelkeranjang", null, cv);
        db.close();

    }


    public void deleteKeranjang(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {id};
        ContentValues cv = new ContentValues();

        db.delete("tabelkeranjang", "id=?", args);
        db.close();
    }


    public void updateKeranjang (KeranjangClassData classData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(nama_produk, classData.getNama_produk());
        cv.put(harga_produk, classData.getHarga_produk());
        cv.put(gambar_produk, classData.getGambar_produk());
        cv.put(jumlah, classData.getJumlah());

        db.update("tabelkeranjang", cv, idproduk+ " = ?", new String[]{String.valueOf(classData.getIdproduk())});
        db.close();

    }

    public List<KeranjangClassData> getallKeranjang() {
        List<KeranjangClassData> dataArray = new ArrayList<KeranjangClassData>();
        String query = "SELECT * FROM tabelkeranjang";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {

                KeranjangClassData datalist = new KeranjangClassData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                dataArray.add(datalist);

            } while (cursor.moveToNext());
        }
        return dataArray;
    }

}
