package com.polymorphic.media.internal.application.taxonomy.exception;

public class DuplicateTaxonomyException extends RuntimeException {

    public DuplicateTaxonomyException(String taxonomyType) {
        super("Taxonomy already exists: " + taxonomyType);
    }
}

