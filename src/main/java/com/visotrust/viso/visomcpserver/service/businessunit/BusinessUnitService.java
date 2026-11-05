/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.service.businessunit;

import com.visotrust.viso.visomcpserver.model.businessunit.BusinessUnit;
import com.visotrust.viso.visomcpserver.service.ApiService;
import java.util.List;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class BusinessUnitService {

    private static final String BUSINESS_UNITS_API_PATH = "/api/v1/business-units";
    private final ApiService apiService;

    public BusinessUnitService(ApiService apiService) {
        this.apiService = apiService;
    }

    @Tool(
            name = "get_all_business_units",
            description =
                    "Get all business units in the organization, sorted by name. Use the id to filter "
                            + "search_relationships by businessUnitIds, and the name as the businessUnit when creating a user.")
    public List<BusinessUnit> getAllBusinessUnits() {
        return apiService.getList(BUSINESS_UNITS_API_PATH, BusinessUnit.class);
    }
}
