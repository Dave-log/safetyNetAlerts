package com.safetyNet.safetyNetAlerts.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class LoggingHashMap<K, V> extends HashMap<K, V> {

    private static final Logger logger = LogManager.getLogger(LoggingHashMap.class);

    @Override
    public V put(K key, V value) {
        logger.info("PUT operation - Key: " + key + ", Value: " + value);
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        logger.info("PUT ALL operation");
        super.putAll(m);
    }

    @Override
    public V remove(Object key) {
        logger.info("REMOVE operation - Key: " + key);
        return super.remove(key);
    }

}
