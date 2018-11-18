package himorfosis.kerajinanaceh;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailPembayaran extends AppCompatActivity {

    TextView bank, rekening, bayar;
    Button konfirmasi;

    String getbank, getrekening, getbayar, getid, getidpelanggan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailpembayaran);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView toolbartext = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.toolbartext);

        Button kembali = (Button)getSupportActionBar().getCustomView().findViewById(R.id.kembali);
        kembali.setVisibility(View.VISIBLE);

        bank = findViewById(R.id.bayar);
        bayar = findViewById(R.id.bayar);
        rekening = findViewById(R.id.rekening);
        konfirmasi = findViewById(R.id.konfirmasi);

        Intent bundle = getIntent();

        getid = bundle.getStringExtra("id");
        getidpelanggan = bundle.getStringExtra("id_pelanggan");
        getbank = bundle.getStringExtra("id_bank");
        getrekening = bundle.getStringExtra("rekening");
        getbayar = bundle.getStringExtra("total_bayar");

        bank.setText(getbank);
        bayar.setText(getbayar);



        rekening.setText(getrekening);

        konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(DetailPembayaran.this, "Pembayaran", Toast.LENGTH_SHORT).show();

            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


    }
}
