package com.safetyNet.safetyNetAlerts.repository;

import com.safetyNet.safetyNetAlerts.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person,Long> {}
