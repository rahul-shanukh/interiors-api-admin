
package com.polymorphic.media.internal.infrastructure.taxonomy.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "taxonomy_schemas")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxonomySchemaEntity {

    @Id
    @Column(name = "type", nullable = false, updatable = false, length = 64)
    private String type;

    @Column(name = "description")
    private String description;

    @OneToMany(
            mappedBy = "schema",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<TaxonomySchemaValueEntity> values = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
