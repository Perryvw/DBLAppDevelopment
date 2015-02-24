package com.dblappdev.hitch.app;

/**
 * Created by s138308 on 24/02/2015.
 */
import com.dblappdev.hitch.app.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HitchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_hitch, container, false);

        return rootView;
    }
}