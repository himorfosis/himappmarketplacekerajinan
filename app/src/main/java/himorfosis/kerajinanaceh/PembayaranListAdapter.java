package himorfosis.kerajinanaceh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PembayaranListAdapter extends ArrayAdapter<PemesananClassData> {

    Context context;
    private Bitmap bitmap;
    List<PemesananClassData> list;


    public PembayaranListAdapter(Context context, List<PemesananClassData> objects) {

        super(context, R.layout.rowtagihan, objects);
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
            convertView = layoutInflater.inflate(R.layout.rowtagihan, null);

        }

        View v = convertView;

        final PemesananClassData produk = list.get(position);

        if (produk != null) {

            TextView tanggal = (TextView) v.findViewById(R.id.tanggal);
            TextView barang = (TextView) v.findViewById(R.id.barang);
            TextView pembayaran = (TextView) v.findViewById(R.id.pembayaran);

//            DateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd, h:mm");

            Log.e("date", "" +produk.getWaktu());
//            Log.e("date", "" +dateFormat.format(produk.getWaktu()));

            try {

            tanggal.setText(produk.getWaktu());

            }
            catch (Exception ex ){

                Log.e("error", "" +ex);

            }

            barang.setText(produk.getTotal_produk() + " barang");
            pembayaran.setText("Rp " + produk.getTotal_bayar());

        }

        return v;

    }

}
