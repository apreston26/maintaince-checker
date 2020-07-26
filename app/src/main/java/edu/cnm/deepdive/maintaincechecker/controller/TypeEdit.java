package edu.cnm.deepdive.maintaincechecker.controller;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.cnm.deepdive.maintaincechecker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TypeEdit extends Fragment {

    public TypeEdit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_type_edit, container, false);
    }
}
