package himorfosis.kerajinanaceh;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailPemesanan extends AppCompatActivity {

    TextView namaproduk, jumlahbarang, harga, totalharga ;
    Button beli, tambah, kurang;
    ImageView gambar;

    String getNamaProduk, getHarga,  getGambar, getIdProduk, getidPengrajin, getidPelanggan;

//    getTotalHarga,

    int totalbarang = 1;
    int hargaawal = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailpemesanan);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView toolbartext = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.toolbartext);

        Button kembali = (Button)getSupportActionBar().getCustomView().findViewById(R.id.kembali);
        kembali.setVisibility(View.VISIBLE);


        toolbartext.setText("Keranjang");


        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(DetailPemesanan.this, Aktif.class);
                startActivity(in);

            }
        });


        beli = findViewById(R.id.beli);
        tambah = findViewById(R.id.tambah);
        kurang = findViewById(R.id.kurang);
        totalharga = findViewById(R.id.totalharga);
        harga = findViewById(R.id.harga);
        namaproduk = findViewById(R.id.produk);
        jumlahbarang = findViewById(R.id.jumlahbarang);

        gambar = findViewById(R.id.gambar);


        // get parsing data from detail produk tagihan

        Intent bundle = getIntent();

        getIdProduk = bundle.getStringExtra("id_produk");
        getidPengrajin = bundle.getStringExtra("id_pengrajin");
        getidPelanggan = bundle.getStringExtra("id_pelanggan");
        getNamaProduk = bundle.getStringExtra("namaproduk");
        getGambar = bundle.getStringExtra("gambar");
        getHarga = bundle.getStringExtra("harga");
//        getTotalProduk = bundle.getStringExtra("total_produk");

        Log.e("produk", "" +getIdProduk);
        Log.e("pengrajin", "" +getidPengrajin);
        Log.e("pelanggan", "" +getidPelanggan);
        Log.e("gambar", "" +getGambar);
        Log.e("nama produk", "" +getNamaProduk);
        Log.e("harga", "" +getHarga);


        namaproduk.setText(getNamaProduk);
        harga.setText("Rp " + getHarga);
        jumlahbarang.setText(String.valueOf(totalbarang));

        // menambah harga awal = 0 dengan harga produk

        hargaawal = Integer.valueOf(getHarga) + hargaawal;

        totalharga.setText(getHarga);

        Glide.with(getApplicationContext())
                .load(getGambar)
                .into(gambar);

        Glide.with(getApplicationContext()).load(getGambar).into(gambar);


        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "tambah", Toast.LENGTH_SHORT).show();

                // menambah harga barang

                int bayar = Integer.valueOf(getHarga);

                int totalbayar = bayar + hargaawal;

                hargaawal = totalbayar;

                // menambah jumlah barang

                int tambahbarang = totalbarang + 1;

                totalbarang = tambahbarang;

                Log.e("barang", "" + totalbarang);
                Log.e("harga", "" + hargaawal);

                // menampilkan hasil perhitungan

                jumlahbarang.setText(String.valueOf(totalbarang));

                totalharga.setText(String.valueOf(hargaawal));


            }
        });

        kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Kurang", Toast.LENGTH_SHORT).show();

                if (totalbarang <= 1 ) {

                    Toast.makeText(getApplicationContext(), "Data tidak bisa dikurang", Toast.LENGTH_SHORT).show();

                } else {

                    // mengurang harga barang

                    int bayar = Integer.valueOf(getHarga);

                    Integer totalbayar = bayar - hargaawal;

                    hargaawal = totalbayar;

                    // mengurang jumlah barang

                    int kurangbarang = totalbarang - 1;

                    totalbarang = kurangbarang;

                    Log.e("barang", "" + totalbarang);

                    // menampilkan hasil perhitungan

                    jumlahbarang.setText(String.valueOf(totalbarang));

                    totalharga.setText(String.valueOf(hargaawal));

                }

            }
        });

        beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DetailPemesanan.this, Pemesanan.class);
                intent.putExtra("id_produk", getIdProduk);
                intent.putExtra("id_pengrajin", getidPengrajin);
                intent.putExtra("id_pelanggan", getidPelanggan);
                intent.putExtra("namaproduk", getNamaProduk);
                intent.putExtra("gambar", getGambar);
                intent.putExtra("harga", totalharga.getText().toString());
                intent.putExtra("total_produk", jumlahbarang.getText().toString());
                intent.putExtra("dari", "detailpemesanan");

                startActivity(intent);

            }
        });



    }

}
