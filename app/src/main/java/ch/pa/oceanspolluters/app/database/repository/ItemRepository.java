package ch.pa.oceanspolluters.app.database.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import ch.pa.oceanspolluters.app.database.AppDatabase;
import ch.pa.oceanspolluters.app.database.entity.ItemEntity;
import ch.pa.oceanspolluters.app.database.pojo.ItemWithType;

public class ItemRepository {
    private static ItemRepository sInstance;

    private final AppDatabase mDatabase;

    private ItemRepository(final AppDatabase database) {
        mDatabase = database;
    }

    public static ItemRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (ItemRepository.class) {
                if (sInstance == null) {
                    sInstance = new ItemRepository(database);
                }
            }
        }
        return sInstance;
    }

    public List<ItemWithType> getAll() {
        return mDatabase.itemDao().getAll();
    }

    public LiveData<ItemWithType> getItemLD(final int id) {
        return mDatabase.itemDao().getByIdLD(id);
    }

    public LiveData<List<ItemWithType>> getItemsFromContainerLD(int containerId) {
        return mDatabase.itemDao().getItemsFromContainerLD(containerId);
    }

    public void insert(final ItemEntity item) {
        mDatabase.itemDao().insert(item);
    }

    public void update(final ItemEntity item) {
        mDatabase.itemDao().update(item);
    }

    public void delete(final ItemEntity item) {
        mDatabase.itemDao().delete(item);
    }

}
