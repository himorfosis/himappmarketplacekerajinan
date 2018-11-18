package himorfosis.kerajinanaceh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AktifFragKeranjang extends Fragment {


    ListView list;
    TextView kosong, totalharga, textharga;
    Button bayar;

    String id_pelanggan;

    Database db;

    KeranjangListAdapter adapter;

    int totalbayar = 0;
    int totalproduk = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.aktifkeranjang, container, false);

    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();

        list = view.findViewById(R.id.list);
        kosong = (TextView) view.findViewById(R.id.kosong);
        bayar = (Button) view.findViewById(R.id.bayar);
        totalharga = (TextView) view.findViewById(R.id.totalharga);
        textharga = view.findViewById(R.id.textharga);

        // mengambil data keranjang di database

        String data[] = null;
        db = new Database(getActivity());

        final List<KeranjangClassData> dataList = db.getallKeranjang();
        data = new String[dataList.size()];

        int i = 0;
        String cekdata[];
        cekdata = new String[dataList.size()];

        ArrayList<String> array = new ArrayList<String>();

        // mengambil data keranjang dari database

        for (KeranjangClassData b : dataList) {

            data[i] = b.getId() + b.getIdproduk() + b.getNama_produk() + b.getHarga_produk() + b.getGambar_produk() + b.getJumlah();
            cekdata[i] = b.getId() +", " + b.getIdproduk() +", " + b.getNama_produk() +", " + b.getHarga_produk() +", " + b.getGambar_produk() +", " + b.getJumlah();

//            array.add(dataList);

            if (b.getHarga_produk().equals("") || b.getHarga_produk() == null) {

                Log.e("data harga", "kosong");

            } else {

//                int bayarbelanja = 0;

                // menghitung total belanja

                int harga = Integer.valueOf(b.getHarga_produk());
                int jumlahproduk = Integer.valueOf(b.getJumlah());

                // menghitung jumlah produk

                totalproduk = jumlahproduk + totalproduk;

                for (int a = 0; a < jumlahproduk; a++) {

                    totalbayar = harga + totalbayar;

                }

            }

            i++;

        }

//        for (int z = 0; z <= dataList.size(); z++) {
//
//            array.add(dataList.get(z));
//
//        }


        Log.e("total bayar", "" +totalbayar);
        Log.e("total produk", "" +totalproduk);
        Log.e("database", "" + Arrays.deepToString(cekdata));
        Log.e("datalist", "" + dataList);



        if (dataList.isEmpty()) {

            kosong.setVisibility(View.VISIBLE);
            kosong.setText("Keranjang kosong");
            bayar.setVisibility(View.INVISIBLE);
            textharga.setVisibility(View.INVISIBLE);
            totalharga.setVisibility(View.INVISIBLE);

        } else {

            // menampilkan total bayar

            totalharga.setText(String.valueOf(totalbayar));

        }

        Log.e("datalist", "" +dataList);


        KeranjangListAdapter adapter = new KeranjangListAdapter(getContext(), dataList, getFragmentManager());

        Collections.reverse(dataList);

        list.setAdapter(adapter);

        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id_pelanggan = SharedPrefManager.getLogin("ID_PELANGGAN", "key", getContext());

                Intent intent = new Intent(getContext(), Pemesanan.class);
                intent.putExtra("dari", "keranjang");
                intent.putExtra("id_pelanggan", id_pelanggan);
                intent.putExtra("harga", String.valueOf(totalbayar));
                intent.putExtra("total_produk", String.valueOf(totalproduk));

                startActivity(intent);

            }
        });

    }
}
