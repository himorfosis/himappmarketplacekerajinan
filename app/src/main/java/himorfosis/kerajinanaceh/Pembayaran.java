package himorfosis.kerajinanaceh;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Pembayaran extends AppCompatActivity {

    String getIdPemesanan, getWaktu, getidPelanggan, getIdAlamat, getidBank, getHarga, getTotalProduk;

    TextView totalbayar, bank, norekening, txtupload;
    Button konfirmasi;
    View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pembayaran);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView textToolbar = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.toolbartext);
        Button kembali = (Button) getSupportActionBar().getCustomView().findViewById(R.id.kembali);
        textToolbar.setText("Pembayaran");

        totalbayar = findViewById(R.id.totalbayar);
        bank = findViewById(R.id.bank);
        norekening = findViewById(R.id.rekening);
        konfirmasi = findViewById(R.id.konfirmasi);
        view = findViewById(R.id.view);
        txtupload = findViewById(R.id.txtupload);


        Intent bundle = getIntent();

        getIdPemesanan = bundle.getStringExtra("id");
        getWaktu = bundle.getStringExtra("waktu");
        getidPelanggan = bundle.getStringExtra("id_pelanggan");
        getIdAlamat = bundle.getStringExtra("id_alamat");
        getidBank = bundle.getStringExtra("id_bank");
        getHarga = bundle.getStringExtra("total_bayar");
        getTotalProduk = bundle.getStringExtra("total_produk");

        Log.e(" ", " Pembayaran" );
        Log.e("id", "" +getIdPemesanan);
        Log.e("waktu", "" +getWaktu);
        Log.e("id_pelanggan", "" +getidPelanggan);
        Log.e("id_alamat", "" +getIdAlamat);
        Log.e("id_bank", "" +getidBank);
        Log.e("total_bayar", "" +getHarga);
        Log.e("total_produk", "" +getTotalProduk);

        if (getIdPemesanan == null) {

            view.setVisibility(View.INVISIBLE);
            konfirmasi.setVisibility(View.INVISIBLE);
            txtupload.setVisibility(View.INVISIBLE);
        }


        totalbayar.setText("Rp " +getHarga);

        if (getidBank.equals("1")) {

            bank.setText("Bank BRI");
            norekening.setText("1234-5678");


        } else if (getidBank.equals("2")) {

            bank.setText("Bank BNI");
            norekening.setText("9090-8080");


        } else {

            bank.setText("Bank Mandiri");
            norekening.setText("5432-6789");

        }

        konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Pembayaran.this, PembayaranKonfimasi.class);
                intent.putExtra("id", getIdPemesanan);
                intent.putExtra("waktu", getWaktu);
                intent.putExtra("id_pelanggan", getidPelanggan);
                intent.putExtra("id_alamat", getIdAlamat);
                intent.putExtra("id_bank", getidBank);
                intent.putExtra("totalproduk", getTotalProduk);
                intent.putExtra("totalbayar", getHarga);

                startActivity(intent);

            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Pembayaran.this, Aktif.class);
                startActivity(in);

            }
        });

    }
}
