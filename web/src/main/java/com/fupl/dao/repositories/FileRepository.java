package com.fupl.dao.repositories;

import com.fupl.entityes.FileEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FileRepository extends CrudRepository<FileEntity, Integer> {

    FileEntity findByName(String name);

    List<FileEntity> findAll();

    @Query("SELECT f.name FROM FileEntity f")
    List<String> findAllNames();

    boolean existsByName(String name);
}
