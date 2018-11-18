package himorfosis.kerajinanaceh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AktifFragProfil extends Fragment {

    TextView nama, email, telp, kota, provinsi;

    String getId, getNama, getEmail, getTelp, getKota, getProv, getPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.aktifprofil, container, false);

    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();



        nama = view.findViewById(R.id.namauser);
        email = view.findViewById(R.id.emailuser);
        telp = view.findViewById(R.id.telpuser);
        kota = view.findViewById(R.id.kotauser);
        provinsi = view.findViewById(R.id.provuser);
        Button update = view.findViewById(R.id.update);

        getId = SharedPrefManager.getLogin("ID_PELANGGAN", "key", getContext());
        getEmail = SharedPrefManager.getLogin("EMAIL", "key", getContext());
        getNama = SharedPrefManager.getLogin("NAMA", "key", getContext());
        getTelp = SharedPrefManager.getLogin("TELP", "key", getContext());
        getKota = SharedPrefManager.getLogin("KOTA", "key", getContext());
        getProv = SharedPrefManager.getLogin("PROVINSI", "key", getContext());
        getPass = SharedPrefManager.getLogin("PASSWORD", "key", getContext());

        Log.e("id", ""+getId);
        Log.e("id", ""+getEmail);
        Log.e("id", ""+getNama);
        Log.e("id", ""+getTelp);
        Log.e("id", ""+getKota);
        Log.e("id", ""+getProv);
        Log.e("id", ""+getPass);

        nama.setText(getNama);
        email.setText(getEmail);
        telp.setText(getTelp);
        kota.setText(getKota);
        provinsi.setText(getProv);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), ProfilUpdate.class);
                intent.putExtra("id", getId);
                intent.putExtra("nama", getNama);
                intent.putExtra("email", getEmail);
                intent.putExtra("pass", getPass);
                intent.putExtra("telp", getTelp);
                intent.putExtra("kota", getKota);
                intent.putExtra("prov", getProv);
                startActivity(intent);



            }
        });

    }

}
