package himorfosis.kerajinanaceh;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Aktif extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment;
    FragmentTransaction ft;

    TextView nama, email;

    String getIdPelanggan, getDataEmail, getDataNama, getDataTelp, getDataKota, getDataProv, getDataPass;
    String getEmailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aktif);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

//        ImageView gambar = (ImageView) headerView.findViewById(R.id.gambar);
        nama = (TextView) headerView.findViewById(R.id.nama);
//        email = (TextView) headerView.findViewById(R.id.email);

        getDataEmail = SharedPrefManager.getLogin("EMAIL", "key", getApplicationContext());
        getEmailUser = SharedPrefManager.getLogin("loginEMAIL", "key", getApplicationContext());

        Log.e("email user", "" +getDataEmail);
        Log.e("loginEMAIL", "" +getEmailUser);

        if (getDataEmail == null || getDataEmail.equals("")) {

            getUserData();

        } else {

            getDataEmail = SharedPrefManager.getLogin("EMAIL", "key", getApplicationContext());
            getDataNama = SharedPrefManager.getLogin("NAMA", "key", getApplicationContext());
            getDataTelp = SharedPrefManager.getLogin("TELP", "key", getApplicationContext());
            getDataKota = SharedPrefManager.getLogin("KOTA", "key", getApplicationContext());
            getDataProv = SharedPrefManager.getLogin("PROVINSI", "key", getApplicationContext());
            String idpelanggan = SharedPrefManager.getLogin("ID_PELANGGAN", "key", getApplicationContext());

            Log.e("id pelanggan", "" +idpelanggan);
            Log.e("nama", "" +getDataNama);

            nama.setText(getDataNama);
//            email.setText(getDataEmail);

        }


        pilihFragment(R.id.beranda);

        cekData();

    }

    private void cekData() {

        String getIdProduk = SharedPrefManager.getData("idproduk", "key", getApplicationContext());

        if (getIdProduk == null || getIdProduk.equals("")) {

            Log.e("data ", "kosong" );

        } else {

            SharedPrefManager.deleteData("idproduk", getApplicationContext());
            SharedPrefManager.deleteData("idpengrajin", getApplicationContext());
            SharedPrefManager.deleteData("idpelanggan", getApplicationContext());
            SharedPrefManager.deleteData("gambar", getApplicationContext());
            SharedPrefManager.deleteData("namaproduk", getApplicationContext());
            SharedPrefManager.deleteData("harga", getApplicationContext());

            Log.e("data ", "terhapus" );

        }

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
//                doubleBackToExitPressedOnce=false;

            }
        }, 2000);

        finishAffinity();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.aktif, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void pilihFragment (int itemId) {

        fragment = null;

        switch (itemId) {

            case R.id.beranda:
                beranda();
                break;

            case R.id.keranjang:
                keranjang();
                break;

            case R.id.transaksi:
                transaksi();
                break;

            case R.id.profil:
                profil();
                break;

            case R.id.ongkir:
                ongkir();
                break;

            case R.id.logout:
                logout();
                break;


        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        pilihFragment(item.getItemId());

        return true;

    }

    private void beranda() {

        fragment = new AktifFragBeranda();
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        Log.e("kelas", " log");
        ft.commit();

    }

    private void profil() {

        fragment = new AktifFragProfil();
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        Log.e("kelas", " log");
        ft.commit();

    }

    private void keranjang() {

        fragment = new AktifFragKeranjang();
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        Log.e("kelas", " log");
        ft.commit();

    }


    private void transaksi() {

        fragment = new AktifFragTransaksi();
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        Log.e("kelas", " log");
        ft.commit();

    }

    private void ongkir() {


        fragment = new UtamaFragOngkir();
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        Log.e("kelas", " log");
        ft.commit();


    }

    private void logout() {

        SharedPrefManager.deleteLogin("loginEMAIL", getApplicationContext());
        SharedPrefManager.deleteLogin("loginPASS", getApplicationContext());
        SharedPrefManager.deleteLogin("EMAIL",  getApplicationContext());
        SharedPrefManager.deleteLogin("NAMA",  getApplicationContext());
        SharedPrefManager.deleteLogin("TELP",  getApplicationContext());
        SharedPrefManager.deleteLogin("KOTA", getApplicationContext());
        SharedPrefManager.deleteLogin("PROVINSI", getApplicationContext());
        SharedPrefManager.deleteLogin("ID_PELANGGAN", getApplicationContext());
        SharedPrefManager.deleteLogin("PASSWORD", getApplicationContext());

        finish();
        startActivity(new Intent(Aktif.this, Utama.class));

    }

    private void getUserData() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Koneksi.Pelanggan, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //hiding the progressbar after completion and showing list view
//                progressBar.setVisibility(View.GONE);

                // Showing json data in log monitor
                Log.e("response", "" + response);

                try {

                    //we have the array named hero inside the object
                    //so here we are getting that json array

                    JSONArray jsonArray = response.getJSONArray("pelanggan");

                    //now looping through all the elements of the json array
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //getting the json object of the particular index inside the array
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        //creating a hero object and giving them the values from json object
                        PelangganClassData item = new PelangganClassData();

                        item.setId(jsonObject.getInt("id_pelanggan"));
                        item.setNama(jsonObject.getString("nama_pelanggan"));
                        item.setEmail(jsonObject.getString("email"));
                        item.setTelp(jsonObject.getString("telp"));
                        item.setKota(jsonObject.getString("kota"));
                        item.setProvinsi(jsonObject.getString("provinsi"));
                        item.setPassword(jsonObject.getString("password"));

                        Log.e("email", "" +item.getEmail());
                        Log.e("login email", "" +getEmailUser);

                        if(getEmailUser.equals(item.getEmail())) {

                            getIdPelanggan = String.valueOf(item.getId());
                            getDataEmail = item.getEmail();
                            getDataNama = item.getNama();
                            getDataTelp = item.getTelp();
                            getDataKota = item.getKota();
                            getDataProv = item.getProvinsi();
                            getDataPass = item.getPassword();

                            SharedPrefManager.saveLogin("ID_PELANGGAN", "key", getIdPelanggan, getApplicationContext());
                            SharedPrefManager.saveLogin("EMAIL", "key", getDataEmail, getApplicationContext());
                            SharedPrefManager.saveLogin("NAMA", "key", getDataNama, getApplicationContext());
                            SharedPrefManager.saveLogin("TELP", "key", getDataTelp, getApplicationContext());
                            SharedPrefManager.saveLogin("KOTA", "key", getDataKota, getApplicationContext());
                            SharedPrefManager.saveLogin("PROVINSI", "key", getDataProv, getApplicationContext());
                            SharedPrefManager.saveLogin("PASSWORD", "key", getDataPass, getApplicationContext());

                            Log.e("id pelanggan", "" +getIdPelanggan);
                            Log.e("email", "" +getDataEmail);
                            Log.e("nama", "" +getDataNama);

                            nama.setText(getDataNama);
//                            email.setText(getDataEmail);

                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                    Log.e("error", "" + e);

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

//                        progressBar.setVisibility(View.GONE);
                        //displaying the error in toast if occurred
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //adding the string request to request queue
        VolleyClass.getInstance().addToRequestQueue(jsonObjectRequest);

    }
}
