package nl.jochemkuijpers.vdb.dice.ast;

import nl.jochemkuijpers.vdb.dice.DiceTokenType;
import nl.jochemkuijpers.vdb.token.Token;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class Literal implements Node {

	/** The string content describing the literal. */
	@Nonnull private final Token<DiceTokenType> token;

	protected Literal(@Nonnull Token<DiceTokenType> token) {
		this.token = token;
	}

	@Nonnull
	public Token<DiceTokenType> getToken() {
		return token;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public void buildExpressionString(StringBuilder stringBuilder) {
		stringBuilder.append(token.srcString());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Literal that = (Literal) o;
		return token.equals(that.token);
	}

	@Override
	public int hashCode() {
		return Objects.hash(token);
	}
}
