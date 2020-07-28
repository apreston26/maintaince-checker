package edu.cnm.deepdive.maintaincechecker.service;

import android.annotation.SuppressLint;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import edu.cnm.deepdive.maintaincechecker.R;
import edu.cnm.deepdive.maintaincechecker.model.dao.MaintenanceDao;
import edu.cnm.deepdive.maintaincechecker.model.dao.MechanicDao;
import edu.cnm.deepdive.maintaincechecker.model.dao.ReviewDao;
import edu.cnm.deepdive.maintaincechecker.model.entity.Maintenance;
import edu.cnm.deepdive.maintaincechecker.model.entity.Maintenance.Type;
import edu.cnm.deepdive.maintaincechecker.model.entity.Mechanic;
import edu.cnm.deepdive.maintaincechecker.model.entity.Review;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    version = 2,
    exportSchema = true
)
@TypeConverters({MaintenanceDatabase.Converters.class, Maintenance.Type.class})
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
      try (
          InputStream input = context.getResources().openRawResource(R.raw.maintenance);
          Reader reader = new InputStreamReader(input);
          CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL
              .withIgnoreEmptyLines().withIgnoreSurroundingSpaces().withFirstRecordAsHeader());
      ) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<Maintenance> maints = new LinkedList<>();
        for (CSVRecord record : parser) {
          Maintenance maintenance = new Maintenance();
          maintenance.setDate(formatter.parse(record.get(0)));
          maintenance.setType(Type.valueOf(record.get(1)));
          maints.add(maintenance);
        }
        MaintenanceDatabase.getInstance().getMaintenanceDao().insert(maints)
            .subscribeOn(Schedulers.io())
            .subscribe();
      } catch (IOException | ParseException e) {
        throw new RuntimeException(e);
      }
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
