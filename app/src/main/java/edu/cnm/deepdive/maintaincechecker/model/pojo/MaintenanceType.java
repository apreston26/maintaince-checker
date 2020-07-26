package edu.cnm.deepdive.maintaincechecker.model.pojo;

import androidx.annotation.NonNull;
import androidx.room.Relation;
import edu.cnm.deepdive.maintaincechecker.model.entity.Maintenance;
import edu.cnm.deepdive.maintaincechecker.model.entity.Mechanic;

public class MaintenanceType extends Maintenance {

  @Relation(entity = Mechanic.class, entityColumn = "mechanic_id", parentColumn = "mechanic_id")
  private Mechanic mechanic;

  public Mechanic getMechanic() {
    return mechanic;
  }

  public void setMechanic(Mechanic mechanic) {
    this.mechanic = mechanic;
  }

  @NonNull
  @Override
  public String toString() {
    String mechanicName = (mechanic != null) ? mechanic.getName() : "No Preference";
    return String.format("%s%n\u2014%s", getType(), mechanicName);
  }
}
