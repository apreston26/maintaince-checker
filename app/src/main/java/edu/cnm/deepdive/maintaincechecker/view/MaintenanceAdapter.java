package edu.cnm.deepdive.maintaincechecker.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.maintaincechecker.R;
import edu.cnm.deepdive.maintaincechecker.model.pojo.MaintenanceWithMechanic;

import edu.cnm.deepdive.maintaincechecker.view.MaintenanceAdapter.Holder;
import java.text.DateFormat;
import java.util.List;

public class MaintenanceAdapter extends RecyclerView.Adapter<Holder> {

  private final String unattributedMechanic;
  private final Context context;
  private final List<MaintenanceWithMechanic> maintenanceHistory;
  private final OnClickListener clickListener;
  private final DateFormat dateFormat;


  public MaintenanceAdapter(Context context, List<MaintenanceWithMechanic> maintenanceHistory,
      OnClickListener clickListener) {
    super();
    this.context = context;
    this.maintenanceHistory = maintenanceHistory;
    unattributedMechanic = context.getString(R.string.no_preference_mechanic);
    this.clickListener = clickListener;
    dateFormat = android.text.format.DateFormat.getDateFormat(context);
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.maintenance_type, parent, false);
    return new Holder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.bind(position);
  }

  @Override
  public int getItemCount() {
    return maintenanceHistory.size();
  }


  class Holder extends RecyclerView.ViewHolder {

    private final View itemView;
    private final TextView type;
    private final TextView mechanic;
    private final TextView date;

    public Holder(@NonNull View itemView) {
      super(itemView);
      this.itemView = itemView;
      type = itemView.findViewById(R.id.type);
      mechanic = itemView.findViewById(R.id.mechanic);
      date = itemView.findViewById(R.id.date);
    }

    private void bind(int position) {
      MaintenanceWithMechanic item = maintenanceHistory.get(position);
      String mechanicName =
          (item.getMechanic() != null) ? item.getMechanic().getName() : unattributedMechanic;
      mechanic.setText(mechanicName);
      type.setText(item.getType().toString());
      date.setText(dateFormat.format(item.getDate()));
      itemView.setOnClickListener((v) -> clickListener.onClick(v, getAdapterPosition(), item));
    }

  }

  public interface OnClickListener {

    void onClick(View v, int position, MaintenanceWithMechanic type);

  }

}