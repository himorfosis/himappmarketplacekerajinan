package himorfosis.kerajinanaceh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProdukAdapter extends ArrayAdapter<ProdukClassData> {

//    List produk = new ArrayList<>();
    Context context;
    private Bitmap bitmap;
    List<ProdukClassData> list;


    public ProdukAdapter(Context context, List<ProdukClassData> objects) {

        super(context, R.layout.rowproduk, objects);
        this.context = context;
        list = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {

            LayoutInflater layoutInflater;

            layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.rowproduk, null);

        }

        View v = convertView;

        final ProdukClassData produk = list.get(position);

        if (produk != null) {

            TextView namaproduk = (TextView) v.findViewById(R.id.namaproduk);
            ImageView gambar = (ImageView) v.findViewById(R.id.gambarproduk);
            TextView harga = (TextView) v.findViewById(R.id.harga);
            TextView namausaha = (TextView) v.findViewById(R.id.namausaha);

            namaproduk.setText(produk.getNama_produk());
            harga.setText("Rp " + produk.getHarga());
            namausaha.setText(produk.getNama_usaha());

            String getGambar = produk.getGambar_produk();

            if(getGambar != null) {

                final String urlGambar = Koneksi.getImage + produk.getGambar_produk();

                Log.e("url gambar", "" +urlGambar);

                Picasso.with(getContext()).load(urlGambar).into(gambar);

            } else {

                gambar.setImageResource(R.drawable.notimage);

            }

        }

        return v;

    }
}
