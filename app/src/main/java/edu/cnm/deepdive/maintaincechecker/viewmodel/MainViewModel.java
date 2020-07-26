package edu.cnm.deepdive.maintaincechecker.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import edu.cnm.deepdive.maintaincechecker.model.entity.Maintenance;
import edu.cnm.deepdive.maintaincechecker.model.entity.Mechanic;
import edu.cnm.deepdive.maintaincechecker.model.pojo.MaintenanceType;
import edu.cnm.deepdive.maintaincechecker.service.MaintenanceRepository;
import edu.cnm.deepdive.maintaincechecker.service.MechanicRepository;
import edu.cnm.deepdive.maintaincechecker.service.ReviewRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

public class MainViewModel extends AndroidViewModel implements LifecycleObserver {

  private final MaintenanceRepository maintenanceRepository;
  private final MechanicRepository mechanicRepository;
  private final ReviewRepository reviewRepository;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final MutableLiveData<MaintenanceType> maintenance;
  private final MutableLiveData<Boolean> permissionsChecked;

  public MainViewModel(@NonNull Application application) {
    super(application);
    maintenanceRepository = new MaintenanceRepository(application);
    mechanicRepository = new MechanicRepository(application);
    permissionsChecked = new MutableLiveData<>(false);
    reviewRepository = new ReviewRepository(application);
    maintenance = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();

  }

  public LiveData<List<MaintenanceType>> getMaintenanceTypes() {
    return maintenanceRepository.getAll();
  }

  public LiveData<List<Mechanic>> getMechanics() {
    return mechanicRepository.getAll();
  }

  public MutableLiveData<MaintenanceType> getMaintenance() {
    return maintenance;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public LiveData<Boolean> getPermissionsChecked() {
    return permissionsChecked;
  }

  public void setPermissionsChecked(boolean checked) {
    permissionsChecked.setValue(checked);
  }

  public void setQuoteId(long id) {
    throwable.setValue(null);
    pending.add(
        maintenanceRepository.get(id)
            .subscribe(
                this.maintenanceRepository::postValue,
                this.throwable::postValue
            )
    );
  }

  public void saveType(Maintenance maintenance) {
    throwable.setValue(null);
    pending.add(
        maintenanceRepository.save(maintenance)
            .subscribe(
                () -> {},
                this.throwable::postValue
            )
    );
  }

@OnLifecycleEvent(Event.ON_STOP)
  private void clearPending() {
    pending.clear();
}
}
