package himorfosis.kerajinanaceh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class KategoriListAdapter extends ArrayAdapter<String> {

    Context context;
    List<String> list;


    public KategoriListAdapter(Context context, List<String> object ) {

        super(context, R.layout.rowjadwalsekarang, object);
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
            convertView = layoutInflater.inflate(android.R.layout.simple_list_item_1, null);

        }

        String data = list.get(position);

        if (data != null) {

            TextView matkul = convertView.findViewById(android.R.id.text1);

            matkul.setText(data);

        }

        return convertView;

    }

}
