package himorfosis.kerajinanaceh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PemesananListAdapter extends ArrayAdapter<AlamatClassData> {

    Context context;
    List<AlamatClassData> list;


    public PemesananListAdapter(Context context, List<AlamatClassData> object ) {

        super(context, R.layout.rowalamat, object);
        this.context = context;
        this.list = object;

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent) {


        if (convertView == null) {

            LayoutInflater layoutInflater;

            layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.rowalamat, null);

        }

        AlamatClassData data = list.get(position);

        if (data != null) {

            TextView nama = convertView.findViewById(R.id.nama);
            TextView telp = convertView.findViewById(R.id.telp);
            TextView alamat = convertView.findViewById(R.id.alamat);
            TextView lengkap = convertView.findViewById(R.id.lengkap);
            TextView kodepos = convertView.findViewById(R.id.kodepos);

            nama.setText(data.getNama_penerima());
            telp.setText(data.getTelp());
            alamat.setText(data.getKecamatan() + ", " + data.getKota() + ", " + data.getProvinsi());
            lengkap.setText(data.getTelp());
            kodepos.setText(data.getTelp());

        }

        return convertView;

    }

}
