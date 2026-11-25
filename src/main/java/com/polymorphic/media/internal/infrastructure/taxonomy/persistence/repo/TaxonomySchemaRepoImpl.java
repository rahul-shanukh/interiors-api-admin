package com.polymorphic.media.internal.infrastructure.taxonomy.persistence.repo;

import com.polymorphic.media.internal.domain.taxonomy.repo.TaxonomySchemaRepository;
import com.polymorphic.media.internal.domain.taxonomy.types.TaxonomySchema;
import com.polymorphic.media.internal.infrastructure.taxonomy.persistence.entity.TaxonomySchemaEntity;
import com.polymorphic.media.internal.infrastructure.taxonomy.persistence.jpa.TaxonomySchemaJpaRepository;
import com.polymorphic.media.internal.infrastructure.taxonomy.persistence.mapper.TaxonomySchemaMapperDoc;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaxonomySchemaRepoImpl implements TaxonomySchemaRepository {

    private final TaxonomySchemaJpaRepository jpaRepository;
    private final TaxonomySchemaMapperDoc mapper;

    @Override
    public TaxonomySchema save(TaxonomySchema schema) {
        TaxonomySchemaEntity entity = mapper.toEntity(schema);

        TaxonomySchemaEntity saved = jpaRepository.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public List<TaxonomySchema> saveAll(Iterable<TaxonomySchema> schemas) {
        return List.of();
    }

    @Override
    public Optional<TaxonomySchema> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<TaxonomySchema> findByType(String type) {
        return jpaRepository.findById(type).map(mapper::toDomain);
    }

    @Override
    public List<TaxonomySchema> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Page<TaxonomySchema> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public boolean existsByType(String type) {
        return jpaRepository.existsById(type);
    }

    @Override
    public long count() {
        return 0;
    }
}