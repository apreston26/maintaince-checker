package edu.cnm.deepdive.maintaincechecker.service;

import android.annotation.SuppressLint;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import edu.cnm.deepdive.maintaincechecker.R;
import edu.cnm.deepdive.maintaincechecker.model.dao.MaintenanceDao;
import edu.cnm.deepdive.maintaincechecker.model.dao.MechanicDao;
import edu.cnm.deepdive.maintaincechecker.model.dao.ReviewDao;
import edu.cnm.deepdive.maintaincechecker.model.entity.Maintenance;
import edu.cnm.deepdive.maintaincechecker.model.entity.Mechanic;
import edu.cnm.deepdive.maintaincechecker.model.entity.Review;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Database(
    entities = {Review.class, Mechanic.class, Maintenance.class},
    version = 1
)
@TypeConverters({MaintenanceDatabase.Converters.class})
public abstract class MaintenanceDatabase extends RoomDatabase {

  private static final String DB_NAME = "maintenance_db";

  private static Application context;

  public static void setContext(Application context) {
    MaintenanceDatabase.context = context;
  }

  public abstract MaintenanceDao getMaintenanceDao();

  public abstract MechanicDao getMechanicDao();

  public abstract ReviewDao getReviewDao();

  public static MaintenanceDatabase getInstance() {
    return InstanceHolder.INSTANCE;
  }

  private static class InstanceHolder {

    private static final MaintenanceDatabase INSTANCE = Room
        .databaseBuilder(context, MaintenanceDatabase.class, DB_NAME)
        .addCallback(new MaintenanceCallback())
        .build();
  }
  // This file might be deleted/altered since it changed from a string to an enum.
  private static class MaintenanceCallback extends Callback {

    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
      try {
        Map<Mechanic, List<Maintenance>> map = parseFile(R.raw.mechanics); //TODO See if this is needed
        persist(map);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }



    private Map<Mechanic, List<Maintenance>> parseFile(int resourceId) throws IOException {
      try (
          InputStream input = MaintenanceDatabase.context.getResources()
              .openRawResource(resourceId);
          Reader reader = new InputStreamReader(input);
          CSVParser parser = CSVParser.parse(
              reader, CSVFormat.EXCEL.withIgnoreSurroundingSpaces().withIgnoreEmptyLines());
      ) {
        Map<Mechanic, List<Maintenance>> map = new HashMap<>();
        for (CSVRecord record : parser) {
          Mechanic mechanic = null;
          String mechanicName = record.get(0).trim();
          if (!mechanicName.isEmpty()) {
            mechanic = new Mechanic();
            mechanic.setName(mechanicName);
          }
          List<Maintenance> maintenanceList = map
              .computeIfAbsent(mechanic, (s) -> new LinkedList<>());
          Maintenance maintenance = new Maintenance();
          maintenanceList.add(maintenance);
        }
        return map;
      }
    }

    @SuppressLint("CheckResult")
    private void persist(Map<Mechanic, List<Maintenance>> map) {
      MaintenanceDatabase database = MaintenanceDatabase.getInstance();
      MechanicDao mechanicDao = database.getMechanicDao();
      MaintenanceDao maintenanceDao = database.getMaintenanceDao();
      List<Mechanic> mechanics = new LinkedList<>(map.keySet());
      List<Maintenance> unattributed = map.getOrDefault(null, Collections.emptyList());
      mechanics.remove(null);
      //noinspection ResultOfMethodCallIgnored
      mechanicDao.insert(mechanics)
          .subscribeOn(Schedulers.io())
          .flatMap((mechanicIds) -> {
            List<Maintenance> maintenanceList = new LinkedList<>();
            Iterator<Long> idIterator = mechanicIds.iterator();
            Iterator<Mechanic> mechanicIterator = mechanics.iterator();
            while (idIterator.hasNext()) {
              long mechanicId = idIterator.next();
              for (Maintenance maintenance : map
                  .getOrDefault(mechanicIterator.next(), Collections.emptyList())) {
                maintenance.setMechanicId(mechanicId);
                maintenanceList.add(maintenance);
              }
            }

            maintenanceList.addAll(unattributed);
            return maintenanceDao.insert(maintenanceList);
          })
          .subscribe(
              (quoteIds) -> {
              },
              (throwable) -> {
                throw new RuntimeException(throwable);
              }
          );
    }
  }

  public static class Converters {

    @TypeConverter
    public static Long dateToLong(Date value) {
      return (value != null) ? value.getTime() : null;
    }

    @TypeConverter
    public static Date longToDate(Long value) {
      return (value != null) ? new Date(value) : null;
    }
  }
}
