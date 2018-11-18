package himorfosis.kerajinanaceh;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class KeranjangListAdapter extends BaseAdapter {

    Context context;
    private Bitmap bitmap;
    List<KeranjangClassData> list;
    ArrayList<KeranjangClassData> arraylist;
    LayoutInflater inflater;
    Database db;
    FragmentManager fragmentManager;


    public KeranjangListAdapter(Context context, List<KeranjangClassData> objects, FragmentManager fragmentManager) {

//        super(context, R.layout.rowpemesanan, objects);
        this.context = context;
        this.list = objects;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<KeranjangClassData>();
        this.arraylist.addAll(list);
        this.fragmentManager = fragmentManager;

    }

    public class ViewHolder {
        LinearLayout hapus;
        Button tambah;
        Button kurang;
        TextView jumlahproduk;
        TextView namaproduk;
        ImageView gambar;
        TextView harga;


    }

    @Override
    public  int getCount() {
        return  list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        final KeranjangClassData produk = (KeranjangClassData) getItem(position);

        db = new Database(context);

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.rowkeranjang, null);

            holder.hapus = convertView.findViewById(R.id.hapus);
            holder.tambah = convertView.findViewById(R.id.tambah);
            holder.kurang = convertView.findViewById(R.id.kurang);
            holder.namaproduk = (TextView) convertView.findViewById(R.id.namaproduk);
            holder.gambar = (ImageView) convertView.findViewById(R.id.gambarproduk);
            holder.harga = (TextView) convertView.findViewById(R.id.hargaproduk);
            holder.jumlahproduk = (TextView) convertView.findViewById(R.id.jumlah);

            holder.tambah.getContext();
            holder.kurang.getContext();
            holder.hapus.getContext();

            holder.tambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "tambah", Toast.LENGTH_SHORT).show();


                    int jumlahbarang = 1;

                    int barang = Integer.valueOf(produk.getJumlah());

                    int totalbarang = jumlahbarang + barang;

                    Log.e("barang", "" + totalbarang);
//                    Log.e("harga", "" + hargaawal);

                    // menampilkan hasil perhitungan


                    String strtotalbarang = String.valueOf(totalbarang);

                    db.updateKeranjang(new KeranjangClassData(produk.getId(), produk.getIdproduk(), produk.getNama_produk(), produk.getHarga_produk(), produk.getGambar_produk(), strtotalbarang) );

                    Fragment fragment = new AktifFragKeranjang();
                    fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();

                }
            });

            holder.kurang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "kurang", Toast.LENGTH_SHORT).show();

                    // mengurang jumlah barang

                    int jumlahbarang = 1;

                    int barang = Integer.valueOf(produk.getJumlah());

                    int totalbarang = barang - jumlahbarang;

                    Log.e("barang", "" + totalbarang);
//                    Log.e("harga", "" + hargaawal);

                    // menampilkan hasil perhitungan

                    String strtotalbarang = String.valueOf(totalbarang);

                    // update data barang

                    db.updateKeranjang(new KeranjangClassData(produk.getId(), produk.getIdproduk(), produk.getNama_produk(), produk.getHarga_produk(), produk.getGambar_produk(), strtotalbarang) );

                    Fragment fragment = new AktifFragKeranjang();
                    fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();

                }
            });

            holder.hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "hapus", Toast.LENGTH_SHORT).show();

                    // menghapus data di keranjang

                    db.deleteKeranjang(String.valueOf(produk.getId()));

                    SharedPrefManager.deleteData(String.valueOf(produk.getId()), context);


                    Fragment fragment = new AktifFragKeranjang();
                    fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();

                }
            });


        } else {

            holder = (ViewHolder) convertView.getTag();

        }


        if (produk != null ) {


        holder.namaproduk.setText(produk.getNama_produk());
        holder.harga.setText("Rp " + produk.getHarga_produk());

        String totalbarang = SharedPrefManager.getData(String.valueOf(produk.getId()), "key", context);

        if (totalbarang == null) {

            holder.jumlahproduk.setText(produk.getJumlah());

        } else {

            holder.jumlahproduk.setText(String.valueOf(totalbarang));

            Log.e("jumlah produk", "" +String.valueOf(totalbarang));

        }

            String getGambar = produk.getGambar_produk();

            if (getGambar != null) {

                Log.e("url gambar", "" + getGambar);

                Picasso.with(context).load(getGambar).into(holder.gambar);


            } else {

                holder.gambar.setImageResource(R.drawable.notimage);

            }

        }

        return convertView;

    }

}
