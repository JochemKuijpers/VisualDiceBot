package nl.jochemkuijpers.vdb.dice;

import nl.jochemkuijpers.vdb.dice.ast.*;
import nl.jochemkuijpers.vdb.token.Token;

import java.util.List;

public class DiceParser {
	/** Maximum recursion depth. */
	private static final int MAX_RECURSION = 128;

	private final List<Token<DiceTokenType>> tokens;

	private int pos;
	private int level;

	private Node result;

	private String error;

	private Token<DiceTokenType> errorToken;

	public DiceParser(List<Token<DiceTokenType>> tokens) {
		this.tokens = tokens;
	}

	public Node parse() {
		if (result == null) {
			pos = 0;
			level = 0;
			Node result = parseExpression();
			if (!isEof()) {
				setError("Input until here is a complete expression, but there is more input left.", peek());
				return null;
			}
			this.result = result;
		}
		return result;
	}

	private Node parseExpression() {
		if (level > MAX_RECURSION) { setError("Reached parser recursion limit.", peek()); return null; }
		level += 1;

		Node expression = parseFactor();

		while (match(DiceTokenType.MINUS) || match(DiceTokenType.PLUS)) {
			Token<DiceTokenType> operator = previous();
			Node right = parseFactor();
			if (right == null) { setError("Second operand did not parse correctly.", operator); return null; }

			if (operator.type() == DiceTokenType.MINUS) {
				expression = new BinaryMinusNode(operator, expression, right);
			} else if (operator.type() == DiceTokenType.PLUS) {
				expression = new BinaryPlusNode(operator, expression, right);
			}
		}

		level--;
		return expression;
	}

	private Node parseFactor() {
		if (level > MAX_RECURSION) { setError("Reached parser recursion limit.", peek()); return null; }
		level += 1;

		Node expression = parseUnary();

		while (match(DiceTokenType.DIV) || match(DiceTokenType.MULT)) {
			Token<DiceTokenType> operator = previous();
			Node right = parseUnary();
			if (right == null) { setError("Right-hand side operand did not parse correctly.", operator); return null; }

			if (operator.type() == DiceTokenType.DIV) {
				expression = new DivideNode(operator, expression, right);
			} else if (operator.type() == DiceTokenType.MULT) {
				expression = new MultiplyNode(operator, expression, right);
			}
		}

		level -= 1;
		return expression;
	}

	private Node parseUnary() {
		if (level > MAX_RECURSION) { setError("Reached parser recursion limit.", peek()); return null; }
		level += 1;

		if (match(DiceTokenType.PLUS) || match(DiceTokenType.MINUS)) {
			Token<DiceTokenType> operator = previous();
			Node right = parseUnary();
			if (right == null) { setError("Right-hand side operand did not parse correctly.", operator); return null; }

			if (operator.type() == DiceTokenType.MINUS) {
				level -= 1;
				return new UnaryMinusNode(operator, right);
			} else if (operator.type() == DiceTokenType.PLUS) {
				level -= 1;
				return new UnaryPlusNode(operator, right);
			}
		}

		Node primary = parsePrimary();

		level -= 1;
		return primary;
	}

	private Node parsePrimary() {
		if (level > MAX_RECURSION) { setError("Reached parser recursion limit.", previous()); return null; }

		if (match(DiceTokenType.NUMBER))	return new NumberLiteral(previous());
		if (match(DiceTokenType.DICE))		return new DiceLiteral(previous());

		if (match(DiceTokenType.PAREN_OPEN)) {
			Token<DiceTokenType> leftParen = previous();

			level += 1;
			Node expression = parseExpression();
			level -= 1;
			
			if (!match(DiceTokenType.PAREN_CLOSE)) {
				setError("Missing ')'.", peek());
				return null;
			}
			return new ParenthesizedNode(leftParen, previous(), expression);
		}
		setError("Expected a value.", peek());
		return null;
	}

	private boolean match(DiceTokenType type) {
		if (isEof()) return false;
		if (peek().type() == type) {
			pos++;
			return true;
		}
		return false;
	}

	private Token<DiceTokenType> peek() {
		return tokens.get(pos);
	}

	private Token<DiceTokenType> previous() {
		return tokens.get(pos - 1);
	}

	private boolean isEof() {
		return peek().type() == DiceTokenType.EOF;
	}

	private void setError(String error, Token<DiceTokenType> errorToken) {
		if (this.error != null) return;
		this.error = error;
		this.errorToken = errorToken;
	}

	public boolean hasError() {
		return error != null;
	}

	public String getError() {
		return error;
	}

	public Token<DiceTokenType> getErrorToken() {
		return errorToken;
	}
}
