package himorfosis.kerajinanaceh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class OngkirAdapter extends ArrayAdapter<OngkirClassData> {

    Context context;
    List<OngkirClassData> list;

    public OngkirAdapter(Context context, List<OngkirClassData> objects) {

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

        OngkirClassData data = list.get(position);

        if (data != null) {

            daerah.setText(data.getNama());

        }


        return v;

    }

}
