package himorfosis.kerajinanaceh;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Pemesanan extends AppCompatActivity {

    Fragment fragment;
    FragmentManager fragmentManager;

    String getDari, getIdProduk, getidPengrajin, getidPelanggan, getbank, getGambar, getNamaProduk, getHarga, getTotalProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pemesanan);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView judul = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.toolbartext);
        judul.setText("Pemesanan");

        Intent bundle = getIntent();

        getIdProduk = bundle.getStringExtra("id_produk");
        getidPengrajin = bundle.getStringExtra("id_pengrajin");
        getidPelanggan = bundle.getStringExtra("id_pelanggan");
        getNamaProduk = bundle.getStringExtra("namaproduk");
        getGambar = bundle.getStringExtra("gambar");
        getHarga = bundle.getStringExtra("harga");
        getTotalProduk = bundle.getStringExtra("total_produk");
        getDari = bundle.getStringExtra("dari");

        Log.e("produk", "" +getIdProduk);
        Log.e("pengrajin", "" +getidPengrajin);
        Log.e("pelanggan", "" +getidPelanggan);
        Log.e("gambar", "" +getGambar);
        Log.e("nama produk", "" +getNamaProduk);
        Log.e("harga", "" +getHarga);
        Log.e("total", "" +getTotalProduk);
        Log.e("dari", "" +getDari);

        if (getDari.equals("keranjang")) {

            SharedPrefManager.saveData("idpelanggan", "key", getidPelanggan, getApplicationContext());
            SharedPrefManager.saveData("namaproduk", "key", getNamaProduk, getApplicationContext());
            SharedPrefManager.saveData("harga", "key", getHarga, getApplicationContext());
            SharedPrefManager.saveData("totalproduk", "key", getTotalProduk, getApplicationContext());

            Bundle data = new Bundle();

            fragment = new PemesananPengiriman();
            fragment.setArguments(data);

            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();

        } else if (getDari.equals("aktif")) {

            Bundle data = new Bundle();

            fragment = new PemesananPembayaran();
            fragment.setArguments(data);

            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();


        } else {

            SharedPrefManager.saveData("idpengrajin", "key", getidPengrajin, getApplicationContext());
            SharedPrefManager.saveData("idpelanggan", "key", getidPelanggan, getApplicationContext());
            SharedPrefManager.saveData("gambar", "key", getGambar, getApplicationContext());
            SharedPrefManager.saveData("namaproduk", "key", getNamaProduk, getApplicationContext());
            SharedPrefManager.saveData("harga", "key", getHarga, getApplicationContext());
            SharedPrefManager.saveData("totalproduk", "key", getTotalProduk, getApplicationContext());
            SharedPrefManager.saveData("idproduk", "key", getIdProduk, getApplicationContext());

            Bundle data = new Bundle();

            fragment = new PemesananPengiriman();
            fragment.setArguments(data);

            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();

        }

    }
}
