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

import java.util.List;

public class DetailPemesananListAdapter extends ArrayAdapter<AlamatClassData> {

    Context context;
    private Bitmap bitmap;
    List<AlamatClassData> list;


    public DetailPemesananListAdapter(Context context, List<AlamatClassData> objects) {

        super(context, R.layout.rowalamat, objects);
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
            convertView = layoutInflater.inflate(R.layout.rowalamat, null);

        }

        View v = convertView;

        final AlamatClassData data = list.get(position);

        if (data != null) {

            TextView nama = (TextView) v.findViewById(R.id.nama);
            TextView telp = (TextView) v.findViewById(R.id.telp);
            TextView alamat = (TextView) v.findViewById(R.id.alamat);
            TextView alamatlengkap = (TextView) v.findViewById(R.id.lengkap);
            TextView kodepos = (TextView) v.findViewById(R.id.kodepos);

            nama.setText(data.getNama_penerima());
            telp.setText(data.getTelp());
            alamat.setText(data.getKecamatan() + ", " + data.getKota()+ ", " + data.getProvinsi());
            alamatlengkap.setText(data.getAlamat_lengkap());
            kodepos.setText(data.getKode_pos());


        }

        return v;

    }



}
