package edu.cnm.deepdive.maintaincechecker.model.entity;

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

  @ColumnInfo(name = "type", collate = ColumnInfo.NOCASE)
  private int type; //TODO Make this refer to an enum with a list of things that could require maintenance

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

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }
}
