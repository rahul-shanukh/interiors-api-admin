package com.polymorphic.media.internal.infrastructure.taxonomy.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "taxonomy_schema_value")
@Getter
@Setter
public class TaxonomySchemaValueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value", nullable = false, length = 128)
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private TaxonomyValueStatus status = TaxonomyValueStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schema_type", nullable = false)
    private TaxonomySchemaEntity schema;

    @OneToMany(
            mappedBy = "schemaValue",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<TaxonomySchemaValueAuditEntity> history = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        createdAt = Instant.now();
    }
}