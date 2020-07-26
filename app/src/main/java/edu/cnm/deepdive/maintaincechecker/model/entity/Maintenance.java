package edu.cnm.deepdive.maintaincechecker.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import java.util.Date;
import javax.annotation.Nullable;

@Nullable
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
  private Long mechanicId;

  @ColumnInfo(name = "date")
  private Date date;

  @NonNull
  @ColumnInfo(name = "type", collate = ColumnInfo.NOCASE)
  private String type;

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

  public void setMechanicId(Long mechanicId) {
    this.mechanicId = mechanicId;
  }

  @NonNull
  public String getType() {
    return type;
  }

  public void setType(@NonNull String type) {
    this.type = type;
  }

  // TODO STRETCH-GOAL If there is enough time implement an enum instead of editable text.

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
