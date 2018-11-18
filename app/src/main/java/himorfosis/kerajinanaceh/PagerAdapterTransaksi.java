package himorfosis.kerajinanaceh;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapterTransaksi extends FragmentPagerAdapter {

    int mNumOfTabs;

    public PagerAdapterTransaksi(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AktifFragTransaksiTagihan tab1 = new AktifFragTransaksiTagihan();
                return tab1;
            case 1:
                AktifFragTransaksiPembelian tab2 = new AktifFragTransaksiPembelian();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
