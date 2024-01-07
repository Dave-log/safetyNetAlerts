package com.safetyNet.safetyNetAlerts.repository;

import java.util.Collection;

public interface EntityRepository<T> {

    T find();
    void save(T value);
    void delete();
    void saveAll(Collection<T> list);
    Collection<T> findAll();
}
