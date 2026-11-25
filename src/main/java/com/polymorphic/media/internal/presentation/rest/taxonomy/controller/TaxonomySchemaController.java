package com.polymorphic.media.internal.presentation.rest.taxonomy.controller;

import com.polymorphic.media.internal.application.taxonomy.contract.TaxonomySchemaService;
import com.polymorphic.media.internal.application.taxonomy.mapper.TaxonomyDtoMapper;
import com.polymorphic.media.internal.presentation.rest.common.dto.ApiResponse;
import com.polymorphic.media.internal.presentation.rest.taxonomy.dto.request.TaxonomySchemaCreateRequest;
import com.polymorphic.media.internal.presentation.rest.taxonomy.dto.request.TaxonomySchemaDescriptionUpdateRequest;
import com.polymorphic.media.internal.presentation.rest.taxonomy.dto.request.TaxonomySchemaValuesUpdateRequest;
import com.polymorphic.media.internal.presentation.rest.taxonomy.dto.request.TaxonomyValueStatusRequest;
import com.polymorphic.media.internal.presentation.rest.taxonomy.dto.response.TaxonomySchemaResponse;
import com.polymorphic.media.internal.presentation.rest.taxonomy.dto.response.TaxonomyValueResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/taxonomies")
@Tag(name = "Taxonomy Schemas", description = "Manage canonical taxonomy types and allowed values")
public class TaxonomySchemaController {


    private final TaxonomySchemaService taxonomySchemaService;
    private final TaxonomyDtoMapper taxonomyDtoMapper;

    public TaxonomySchemaController(
            TaxonomySchemaService taxonomySchemaService,
            TaxonomyDtoMapper taxonomyDtoMapper
    ) {
        this.taxonomySchemaService = taxonomySchemaService;
        this.taxonomyDtoMapper = taxonomyDtoMapper;
    }

    @PostMapping(version = "1")
    @Operation(summary = "Create a new taxonomy schema")
    public ResponseEntity<ApiResponse<TaxonomySchemaResponse>> createSchema(
            @Valid @RequestBody TaxonomySchemaCreateRequest request
    ) {
        var command = taxonomyDtoMapper.toCreateCommand(request);
        var schema = taxonomySchemaService.createSchema(command);
        var response = taxonomyDtoMapper.toResponse(schema);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{type}")
                .buildAndExpand(response.type())
                .toUri();

        String path  = ServletUriComponentsBuilder.fromCurrentRequestUri().build().getPath();

        return ResponseEntity
                .created(location)
                .body(ApiResponse.success("Taxonomy schema created successfully", response , path));
    }

    @GetMapping(value = "/{type}", version = "1")
    @Operation(summary = "Get a taxonomy schema by type")
    public ResponseEntity<ApiResponse<TaxonomySchemaResponse>> getSchema(
            @PathVariable @NotBlank String type
    ) {
        var schema = taxonomySchemaService.getSchema(type);
        var response = taxonomyDtoMapper.toResponse(schema);

        return ResponseEntity.ok(
                ApiResponse.success("Taxonomy schema retrieved successfully", response)
        );
    }

    @GetMapping(version = "1")
    @Operation(summary = "List all taxonomy schemas")
    public ResponseEntity<ApiResponse<List<TaxonomySchemaResponse>>> getAllSchemas() {
        var response = taxonomySchemaService.getAllSchemas().stream()
                .map(taxonomyDtoMapper::toResponse)
                .toList();

        return ResponseEntity.ok(
                ApiResponse.success("Taxonomy schemas retrieved successfully", response)
        );
    }

    @PatchMapping(value = "/{type}/description", version = "1")
    @Operation(summary = "Update taxonomy schema description")
    public ResponseEntity<ApiResponse<TaxonomySchemaResponse>> updateDescription(
            @PathVariable @NotBlank String type,
            @Valid @RequestBody TaxonomySchemaDescriptionUpdateRequest request
    ) {
        var schema = taxonomySchemaService.updateDescription(type, request.description());
        var response = taxonomyDtoMapper.toResponse(schema);

        return ResponseEntity.ok(
                ApiResponse.success("Taxonomy description updated successfully", response)
        );
    }

    @PostMapping(value = "/{type}/values", version = "1")
    @Operation(summary = "Append new allowed values to a taxonomy schema")
    public ResponseEntity<ApiResponse<TaxonomySchemaResponse>> addValues(
            @PathVariable @NotBlank String type,
            @Valid @RequestBody TaxonomySchemaValuesUpdateRequest request
    ) {
        var schema = taxonomySchemaService.addValues(type, request.values(), request.reason());
        var response = taxonomyDtoMapper.toResponse(schema);

        return ResponseEntity.ok(
                ApiResponse.success("Taxonomy values added successfully", response)
        );
    }

    @GetMapping(value = "/{type}/values", version = "1")
    @Operation(summary = "Get all values under a taxonomy type")
    public ResponseEntity<ApiResponse<List<TaxonomyValueResponse>>> getValues(
            @PathVariable @NotBlank String type
    ) {
        var response = taxonomySchemaService.getValues(type).stream()
                .map(taxonomyDtoMapper::toValueResponse)
                .toList();

        return ResponseEntity.ok(
                ApiResponse.success("Taxonomy values retrieved successfully", response)
        );
    }

    @PatchMapping(value = "/{type}/values/{value}/status", version = "1")
    @Operation(summary = "Activate or deprecate a taxonomy value")
    public ResponseEntity<ApiResponse<TaxonomyValueResponse>> updateValueStatus(
            @PathVariable @NotBlank String type,
            @PathVariable @NotBlank String value,
            @Valid @RequestBody TaxonomyValueStatusRequest request
    ) {
        var updatedValue = taxonomySchemaService.updateValueStatus(
                type,
                value,
                request.status(),
                request.reason()
        );
        var response = taxonomyDtoMapper.toValueResponse(updatedValue);

        return ResponseEntity.ok(
                ApiResponse.success("Taxonomy value status updated successfully", response)
        );
    }
}