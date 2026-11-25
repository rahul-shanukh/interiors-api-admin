package com.interiors.interiorsadminapi.configuration;

import com.polymorphic.media.internal.application.taxonomy.contract.TaxonomySchemaService;
import com.polymorphic.media.internal.application.taxonomy.exception.TaxonomyNotFoundException;
import com.polymorphic.media.internal.application.taxonomy.mapper.TaxonomyDtoMapper;
import com.polymorphic.media.internal.presentation.rest.config.ApiVersionConfiguration;
import com.polymorphic.media.internal.presentation.rest.exception.GlobalApiExceptionHandler;

import com.polymorphic.media.internal.presentation.rest.taxonomy.controller.TaxonomySchemaController;
import com.polymorphic.media.internal.presentation.rest.taxonomy.exception.TaxonomyExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; 


@WebMvcTest(controllers = TaxonomySchemaController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({
        TaxonomySchemaController.class,
        TaxonomyExceptionHandler.class,
        ApiVersionConfiguration.class,
        GlobalApiExceptionHandler.class
})
class ApiVersionConfigurationTest {

    @MockitoBean
    private TaxonomySchemaService taxonomySchemaService;

    @MockitoBean
    private TaxonomyDtoMapper taxonomyDtoMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAcceptSupportedApiVersion() throws Exception {
        org.mockito.Mockito.when(taxonomySchemaService.getAllSchemas())
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/taxonomies"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRejectVersionWithoutPrefix() throws Exception {
        mockMvc.perform(get("/api/1/taxonomies"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectSemanticVersion() throws Exception {
        mockMvc.perform(get("/api/v1.2/taxonomies"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectZeroVersion() throws Exception {
        mockMvc.perform(get("/api/v0/taxonomies"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectMalformedVersion() throws Exception {
        mockMvc.perform(get("/api/foo1/taxonomies"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectUnsupportedApiVersion() throws Exception {
        mockMvc.perform(get("/api/v99/taxonomies"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnTaxonomyNotFoundResponse() throws Exception {
        org.mockito.Mockito.when(taxonomySchemaService.getSchema("unknown"))
                .thenThrow(new TaxonomyNotFoundException("unknown"));

        mockMvc.perform(get("/api/v1/taxonomies/unknown"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error.code").value("TAXONOMY_NOT_FOUND"));
    }

    @Test
    void shouldReturnTaxonomyValidationResponse() throws Exception {
        mockMvc.perform(post("/api/v1/taxonomies")
                        .contentType("application/json")
                        .content("{\"type\":\"\",\"description\":\"test\",\"values\":[]}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error.code").value("VALIDATION_ERROR"));
    }
}
