package himorfosis.kerajinanaceh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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

public class UtamaFragBeranda extends Fragment {

    ProgressBar progressBar;
    ProdukAdapter adapter;

    List<ProdukClassData> listproduk = new ArrayList<>();

    GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.utamafragberanda, container, false);

    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();

        gridView = view.findViewById(R.id.gridview);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);

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

                        listproduk.add(item);

                    }

                    adapter = new ProdukAdapter(getContext(), listproduk);

                    gridView.setAdapter(adapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            ProdukClassData data = listproduk.get(position);

                            String datagambar = Koneksi.getImage + data.getGambar_produk();

                            Intent intent = new Intent(getContext(), DetailProduk.class);
                            intent.putExtra("namaproduk", data.getNama_produk());
                            intent.putExtra("gambar", datagambar);
                            intent.putExtra("namausaha", data.getNama_usaha());
                            intent.putExtra("deskripsi", data.getDeskripsi());
                            intent.putExtra("harga", data.getHarga());
                            intent.putExtra("login", "Utama");

                            startActivity(intent);

                            Log.e("post", "" +data.getNama_produk());
                            Log.e("post", "" +data.getGambar_produk());
                            Log.e("post", "" +data.getNama_usaha());

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
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //adding the string request to request queue
        VolleyClass.getInstance().addToRequestQueue(jsonObjectRequest);




    }

}
