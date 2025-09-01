package com.demo.service.entrypoint.provinces.params;

import java.util.HashMap;
import java.util.Map;

import com.demo.commons.validations.ParamMapper;

import org.springframework.stereotype.Component;

@Component
public class ProvinceParamMapper implements ParamMapper<ProvinceParam> {

  private static final String DEPARTMENT_ID_KEY = "departmentId";

  @Override
  public Map.Entry<ProvinceParam, Map<String, String>> map(Map<String, String> params) {
    ProvinceParam param = ProvinceParam.builder()
        .departmentId(params.get(DEPARTMENT_ID_KEY))
        .build();

    Map<String, String> paramMap = new HashMap<>();
    paramMap.put(DEPARTMENT_ID_KEY, param.getDepartmentId());

    return Map.entry(param, paramMap);
  }

  @Override
  public boolean supports(Class<?> paramClass) {
    return ProvinceParam.class.isAssignableFrom(paramClass);
  }
}
