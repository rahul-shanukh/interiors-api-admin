package com.polymorphic.media.internal.application.taxonomy.exception;

public class TaxonomyNotFoundException extends RuntimeException {

    public TaxonomyNotFoundException(String taxonomyType) {
        super("Taxonomy not found: " + taxonomyType);
    }
}

