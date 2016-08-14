package br.com.android.google.carros.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.TabLayout;


import br.com.android.google.carros.R;
import br.com.android.google.carros.adapter.TabsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarrosTabFragment extends Fragment {


    public CarrosTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carros_tab, container, false);

        return view;
    }

}
