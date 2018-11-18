package himorfosis.kerajinanaceh;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PemesananPembayaran extends Fragment {

    String getIdProduk, getidPengrajin, getidPelanggan, getGambar, getNamaProduk, getHarga, getTotalProduk, getidAlamat, getidBank;
    TextView totalbarang, totalbelanja, kurir, ongkir;
    Button btnbayar;
    RadioGroup radioGroup;

    ProgressDialog pDialog;

    DateFormat date;
    Calendar cal;

    Database db;

    public static final String TAG = "data";

    List<String> arrayProduk = new ArrayList<String>();
    List<String> arrayJumlah = new ArrayList<String>();

    // Json data

    JSONObject object;

    String[] namakurir = {"JNE", "TIKI", "POS Indonesia"};
    String[] kurircode = {"jne", "tiki", "pos"};

    String idkurir;
    String postcode;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.pemesananpembayaran, container, false);

    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        final Button kembali = (Button) actionBar.getCustomView().findViewById(R.id.kembali);

        totalbarang = view.findViewById(R.id.totalbarang);
        totalbelanja = view.findViewById(R.id.totalbelanja);
        btnbayar = view.findViewById(R.id.bayar);
        radioGroup = view.findViewById(R.id.radiobutton);
        kurir = view.findViewById(R.id.kurir);
        ongkir = view.findViewById(R.id.ongkir);


        // Progress dialog
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);

        // database

        db = new Database(getContext());


        savedInstanceState = getArguments();
        if (savedInstanceState == null) {

        } else {

            getidAlamat = savedInstanceState.getString("id_alamat");
            postcode = savedInstanceState.getString("postcode");

        }

        getIdProduk = SharedPrefManager.getData("idproduk", "key", getContext());
        getidPengrajin = SharedPrefManager.getData("idpengrajin", "key", getContext());
        getidPelanggan = SharedPrefManager.getData("idpelanggan", "key", getContext());
        getNamaProduk = SharedPrefManager.getData("namaproduk", "key", getContext());
        getGambar = SharedPrefManager.getData("gambar", "key", getContext());
        getHarga = SharedPrefManager.getData("harga", "key", getContext());
        getTotalProduk = SharedPrefManager.getData("totalproduk", "key", getContext());


        Log.e("produk", "" + getIdProduk);
        Log.e("pengrajin", "" + getidPengrajin);
        Log.e("pelanggan", "" + getidPelanggan);
        Log.e("alamat", "" + getidAlamat);
        Log.e("gambar", "" + getGambar);
        Log.e("nama produk", "" + getNamaProduk);
        Log.e("harga", "" + getHarga);
        Log.e("total", "" + getTotalProduk);

        totalbarang.setText(getTotalProduk);
        totalbelanja.setText(getHarga);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton pilih = (RadioButton) view.findViewById(checkedId);

                Toast.makeText(getContext(), pilih.getText().toString(), Toast.LENGTH_SHORT).show();

                getidBank = String.valueOf(checkedId);

                Log.e("id bank", "" + getidBank);

            }
        });

        // mengecek data keranjang di database

        String data[] = null;
        db = new Database(getContext());

        final List<KeranjangClassData> dataList = db.getallKeranjang();
        data = new String[dataList.size()];

        int i = 0;

        for (KeranjangClassData b : dataList) {

            data[i] = b.getId() + b.getIdproduk() + b.getNama_produk() + b.getHarga_produk() + b.getGambar_produk() + b.getJumlah();

            // menyimpan idproduk pada database keranjang kedalam array

            arrayProduk.add(b.getIdproduk());
            arrayJumlah.add(b.getJumlah());

            // cek data barang di keranjang

            Log.e("id barang", "" + b.getIdproduk());
            Log.e("id barang", "" + getIdProduk);

            i++;

        }

        if (data.length == 0) {

            arrayProduk.add(getIdProduk);
            arrayJumlah.add(getTotalProduk);

        }

        Log.e("database", "" + data.length);
        Log.e("database", "" + data);
        Log.e("database", "" + dataList);

        kurir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog ongkirdialog = new AlertDialog.Builder(getContext())

                        .setTitle("Pilih Kurir")
                        .setSingleChoiceItems(namakurir, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                idkurir = kurircode[which];
                                kurir.setText(namakurir[which]);

                            }
                        })

                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })

                        .setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

