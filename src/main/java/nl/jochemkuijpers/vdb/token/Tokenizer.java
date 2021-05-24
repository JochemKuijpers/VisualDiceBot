package nl.jochemkuijpers.vdb.token;

import java.util.List;

public interface Tokenizer<T extends TokenType> {
	List<Token<T>> tokenize();
}
