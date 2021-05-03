package com.mps.sample.filediff.dao;

import com.mps.sample.filediff.domain.Bucket;
import com.mps.sample.filediff.domain.FileDiff;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FileDiffRepository extends CrudRepository<Bucket, Long> {

    Optional<Bucket> findByName(String name);

    boolean existsByName(String id);

    @Query("select b.diff from Bucket b where b.name = :name")
    Optional<FileDiff> getFileDiff(@Param("name") String name);
}
