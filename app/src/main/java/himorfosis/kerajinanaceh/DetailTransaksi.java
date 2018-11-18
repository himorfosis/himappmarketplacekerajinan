package himorfosis.kerajinanaceh;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailTransaksi extends AppCompatActivity {

    TextView nomortransaksi, pengrajin, totalpembelian, waktutransaksi, nama, alamat,
            kodepos, telp, alamatlengkap;

    String getnomortransaksi, getpengrajin, gettotalpembelian, getwaktu, getnama, getalamat, getkodepos, gettelp, getproduk, getalamatlengkap;

    LinearLayout detailpemesanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailtransaksi);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView toolbartext = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.toolbartext);

        Button kembali = (Button) getSupportActionBar().getCustomView().findViewById(R.id.kembali);
        kembali.setVisibility(View.VISIBLE);

        toolbartext.setText("Detail Pembelian");

        nomortransaksi = findViewById(R.id.id_transaksi);
        pengrajin = findViewById(R.id.pengrajin);
        totalpembelian = findViewById(R.id.total_bayar);
        waktutransaksi = findViewById(R.id.waktu_transaksi);
        nama = findViewById(R.id.nama);
        alamat = findViewById(R.id.alamat);
        kodepos = findViewById(R.id.kodepos);
        telp = findViewById(R.id.telp);
        alamatlengkap = findViewById(R.id.alamatlengkap);

        detailpemesanan = findViewById(R.id.detailpemesanan);

        Intent bundle = getIntent();

        getnomortransaksi = bundle.getStringExtra("id");
        getpengrajin = bundle.getStringExtra("pengrajin");
        gettotalpembelian = bundle.getStringExtra("totalpembelian");
        getwaktu = bundle.getStringExtra("waktu");
        getnama = bundle.getStringExtra("nama");
        getalamat = bundle.getStringExtra("alamat");
        getkodepos = bundle.getStringExtra("kodepos");
        getalamatlengkap = bundle.getStringExtra("alamatlengkap");
        gettelp = bundle.getStringExtra("telp");
        getproduk = bundle.getStringExtra("produk");

        nomortransaksi.setText(getnomortransaksi);
        pengrajin.setText(getpengrajin);
        totalpembelian.setText(gettotalpembelian);
        waktutransaksi.setText(getwaktu);
        nama.setText(getnama);
        alamat.setText(getalamat);
        alamatlengkap.setText(getalamatlengkap);
        kodepos.setText(getkodepos);
        telp.setText(gettelp);


        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(DetailTransaksi.this, Aktif.class);
                startActivity(in);

            }
        });


    }
}
