package himorfosis.kerajinanaceh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AktifFragPemesanan extends Fragment {

    List<KeranjangClassData> listpemesanan = new ArrayList<>();

//    JadwalListAdapter adapter;
    ListView list;
    ProgressBar progressBar;
    TextView kosong;

//    ArrayList<String> kategori = new ArrayList<>();
    KeranjangListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.aktifpemesanan, container, false);

    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();

        list = view.findViewById(R.id.list);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        kosong = (TextView) view.findViewById(R.id.kosong);

        progressBar.setVisibility(View.GONE);
        kosong.setVisibility(View.VISIBLE);



    }


}
