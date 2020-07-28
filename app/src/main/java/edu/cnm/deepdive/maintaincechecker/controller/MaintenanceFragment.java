package edu.cnm.deepdive.maintaincechecker.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import edu.cnm.deepdive.maintaincechecker.R;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import edu.cnm.deepdive.maintaincechecker.model.pojo.MaintenanceWithMechanic;
import edu.cnm.deepdive.maintaincechecker.view.MaintenanceAdapter;
import edu.cnm.deepdive.maintaincechecker.viewmodel.MainViewModel;

public class MaintenanceFragment extends Fragment implements MaintenanceAdapter.OnClickListener {

  private MainViewModel mainViewModel;
  private RecyclerView maintenanceList;
  private FloatingActionButton addMechanic;
  private FloatingActionButton addMaintenance;
  private boolean isOpen;


  @SuppressWarnings("ConstantConditions")
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    mainViewModel.getMaintenanceHistory().observe(getViewLifecycleOwner(),
        (maintenanceHistory) -> {
      maintenanceList.setAdapter(new MaintenanceAdapter(getContext(), maintenanceHistory, this));
        });

    // TODO Find a way to change this button to the button in the fab menu
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
    addMaintenance = root.findViewById(R.id.add_maintenance);
    addMaintenance.setTooltipText(getString(R.string.add_maintenance_text));
    addMaintenance.setOnClickListener((v) -> editType(0));
    addMechanic = root.findViewById(R.id.add_mechanic);
    addMechanic.setTooltipText(getString(R.string.add_mechanic_text));
    return root;
  }


  @Override
  public void onClick(View v, int position, MaintenanceWithMechanic type) {
    editType(type.getId());
  }

  private void editType(long typeId) {
    TypeEditFragment fragment = TypeEditFragment.newInstance(typeId);
    fragment.show(getChildFragmentManager(), fragment.getClass().getName());

  }

}
