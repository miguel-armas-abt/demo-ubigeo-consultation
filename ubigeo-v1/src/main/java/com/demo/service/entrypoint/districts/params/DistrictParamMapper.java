package com.demo.service.entrypoint.districts.params;

import java.util.HashMap;
import java.util.Map;

import com.demo.commons.validations.ParamMapper;

import org.springframework.stereotype.Component;

@Component
public class DistrictParamMapper implements ParamMapper<DistrictParam> {

  private static final String DEPARTMENT_ID_KEY = "departmentId";
  private static final String PROVINCE_ID_KEY = "provinceId";

  @Override
  public Map.Entry<DistrictParam, Map<String, String>> map(Map<String, String> params) {
    DistrictParam param = DistrictParam.builder()
        .departmentId(params.get(DEPARTMENT_ID_KEY))
        .provinceId(params.get(PROVINCE_ID_KEY))
        .build();

    Map<String, String> paramMap = new HashMap<>();
    paramMap.put(DEPARTMENT_ID_KEY, param.getDepartmentId());
    paramMap.put(PROVINCE_ID_KEY, param.getProvinceId());

    return Map.entry(param, paramMap);
  }

  @Override
  public boolean supports(Class<?> paramClass) {
    return DistrictParam.class.isAssignableFrom(paramClass);
  }
}
