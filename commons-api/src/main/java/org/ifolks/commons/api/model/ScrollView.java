package org.ifolks.commons.api.model;

import java.util.List;

public record ScrollView<T extends Record> (
/**
* global number of elements
*/
Long size,
/**
* number of filtered elements
*/
Long count,
Long numberOfPages,
Long currentPage,
List<T> elements
) {
}
