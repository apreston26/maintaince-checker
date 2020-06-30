package edu.cnm.deepdive.maintaincechecker.model.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import edu.cnm.deepdive.maintaincechecker.model.entity.Review;
import edu.cnm.deepdive.maintaincechecker.model.pojo.MechanicWithReviews;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;

public interface ReviewDao {
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<Long> insert(Review review);

  @Insert(onConflict =  OnConflictStrategy.IGNORE)
  Single<List<Long>> insert(Collection<Review> reviews);

  @Update
  Single<Integer> update(Review... review);

  @Delete
  Single<Integer> delete(Review... reviews);

  @Query("SELECT * FROM Review WHERE mechanic_id = :mechanicId ")
  Single<List<MechanicWithReviews>> selectByMechanicId(Long mechanicId);

}
