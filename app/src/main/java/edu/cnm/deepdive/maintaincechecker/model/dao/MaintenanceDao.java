package edu.cnm.deepdive.maintaincechecker.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.maintaincechecker.model.entity.Maintenance;
import edu.cnm.deepdive.maintaincechecker.model.pojo.MaintenanceType;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface MaintenanceDao {
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<Long> insert(Maintenance maintenance);

  @Insert(onConflict =  OnConflictStrategy.IGNORE)
  Single<List<Long>> insert(Collection<Maintenance> maintenance);

  @Update
  Single<Integer> update(Maintenance... maintenance);

  @Delete
  Single<Integer> delete(Maintenance... maintenance);

  @Query("SELECT * FROM Maintenance ORDER BY type ")
  LiveData<List<MaintenanceType>> selectAll();

  @Query("SELECT * FROM Maintenance WHERE mechanic_id = :mechanicId")
  Single<List<Maintenance>> selectByMechanicId(long mechanicId);
}
