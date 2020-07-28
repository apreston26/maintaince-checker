package edu.cnm.deepdive.maintaincechecker.service;

import android.content.Context;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.maintaincechecker.model.dao.MechanicDao;
import edu.cnm.deepdive.maintaincechecker.model.dao.ReviewDao;
import edu.cnm.deepdive.maintaincechecker.model.entity.Review;
import edu.cnm.deepdive.maintaincechecker.model.pojo.MechanicWithReviews;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class ReviewRepository {

  private final Context context;
  private final MaintenanceDatabase database;
  private final MechanicDao mechanicDao;
  private final ReviewDao reviewDao;

  public ReviewRepository(Context context) {
    super();
    this.context = context;
    database = MaintenanceDatabase.getInstance();
    mechanicDao = database.getMechanicDao();
    reviewDao = database.getReviewDao();
  }

  public LiveData<List<Review>> getAll() {
    return reviewDao.selectAll();
  }

  public Single<List<MechanicWithReviews>> get(long id) {
    return reviewDao.selectByMechanicId(id).subscribeOn(Schedulers.io());
  }

  public Completable save (Review review) {
    if (review.getId() == 0) {
      return Completable.fromSingle(reviewDao.insert(review))
          .subscribeOn(Schedulers.io());
    } else {
      return Completable.fromSingle(reviewDao.update(review))
          .subscribeOn(Schedulers.io());
    }
  }

  public Completable delete (Review review) {
    if (review.getId() == 0) {
      return Completable.fromAction(() -> {})
          .subscribeOn(Schedulers.io());
    } else {
      return Completable.fromSingle(reviewDao.delete(review))
          .subscribeOn(Schedulers.io());
    }
  }

}
