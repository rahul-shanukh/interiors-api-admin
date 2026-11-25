package com.polymorphic.media.internal.infrastructure.taxonomy.persistence.jpa;

import com.polymorphic.media.internal.infrastructure.taxonomy.persistence.entity.TaxonomySchemaEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxonomySchemaJpaRepository
        extends JpaRepository<TaxonomySchemaEntity, String> {

    @EntityGraph(attributePaths = "values")
    @Override
    List<TaxonomySchemaEntity> findAll();
}