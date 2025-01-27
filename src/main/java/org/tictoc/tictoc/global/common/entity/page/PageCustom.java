package org.tictoc.tictoc.global.common.entity.page;

import java.util.List;

public record PageCustom<T> (
        List<T> content,
        int totalPages,
        long totalElements,
        int size,
        int number
) {}