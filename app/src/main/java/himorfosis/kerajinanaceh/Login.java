package himorfosis.kerajinanaceh;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends AppCompatActivity {


    Button btnLogin;
    EditText email, password;
    ImageView gambarLogin;
    ProgressBar progressBar;

    ProgressDialog pDialog;

    String stremail, strpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView textToolbar = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.toolbartext);
        Button kembali = (Button) getSupportActionBar().getCustomView().findViewById(R.id.kembali);
        textToolbar.setText("Login");

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnLogin = findViewById(R.id.btnLogin);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progress);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().toString().equals("") || password.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "Harap isi secara lengkap", Toast.LENGTH_SHORT).show();

                } else {

                    LoginBoss();

//                        progressBar.setVisibility(View.VISIBLE);
//                        progressBar.setBackgroundColor(Color.WHITE);

                }

            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Login.this, Utama.class);
                startActivity(in);

            }
        });
    }


    private void LoginBoss() {

        stremail = email.getText().toString();
        strpass = password.getText().toString();

        pDialog.setMessage("Login ...");
        showDialog();

        Log.e("str", "" + stremail);
        Log.e("str", "" + strpass);

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.Login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //If we are getting success from server

                Log.e("response", " " + response);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(response);

//                    String res = response;

                    Log.e("obj", ""+obj);

                    if (!obj.getBoolean("error")) {

                        Toast.makeText(Login.this, obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //Creating a shared preference

                        SharedPrefManager.saveLogin("loginEMAIL", "key", stremail, getApplicationContext());
                        SharedPrefManager.saveLogin("loginPASS", "key", strpass, getApplicationContext());


                        //Starting profile activity
                        Intent intent = new Intent(Login.this, Aktif.class);
                        startActivity(intent);

                    } else {
                        //If the server response is not success
                        //Displaying an error message on toast
//                        Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_LONG).show();

                        hideDialog();

                        Toast.makeText(Login.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want

                        hideDialog();

                        Toast.makeText(Login.this, "Login Gagal", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("email", stremail);
                params.put("password", strpass);

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

