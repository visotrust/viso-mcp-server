/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.businessunit;

/**
 * @param id usable in search_relationships as a businessUnitIds value
 * @param name usable as the businessUnit value when creating a user
 */
public record BusinessUnit(Long id, String name) {}
