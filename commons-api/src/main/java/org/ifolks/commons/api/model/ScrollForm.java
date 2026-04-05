package org.ifolks.commons.api.model;

import java.io.Serializable;

public record ScrollForm<F extends Serializable, S extends Serializable>(
		F filter,
		S sorting,
		Long page,
		Long elementsPerPage) implements Serializable {

	private static final long serialVersionUID = 1L;

	public ScrollForm {
		if (page == null) {
			page = 1L;
		}
		if (elementsPerPage == null) {
			elementsPerPage = 10L;
		}
	}

	public ScrollForm() {
		this(null, null, 1L, 10L);
	}
}
