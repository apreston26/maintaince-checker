package edu.cnm.deepdive.maintaincechecker.service;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import edu.cnm.deepdive.maintaincechecker.model.dao.MaintenanceDao;
import edu.cnm.deepdive.maintaincechecker.model.dao.MechanicDao;
import edu.cnm.deepdive.maintaincechecker.model.entity.Maintenance;
import edu.cnm.deepdive.maintaincechecker.model.entity.Mechanic;
import edu.cnm.deepdive.maintaincechecker.model.entity.Review;

@Database(
    entities = {Review.class, Mechanic.class, Maintenance.class},
    version = 1
)
public abstract class MaintenanceDatabase extends RoomDatabase {

  private static final String DB_NAME = "maintenance_db";

  private static Application context;

  public static void setContext(Application context) {
    MaintenanceDatabase.context = context;
  }

  public abstract MaintenanceDao getMaintenanceDao();

  public abstract MechanicDao getMechanicDao();

  public static MaintenanceDatabase getInstance() {
    return InstanceHolder.INSTANCE;
  }

  private static class InstanceHolder {
    private static final MaintenanceDatabase INSTANCE = Room.databaseBuilder(context, MaintenanceDatabase.class, DB_NAME)
        .addCallback(new MaintenanceCallback())
        .build();
  }

private static class MaintenanceCallback extends Callback {

  @Override
  public void onCreate(@NonNull SupportSQLiteDatabase db) {
    super.onCreate(db);

  }
}
}
