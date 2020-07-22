package edu.cnm.deepdive.maintaincechecker.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.maintaincechecker.R;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import edu.cnm.deepdive.maintaincechecker.model.pojo.MaintenanceType;
import edu.cnm.deepdive.maintaincechecker.view.MaintenanceAdapter;
import edu.cnm.deepdive.maintaincechecker.viewmodel.MainViewModel;

public class MaintenanceFragment extends Fragment implements MaintenanceAdapter.OnClickListener {

  private MainViewModel mainViewModel;
  private RecyclerView maintenanceList;

  @SuppressWarnings("ConstantConditions")
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    mainViewModel.getMaintenanceType().observe(getViewLifecycleOwner(),
        (types) -> maintenanceList.setAdapter(new MaintenanceAdapter(getContext(), types, this)));

    view.findViewById(R.id.button_first).setOnClickListener(view1 -> {
      MaintenanceFragmentDirections.ActionFirstFragmentToSecondFragment action =
          MaintenanceFragmentDirections.actionFirstFragmentToSecondFragment("From FirstFragment");
      NavHostFragment.findNavController(MaintenanceFragment.this)
          .navigate(action);
    });
  }

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_maintenace, container, false);
    maintenanceList = root.findViewById(R.id.maintenance_list);
    return root;
  }

  @Override
  public void onClick(View v, int position, MaintenanceType type) {

  }
}
