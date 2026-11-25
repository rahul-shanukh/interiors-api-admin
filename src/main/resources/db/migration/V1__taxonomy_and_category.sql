CREATE TABLE taxonomy_type (
                               type VARCHAR(100) PRIMARY KEY,
                               CONSTRAINT chk_type_upper CHECK (type = UPPER(type))
);

CREATE TABLE taxonomy_schema (
                                 type  VARCHAR(100) NOT NULL REFERENCES taxonomy_type(type) ON DELETE CASCADE,
                                 value VARCHAR(100) NOT NULL,
                                 PRIMARY KEY (type, value),
                                 CONSTRAINT chk_value_lower CHECK (value = LOWER(value))
);

CREATE TABLE taxonomy_entry (
                                type         VARCHAR(100) NOT NULL,
                                value        VARCHAR(100) NOT NULL,
                                display_name VARCHAR(200) NOT NULL,
                                active       BOOLEAN      NOT NULL DEFAULT TRUE,
                                replaced_by  VARCHAR(100),
                                sort_order   INT,
                                created_at   TIMESTAMPTZ  NOT NULL,
                                updated_at   TIMESTAMPTZ  NOT NULL,
                                PRIMARY KEY (type, value),
                                FOREIGN KEY (type, value)       REFERENCES taxonomy_schema(type, value) ON DELETE RESTRICT,
                                FOREIGN KEY (type, replaced_by) REFERENCES taxonomy_schema(type, value),
                                CONSTRAINT chk_replaced_inactive CHECK (replaced_by IS NULL OR active = FALSE),
                                CONSTRAINT chk_not_self CHECK (replaced_by IS NULL OR replaced_by <> value)
);
CREATE INDEX idx_entry_active ON taxonomy_entry(type) WHERE active;

CREATE TABLE category_definition (
                                     category_key VARCHAR(100) PRIMARY KEY,
                                     display_name VARCHAR(200) NOT NULL,
                                     description  TEXT,
                                     active       BOOLEAN      NOT NULL DEFAULT TRUE,
                                     created_at   TIMESTAMPTZ  NOT NULL,
                                     updated_at   TIMESTAMPTZ  NOT NULL,
                                     CONSTRAINT chk_key_lower CHECK (category_key = LOWER(category_key))
);

CREATE TABLE category_field (
                                category_key  VARCHAR(100) NOT NULL REFERENCES category_definition(category_key) ON DELETE CASCADE,
                                field_key     VARCHAR(100) NOT NULL,
                                label         VARCHAR(200) NOT NULL,
                                group_name    VARCHAR(100),
                                taxonomy_type VARCHAR(100) REFERENCES taxonomy_type(type),
                                input_type    VARCHAR(20)  NOT NULL,
                                unit          VARCHAR(20),
                                required      BOOLEAN      NOT NULL DEFAULT FALSE,
                                sort_order    INT          NOT NULL,
                                active        BOOLEAN      NOT NULL DEFAULT TRUE,
                                PRIMARY KEY (category_key, field_key),
                                CONSTRAINT chk_input_type CHECK (input_type IN ('SELECT','MULTI_SELECT','TEXT','NUMBER')),
                                CONSTRAINT chk_taxonomy_rule CHECK (
                                    (input_type IN ('SELECT','MULTI_SELECT') AND taxonomy_type IS NOT NULL)
                                        OR (input_type IN ('TEXT','NUMBER') AND taxonomy_type IS NULL)
                                    )
);