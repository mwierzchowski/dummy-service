package com.github.mwierzchowski.dummy.core;

import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = Dummy.class, idClass = Integer.class)
public interface DummyRepository {
    List<Dummy> findAll();
    void save(Dummy dummy);
}
