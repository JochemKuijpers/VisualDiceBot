package nl.jochemkuijpers.vdb.dice.ast;

import nl.jochemkuijpers.vdb.dice.BinaryExpressionException;
import nl.jochemkuijpers.vdb.dice.DiceRoller;
import nl.jochemkuijpers.vdb.dice.ast.valued.ValuedNode;

public interface Node {

	/** Whether it is a leaf node. */
	boolean isLeaf();

	/** Returns the value of the ASTNode. */
	ValuedNode<? extends Node> evaluate(DiceRoller diceRoller) throws BinaryExpressionException;

	/** Add this node to the string builder. */
	void buildExpressionString(StringBuilder stringBuilder);
}