package himorfosis.kerajinanaceh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.List;

public class PemesananPengiriman extends Fragment {

    List<AlamatClassData> listalamat = new ArrayList<>();

    ListView list;
    ProgressBar progressBar;
    TextView kosong;
    LinearLayout bar;

    PemesananListAdapter adapter;
    Button tambahalamat;

    TextView tujuan;


    String getIdProduk, getidPengrajin, getidPelanggan, getGambar, getNamaProduk, getHarga, getTotalProduk;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.pemesananpengiriman, container, false);

    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        Button kembali = (Button) actionBar.getCustomView().findViewById(R.id.kembali);

        list = view.findViewById(R.id.list);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        kosong = (TextView) view.findViewById(R.id.kosong);
        tambahalamat = view.findViewById(R.id.tambah);
        bar = view.findViewById(R.id.bar);
        tujuan = view.findViewById(R.id.tujuan);


        //get Data from SharedPreference

        getIdProduk = SharedPrefManager.getData("idproduk", "key", getContext());
        getidPengrajin = SharedPrefManager.getData("idpengrajin", "key", getContext());
        getidPelanggan = SharedPrefManager.getData("idpelanggan", "key", getContext());
        getNamaProduk = SharedPrefManager.getData("namaproduk", "key", getContext());
        getGambar = SharedPrefManager.getData("gambar", "key", getContext());
        getHarga = SharedPrefManager.getData("harga", "key", getContext());
        getTotalProduk = SharedPrefManager.getData("totalproduk", "key", getContext());


        Log.e("id produk", "" + getIdProduk);
        Log.e("id pengrajin", "" + getidPengrajin);
        Log.e("pelanggan", "" + getidPelanggan);
        Log.e("gambar", "" + getGambar);
        Log.e("nama produk", "" + getNamaProduk);
        Log.e("harga", "" + getHarga);
        Log.e("total", "" + getTotalProduk);


        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        tambahalamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new PemesananIsiPengiriman();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame, fragment);
                ft.commit();

            }
        });

        alamat();

    }

    private void alamat() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Koneksi.alamatApi, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //hiding the progressbar after completion and showing list view
                progressBar.setVisibility(View.GONE);
                list.setVisibility(View.VISIBLE);
                tambahalamat.setVisibility(View.VISIBLE);
                bar.setVisibility(View.VISIBLE);
                tujuan.setVisibility(View.VISIBLE);


                // Showing json data in log monitor
                Log.e("response", "" + response);
                Log.e("get", "" + Request.Method.GET);

                try {

                    //we have the array named hero inside the object
                    //so here we are getting that json array

                    JSONArray jsonArray = response.getJSONArray("alamat");

                    Log.e("json array", "" + jsonArray);

                    //now looping through all the elements of the json array
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //getting the json object of the particular index inside the array
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        //creating a hero object and giving them the values from json object

                        AlamatClassData item = new AlamatClassData();

                        item.setId_alamat(jsonObject.getInt("id_alamat"));
                        item.setId_pelanggan(jsonObject.getInt("id_pelanggan"));
                        item.setNama_penerima(jsonObject.getString("nama_penerima"));
                        item.setTelp(jsonObject.getString("telp"));
                        item.setKode_pos(jsonObject.getString("kode_pos"));
                        item.setProvinsi(jsonObject.getString("provinsi"));
                        item.setKota(jsonObject.getString("kota"));
                        item.setKecamatan(jsonObject.getString("kecamatan"));
                        item.setAlamat_lengkap(jsonObject.getString("alamat_lengkap"));
                        item.setPostcode(jsonObject.getString("post_code"));

                        String idpelanggan = String.valueOf(item.getId_pelanggan());

                        if (getidPelanggan.equals(idpelanggan)) {

                            listalamat.add(item);

                        }

                    }

                    if (listalamat.isEmpty()) {

                        Fragment fragment = new PemesananIsiPengiriman();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.frame, fragment);
                        ft.commit();

                    }

                    adapter = new PemesananListAdapter(getContext(), listalamat);

                    list.setAdapter(adapter);

                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            AlamatClassData item = listalamat.get(position);

                            String idalamat = String.valueOf(item.getId_alamat());

                            Bundle data = new Bundle();

                            data.putString("id_alamat", idalamat);
                            data.putString("postcode", item.getPostcode());

                            Fragment fragment = new PemesananPembayaran();
                            fragment.setArguments(data);

                            getFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();


                        }
                    });

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
                    }
                });

        //adding the string request to request queue
        VolleyClass.getInstance().addToRequestQueue(jsonObjectRequest);

    }
}
