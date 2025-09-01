package com.demo.service.entrypoint.ubigeo.params;

import java.util.HashMap;
import java.util.Map;

import com.demo.commons.validations.ParamMapper;

public class UbigeoParamMapper implements ParamMapper<UbigeoParam> {

  private static final String DEPARTMENT_ID_KEY = "departmentId";
  private static final String PROVINCE_ID_KEY = "provinceId";
  private static final String DISTRICT_ID_KEY = "districtId";

  @Override
  public Map.Entry<UbigeoParam, Map<String, String>> map(Map<String, String> params) {
    UbigeoParam param = UbigeoParam.builder()
        .departmentId(params.get(DEPARTMENT_ID_KEY))
        .provinceId(params.get(PROVINCE_ID_KEY))
        .districtId(params.get(DISTRICT_ID_KEY))
        .build();

    Map<String, String> paramMap = new HashMap<>();
    paramMap.put(DEPARTMENT_ID_KEY, param.getDepartmentId());
    paramMap.put(PROVINCE_ID_KEY, param.getProvinceId());
    paramMap.put(DISTRICT_ID_KEY, param.getDistrictId());

    return Map.entry(param, paramMap);
  }

  @Override
  public boolean supports(Class<?> paramClass) {
    return UbigeoParam.class.isAssignableFrom(paramClass);
  }
}
