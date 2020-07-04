package edu.cnm.deepdive.maintaincechecker.model.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

//TODO Make this null
@Entity(foreignKeys = @ForeignKey(
    entity = Mechanic.class,
    parentColumns = "mechanic_id",
    childColumns = "mechanic_id",
    onDelete = ForeignKey.SET_NULL))
public class Review {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "review_id")
  private long id;

  //Allows the user to search reviews by mechanic
  @ColumnInfo(name = "mechanic_id", index = true) // TODO update ERD for index
  private long mechanicId;

  @NonNull
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String reviewMsg = "";

//  //TODO Update ERD with this column
//  @ColumnInfo(name = "reputation")
//  private double rep;

  public long getMechanicId() {
    return mechanicId;
  }

  public void setMechanicId(long mechanicId) {
    this.mechanicId = mechanicId;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @NonNull
  public String getReviewMsg() {
    return reviewMsg;
  }

  public void setReviewMsg(@NonNull String reviewMsg) {
    this.reviewMsg = reviewMsg;
  }

  @NonNull
  @Override
  public String toString() {
    return reviewMsg;
  }
}
