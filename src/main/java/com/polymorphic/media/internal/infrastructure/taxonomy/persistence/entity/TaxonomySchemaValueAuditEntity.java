
package com.polymorphic.media.internal.infrastructure.taxonomy.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;

@Entity
@Table(name = "taxonomy_schema_value_audit")
@Getter
@Setter
public class TaxonomySchemaValueAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schema_value_id", nullable = false)
    private TaxonomySchemaValueEntity schemaValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false, length = 32)
    private TaxonomyAuditAction action;

    @Column(name = "previous_value", length = 128)
    private String previousValue;

    @Column(name = "new_value", length = 128)
    private String newValue;

    @Column(name = "previous_status", length = 32)
    private String previousStatus;

    @Column(name = "new_status", length = 32)
    private String newStatus;

    @Column(name = "reason")
    private String reason;

    @Column(name = "changed_by", length = 128)
    private String changedBy;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "jsonb")
    private Map<String, Object> metadata;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }
}
