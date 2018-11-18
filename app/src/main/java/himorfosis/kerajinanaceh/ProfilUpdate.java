package himorfosis.kerajinanaceh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfilUpdate extends AppCompatActivity {

    EditText nama, email, password, konfirpassword, telp, kota, provinsi;
    Button update;

    String id, getnama, getemail, getpassword, gettelp, getkota, getprovinsi;
    Integer getid;

    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_update);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView textToolbar = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.toolbartext);
        Button kembali = (Button) getSupportActionBar().getCustomView().findViewById(R.id.kembali);
        textToolbar.setText("Update profil");

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        nama = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        telp = findViewById(R.id.telp);
        kota = findViewById(R.id.kota);
        provinsi = findViewById(R.id.provinsi);
        update = findViewById(R.id.update);

        // get parsing data

        Intent bundle = getIntent();

        id = bundle.getStringExtra("id");
        getnama = bundle.getStringExtra("nama");
        getemail = bundle.getStringExtra("email");
        getpassword = bundle.getStringExtra("pass");
        gettelp = bundle.getStringExtra("telp");
        getkota = bundle.getStringExtra("kota");
        getprovinsi = bundle.getStringExtra("prov");


        nama.setText(getnama);
        email.setText(getemail);
        password.setText(getpassword);
        telp.setText(gettelp);
        kota.setText(getkota);
        provinsi.setText(getprovinsi);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getnama = nama.getText().toString();
                getemail = email.getText().toString();
                getpassword = password.getText().toString();
                gettelp = telp.getText().toString();
                getkota = kota.getText().toString();
                getprovinsi = provinsi.getText().toString();

                if (getnama.equals("null") || getemail.equals("null") || getpassword.equals("null") || gettelp.equals("null") || getkota.equals("null") || getprovinsi.equals("null")) {

                    Toast.makeText(getApplicationContext(), "Harap isi secara lengkap", Toast.LENGTH_SHORT).show();


                } else {

                    updateProfil();


                }

            }
        });

    }

    private void updateProfil() {

        pDialog.setMessage("Update Profil ...");
        showDialog();

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.updateProfil, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //If we are getting success from server

                Log.e("response", " " + response);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(response);

                    Log.e("obj", ""+obj);

                    if (!obj.getBoolean("error")) {

                        Toast.makeText(ProfilUpdate.this, obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //Creating a shared preference

                        SharedPrefManager.deleteLogin("loginEMAIL", getApplicationContext());
                        SharedPrefManager.deleteLogin("loginPASS", getApplicationContext());
                        SharedPrefManager.deleteLogin("EMAIL",  getApplicationContext());
                        SharedPrefManager.deleteLogin("NAMA",  getApplicationContext());
                        SharedPrefManager.deleteLogin("TELP",  getApplicationContext());
                        SharedPrefManager.deleteLogin("KOTA", getApplicationContext());
                        SharedPrefManager.deleteLogin("PROVINSI", getApplicationContext());
                        SharedPrefManager.deleteLogin("ID_PELANGGAN", getApplicationContext());
                        SharedPrefManager.deleteLogin("PASSWORD", getApplicationContext());


                        SharedPrefManager.saveLogin("loginEMAIL", "key", getemail, getApplicationContext());
                        SharedPrefManager.saveLogin("loginPASS", "key", getpassword, getApplicationContext());


                        //Starting profile activity
                        Intent intent = new Intent(ProfilUpdate.this, Aktif.class);
                        startActivity(intent);

                    } else {
                        //If the server response is not success
                        //Displaying an error message on toast
//                        Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                        hideDialog();

                        Intent in = new Intent(ProfilUpdate.this, Aktif.class);
                        startActivity(in);

                        Toast.makeText(ProfilUpdate.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    hideDialog();

                    Intent in = new Intent(ProfilUpdate.this, Aktif.class);
                    startActivity(in);

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want

                        hideDialog();

                        Toast.makeText(ProfilUpdate.this, "Register Gagal", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("id_pelanggan", String.valueOf(getid));
                params.put("nama_pelanggan", getnama);
                params.put("password", getpassword);
                params.put("email", getemail);
                params.put("telp", gettelp);
                params.put("kota", getkota);
                params.put("provinsi", getprovinsi);

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
