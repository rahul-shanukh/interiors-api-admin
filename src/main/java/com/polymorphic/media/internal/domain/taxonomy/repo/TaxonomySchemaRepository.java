
package com.polymorphic.media.internal.domain.taxonomy.repo;

import com.polymorphic.media.internal.domain.taxonomy.types.TaxonomySchema;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TaxonomySchemaRepository {

    // -------------------- Write Operations --------------------

    TaxonomySchema save(TaxonomySchema schema);

    List<TaxonomySchema> saveAll(Iterable<TaxonomySchema> schemas);

    // -------------------- Read Operations --------------------

    Optional<TaxonomySchema> findById(Long id);

    Optional<TaxonomySchema> findByType(String type);

    List<TaxonomySchema> findAll();

    Page<TaxonomySchema> findAll(Pageable pageable);

    // -------------------- Existence Operations --------------------

    boolean existsById(Long id);

    boolean existsByType(String type);

    long count();

    // DEFAULT METHOD
    default boolean isSchemaAvailable(String type) {
        return existsByType(type);
    }

    // STATIC METHOD
    static boolean isValidType(String type) {
        return type != null && !type.isBlank();
    }

}
