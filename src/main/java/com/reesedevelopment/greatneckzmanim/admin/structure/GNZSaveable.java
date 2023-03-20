package com.reesedevelopment.greatneckzmanim.admin.structure;

import java.sql.SQLException;
import java.util.List;

public interface GNZSaveable<T extends GNZObject> {
//    T findByName(String name);

    T findById(String id);

    List<T> getAll();

    boolean save(T objectToSave);

//    boolean disable(T objectToDisable);

    boolean delete(T objectToDelete) throws SQLException;

    boolean update(T objectToUpdate);
}
