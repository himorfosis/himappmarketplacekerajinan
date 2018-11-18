package himorfosis.kerajinanaceh;

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

import java.util.ArrayList;
import java.util.List;

public class DetailPemesananData  extends Fragment {

    DetailPemesananListAdapter adapter;
    List<AlamatClassData> listalamat= new ArrayList<>();



    ListView list;
    TextView kosong;
    Button tambah;
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.detailpemesanandata, container, false);

    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();

        tambah = view.findViewById(R.id.tambah);
        list = view.findViewById(R.id.list);
        kosong = view.findViewById(R.id.kosong);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);

        getAlamat();

    }

    private void getAlamat() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Koneksi.alamatApi, null, new Response.Listener<JSONObject>() {
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

                    JSONArray jsonArray = response.getJSONArray("keranjang");

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
                        item.setAlamat_lengkap(jsonObject.getString("alamat_lengkap"));
                        item.setProvinsi(jsonObject.getString("provinsi"));
                        item.setKecamatan(jsonObject.getString("kecamatan"));
                        item.setKode_pos(jsonObject.getString("kode_pos"));
                        item.setKota(jsonObject.getString("kota"));
                        item.setTelp(jsonObject.getString("telp"));

                        listalamat.add(item);

                    }

                    if (listalamat.isEmpty()) {

                        kosong.setVisibility(View.VISIBLE);
                        kosong.setText("Keranjang kosong");
                    }

                    adapter = new DetailPemesananListAdapter(getContext(), listalamat);

                    list.setAdapter(adapter);


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
