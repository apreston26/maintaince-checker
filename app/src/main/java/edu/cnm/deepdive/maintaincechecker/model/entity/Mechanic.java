package edu.cnm.deepdive.maintaincechecker.model.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = @Index(value = "name", unique = true))
public class Mechanic {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "mechanic_id" )
  private long id;

  @NonNull
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String name = "";

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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
