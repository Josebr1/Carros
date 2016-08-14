package br.com.android.google.carros.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.android.google.carros.R;
import br.com.android.google.carros.fragments.CarrosFragment;

/**
 * Created by jose on 07/08/2016.
 *
 */
public class TabsAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public TabsAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return mContext.getString(R.string.classicos);
        }else if(position == 1){
            return mContext.getString(R.string.esportivos);
        }
        return mContext.getString(R.string.luxo);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment f = null;
        if(position == 0){
            f = CarrosFragment.newInstance(R.string.classicos);
        }else if(position == 1){
            f = CarrosFragment.newInstance(R.string.esportivos);
        }else{
            f = CarrosFragment.newInstance(R.string.luxo);
        }

        return f;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
