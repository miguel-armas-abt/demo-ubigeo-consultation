package com.demo.service.entrypoint.ubigeo.rest;

import java.util.Map;

import com.demo.commons.restserver.utils.RestServerUtils;
import com.demo.commons.validations.headers.DefaultHeaders;
import com.demo.commons.validations.ParamValidator;
import com.demo.service.entrypoint.ubigeo.params.UbigeoParam;
import com.demo.service.entrypoint.ubigeo.service.UbigeoService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
@RequiredArgsConstructor
public class UbigeoHandler {

  private final UbigeoService ubigeoService;
  private final ParamValidator paramValidator;

  public Mono<ServerResponse> findUbigeo(ServerRequest serverRequest) {
    Mono<UbigeoParam> paramsMono = paramValidator.validateQueryParamsAndGet(serverRequest, UbigeoParam.class).map(Map.Entry::getKey);

    return paramValidator.validateHeadersAndGet(serverRequest, DefaultHeaders.class)
        .zipWith(paramsMono)
        .flatMap(tuple -> {
          UbigeoParam params = tuple.getT2();
          String ubigeo = params.getDepartmentId() + params.getProvinceId() + params.getDistrictId();
          return ubigeoService.findUbigeo(ubigeo);
        })
        .flatMap(response -> ServerResponse.ok()
            .headers(httpHeaders -> RestServerUtils.buildResponseHeaders(serverRequest.headers()).accept(httpHeaders))
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(response)));
  }
}
