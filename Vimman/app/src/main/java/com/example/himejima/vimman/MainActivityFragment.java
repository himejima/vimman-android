package com.example.himejima.vimman;

import android.app.Activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private MainActivity parent;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.fragment_main, container, false);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Button btnMove = (Button) view.findViewById(R.id.button);
        btnMove.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                parent.move();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        parent = (MainActivity) activity;
        super.onAttach(activity);
    }
}
