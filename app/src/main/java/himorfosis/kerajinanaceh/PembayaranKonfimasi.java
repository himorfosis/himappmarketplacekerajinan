package himorfosis.kerajinanaceh;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.internal.Constants;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.Manifest;

import static android.util.Log.e;

public class PembayaranKonfimasi extends AppCompatActivity {


    String getId, getWaktu, getidPelanggan, getIdAlamat, getidBank, getTotalProduk, getTotalBayar;

    ProgressDialog pDialog;
//    AlertDialog.Builder pDialog;

    Button pilihgambar, uploadgambar;
    ImageView gambar, gambarpilih;

    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;

    DateFormat date;
    Calendar cal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pembayarankonfimasi);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView textToolbar = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.toolbartext);
        Button kembali = (Button) getSupportActionBar().getCustomView().findViewById(R.id.kembali);
        textToolbar.setText("Konfirmasi Pembayaran");

        //Requesting storage permission
        requestStoragePermission();

//         Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        pilihgambar = findViewById(R.id.pilihgambar);
        uploadgambar = findViewById(R.id.uploadgambar);
        gambar = findViewById(R.id.gambar);
        gambarpilih = findViewById(R.id.gambarpilih);

        Intent bundle = getIntent();

        getId = bundle.getStringExtra("id");
        getWaktu = bundle.getStringExtra("waktu");
        getidPelanggan = bundle.getStringExtra("id_pelanggan");
        getidBank = bundle.getStringExtra("id_bank");
        getIdAlamat = bundle.getStringExtra("id_alamat");
        getTotalBayar = bundle.getStringExtra("totalbayar");
        getTotalProduk = bundle.getStringExtra("totalproduk");

        e("konfirmasi", " Pembayaran" );
        e("id", "" +getId);
        e("waktu", "" +getWaktu);
        e("id_pelanggan", "" +getidPelanggan);
        e("nama id_bank", "" +getidBank);
        e("total_bayar", "" +getTotalBayar);
        e("total_produk", "" +getTotalProduk);


        pilihgambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });

        uploadgambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                cekHari();

                pDialog.setMessage("Konfirmasi Pembayaran");
                showDialog();

                pembayaranDiKonfirmasi();

                hideDialog();



            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(PembayaranKonfimasi.this, Aktif.class);
                startActivity(in);

            }
        });


    }

    //Creating a multi part request
//            new MultipartUploadRequest(this, uploadId, Koneksi.konfirmasiPembayaran)
//                    .addFileToUpload(path, "gambar", getId) //Adding file
//                    .addParameter("id", getId) //Adding text parameter to the request
//                    .addParameter("id_produk", getIdProduk) //Adding text parameter to the request
//                    .addParameter("id_pengrajin", getidPengrajin)
//                    .addParameter("id_pelanggan", getidPelanggan)
//                    .addParameter("id_alamat", getIdAlamat)
//                    .addParameter("id_bank", getidBank)
//                    .addParameter("total_produk", getTotalProduk)
//                    .addParameter("total_bayar", getTotalBayar)
//                    .addParameter("status", "2")
//
//                    .setNotificationConfig(new UploadNotificationConfig())
//                    .setMaxRetries(11)
//                    .startUpload();
    //Starting the upload

    /*
     * This is the method responsible for image upload
     * We need the full image path and the name for the image in this method
     * */
    public void pembayaranDiKonfirmasi() {

        //getting the actual path of the image
        String path = getPath(filePath);

        Log.e("path", "" +path);

        //Uploading code
        try {

            String uploadId = UUID.randomUUID().toString();



            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, Koneksi.konfirmasiPembayaran)
                    .addFileToUpload(path, "gambar") //Adding file
                    .addParameter("id_pemesanan", getId) //Adding text parameter to the request
                    .addParameter("id_bank", getidBank)
                    .addParameter("total_bayar", getTotalBayar)
                    .addParameter("total_produk", getTotalProduk)
                    .addParameter("waktu", getWaktu)
                    .addParameter("id_pelanggan", getidPelanggan)
                    .addParameter("id_alamat", getIdAlamat)

                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(8)
                    .startUpload();
            //Starting the upload

            Toast.makeText(PembayaranKonfimasi.this, "Bukti pembayaran berhasil di upload", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(PembayaranKonfimasi.this, Aktif.class);
            startActivity(intent);

//            hideDialog();

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();

            Toast.makeText(PembayaranKonfimasi.this, "Gagal", Toast.LENGTH_SHORT).show();

            hideDialog();


        }
    }


    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Izin diberikan sekarang Anda dapat membaca penyimpanan", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Ups Anda baru saja menolak izin", Toast.LENGTH_LONG).show();
            }
        }

    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            Log.e("file path", "" +filePath);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                gambarpilih.setImageBitmap(bitmap);

                gambar.setVisibility(View.INVISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void showDialog() {

        if (!pDialog.isShowing())
            pDialog.show();

    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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

}
