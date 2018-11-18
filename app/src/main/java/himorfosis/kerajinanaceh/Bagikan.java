package himorfosis.kerajinanaceh;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Bagikan extends AppCompatActivity {

    Button beli, jual;

    ListView list;
    ProgressBar progressBar;

    String stridpelanggan;
    int idpelanggan;

    //list adapter
    PembayaranListAdapter adapter;

    List<PemesananClassData> listpembayaran = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bagikan);

        //untuk toolbar

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView toolbartext = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.toolbartext);

        Button kembali = (Button)getSupportActionBar().getCustomView().findViewById(R.id.kembali);
        kembali.setVisibility(View.VISIBLE);

        toolbartext.setText("Bagikan");

        beli = (Button) findViewById(R.id.beli);
        list = (ListView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        stridpelanggan = SharedPrefManager.getLogin("ID_PELANGGAN", "key", getApplicationContext());
        idpelanggan = Integer.valueOf(stridpelanggan);


        beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "beli", Toast.LENGTH_LONG).show();
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Kembali", Toast.LENGTH_SHORT).show();


                Intent in = new Intent(Bagikan.this, Aktif.class);
                startActivity(in);

            }
        });

        getData();


    }

    private void getData() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Koneksi.pemesananApi, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //hiding the progressbar after completion and showing list view
                progressBar.setVisibility(View.GONE);
                list.setVisibility(View.VISIBLE);

                // Showing json data in log monitor
                Log.e("response", "" + response);
                Log.e("get", "" + Request.Method.GET);

                try {

                    //we have the array named hero inside the object
                    //so here we are getting that json array

                    JSONArray jsonArray = response.getJSONArray("pemesanan");

                    Log.e("json array", "" + jsonArray);

                    //now looping through all the elements of the json array
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //getting the json object of the particular index inside the array
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        //creating a hero object and giving them the values from json object
                        PemesananClassData item = new PemesananClassData();

                        item.setId_pemesanan(jsonObject.getString("id_pemesanan"));
                        item.setId_pelanggan(jsonObject.getInt("id_pelanggan"));
                        item.setId_alamat(jsonObject.getInt("id_alamat"));
                        item.setId_bank(jsonObject.getInt("id_bank"));
                        item.setTotal_bayar(jsonObject.getString("total_bayar"));
                        item.setTotal_produk(jsonObject.getString("total_produk"));
                        item.setWaktu(jsonObject.getString("waktu"));
                        item.setStatus(jsonObject.getString("status"));
                        item.setNama_penerima(jsonObject.getString("nama_penerima"));
                        item.setTelp(jsonObject.getString("telp"));
                        item.setKodepos(jsonObject.getString("kode_pos"));
                        item.setProvinsi(jsonObject.getString("provinsi"));
                        item.setKota(jsonObject.getString("kota"));
                        item.setKecamatan(jsonObject.getString("kecamatan"));
                        item.setAlamatlengkap(jsonObject.getString("alamat_lengkap"));

                        Log.e("status", "" + item.getStatus());
                        Log.e("id pelanggan", "" + idpelanggan);
                        Log.e("status", "" + item.getId_pelanggan());

                        if (item.getId_pelanggan() == idpelanggan) {

                            if (item.getStatus().equals("Dikonfirmasi")) {

                                listpembayaran.add(item);

                            }

                        }

                    }


                    adapter = new PembayaranListAdapter(getApplicationContext(), listpembayaran);

                    list.setAdapter(adapter);

                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            PemesananClassData data = listpembayaran.get(position);

                            Intent intent = new Intent(getApplicationContext(), DetailTransaksi.class);

                            String alamat = data.getKecamatan() + ", " + data.getKota() + ", " + data.getProvinsi();

                            intent.putExtra("id", data.getId_pemesanan());
                            intent.putExtra("pengrajin", data.getPengrajin());
                            intent.putExtra("totalpembelian", data.getTotal_bayar());
                            intent.putExtra("waktu", data.getWaktu());
                            intent.putExtra("nama", data.getNama_penerima());
                            intent.putExtra("alamat", alamat);
                            intent.putExtra("alamatlengkap", data.getAlamatlengkap());
                            intent.putExtra("kodepos", data.getKodepos());
                            intent.putExtra("telp", data.getTelp());
                            intent.putExtra("produk", data.getTotal_produk());

                            Log.e("data", ""+data);

                            startActivity(intent);

                        }
                    });

                    // get data and play doa from array datadoa


                } catch (JSONException e) {
                    e.printStackTrace();

                    Log.e("error", "" + e);

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressBar.setVisibility(View.GONE);

                        //displaying the error in toast if occurred
//                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("error", "" + error);


                    }
                });

        //adding the string request to request queue
        VolleyClass.getInstance().addToRequestQueue(jsonObjectRequest);



    }
}
