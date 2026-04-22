/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.service.vendordirectory;

import com.visotrust.viso.visomcpserver.model.vendordirectory.VendorDirectoryView;
import com.visotrust.viso.visomcpserver.service.ApiService;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class VendorDirectoryService {

    private static final String VENDOR_DIRECTORY_SEARCH_PATH = "/api/v1/directory/search";
    private final ApiService apiService;

    public VendorDirectoryService(ApiService apiService) {
        this.apiService = apiService;
    }

    @Tool(
            name = "search_vendor_directory",
            description =
                    "Look up a vendor in the VISO TRUST vendor directory by URL or domain name. Returns basic vendor metadata (name, homepage, description, favicon, known domains).")
    public VendorDirectoryView searchVendorDirectory(
            @ToolParam(description = "The URL or domain name to search for, e.g. example.com")
                    String urlOrDomain) {
        String encoded = URLEncoder.encode(urlOrDomain, StandardCharsets.UTF_8);
        return apiService.get(
                VENDOR_DIRECTORY_SEARCH_PATH + "?urlOrDomain=" + encoded,
                VendorDirectoryView.class);
    }
}
