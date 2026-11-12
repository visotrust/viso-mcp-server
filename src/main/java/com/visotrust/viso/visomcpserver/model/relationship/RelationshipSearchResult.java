/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.relationship;

import java.util.List;

/**
 * One page of relationship search results.
 *
 * @param totalCount total number of relationships matching the filters, across all pages
 * @param page zero-based page number returned
 * @param size page size requested
 * @param hasMore true when more matching relationships exist beyond this page
 */
public record RelationshipSearchResult(
        long totalCount, int page, int size, boolean hasMore, List<Relationship> relationships) {}
