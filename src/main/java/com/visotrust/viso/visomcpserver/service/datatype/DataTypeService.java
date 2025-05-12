/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.service.datatype;

import com.visotrust.viso.visomcpserver.model.datatype.DataTypeView;
import com.visotrust.viso.visomcpserver.service.ApiService;
import java.util.List;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class DataTypeService {

    private static final String DATA_TYPES_API_PATH = "/api/v1/data-types";
    private final ApiService apiService;

    public DataTypeService(ApiService apiService) {
        this.apiService = apiService;
    }

    @Tool(
            name = "get_all_datatypes",
            description = "Get all available data types for your organization.")
    public List<DataTypeView> getAllDataTypes() {
        return apiService.getList(DATA_TYPES_API_PATH, DataTypeView.class);
    }
}
