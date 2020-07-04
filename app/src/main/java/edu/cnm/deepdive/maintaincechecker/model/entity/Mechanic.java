package edu.cnm.deepdive.maintaincechecker.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = @Index(value = "name", unique = true))
public class Mechanic {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "mechanic_id" )
  private long id;

  @ColumnInfo(name = "location")
  private long location;

  @NonNull
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String name = "";


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getLocation() {
    return location;
  }

  public void setLocation(long location) {
    this.location = location;
  }

  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  @NonNull
  @Override
  public String toString() {
    return name;
  }
}
