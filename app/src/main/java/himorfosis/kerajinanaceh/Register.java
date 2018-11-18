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

public class Register extends AppCompatActivity {

    EditText nama, email, password, konfirpassword, telp, kota, provinsi;
    String strnama, stremail, strpassword, strkonfirpassword, strtelp, strkota, strprovinsi;
    Button daftar;

    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView textToolbar = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.toolbartext);
        Button kembali = (Button) getSupportActionBar().getCustomView().findViewById(R.id.kembali);
        textToolbar.setText("Register");

        nama = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        konfirpassword = findViewById(R.id.konfirpassword);
        telp = findViewById(R.id.telp);
        kota = findViewById(R.id.kota);
        provinsi = findViewById(R.id.provinsi);
        daftar = findViewById(R.id.daftar);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strnama = nama.getText().toString();
                stremail = email.getText().toString();
                strpassword = password.getText().toString();
                strkonfirpassword = konfirpassword.getText().toString();
                strtelp = telp.getText().toString();
                strkota = kota.getText().toString();
                strprovinsi = provinsi.getText().toString();

                if (strnama == null || stremail == null || strpassword == null || strkonfirpassword == null || strtelp == null || strkota == null || strprovinsi == null) {

                    Toast.makeText(getApplicationContext(), "Harap isi secara lengkap", Toast.LENGTH_SHORT).show();


                } else {

                    if (strpassword.equals(strkonfirpassword)) {

                        daftarAkun();

                    } else {

                        Toast.makeText(getApplicationContext(), "Konfirmasi password tidak sesuai", Toast.LENGTH_SHORT).show();


                    }

                }


            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Register.this, Utama.class);
                startActivity(in);

            }
        });

    }

    private void daftarAkun() {

        pDialog.setMessage("Register ...");
        showDialog();

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.Register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //If we are getting success from server

                Log.e("response", " " + response);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(response);

                    Log.e("obj", ""+obj);

                    if (!obj.getBoolean("error")) {

                        Log.e("register", "if !eror");


                        Toast.makeText(Register.this, obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //Creating a shared preference

                        SharedPrefManager.saveLogin("loginEMAIL", "key", stremail, getApplicationContext());
                        SharedPrefManager.saveLogin("loginPASS", "key", strpassword, getApplicationContext());


                        //Starting profile activity
                        Intent intent = new Intent(Register.this, Aktif.class);
                        startActivity(intent);

                    } else {
                        //If the server response is not success
                        //Displaying an error message on toast
//                        Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_LONG).show();

                        Toast.makeText(Register.this, obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //Creating a shared preference

                        SharedPrefManager.saveLogin("loginEMAIL", "key", stremail, getApplicationContext());
                        SharedPrefManager.saveLogin("loginPASS", "key", strpassword, getApplicationContext());

                        Log.e("register", "else");

                        hideDialog();

                        Intent in = new Intent(Register.this, Aktif.class);
                        startActivity(in);

                        Toast.makeText(Register.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    Log.e("register", "erorr");

                    hideDialog();

                    Intent in = new Intent(Register.this, Utama.class);
                    startActivity(in);

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want

                        hideDialog();

                        Toast.makeText(Register.this, "Register Gagal", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("nama_pelanggan", strnama);
                params.put("password", strpassword);
                params.put("email", stremail);
                params.put("telp", strtelp);
                params.put("kota", strkota);
                params.put("provinsi", strprovinsi);

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
