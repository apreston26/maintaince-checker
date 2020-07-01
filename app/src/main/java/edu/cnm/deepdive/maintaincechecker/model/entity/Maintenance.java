package edu.cnm.deepdive.maintaincechecker.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.Date;

//TODO Make this null
@Entity(foreignKeys = @ForeignKey(
    entity = Mechanic.class,
    parentColumns = "mechanic_id",
    childColumns = "mechanic_id",
    onDelete = ForeignKey.SET_NULL))
public class Maintenance {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "maintenance_id")
  private long id;

  @ColumnInfo(name = "mechanic_id", index = true)
  private long mechanicId;

  @ColumnInfo(name = "date")
  private Date date;

  @NonNull
  @ColumnInfo(name = "type", collate = ColumnInfo.NOCASE)
  private String text;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public long getMechanicId() {
    return mechanicId;
  }

  public void setMechanicId(long mechanicId) {
    this.mechanicId = mechanicId;
  }

  @NonNull
  public String getText() {
    return text;
  }

  public void setText(@NonNull String text) {
    this.text = text;
  }
}
