package edu.cnm.deepdive.maintaincechecker.controller;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.maintaincechecker.R;
import edu.cnm.deepdive.maintaincechecker.model.entity.Maintenance;
import edu.cnm.deepdive.maintaincechecker.model.entity.Maintenance.Type;
import edu.cnm.deepdive.maintaincechecker.model.entity.Mechanic;
import edu.cnm.deepdive.maintaincechecker.viewmodel.MainViewModel;
import java.util.Calendar;
import java.util.List;

public class TypeEditFragment extends DialogFragment {

  private static final String ID_KEY = "type_id";

  private long typeId;
  private Calendar calendar;
  private View root;
  private Spinner typeSpinner;
  private AutoCompleteTextView mechanicName;
  private AlertDialog dialog;
  private MainViewModel viewModel;
  private Maintenance maintenance;
  private DatePicker date;
  private List<Mechanic> mechanics;

  public static TypeEditFragment newInstance(long typeId) {
    TypeEditFragment fragment = new TypeEditFragment();
    Bundle args = new Bundle();
    args.putLong(ID_KEY, typeId);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      typeId = getArguments().getLong(ID_KEY, 0);
    }
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    calendar = Calendar.getInstance();
    root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_type_edit, null, false);
    typeSpinner = root.findViewById(R.id.type_spinner);
    ArrayAdapter<Type> adapter = new ArrayAdapter<>(getContext(),
        android.R.layout.simple_spinner_item, Type.values());
    typeSpinner.setAdapter(adapter);
    mechanicName = root.findViewById(R.id.mechanic_name);
    date = root.findViewById(R.id.date);
    dialog = new AlertDialog.Builder(getContext())
        .setIcon(R.drawable.ic_logo)
        .setTitle("Maintenance Details")
        .setView(root)
        .setPositiveButton(android.R.string.ok, (dlg, which) -> save())
        .setNegativeButton(android.R.string.cancel, (dlg, which) -> {
        })
        .create();
    return dialog;
  }

  private void save() {
    maintenance.setType((Type) typeSpinner.getSelectedItem());
    calendar.set(Calendar.YEAR, date.getYear());
    calendar.set(Calendar.MONTH, date.getMonth());
    calendar.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
    maintenance.setDate(calendar.getTime());
    Mechanic mechanic = null;
    String name = mechanicName.getText().toString().trim();
    maintenance.setMechanicId(null);
    if (!name.isEmpty()) {
      for (Mechanic m : mechanics) {
        if (name.equalsIgnoreCase(m.getName())) {
          maintenance.setMechanicId(m.getId());
          break;
        }
      }
    }
    if (maintenance.getMechanicId() != null || name.isEmpty()) {
      viewModel.save(maintenance);
    } else {
      viewModel.save(maintenance, name);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return root;
  }

  @SuppressWarnings("ConstantConditions")
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    viewModel.getMechanics().observe(getViewLifecycleOwner(), (sources) -> {
      this.mechanics = sources;
      ArrayAdapter<Mechanic> adapter =
          new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line,
              sources);
      mechanicName.setAdapter(adapter);
    });
    if (typeId != 0) {
      viewModel.getMaintenance().observe(getViewLifecycleOwner(), (maintenance) -> {
        this.maintenance = maintenance;
        typeSpinner.setSelection(maintenance.getType().ordinal());
        mechanicName.setText(
            (maintenance.getMechanic() != null) ? maintenance.getMechanic().getName() : "");
        calendar.setTime(maintenance.getDate());
        date.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH));
      });
      viewModel.setMaintenanceId(typeId);
    } else {
      maintenance = new Maintenance();
      date.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
          calendar.get(Calendar.DAY_OF_MONTH));
    }
  }
}
