package edu.cnm.deepdive.maintaincechecker.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import edu.cnm.deepdive.maintaincechecker.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class SecondFragment extends Fragment {

  private static final String ID_KEY = "type_id";

  public static SecondFragment newInstance(long typeId) {
    SecondFragment fragment = new SecondFragment();
    Bundle args = new Bundle();
    args.putLong(ID_KEY, typeId);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState
  ) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_second, container, false);
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    String myArg = SecondFragmentArgs.fromBundle(getArguments()).getMyArg();
    TextView textView = view.findViewById(R.id.textview_second);
    textView.setText(getString(R.string.hello_second_fragment, myArg));

    view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        NavHostFragment.findNavController(SecondFragment.this)
            .navigate(R.id.action_SecondFragment_to_FirstFragment);
      }
    });
  }
}
