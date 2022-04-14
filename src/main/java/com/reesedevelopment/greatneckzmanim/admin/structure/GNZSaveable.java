package com.reesedevelopment.greatneckzmanim.admin.structure;

import java.util.List;

public interface GNZSaveable<T extends GNZObject> {
    T find(String id);

    List<T> getAll();

    boolean save(T objectToSave);

//    boolean disable(T objectToDisable);

    boolean delete(T objectToDelete);

    boolean update(T objectToUpdate);
}
