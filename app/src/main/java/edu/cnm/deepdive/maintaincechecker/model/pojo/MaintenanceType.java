package edu.cnm.deepdive.maintaincechecker.model.pojo;

import androidx.room.Relation;
import edu.cnm.deepdive.maintaincechecker.model.entity.Maintenance;
import java.util.List;

public class MaintenanceType extends Maintenance {

  @Relation(entity = Maintenance.class, entityColumn = "mechanic_id", parentColumn = "mechanic_id")
  private List<Maintenance> Maintenance;

  public List<Maintenance> getMaintenance() {
    return Maintenance;
  }

  public void setMaintenance(
      List<Maintenance> maintenance) {
    Maintenance = maintenance;
  }
}
