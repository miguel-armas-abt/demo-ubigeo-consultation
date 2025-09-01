package com.demo.service.entrypoint.districts.rest;

import java.util.Map;

import com.demo.commons.restserver.utils.RestServerUtils;
import com.demo.commons.validations.headers.DefaultHeaders;
import com.demo.commons.validations.ParamValidator;
import com.demo.service.entrypoint.districts.params.DistrictParam;
import com.demo.service.entrypoint.districts.repository.entity.DistrictEntity;
import com.demo.service.entrypoint.districts.service.DistrictService;
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
public class DistrictHandler {

  private final DistrictService districtService;
  private final ParamValidator paramValidator;

  public Mono<ServerResponse> findByProvinceIdAndDepartmentId(ServerRequest serverRequest) {
    Mono<DistrictParam> paramsMono = paramValidator.validateQueryParamsAndGet(serverRequest, DistrictParam.class).map(Map.Entry::getKey);

    Flux<DistrictEntity> response = paramValidator.validateHeadersAndGet(serverRequest, DefaultHeaders.class)
        .zipWith(paramsMono)
        .flatMapMany(tuple -> {
          DistrictParam params = tuple.getT2();
          return districtService.findByProvinceIdAndDepartmentId(params.getProvinceId(), params.getDepartmentId());
        });

    return ServerResponse.ok()
        .headers(httpHeaders -> RestServerUtils.buildResponseHeaders(serverRequest.headers()).accept(httpHeaders))
        .contentType(MediaType.APPLICATION_NDJSON)
        .body(BodyInserters.fromPublisher(response, DistrictEntity.class));
  }
}
