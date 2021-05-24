package nl.jochemkuijpers.vdb.token;

import java.util.Objects;

public class TokenType {
	private final String type;

	protected TokenType(String type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		return this == o;
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}

	@Override
	public String toString() {
		return type;
	}
}
