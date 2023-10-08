// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curLine;
    private int curLinePos = 0;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private Stack<Integer> indents = new Stack<>();
    private final int TABDIST = 4;

    public Scanner(String fileName) {
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

    public int curLineNum() {
        return sourceFile != null ? sourceFile.getLineNumber() : 0;
    }

    public Token curToken() {
        while (curLineTokens.isEmpty())
            readNextLine();

        return curLineTokens.get(0);
    }

    public void readNextToken() {
        if (!curLineTokens.isEmpty())
            curLineTokens.remove(0);
    }

    private void scannerError(String message) {
        String m = "Asp scanner error";
        if (curLineNum() > 0)
            m += " on line " + curLineNum();
        m += ": " + message;

        Main.error(m);
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

        try {
            curLine = sourceFile.readLine();
            if (curLine == null) {
                stopScanning();
                return;
            } else {
                // It gives a cleaner output to call expandLeadingTabs on curLine before
                // logging it, but in order to match the referance interpreter I will 
                // do it in reverse order.
                Main.log.noteSourceLine(curLineNum(), curLine);
                curLine = expandLeadingTabs(curLine);
            }
        } catch (IOException e) {
            sourceFile = null;
            scannerError("Unspecified I/O error!");
        }

        int curIndent = findIndent(curLine);
        
        if (curIndent == curLine.length() || curLine.charAt(curIndent) == '#')
            return;

        if (curIndent > indents.peek()) {
            indents.push(curIndent);
            curLineTokens.add(new Token(indentToken, curLineNum()));
        } else {
            while (curIndent < indents.peek()) {
                indents.pop();
                curLineTokens.add(new Token(dedentToken, curLineNum()));
            }
        } 

        if (curIndent != indents.peek()) 
            scannerError("Indentation Error");

        for (curLinePos = curIndent; curLinePos < curLine.length(); curLinePos++) {
            char curChar = curLine.charAt(curLinePos);
            if (curChar == '#')
                break;

            if (curChar == ' ')
                continue;

            if (isQuote(curChar))
                curLineTokens.add(scanString());
            else if (isLetterAZ(curChar))
                curLineTokens.add(scanKeywordOrName());
            else if (isDigit(curChar))
                curLineTokens.add(scanNumber());
            else
                curLineTokens.add(scanOperator());
        }
        
        curLineTokens.add(new Token(newLineToken, curLineNum()));
        
        for (Token t : curLineTokens)
            Main.log.noteToken(t);
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

        TokenKind curTokKind = findTokenKind(curWord);
        Token curTok = new Token(curTokKind, curLineNum());
    
        if (curTokKind == nameToken)
            curTok.name = curWord;

        curLinePos--;

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
            if (curNum.startsWith("0") && curNum.length() > 1) {
                // probably easier to just raise a scanner error here but text wants us to wait until parser
                Token zeroTok = new Token(integerToken, curLineNum());
                zeroTok.integerLit = 0;
                curLineTokens.add(zeroTok);
            }
            
            curTok = new Token(integerToken, curLineNum());
            curTok.integerLit = Integer.parseInt(curNum);
        }
        
        curLinePos--;
        
        return curTok;
    }

    private Token scanOperator() {
        String curOperator = String.valueOf(curLine.charAt(curLinePos++));

        while (curLinePos < curLine.length() && findTokenKind(curOperator + curLine.charAt(curLinePos)) != nameToken) {
            curOperator += curLine.charAt(curLinePos++);
        }
        curLinePos--;
        
        return new Token(findTokenKind(curOperator), curLineNum());
    }

    private TokenKind findTokenKind(String tokString) {
        TokenKind t = TokenKind.findByKey(tokString);
        return t == null ? nameToken : t;
    }

    private int findIndent(String s) {
        int indent = 0;

        while (indent<s.length() && s.charAt(indent)==' ') indent++;
        return indent;
    }

    private String expandLeadingTabs(String s) {
        StringBuilder tabConverter = new StringBuilder(s);
        int n = 0;

        for (int i = 0; i < tabConverter.length(); i++) {
            if (tabConverter.charAt(i) == ' ') {
                ++n;
            } else if (tabConverter.charAt(i) == '\t') {
                int tabWidth = TABDIST - (n % TABDIST);
                n += tabWidth;

                tabConverter.deleteCharAt(i);
                for (int j = 0; j < tabWidth; j++)
                    tabConverter.insert(i, ' ');
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

    public boolean anyEqualToken() {
        for (Token t : curLineTokens) {
            if (t.kind == equalToken) return true;
            if (t.kind == semicolonToken) return false;
        }
        return false;
    }
}
