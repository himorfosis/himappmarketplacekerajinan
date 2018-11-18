package himorfosis.kerajinanaceh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class KabOngkirAdapter extends ArrayAdapter<KabOngkirClassData> {

    Context context;
    List<KabOngkirClassData> list;

    public KabOngkirAdapter(Context context, List<KabOngkirClassData> objects) {

        super(context, R.layout.rowisi, objects);
        this.context = context;
        this.list = objects;


    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.rowisi, null);


        TextView daerah = (TextView) v.findViewById(R.id.daerah);

        KabOngkirClassData data = list.get(position);

        if (data != null) {

            daerah.setText(data.getKabupaten());

        }


        return v;

    }

}
