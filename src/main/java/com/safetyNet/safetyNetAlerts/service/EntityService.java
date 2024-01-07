package com.safetyNet.safetyNetAlerts.service;

import java.util.Collection;
import java.util.List;

public interface EntityService<T> {

    void create(T value);
    void creates(List<T> list);
    Collection<T> findAll();
    T update(T value);
    T delete();
}
