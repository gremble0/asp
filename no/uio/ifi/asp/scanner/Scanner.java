// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private String curLine;
    private int curLinePos = 0;
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

    private void stopScanning() {
	try {
	    sourceFile.close();
	} catch (IOException e) {
	    scannerError("Error trying to close file");
	}
	sourceFile = null;

	while (indents.pop() != 0)
	    Main.log.noteToken(new Token(dedentToken, curLineNum()));
	
	Token eof = new Token(eofToken, curLineNum());
	Main.log.noteToken(eof);
	curLineTokens.add(eof);
    }

    private void readNextLine() {
        curLineTokens.clear();
        int curLineNum = curLineNum();

        try {
            curLine = sourceFile.readLine();
            if (curLine == null) {
		stopScanning();
                return;
            } else {
                curLine = expandLeadingTabs(curLine);
                Main.log.noteSourceLine(curLineNum, curLine);
            }
        } catch (IOException e) {
            sourceFile = null;
            scannerError("Unspecified I/O error!");
        }

        if (curLine.length() == 0 || curLine.charAt(0) == '#')
            return;

        int curIndent = findIndent(curLine);
        if (curIndent > indents.peek()) {
            indents.push(curIndent);
            curLineTokens.add(new Token(indentToken, curLineNum));
        } else {
            while (curIndent < indents.peek()) {
                indents.pop();
                curLineTokens.add(new Token(dedentToken, curLineNum));
            }
        } 

        if (curIndent != indents.peek()) 
            scannerError("Indentation Error");

        Token curTok;
        String curTokIter = "";
        for (curLinePos = curIndent; curLinePos < curLine.length(); curLinePos++) {
	    char curChar = curLine.charAt(curLinePos);
	    if (curChar == '#')
		break;

	    if (curChar == ' ')
		continue;

	    // TODO: split each branch into a separate function and use the curLinePos variable
	    if (isQuote(curChar)) {
		curTok = scanString();
	    }
	    else if (isLetterAZ(curChar)) {
		curTok = scanKeywordOrName();
	    }
	    else if (isDigit(curChar)) {
		curTok = scanNumber();
	    }
	    else {
		curTokIter += curLine.charAt(curLinePos++);
		while (curLinePos < curLine.length() && findOperatorKind(curTokIter + curLine.charAt(curLinePos)) != null) {
		    curTokIter += curLine.charAt(curLinePos++);
		}
		curLinePos--;

		curTok = new Token(findOperatorKind(curTokIter), curLineNum);
	    }

	    curTokIter = "";
	    curLineTokens.add(curTok);
	}

        curLineTokens.add(new Token(newLineToken, curLineNum));

        for (Token t : curLineTokens)
            Main.log.noteToken(t);
    }

    public int curLineNum() {
        return sourceFile != null ? sourceFile.getLineNumber() + 1 : 0;
    }

    private Token scanString() {
	char startQuote = curLine.charAt(curLinePos);
	Token curTok = new Token(stringToken, curLineNum());
	curTok.stringLit = "";

	while (curLinePos < curLine.length() && (curLine.charAt(++curLinePos)) != startQuote)
	    curTok.stringLit += curLine.charAt(curLinePos);

	return curTok;
    }

    private Token scanKeywordOrName() {
	String curWord = "";
	while (curLinePos < curLine.length() && (isLetterAZ(curLine.charAt(curLinePos)) || isDigit(curLine.charAt(curLinePos)))) {
	    curWord += curLine.charAt(curLinePos);
	    curLinePos++;
	}
	
	TokenKind curTokKind = findKeywordKind(curWord);
	Token curTok = new Token(curTokKind, curLineNum());
	
	if (curTokKind == nameToken)
	    curTok.name = curWord;

	curLinePos--; // TODO: find a better way of doing this

	return curTok;
    }

    private Token scanNumber() {
	// TODO: Handle errors for misformed floats (multiple .'s etc)
	String curNum = "";
	while (curLinePos < curLine.length() && (isDigit(curLine.charAt(curLinePos)) || curLine.charAt(curLinePos) == '.')) {
	    curNum += curLine.charAt(curLinePos);
	    curLinePos++;
	}

	Token curTok;
	if (curNum.contains(".")) {
	    curTok = new Token(floatToken, curLineNum());
	    curTok.floatLit = Double.parseDouble(curNum);
	} else {
	    curTok = new Token(integerToken, curLineNum());
	    curTok.integerLit = Integer.parseInt(curNum);
	}

	curLinePos--; // TODO: find a better way of doing this

	return curTok;
    }

    private TokenKind findKeywordKind(String tokString) {
        switch (tokString) {
        case "and": return andToken;
        case "def": return defToken;
        case "elif": return elifToken;
        case "else": return elseToken;
        case "False": return falseToken;
        case "for": return forToken;
        case "global": return globalToken;
        case "if": return ifToken;
        case "in": return inToken;
        case "None": return noneToken;
        case "not": return notToken;
        case "or": return orToken;
	case "return": return returnToken;
        case "pass": return passToken;
        case "True": return trueToken;
        case "while": return whileToken;
        default: return nameToken;
        }
    }

    // TODO: Maybe combine with findKeywordKind
    private TokenKind findOperatorKind(String tokString) {
        switch (tokString) {
        case "*": return astToken;
        case ">": return greaterToken;
        case ">=": return greaterEqualToken;
        case "<": return lessToken;
        case "<=": return lessEqualToken;
        case "-": return minusToken;
        case "!=": return notEqualToken;
        case "%": return percentToken;
        case "+": return plusToken;
        case "/": return slashToken;
        case "//": return doubleSlashToken;
        case ":": return colonToken;
        case ",": return commaToken;
        case "=": return equalToken;
        case "==": return doubleEqualToken;
        case "{": return leftBraceToken;
        case "[": return leftBracketToken;
        case "(": return leftParToken;
        case "}": return rightBraceToken;
        case "]": return rightBracketToken;
        case ")": return rightParToken;
        case ";": return semicolonToken;
        default: return null;
        }
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

    private boolean isQuote(char c) {
	return c == '"' || c == '\'';
    }

    private boolean isOperator(char c) {
	return !(isLetterAZ(c) || isDigit(c) || isQuote(c) || c == ' ');
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
