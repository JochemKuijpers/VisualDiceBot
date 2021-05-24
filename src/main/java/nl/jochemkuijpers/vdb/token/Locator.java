package nl.jochemkuijpers.vdb.token;

/** Locating a token in a source string and printing the result. */
public class Locator {
	private final Token<?> token;

	public Locator(Token<?> token) {
		this.token = token;
	}

	public String getContextString(int numLines) throws IllegalArgumentException {
		final int[] codepoints = token.codepoints();
		if (numLines < 1) throw new IllegalArgumentException("numLines must be 1 or more.");

		int start = 0;
		if (token.row() > numLines) {
			int ignoreNewlines = numLines - 1;
			int lastNonWhitespace = token.start();
			for (int i = token.start(); i > 0; i--) {
				int chr = codepoints[i];

				// check for newline char
				if (chr == 0x0A || chr == 0x0B || chr == 0x0C || chr == 0x0D || chr == 0x85 || chr == 0x2028 || chr == 0x2029) {
					if (chr != '\r' || codepoints[i + 1] != '\n') {
						ignoreNewlines -= 1;
					}
				}

				if (ignoreNewlines <= 0) break;
				if (!Character.isWhitespace(chr)) lastNonWhitespace = i;
			}
			start = lastNonWhitespace;
		}

		int end = token.end();
		for (int i = token.end(); i < codepoints.length; i++) {
			// check for newline char
			int chr = codepoints[i];
			if (chr == 0x0A || chr == 0x0B || chr == 0x0C || chr == 0x0D || chr == 0x85 || chr == 0x2028 || chr == 0x2029) break;

			// if not a newline, move the end position
			end = i + 1;
		}

		return new String(codepoints, start, end - start);
	}

	public String getHighlightString() {
		return " ".repeat(token.col()) + "^".repeat(Math.max(1, token.end() - token.start()));
	}
}
