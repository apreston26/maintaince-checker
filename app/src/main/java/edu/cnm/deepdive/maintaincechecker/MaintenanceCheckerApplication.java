package edu.cnm.deepdive.maintaincechecker;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.maintaincechecker.service.MaintenanceDatabase;
import io.reactivex.schedulers.Schedulers;

public class MaintenanceCheckerApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    MaintenanceDatabase.setContext(this);
    MaintenanceDatabase database = MaintenanceDatabase.getInstance();
    database.getMechanicDao().delete().subscribeOn(Schedulers.io()).subscribe();
    Stetho.initializeWithDefaults(this);
  }
}
