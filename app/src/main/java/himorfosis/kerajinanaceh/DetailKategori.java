package himorfosis.kerajinanaceh;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
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

public class DetailKategori extends AppCompatActivity {

    ProgressBar progressBar;
    ProdukAdapter adapter;
    TextView kosong;

    List<ProdukClassData> listproduk = new ArrayList<>();

    GridView gridView;

    String getkategori;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailkategori);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        gridView = findViewById(R.id.gridview);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        kosong = findViewById(R.id.kosong);

        TextView textToolbar = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.toolbartext);
        Button kembali = (Button) getSupportActionBar().getCustomView().findViewById(R.id.kembali);

        textToolbar.setText("Kategori");

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        Intent bundle = getIntent();

        getkategori = bundle.getStringExtra("data");

        getProduk();

    }

    private void getProduk() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Koneksi.ProdukApi, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //hiding the progressbar after completion and showing list view
                progressBar.setVisibility(View.GONE);
                gridView.setVisibility(View.VISIBLE);

                // Showing json data in log monitor
                Log.e("response", "" + response);
                Log.e("get", "" + Request.Method.GET);

                int cekData = 0;

                try {

                    //we have the array named hero inside the object
                    //so here we are getting that json array

                    JSONArray jsonArray = response.getJSONArray("produk");

                    Log.e("json array", "" + jsonArray);

                    //now looping through all the elements of the json array
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //getting the json object of the particular index inside the array
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        //creating a hero object and giving them the values from json object
                        ProdukClassData item = new ProdukClassData();

                        item.setGambar_produk(jsonObject.getString("gambar_produk"));
                        item.setNama_produk(jsonObject.getString("nama_produk"));
                        item.setDeskripsi(jsonObject.getString("des_produk"));
                        item.setHarga(jsonObject.getString("harga_produk"));
                        item.setDeskripsi(jsonObject.getString("des_produk"));

                        if (getkategori.equals("Batik")) {

                            listproduk.add(item);

                        } else {

//                            listproduk.add(item);
                            kosong.setVisibility(View.VISIBLE);

                        }

                    }

                    adapter = new ProdukAdapter(DetailKategori.this, listproduk);

                    gridView.setAdapter(adapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            ProdukClassData data = listproduk.get(position);

                            String datagambar = Koneksi.getImage + data.getGambar_produk();

                            Intent intent = new Intent(getApplicationContext(), DetailProduk.class);

                            intent.putExtra("id", data.getId_produk());
                            intent.putExtra("namaproduk", data.getNama_produk());
                            intent.putExtra("gambar", datagambar);
                            intent.putExtra("namausaha", data.getNama_usaha());
                            intent.putExtra("deskripsi", data.getDeskripsi());
                            intent.putExtra("harga", data.getHarga());

                            Log.e("post", "" +data.getNama_produk());
                            Log.e("post", "" +data.getGambar_produk());
                            Log.e("post", "" +data.getNama_usaha());

                            startActivity(intent);

                        }
                    });

                    // get data and play doa from array datadoa

                    Log.e("cek data", "" + cekData);


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
                        Toast.makeText(DetailKategori.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //adding the string request to request queue
        VolleyClass.getInstance().addToRequestQueue(jsonObjectRequest);

    }
}
