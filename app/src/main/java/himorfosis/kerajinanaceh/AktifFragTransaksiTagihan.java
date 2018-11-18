package himorfosis.kerajinanaceh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AktifFragTransaksiTagihan extends Fragment {

    List<PemesananClassData> listpemesanan = new ArrayList<>();

    ListView list;
    ProgressBar progressBar;
    TextView kosong;

    PembayaranListAdapter adapter;

    String stridpelanggan;
    int idpelanggan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.aktiffragtransaksilist, container, false);

    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();

        list = view.findViewById(R.id.list);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        kosong = (TextView) view.findViewById(R.id.kosong);

        stridpelanggan = SharedPrefManager.getLogin("ID_PELANGGAN", "key", getContext());
        idpelanggan = Integer.valueOf(stridpelanggan);

        Log.e("id pelanggan", "" +stridpelanggan);

        // mengambil data di database

        getPemesanan();

    }

    private void getPemesanan() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Koneksi.pemesananApi, null
                , new Response.Listener<JSONObject>() {
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

                        if (item.getId_pelanggan() == idpelanggan) {

                            if (item.getStatus().equals("Dipesan")) {

                                listpemesanan.add(item);

                            }

                        }

                    }

                    if (listpemesanan.isEmpty()) {

                        kosong.setVisibility(View.VISIBLE);
                        kosong.setText("Belum ada pemesanan");

                    }

                    adapter = new PembayaranListAdapter(getContext(), listpemesanan);

                    list.setAdapter(adapter);

                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            PemesananClassData data = listpemesanan.get(position);

                            String strbank = String.valueOf(data.getId_bank());
                            String strpelanggan = String.valueOf(data.getId_pelanggan());
                            String stralamat = String.valueOf(data.getId_alamat());

                            Intent intent = new Intent(getContext(), Pembayaran.class);
                            intent.putExtra("id",data.getId_pemesanan());
                            intent.putExtra("id_pelanggan", strpelanggan);
                            intent.putExtra("waktu", data.getWaktu());
                            intent.putExtra("id_alamat", stralamat);
                            intent.putExtra("id_bank", strbank);
                            intent.putExtra("total_bayar", data.getTotal_bayar());
                            intent.putExtra("total_produk", data.getTotal_produk());


                            Log.e("data", ""+data);

                            startActivity(intent);

                        }
                    });

                    // get data and play doa from array datadoa

                } catch (JSONException e) {
                    e.printStackTrace();

                    Log.e("error", "" + e);
                    kosong.setVisibility(View.VISIBLE);
                    kosong.setText("Belum ada pemesanan");

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressBar.setVisibility(View.GONE);

                        //displaying the error in toast if occurred
//                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("error", "" + error.getMessage());

                        kosong.setVisibility(View.VISIBLE);
                        kosong.setText("Belum ada pemesanan");

                    }
                });

        //adding the string request to request queue
        VolleyClass.getInstance().addToRequestQueue(jsonObjectRequest);


    }

}
