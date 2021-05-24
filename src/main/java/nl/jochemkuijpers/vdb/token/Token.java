package nl.jochemkuijpers.vdb.token;

public record Token<T>(T type, int[] codepoints, int start, int end, int row, int col) {
	public String srcString() {
		if (start >= end || start < 0 || end > codepoints.length)
			return "";
		return new String(codepoints, start, (end - start));
	}
}
