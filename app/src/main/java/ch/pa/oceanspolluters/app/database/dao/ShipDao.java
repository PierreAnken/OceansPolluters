package ch.pa.oceanspolluters.app.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ch.pa.oceanspolluters.app.database.entity.ItemEntity;
import ch.pa.oceanspolluters.app.database.entity.ShipEntity;

;


@Dao
public abstract class ShipDao {

    @Query("SELECT * FROM ships WHERE id = :id")
    public abstract ShipEntity getById(int id);

    @Query("SELECT * FROM ships ORDER BY Name")
    public abstract List<ShipEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insert(ShipEntity ship);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(List<ShipEntity> ships);

    @Update
    public abstract void update(ShipEntity ship);

    @Delete
    public abstract void delete(ShipEntity ship);

    @Query("DELETE FROM ships")
    public abstract void deleteAll();
}