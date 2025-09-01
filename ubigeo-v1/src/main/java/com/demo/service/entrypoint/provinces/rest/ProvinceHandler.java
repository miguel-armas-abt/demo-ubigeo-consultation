package com.demo.service.entrypoint.provinces.rest;

import java.util.Map;

import com.demo.commons.restserver.utils.RestServerUtils;
import com.demo.commons.validations.headers.DefaultHeaders;
import com.demo.commons.validations.ParamValidator;
import com.demo.service.entrypoint.provinces.params.ProvinceParam;
import com.demo.service.entrypoint.provinces.repository.entity.ProvinceEntity;
import com.demo.service.entrypoint.provinces.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
@RequiredArgsConstructor
public class ProvinceHandler {

  private final ProvinceService provinceService;
  private final ParamValidator paramValidator;

  public Mono<ServerResponse> findByDepartmentId(ServerRequest serverRequest) {
    Mono<ProvinceParam> paramsMono = paramValidator.validateQueryParamsAndGet(serverRequest, ProvinceParam.class).map(Map.Entry::getKey);

    Flux<ProvinceEntity> response = paramValidator.validateHeadersAndGet(serverRequest, DefaultHeaders.class)
        .zipWith(paramsMono)
        .flatMapMany(tuple -> {
          ProvinceParam params = tuple.getT2();
          return provinceService.findByDepartmentId(params.getDepartmentId());
        });

    return ServerResponse.ok()
        .headers(httpHeaders -> RestServerUtils.buildResponseHeaders(serverRequest.headers()).accept(httpHeaders))
        .contentType(MediaType.APPLICATION_NDJSON)
        .body(BodyInserters.fromPublisher(response, ProvinceEntity.class));
  }
}
