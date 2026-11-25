package com.polymorphic.media.internal.presentation.rest.config;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ApiVersionParser;
import org.springframework.web.accept.InvalidApiVersionException;
import org.springframework.web.accept.SemanticApiVersionParser;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration(proxyBeanMethods = false)
@NullMarked
public class ApiVersionConfiguration implements WebMvcConfigurer {

    private static final String API_BASE_PACKAGE =
            "com.polymorphic.media.internal.presentation.rest";

    private static final int API_VERSION_PATH_SEGMENT = 1;

    private static final Pattern URL_VERSION_PATTERN =
            Pattern.compile("v[1-9]\\d*");

    private static final Pattern INTERNAL_VERSION_PATTERN =
            Pattern.compile("[1-9]\\d*");

    private static final SemanticApiVersionParser VERSION_PARSER =
            new SemanticApiVersionParser();

    private static final Set<String> SUPPORTED_VERSION = Set.of("1" , "2");

    private final String apiPathPrefix;

    public ApiVersionConfiguration(
            @Value("${app.api.path-prefix:/api}") String apiPathPrefix
    ) {
        this.apiPathPrefix = apiPathPrefix;
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(
                apiPathPrefix + "/{version}",
                HandlerTypePredicate.forBasePackage(API_BASE_PACKAGE)
        );
    }

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer.usePathSegment(API_VERSION_PATH_SEGMENT);
        configurer.setVersionParser(STRICT_VERSION_PARSER);
        configurer.addSupportedVersions(SUPPORTED_VERSION.toArray(String[]::new));
    }


    private static final ApiVersionParser<SemanticApiVersionParser.Version>
            STRICT_VERSION_PARSER = version -> {
        Pattern pattern = version.startsWith("v")
                ? URL_VERSION_PATTERN
                : INTERNAL_VERSION_PATTERN;

        if (!pattern.matcher(version).matches()) {
            throw new InvalidApiVersionException(version);
        }

        String normalized = version.startsWith("v")
                ? version.substring(1)
                : version;

        return VERSION_PARSER.parseVersion(normalized);
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    Object handler
            ) throws Exception {
                String path = request.getRequestURI();
                String apiPrefixWithSlash = apiPathPrefix + "/";

                if (!path.startsWith(apiPrefixWithSlash)) {
                    return true;
                }

                String pathAfterPrefix = path.substring(apiPrefixWithSlash.length());
                int slashIndex = pathAfterPrefix.indexOf('/');
                String rawVersion = slashIndex >= 0
                        ? pathAfterPrefix.substring(0, slashIndex)
                        : pathAfterPrefix;

                if (!rawVersion.isBlank()
                        && URL_VERSION_PATTERN.matcher(rawVersion).matches()) {
                    String numericVersion = rawVersion.substring(1);
                    if (SUPPORTED_VERSION.contains(numericVersion)) {
                        return true;
                    }
                }

                writeInvalidVersionResponse(response);
                return false;
            }
        });
    }

    private void writeInvalidVersionResponse(HttpServletResponse response)
            throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(
                "{\"success\":false,\"message\":\"API version error\"," +
                "\"error\":{\"code\":\"INVALID_API_VERSION\"," +
                "\"details\":\"The requested API version is invalid or unsupported.\"," +
                "\"fieldErrors\":[]}}"
        );
    }

}
