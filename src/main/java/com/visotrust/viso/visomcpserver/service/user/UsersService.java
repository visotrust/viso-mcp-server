/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.service.user;

import com.visotrust.viso.visomcpserver.model.common.VisoUser;
import com.visotrust.viso.visomcpserver.model.user.UserCreateRequest;
import com.visotrust.viso.visomcpserver.service.ApiService;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.validation.Valid;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class UsersService {

    private static final String USERS_API_PATH = "/api/v1/users";
    private final ApiService apiService;

    public UsersService(ApiService apiService) {
        this.apiService = apiService;
    }

    @Tool(
            name = "get_all_users",
            description =
                    "Get all users in your organization. Supports pagination with page, size, and sort parameters (e.g., sort=lastName,asc).")
    public List<VisoUser> getAllUsers(
            @ToolParam(description = "Results page you want to retrieve (0..N)") Integer page,
            @ToolParam(description = "Number of records per page") Integer size,
            @ToolParam(
                            description =
                                    "Sorting criteria in the format: property(,asc|desc). Multiple sort criteria not currently supported via tool.")
                    String sort) {
        int p = page == null ? 0 : page;
        int s = size == null ? 20 : size;
        String sortParam = sort == null ? null : encode(sort);
        StringBuilder path =
                new StringBuilder(USERS_API_PATH)
                        .append("?page=")
                        .append(p)
                        .append("&size=")
                        .append(s);
        if (sortParam != null && !sortParam.isBlank()) {
            path.append("&sort=").append(sortParam);
        }
        return apiService.getList(path.toString(), VisoUser.class);
    }

    @Tool(name = "get_user_by_email", description = "Get a single user by their email address")
    public VisoUser getUserByEmail(
            @ToolParam(description = "The user's email address") String email) {
        return apiService.get(USERS_API_PATH + "/" + encode(email), VisoUser.class);
    }

    @Tool(name = "create_user", description = "Create a new user in your organization")
    public VisoUser createUser(@Valid UserCreateRequest request) {
        return apiService.post(USERS_API_PATH, request, VisoUser.class);
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
