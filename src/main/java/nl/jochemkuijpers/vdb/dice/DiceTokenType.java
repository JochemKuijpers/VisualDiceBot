package nl.jochemkuijpers.vdb.dice;

import nl.jochemkuijpers.vdb.token.TokenType;

public class DiceTokenType extends TokenType {
	public final static DiceTokenType ERROR			= new DiceTokenType("ERROR");
	public final static DiceTokenType EOF			= new DiceTokenType("EOF");
	public static final DiceTokenType PAREN_OPEN	= new DiceTokenType("PAREN_OPEN");
	public static final DiceTokenType PAREN_CLOSE	= new DiceTokenType("PAREN_CLOSE");
	public static final DiceTokenType PLUS			= new DiceTokenType("PLUS");
	public static final DiceTokenType MINUS			= new DiceTokenType("MINUS");
	public static final DiceTokenType MULT			= new DiceTokenType("MULT");
	public static final DiceTokenType DIV			= new DiceTokenType("DIV");
	public static final DiceTokenType NUMBER		= new DiceTokenType("NUMBER");
	public static final DiceTokenType DICE			= new DiceTokenType("DICE");

	protected DiceTokenType(String type) {
		super(type);
	}
}