//                                SharedPref.saveIntPref("alarmnada", "alarm", selectedFont, getContext());
//                                idkurir = kurircode[which];

                                cekongkir();

                                dialog.dismiss();


                            }
                        })


                        .create();
                ongkirdialog.show();

            }
        });

        btnbayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(getContext(), "bayar", Toast.LENGTH_SHORT).show();

                Log.e("idbank", "" + getidBank);

                if (getidBank == null || getidBank.equals("null") || getidBank.equals("") || idkurir.equals("null") || idkurir.equals("")) {

                    Toast.makeText(getContext(), "Harap pilih bank transfer", Toast.LENGTH_SHORT).show();

                } else {

                    try {

                        object = new JSONObject();

                        object.put("id_pelanggan", getidPelanggan);
                        object.put("id_alamat", getidAlamat);
                        object.put("id_bank", getidBank);
                        object.put("total_produk", getTotalProduk);
                        object.put("total_bayar", getHarga);


                        // membuat data json array

                        JSONArray jsonArr = new JSONArray();

                        for (int a = 0; a < arrayProduk.size(); a++) {

                            JSONObject data = new JSONObject();

                            try {

                                data.put("produk", arrayProduk.get(a));
                                data.put("jumlah", arrayJumlah.get(a));
                                jsonArr.put(data);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // menyatukan json object dengan array

                        object.put("detailproduk", jsonArr);

                        // merubah data json object menjadi string

                        String jsondata = object.toString().substring(1, object.toString().length() - 1);

                        Log.e("data", "" + object);

                        // post data to server

                        pemesanan();

                    } catch (JSONException e) {

                        e.printStackTrace();

                    }

                }

            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new PemesananPengiriman();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame, fragment);
                ft.commit();

            }
        });

    }

    private void cekongkir() {

        pDialog.setMessage("Cek Biaya Pengiriman ...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.biayaOngkir, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //If we are getting success from server

                ongkir.setText(response);

                String biayakirim = ongkir.getText().toString();

                int harga = Integer.valueOf(getHarga);
                int ongkir = Integer.valueOf(biayakirim);

                int totalbayar = harga + ongkir;

                getHarga = String.valueOf(totalbayar);

                totalbelanja.setText(getHarga);

                Log.e("Harga", ""+getHarga);

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
                params.put("kab_id", postcode);
                params.put("kurir", idkurir);
                params.put("berat", "500");


                //returning parameter
                return params;
            }
        };

        VolleyClass.getInstance().addToRequestQueue(stringRequest);

    }

    private void hapusDatabaseKeranjang() {

        String data[] = null;
        db = new Database(getContext());

        final List<KeranjangClassData> dataList = db.getallKeranjang();
        data = new String[dataList.size()];

        int i = 0;

        for (KeranjangClassData b : dataList) {

            data[i] = b.getId() + b.getIdproduk() + b.getNama_produk() + b.getHarga_produk() + b.getGambar_produk() + b.getJumlah();

            // cek data barang di keranjang

            Log.e("id barang", "" + b.getIdproduk());
            Log.e("id barang", "" + getIdProduk);

            // menghapus data di keranjang

            db.deleteKeranjang(String.valueOf(b.getId()));

            i++;

        }

    }

//    public void cekHari() {
//
//        cal = Calendar.getInstance();
//
//        date = new SimpleDateFormat("yyyy-MM-dd, h:mm");
//        String formattedDate = date.format(cal.getTime());
//
//
//        Log.e("date time", "" + formattedDate);
//
//    }

    private void pemesanan() {

        pDialog.setMessage("Proses Pembayaran ...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.pemesananTambah,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server

                        Log.e("response", " " + response);

                        hapusDatabaseKeranjang();

                        Intent intent = new Intent(getContext(), Pembayaran.class);
                        intent.putExtra("id_produk", getIdProduk);
                        intent.putExtra("id_pengrajin", getidPengrajin);
                        intent.putExtra("id_pelanggan", getidPelanggan);
                        intent.putExtra("id_bank", getidBank);
                        intent.putExtra("total_produk", getTotalProduk);
                        intent.putExtra("total_bayar", getHarga);
                        startActivity(intent);

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

                // mengirim data json ke server

                String jsondata = "{" + object.toString().substring(1, object.toString().length() - 1) + "}";

                Log.e("data_json", "" + jsondata);

                params.put("data_json", jsondata);

//              params.put("waktu", date.format(cal.getTime()));

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
