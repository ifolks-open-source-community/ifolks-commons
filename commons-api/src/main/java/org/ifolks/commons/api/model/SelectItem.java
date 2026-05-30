package org.ifolks.commons.api.model;

import java.io.Serializable;

/**
 * Tuple immutable (Java Record) used for drop-down selections, options or key-value pairs.
 * 
 * @author Alexandre RUPP
 * @author Antigravity (Google DeepMind)
 */
public record SelectItem(String key, String label) implements Serializable, Comparable<SelectItem> {

	private static final long serialVersionUID = 1L;

	@Override
	public int compareTo(SelectItem selectItem) {
		if (this.label == null) {
			return (selectItem.label() == null) ? 0 : -1;
		} else {
			return (selectItem.label() == null) ? 1 : this.label.compareTo(selectItem.label());
		}
	}

	@Override
	public String toString() {
		return label != null ? label : "";
	}
}
