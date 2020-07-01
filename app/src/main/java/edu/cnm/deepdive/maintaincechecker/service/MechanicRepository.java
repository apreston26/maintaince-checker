package edu.cnm.deepdive.maintaincechecker.service;

import android.content.Context;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.maintaincechecker.model.dao.MechanicDao;
import edu.cnm.deepdive.maintaincechecker.model.entity.Mechanic;
import java.util.List;

public class MechanicRepository {

  private final Context context;
  private final MaintenanceDatabase database;
  private final MechanicDao mechanicDao;

  public MechanicRepository(Context context) {
    this.context = context;
    database = MaintenanceDatabase.getInstance();
    mechanicDao = database.getMechanicDao();
  }

  public LiveData<List<Mechanic>> getAll() {
    return mechanicDao.selectAll();
  }
}
