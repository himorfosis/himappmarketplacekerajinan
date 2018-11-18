package himorfosis.kerajinanaceh;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class UtamaFragOngkir extends Fragment {

    TextView prov, kab, biayaongkir, tvkurir;
    Button cekongkir;
    EditText berat;

    AlertDialog alertDialog;
    LayoutInflater inflater;

    ProgressDialog pDialog;


    ListView listprov, listkab;
    ProgressBar progressBar;

    List<OngkirClassData> provdata = new ArrayList<>();
    List<KabOngkirClassData> kabdata = new ArrayList<>();

    String idprovinsi, idkabupaten;

    int pilihkurir;

    String[] kurir = {"JNE", "TIKI", "POS Indonesia"};
    String[] kurircode = {"jne", "tiki", "pos"};
    String idkurir;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.utamafragongkir, container, false);

    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();

        prov = view.findViewById(R.id.provinsi);
        kab = view.findViewById(R.id.kabupaten);
        biayaongkir = view.findViewById(R.id.biayaongkir);
        cekongkir = view.findViewById(R.id.cekongkir);
        tvkurir = view.findViewById(R.id.kurir);
        berat = view.findViewById(R.id.berat);

        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);


        prov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builderprov = new AlertDialog.Builder(getContext());
                inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.listisi, null);
                listprov = (ListView) dialogView.findViewById(R.id.list);
                progressBar = dialogView.findViewById(R.id.progress);

                Log.e("prov data", ""+provdata);

                provinsi();

//                if (kabdata == null) {
//
//                    provinsi();
//
//                } else {
//
//                    OngkirAdapter adapter = new OngkirAdapter(getContext(), provdata);
//                    listprov.setAdapter(adapter);
//
//                    listprov.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                            OngkirClassData data = provdata.get(position);
//                            prov.setText(data.getNama());
//                            idprovinsi = data.getId();
//
//                            alertDialog.dismiss();
//
//                        }
//                    });
//
//                }



                builderprov.setTitle("Provinsi tujuan");
                builderprov.setView(dialogView);
                alertDialog = builderprov.create();
                alertDialog.show();

            }
        });

        kab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (idprovinsi.equals("null") || idprovinsi.equals("")) {

                    Toast.makeText(getContext(), "Harap pilih provinsi", Toast.LENGTH_SHORT).show();


                } else {

                    AlertDialog.Builder builderkab = new AlertDialog.Builder(getContext());
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.listisi, null);
                    listkab = (ListView) dialogView.findViewById(R.id.list);
                    progressBar = dialogView.findViewById(R.id.progress);

                    Log.e("kab data", ""+kabdata);

                    kabupaten();


//                    if (kabdata == null) {
//
//                        kabupaten();
//
//                    } else {
//
//                        KabOngkirAdapter adapter = new KabOngkirAdapter(getContext(), kabdata);
//                        listkab.setAdapter(adapter);
//
//                        listprov.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                                KabOngkirClassData data = kabdata.get(position);
//                                prov.setText(data.getKabupaten());
//
//                                idkabupaten = data.getPostcode();
//
//                                alertDialog.dismiss();
//
//                            }
//                        });
//
//                    }


                    builderkab.setTitle("Kabupaten tujuan");
                    builderkab.setView(dialogView);

                    alertDialog = builderkab.create();
                    alertDialog.show();


                }

            }
        });

        tvkurir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog ongkirdialog = new AlertDialog.Builder(getContext())

                        .setTitle("Pilih Kurir")
                        .setSingleChoiceItems(kurir, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

//                                pilihkurir = which;

                                tvkurir.setText(kurir[which]);
                                idkurir = kurircode[which];

//                                ongkirdialog.dismiss();

                            }
                        })

                        .create();
                        ongkirdialog.show();

            }
        });

        cekongkir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cekongkir();

            }
        });

    }

    private void provinsi() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Koneksi.provinsiPenerima, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //hiding the progressbar after completion and showing list view
                progressBar.setVisibility(View.GONE);
                listprov.setVisibility(View.VISIBLE);

                try {

                    //we have the array named hero inside the object
                    //so here we are getting that json array

                    JSONObject rajaongkir = response.getJSONObject("rajaongkir");
                    JSONArray jsonArray = rajaongkir.getJSONArray("results");

                    Log.e("json array", "" + jsonArray);

                    //now looping through all the elements of the json array
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        //creating a hero object and giving them the values from json object
                        OngkirClassData item = new OngkirClassData();

                        item.setId(jsonObject.getString("province_id"));
                        item.setNama(jsonObject.getString("province"));

                        provdata.add(item);

//                        Log.e("id", "" + item.getId());


                    }

                    OngkirAdapter adapter = new OngkirAdapter(getContext(), provdata);
                    listprov.setAdapter(adapter);

                    listprov.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            OngkirClassData data = provdata.get(position);
                            prov.setText(data.getNama());
                            idprovinsi = data.getId();

                            alertDialog.dismiss();

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

    private void kabupaten() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Koneksi.kabupatenPenerima, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //hiding the progressbar after completion and showing list view
                progressBar.setVisibility(View.GONE);
                listkab.setVisibility(View.VISIBLE);

                // Showing json data in log monitor
                Log.e("response", "" + response);
                Log.e("get", "" + Request.Method.GET);

                try {

                    JSONObject rajaongkir = response.getJSONObject("rajaongkir");
                    JSONArray jsonArray = rajaongkir.getJSONArray("results");

                    Log.e("json array", "" + jsonArray);

                    //now looping through all the elements of the json array
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        //creating a hero object and giving them the values from json object
                        KabOngkirClassData item = new KabOngkirClassData();

                        item.setId(jsonObject.getString("province_id"));
                        item.setKabupaten(jsonObject.getString("city_name"));
                        item.setPostcode(jsonObject.getString("postal_code"));

                        if (item.getId().equals(idprovinsi)) {

                            kabdata.add(item);

                        }


                    }

                    KabOngkirAdapter adapter = new KabOngkirAdapter(getContext(), kabdata);
                    listkab.setAdapter(adapter);

                    listkab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            KabOngkirClassData data = kabdata.get(position);
                            kab.setText(data.getKabupaten());

                            idkabupaten = data.getPostcode();

                            alertDialog.dismiss();

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
                        Log.e("error", "" + error);

                    }
                });

        //adding the string request to request queue
        VolleyClass.getInstance().addToRequestQueue(jsonObjectRequest);

    }

    private void cekongkir() {

        pDialog.setMessage("Cek Biaya Pengiriman ...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.biayaOngkir, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //If we are getting success from server

                biayaongkir.setText(response);

                hideDialog();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want

                        hideDialog();

                        Toast.makeText(getContext(), "Gagal", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request

                params.put("asal", "23681");
                params.put("kab_id", idkabupaten);
                params.put("kurir", idkurir);
                params.put("berat", berat.getText().toString());


                //returning parameter
                return params;
            }
        };

        VolleyClass.getInstance().addToRequestQueue(stringRequest);

    }

    private void showDialog() {

        if (!pDialog.isShowing())
            pDialog.show();

    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
