package com.polymorphic.media.internal.application.taxonomy.mapper;

import com.polymorphic.media.internal.application.taxonomy.command.TaxonomySchemaCreateCommand;
import com.polymorphic.media.internal.application.taxonomy.result.TaxonomySchemaResult;
import com.polymorphic.media.internal.domain.taxonomy.types.TaxonomySchema;
import com.polymorphic.media.internal.domain.taxonomy.valueobjects.TaxonomyType;
import com.polymorphic.media.internal.domain.taxonomy.valueobjects.TaxonomyValue;
import com.polymorphic.media.internal.presentation.rest.taxonomy.dto.request.TaxonomySchemaCreateRequest;
import com.polymorphic.media.internal.presentation.rest.taxonomy.dto.response.TaxonomySchemaResponse;
import com.polymorphic.media.internal.presentation.rest.taxonomy.dto.response.TaxonomyValueResponse;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TaxonomyDtoMapper {

    // Presentation DTO -> Application Command
    TaxonomySchemaCreateCommand toCreateCommand(
            TaxonomySchemaCreateRequest request
    );

    // Result -> Presentation DTO
    TaxonomySchemaResponse toResponse(
            TaxonomySchemaResult result
    );

    // Domain Model -> Presentation Response
    @Mapping(target = "type", source = "type.value")
    TaxonomySchemaResponse toResponse(
            TaxonomySchema schema
    );

    // Domain Value Object -> Presentation Response
    @Mapping(target = "value", source = "value")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    TaxonomyValueResponse toValueResponse(
            TaxonomyValue value
    );

    // TaxonomyType -> String
    default String map(TaxonomyType type) {
        return type == null ? null : type.value();
    }

    // String -> TaxonomyType
    default TaxonomyType mapToTaxonomyType(String type) {
        return type == null ? null : new TaxonomyType(type);
    }

    // TaxonomyValue -> String
    default String map(TaxonomyValue value) {
        return value == null ? null : value.value();
    }

    // String -> TaxonomyValue
    default TaxonomyValue mapToTaxonomyValue(String value) {
        return value == null ? null : new TaxonomyValue(value);
    }
}
