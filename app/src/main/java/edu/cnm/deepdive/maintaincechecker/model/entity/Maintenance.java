package edu.cnm.deepdive.maintaincechecker.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
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

  @ColumnInfo(name = "mechanic_id", index = true) //TODO update ERD for index
  private long mechanicId;

  @ColumnInfo(name = "date")
  private Date date;

  @NonNull
  @ColumnInfo(name = "type", collate = ColumnInfo.NOCASE)
  private Type type;

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
  public Type getType() {
    return type;
  }

  public void setType(@NonNull Type type) {
    this.type = type;
  }

  public enum Type {
    OIL_CHANGE,
    TIRE_ROTATE;

    @TypeConverter
    public static Integer typeToInteger(Type value) {
      return (value != null) ? value.ordinal() : null;
    }

    @TypeConverter
    public static Type integerToType(Integer value) {
      return (value != null) ? Type.values()[value] : null;
    }
  }
}
