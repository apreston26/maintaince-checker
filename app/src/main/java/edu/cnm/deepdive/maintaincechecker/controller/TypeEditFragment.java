package edu.cnm.deepdive.maintaincechecker.controller;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.maintaincechecker.R;
import edu.cnm.deepdive.maintaincechecker.model.entity.Maintenance;
import edu.cnm.deepdive.maintaincechecker.model.entity.Mechanic;
import edu.cnm.deepdive.maintaincechecker.viewmodel.MainViewModel;
import java.util.List;

public class TypeEditFragment extends DialogFragment implements TextWatcher {

    private static final String ID_KEY = "type_id";

    private long typeId;
    private View root;
    private EditText typeText;
    private AutoCompleteTextView mechanicName;
    private AlertDialog dialog;
    private MainViewModel viewModel;
    private Maintenance maintenance;
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
        root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_type_edit, null, false);
        typeText = root.findViewById(R.id.type_text);
        mechanicName = root.findViewById(R.id.mechanic_name);
        typeText.addTextChangedListener(this);
        dialog = new AlertDialog.Builder(getContext())
            .setIcon(R.drawable.ic_logo)
            .setTitle("Maintenance Details")
            .setView(root)
            .setPositiveButton(android.R.string.ok, (dlg, which) -> save())
            .setNegativeButton(android.R.string.cancel, (dlg, which) -> {})
            .create();
        dialog.setOnShowListener((dlg) -> checkSubmitCondition());
        return dialog;
    }

    private void save() {
        maintenance.setType(typeText.getText().toString().trim());
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
        viewModel.saveType(maintenance);
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
                new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, sources);
            mechanicName.setAdapter(adapter);
        });
        if (typeId != 0) {
            viewModel.getMaintenance().observe(getViewLifecycleOwner(), (type) -> {
                this.maintenance = type;
                if (type != null) {
                    typeText.setText(type.getType());
                    mechanicName.setText((type.getMechanic() != null) ? type.getMechanic().getName() : "");
                }
            });
            viewModel.setQuoteId(typeId);
        } else {
            maintenance = new Maintenance();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        checkSubmitCondition();
    }

    private void checkSubmitCondition() {
        Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positive.setEnabled(!typeText.getText().toString().trim().isEmpty());
    }

}
