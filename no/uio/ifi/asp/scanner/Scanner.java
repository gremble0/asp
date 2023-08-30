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
	if (! curLineTokens.isEmpty())
	    curLineTokens.remove(0);
    }

    private void readNextLine() {
	curLineTokens.clear();

	String line = null;
	try {
	    line = sourceFile.readLine();
	    if (line == null) {
		sourceFile.close();
		sourceFile = null;
	    } else {
		Main.log.noteSourceLine(curLineNum(), line);
	    }
	} catch (IOException e) {
	    sourceFile = null;
	    scannerError("Unspecified I/O error!");
	}

	int curIndent = findIndent(line);

	if (curIndent > indents.peek()) {
	    indents.push(curIndent);
	    curLineTokens.add(new Token(indentToken, curLineNum()));
	} else if (curIndent < indents.peek()) {
	    indents.pop();
	    curLineTokens.add(new Token(dedentToken, curLineNum()));
	}

	String scannableLine = expandLeadingTabs(line);

	String curTok = "";
	for (int i = 0; i < scannableLine.length(); i++) {
	    
	}

	// Terminate line:
	curLineTokens.add(new Token(newLineToken, curLineNum()));

	for (Token t: curLineTokens) 
	    Main.log.noteToken(t);
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
	    }
	    else if (s.charAt(i) == '\t') {
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
