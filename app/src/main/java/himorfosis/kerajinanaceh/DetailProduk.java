package himorfosis.kerajinanaceh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailProduk extends AppCompatActivity {

    TextView namaproduk, harga, namausaha, kota, deskripsi, kategori, datakeranjang;
    ImageView gambar;
    String getId, getproduk, getharga, getusaha, getkota, getdeskripsi, getgambar, getlogin, getdari, getidProduk, getidPengrajin, id_pelanggan, getNamaKategori;
    Button beli, keranjang;

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailproduk);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView textToolbar = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.toolbartext);
        Button kembali = (Button) getSupportActionBar().getCustomView().findViewById(R.id.kembali);
        textToolbar.setText("Detail produk");

        // database

        db = new Database(getApplicationContext());


        namaproduk = (TextView) findViewById(R.id.namaproduk);
        harga = (TextView) findViewById(R.id.harga);
        namausaha = (TextView) findViewById(R.id.namausaha);
        kota = (TextView) findViewById(R.id.kota);
        kategori = (TextView) findViewById(R.id.kategori);
        deskripsi = (TextView) findViewById(R.id.deskripsi);
        gambar = findViewById(R.id.gambar);
        beli = findViewById(R.id.beli);
        keranjang = findViewById(R.id.keranjang);
        datakeranjang = findViewById(R.id.datakeranjang);

        // get parsing data

        Intent bundle = getIntent();

        getId = bundle.getStringExtra("id");
        getidProduk = bundle.getStringExtra("id_produk");
        getidPengrajin = bundle.getStringExtra("id_pengrajin");
        getproduk = bundle.getStringExtra("namaproduk");
        getharga = bundle.getStringExtra("harga");
        getusaha = bundle.getStringExtra("namausaha");
        getdeskripsi = bundle.getStringExtra("deskripsi");
        getgambar = bundle.getStringExtra("gambar");
        getlogin = bundle.getStringExtra("login");
        getNamaKategori = bundle.getStringExtra("nama_kategori");
        id_pelanggan = SharedPrefManager.getLogin("ID_PELANGGAN", "key", getApplicationContext());


        Log.e("produk", "" +getproduk);
        Log.e("id produk", "" +getidProduk);
        Log.e("id pengrajin", "" +getidPengrajin);
        Log.e("id pelanggan", "" +id_pelanggan);
        Log.e("harga", "" +getharga);
        Log.e("gambar", "" +getgambar);
        Log.e("kategori", "" +getNamaKategori);

        // mengecek data keranjang di database

        String data[] = null;
        db = new Database(getApplicationContext());

        final List<KeranjangClassData> dataList = db.getallKeranjang();
        data = new String[dataList.size()];

        int i = 0;

        for (KeranjangClassData b : dataList) {

            data[i] = b.getId() + b.getIdproduk() + b.getNama_produk() + b.getHarga_produk() + b.getGambar_produk() + b.getJumlah();

            // cek data barang di keranjang

            Log.e("id barang", "" + b.getIdproduk());
            Log.e("id barang", "" + getidProduk);

            if (b.getIdproduk().equals(getidProduk)) {

                datakeranjang.setVisibility(View.VISIBLE);
                keranjang.setVisibility(View.INVISIBLE);
            }

            i++;

        }


        namaproduk.setText(getproduk);
        harga.setText("Rp " +getharga);
        deskripsi.setText(getdeskripsi);
        namausaha.setText(getusaha);
        kategori.setText(getNamaKategori);

        Glide.with(getApplicationContext())
                .load(getgambar)
                .into(gambar);

        Glide.with(getApplicationContext()).load(getgambar).into(gambar);

        keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getlogin.equals("Utama")) {

                    Intent intent = new Intent(DetailProduk.this, Login.class);
                    startActivity(intent);

                } else {

                    String jumlah = "1";

                    db.insertKeranjang(new KeranjangClassData(null, getidProduk, getproduk, getharga, getgambar, jumlah));

                    Toast.makeText(getApplicationContext(), "Produk berhasil masuk keranjang", Toast.LENGTH_SHORT).show();

                    Intent in = new Intent(DetailProduk.this, Aktif.class);
                    startActivity(in);

                }

            }
        });

        beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getlogin.equals("Utama")) {

                    Intent in = new Intent(DetailProduk.this, Login.class);
                    startActivity(in);

                } else {

                    Intent intent = new Intent(DetailProduk.this, DetailPemesanan.class);

                    intent.putExtra("id_produk", getidProduk);
                    intent.putExtra("id_pengrajin", getidPengrajin);
                    intent.putExtra("id_pelanggan", id_pelanggan);
                    intent.putExtra("gambar", getgambar);
                    intent.putExtra("namausaha", getusaha);
                    intent.putExtra("namaproduk", getproduk);
                    intent.putExtra("harga", getharga);
                    intent.putExtra("total_produk", "1");
                    startActivity(intent);

                }

            }
        });


        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getlogin.equals("Utama")) {

                    Intent intent = new Intent(DetailProduk.this, Utama.class);
                    startActivity(intent);

                } else {

                    Intent intent = new Intent(DetailProduk.this, Aktif.class);
                    startActivity(intent);

                }

            }
        });

    }


}
