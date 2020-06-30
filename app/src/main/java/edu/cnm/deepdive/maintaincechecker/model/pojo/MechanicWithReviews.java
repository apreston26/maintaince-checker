package edu.cnm.deepdive.maintaincechecker.model.pojo;

import androidx.room.Relation;
import edu.cnm.deepdive.maintaincechecker.model.entity.Review;
import java.util.List;

public class MechanicWithReviews extends Review {

  @Relation(entity = Review.class,  entityColumn = "mechanic_id", parentColumn = "mechanic_id")
  private List<Review> review;

  public List<Review> getReview() {
    return review;
  }

  public void setReview(List<Review> review) {
    this.review = review;
  }
}
