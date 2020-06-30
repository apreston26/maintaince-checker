package edu.cnm.deepdive.maintaincechecker.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import edu.cnm.deepdive.maintaincechecker.model.entity.Maintenance;
import edu.cnm.deepdive.maintaincechecker.model.entity.Review;
import edu.cnm.deepdive.maintaincechecker.model.pojo.MaintenanceType;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;

public interface MaintenanceDao {
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<Long> insert(Maintenance maintenance);

  @Insert(onConflict =  OnConflictStrategy.IGNORE)
  Single<List<Long>> insert(Collection<Maintenance> maintenance);

  @Update
  Single<Integer> update(Maintenance... maintenance);

  @Delete
  Single<Integer> delete(Maintenance... maintenance);

  @Transaction
  @Query("SELECT * FROM Maintenance ORDER BY type ")
  LiveData<List<Maintenance>> selectAll();

  @Query("SELECT * FROM Maintenance WHERE mechanic_id = :mechanicId")
  Single<List<Maintenance>> selectByMechanicId(long mechanicId);
}
