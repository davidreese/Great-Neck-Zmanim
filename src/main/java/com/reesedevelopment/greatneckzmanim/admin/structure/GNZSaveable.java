package com.reesedevelopment.greatneckzmanim.admin.structure;

import java.util.List;

public interface GNZSaveable<T extends GNZObject> {
    boolean save(T objectToSave);

//    boolean disable(T objectToDisable);

//    boolean delete(T objectToDelete);

    List<T> getAll();
}
