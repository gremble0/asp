// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private Stack<Integer> indents = new Stack<>();
    private final int TABDIST = 4;

    public Scanner(String fileName) {
	curFileName = fileName;
	indents.push(0);

	try {
	    sourceFile = new LineNumberReader(
			    new InputStreamReader(
				new FileInputStream(fileName),
				"UTF-8"));
	} catch (IOException e) {
	    scannerError("Cannot read " + fileName + "!");
	}
    }

    private void scannerError(String message) {
	String m = "Asp scanner error";
	if (curLineNum() > 0)
	    m += " on line " + curLineNum();
	m += ": " + message;

	Main.error(m);
    }

    public Token curToken() {
	while (curLineTokens.isEmpty()) {
	    readNextLine();
	}
	return curLineTokens.get(0);
    }

    public void readNextToken() {
	if (!curLineTokens.isEmpty())
	    curLineTokens.remove(0);
    }

    private void readNextLine() {
	curLineTokens.clear();
	int curLineNum = curLineNum();

	String line = null;
	try {
	    line = sourceFile.readLine();
	    if (line == null) {
		sourceFile.close();
		sourceFile = null;

		Token eof = new Token(eofToken, curLineNum);
		Main.log.noteToken(eof);
		curLineTokens.add(eof);
		return;
	    } else {
		line = expandLeadingTabs(line);
		Main.log.noteSourceLine(curLineNum, line);
	    }
	} catch (IOException e) {
	    sourceFile = null;
	    scannerError("Unspecified I/O error!");
	}

	int curIndent = findIndent(line);
	if (curIndent > indents.peek()) {
	    indents.push(curIndent);
	    curLineTokens.add(new Token(indentToken, curLineNum));
	} else {
	    while (curIndent < indents.peek()) {
		indents.pop();
		curLineTokens.add(new Token(dedentToken, curLineNum));
	    }
	} 

	if (curIndent != indents.peek()) {
	    scannerError("Indentation Error");
	}

	String curTokIter = "";
	Token curTok;
	for (int i = curIndent; i < line.length(); i++) {
	    if (line.charAt(i) == '#') {
		return;
	    }

	    if (!isLetterAZ(line.charAt(i)) && !isDigit(line.charAt(i))) {
		System.out.println("\"" + curTokIter + "\"");
		switch (curTokIter) {
		case "and": curTok = new Token(andToken, curLineNum); break;
		case "def": curTok = new Token(defToken, curLineNum); break;
		case "elif": curTok = new Token(elifToken, curLineNum); break;
		case "else": curTok = new Token(elseToken, curLineNum); break;
		case "False": curTok = new Token(falseToken, curLineNum); break;
		case "for": curTok = new Token(forToken, curLineNum); break;
		case "global": curTok = new Token(globalToken, curLineNum); break;
		case "if": curTok = new Token(ifToken, curLineNum); break;
		case "in": curTok = new Token(inToken, curLineNum); break;
		case "None": curTok = new Token(noneToken, curLineNum); break;
		case "not": curTok = new Token(notToken, curLineNum); break;
		case "or": curTok = new Token(orToken, curLineNum); break;
		case "pass": curTok = new Token(returnToken, curLineNum); break;
		case "True": curTok = new Token(trueToken, curLineNum); break;
		case "while": curTok = new Token(whileToken, curLineNum); break;
		default: curTok = new Token(nameToken, curLineNum); break;
		}

		curLineTokens.add(curTok);
		curTokIter = "";
	    }

	    if (!(line.charAt(i) == ' ')) {
		curTokIter += line.charAt(i);
	    }
	}

	curLineTokens.add(new Token(newLineToken, curLineNum));
	for (Token t : curLineTokens) {
	    Main.log.noteToken(t);
	}
	// Different
	// case "*": curTok = new Token(astToken, curLineNum);
	// case "==": curTok = new Token(doubleEqualToken, curLineNum);
	// case "//": curTok = new Token(doubleSlashToken, curLineNum);
	// case ">": curTok = new Token(greaterToken, curLineNum);
	// case ">=": curTok = new Token(greaterEqualToken, curLineNum);
	// case "<": curTok = new Token(lessToken, curLineNum);
	// case "<=": curTok = new Token(lessEqualToken, curLineNum);
	// case "-": curTok = new Token(minusToken, curLineNum);
	// case "!=": curTok = new Token(notEqualToken, curLineNum);
	// case "%": curTok = new Token(percentToken, curLineNum);
	// case "+": curTok = new Token(plusToken, curLineNum);
	// case "/": curTok = new Token(slashToken, curLineNum);
	// case ":": curTok = new Token(colonToken, curLineNum);
	// case ",": curTok = new Token(commaToken, curLineNum);
	// case "=": curTok = new Token(equalToken, curLineNum);
	// case "{": curTok = new Token(leftBraceToken, curLineNum);
	// case "[": curTok = new Token(leftBracketToken, curLineNum);
	// case "(": curTok = new Token(leftParToken, curLineNum);
	// case "}": curTok = new Token(rightBraceToken, curLineNum);
	// case "]": curTok = new Token(rightBracketToken, curLineNum);
	// case ")": curTok = new Token(rightParToken, curLineNum);
	// case ";": curTok = new Token(semicolonToken, curLineNum);
    }

    public int curLineNum() {
	return sourceFile != null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
	int indent = 0;

	while (indent<s.length() && s.charAt(indent)==' ') indent++;
	return indent;
    }

    private String expandLeadingTabs(String s) {
	StringBuilder tabConverter = new StringBuilder(s);
	int n = 0;

	for (int i = 0; i < s.length(); i++) {
	    if (s.charAt(i) == ' ') {
		++n;
	    } else if (s.charAt(i) == '\t') {
		int tabWidth = TABDIST - (n % TABDIST);
		n += tabWidth;

		tabConverter.deleteCharAt(i);
		for (int j = 0; j < tabWidth; j++) {
		    tabConverter.insert(i, ' ');
		}
	    } else {
		break;
	    }
	}

	return tabConverter.toString();
    }

    private boolean isLetterAZ(char c) {
	return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
    }

    private boolean isDigit(char c) {
	return '0'<=c && c<='9';
    }

    private TokenKind parseNumber(String s) {
	try {
	    Integer.parseInt(s);
	    return integerToken;
	} catch (NumberFormatException i) {
	    try {
		Float.parseFloat(s);
		return floatToken;
	    } catch (NumberFormatException f) {
		return null;
	    }
	}
    }

    public boolean isCompOpr() {
	TokenKind k = curToken().kind;
	//-- Must be changed in part 2:
	return false;
    }

    public boolean isFactorPrefix() {
	TokenKind k = curToken().kind;
	//-- Must be changed in part 2:
	return false;
    }

    public boolean isFactorOpr() {
	TokenKind k = curToken().kind;
	//-- Must be changed in part 2:
	return false;
    }

    public boolean isTermOpr() {
	TokenKind k = curToken().kind;
	//-- Must be changed in part 2:
	return false;
    }

    public boolean anyEqualToken() {
	for (Token t: curLineTokens) {
	    if (t.kind == equalToken) return true;
	    if (t.kind == semicolonToken) return false;
	}
	return false;
    }
}
