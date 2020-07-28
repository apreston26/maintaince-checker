package edu.cnm.deepdive.maintaincechecker.service;

import android.content.Context;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.maintaincechecker.model.dao.MaintenanceDao;
import edu.cnm.deepdive.maintaincechecker.model.dao.MechanicDao;
import edu.cnm.deepdive.maintaincechecker.model.entity.Maintenance;
import edu.cnm.deepdive.maintaincechecker.model.entity.Mechanic;
import edu.cnm.deepdive.maintaincechecker.model.pojo.MaintenanceWithMechanic;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class MaintenanceRepository {

  private final Context context;
  private final MaintenanceDatabase database;
  private final MechanicDao mechanicDao;
  private final MaintenanceDao maintenanceDao;

  public MaintenanceRepository(Context context) {
    super();
    this.context = context;
    database = MaintenanceDatabase.getInstance();
    mechanicDao = database.getMechanicDao();
    maintenanceDao = database.getMaintenanceDao();
  }

  public LiveData<List<MaintenanceWithMechanic>> getAll() {
    return maintenanceDao.selectAll();
  }

  public Single<MaintenanceWithMechanic> get(long id) {
    return maintenanceDao.selectByMechanicId(id).subscribeOn(Schedulers.io());
  }

  public Completable save(Maintenance maintenance) {
    if (maintenance.getId() == 0) {
      return Completable.fromSingle(maintenanceDao.insert(maintenance))
          .subscribeOn(Schedulers.io());
    } else {
      return Completable.fromSingle(maintenanceDao.update(maintenance))
          .subscribeOn(Schedulers.io());
    }
  }

  public Completable delete(Maintenance maintenance) {
    if (maintenance.getId() == 0) {
      return Completable.fromAction(() -> {
      })
          .subscribeOn(Schedulers.io());
    } else {
      return Completable.fromSingle(maintenanceDao.delete(maintenance))
          .subscribeOn(Schedulers.io());
    }
  }

  public Completable save(Maintenance maintenance, String mechanicName) {
    Completable completable = (maintenance.getId() == 0)
        ? Completable.fromSingle(maintenanceDao.insert(maintenance))
        : Completable.fromSingle(maintenanceDao.update(maintenance));
    Mechanic mechanic = new Mechanic();
    mechanic.setName(mechanicName);

    // Creates a mechanic on the fly
    return mechanicDao.insert(mechanic)
        .subscribeOn(Schedulers.io())
        .flatMapCompletable((id) -> {
          maintenance.setMechanicId(id);
          return completable;
        });
  }
}

