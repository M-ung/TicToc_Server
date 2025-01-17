package org.tictoc.tictoc.global.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageCustom<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private int size;
    private int number;
}