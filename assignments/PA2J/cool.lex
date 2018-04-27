/*
 *  The scanner definition for COOL.
 */

import java_cup.runtime.Symbol;

%%

%{

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */

    // Max size of string constants
    static int MAX_STR_CONST = 1025;

    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();

    private int curr_lineno = 1;
    int get_curr_lineno() {
        return curr_lineno;
    }

    private AbstractSymbol filename;

    void set_filename(String fname) {
        filename = AbstractTable.stringtable.addString(fname);
    }

    AbstractSymbol curr_filename() {
        return filename;
    }
    int commentDepth = 0;

%}

%init{

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */

    // empty for now
%init}

%eofval{

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */

    switch(yy_lexical_state) {
    case YYINITIAL:
        /* nothing special to do in the initial state */
        break;
     case YYCOMMENT:
          yybegin(YYEOF_ERROR);
          return new Symbol(TokenConstants.ERROR, "EOF in comment");
     case YYSTRING:
          yybegin(YYEOF_ERROR);
          return new Symbol(TokenConstants.ERROR, "EOF in string constant");
     case YYEOF_ERROR:
          break;
    }
    return new Symbol(TokenConstants.EOF);
%eofval}

%class CoolLexer
%cup
%state YYSLCOMMENT, YYCOMMENT, YYSTRING, YYSTRING_NEWLINE_ERR, YYSTRING_NULL_ERR, YYEOF_ERROR

DIGIT        = [0-9]

TYPEID       = [A-Z][A-Za-z0-9_]*
OBJECTID     = [a-z][A-Za-z0-9_]*

COMMENTBEGIN = \(\*
COMMENTEND   = \*\)

IF           = [Ii][Ff]
FI           = [Ff][Ii]
INHERITS     = [Ii][Nn][Hh][Ee][Rr][Ii][Tt][Ss]
POOL         = [Pp][Oo][Oo][Ll]
CASE         = [Cc][Aa][Ss][Ee]
NOT          = [Nn][Oo][Tt]
IN           = [Ii][Nn]
CLASS        = [Cc][Ll][Aa][Ss][Ss]
LOOP         = [Ll][Oo][Oo][Pp]
OF           = [Oo][F]
NEW          = [Nn][Ee][Ww]
ISVOID       = [Ii][Ss][Vv][Oo][Ii][Dd]
ELSE         = [Ee][Ll][Ss][Ee]
WHILE        = [Ww][Hh][Ii][Ll][Ee]
ESAC         = [Ee][Ss][Aa][Cc]
LET          = [Ll][Ee][Tt]
THEN         = [Tt][Hh][Ee][Nn]

TRUE         = t[Rr][Uu][Ee]
FALSE        = f[Aa][Ll][Ss][Ee]

STRINGBEGIN  = \"
STRINGCHARS  = [^\"\0\n\\]+
STRINGEND    = \"

%%
<YYINITIAL>"--" {yybegin(YYSLCOMMENT); }
<YYSLCOMMENT>\n {yybegin(YYINITIAL); }
<YYSLCOMMENT>.  { ; }

<YYCOMMENT, YYINITIAL>{COMMENTBEGIN}  { yybegin(YYCOMMENT); commentDepth++; }
<YYCOMMENT>{COMMENTEND}              { commentDepth--; if(commentDepth == 0) { yybegin(YYINITIAL); } }
<YYCOMMENT>.                         { ; }
<YYINITIAL>{COMMENTEND}              { return new Symbol(TokenConstants.ERROR, "Unmatched *)"); }

<YYINITIAL>{IF}        { return new Symbol(TokenConstants.IF); }
<YYINITIAL>{FI}        { return new Symbol(TokenConstants.FI); }
<YYINITIAL>{INHERITS}  { return new Symbol(TokenConstants.INHERITS); }
<YYINITIAL>{POOL}      { return new Symbol(TokenConstants.POOL); }
<YYINITIAL>{CASE}      { return new Symbol(TokenConstants.CASE); }
<YYINITIAL>{NOT}       { return new Symbol(TokenConstants.NOT); }
<YYINITIAL>{IN}        { return new Symbol(TokenConstants.IN); }
<YYINITIAL>{CLASS}     { return new Symbol(TokenConstants.CLASS); }
<YYINITIAL>{LOOP}      { return new Symbol(TokenConstants.LOOP); }
<YYINITIAL>{OF}        { return new Symbol(TokenConstants.OF); }
<YYINITIAL>{NEW}       { return new Symbol(TokenConstants.NEW); }
<YYINITIAL>{ISVOID}    { return new Symbol(TokenConstants.ISVOID); }
<YYINITIAL>{ELSE}      { return new Symbol(TokenConstants.ELSE); }
<YYINITIAL>{WHILE}     { return new Symbol(TokenConstants.WHILE); }
<YYINITIAL>{ESAC}      { return new Symbol(TokenConstants.ESAC); }
<YYINITIAL>{LET}       { return new Symbol(TokenConstants.LET); }
<YYINITIAL>{THEN}      { return new Symbol(TokenConstants.THEN); }
<YYINITIAL>{TRUE}      { return new Symbol(TokenConstants.BOOL_CONST, "TRUE"); }
<YYINITIAL>{FALSE}     { return new Symbol(TokenConstants.BOOL_CONST, "FALSE"); }

<YYINITIAL>"=>" { return new Symbol(TokenConstants.DARROW); }
<YYINITIAL>"*"  { return new Symbol(TokenConstants.MULT); }
<YYINITIAL>"("  { return new Symbol(TokenConstants.LPAREN); }
<YYINITIAL>";"  { return new Symbol(TokenConstants.SEMI); }
<YYINITIAL>"-"  { return new Symbol(TokenConstants.MINUS); }
<YYINITIAL>")"  { return new Symbol(TokenConstants.RPAREN); }
<YYINITIAL>"<"  { return new Symbol(TokenConstants.LT); }
<YYINITIAL>","  { return new Symbol(TokenConstants.COMMA); }
<YYINITIAL>"/"  { return new Symbol(TokenConstants.DIV); }
<YYINITIAL>"+"  { return new Symbol(TokenConstants.PLUS); }
<YYINITIAL>"<-" { return new Symbol(TokenConstants.ASSIGN); }
<YYINITIAL>"."  { return new Symbol(TokenConstants.DOT); }
<YYINITIAL>"<=" { return new Symbol(TokenConstants.LE); }
<YYINITIAL>"="  { return new Symbol(TokenConstants.EQ); }
<YYINITIAL>":"  { return new Symbol(TokenConstants.COLON); }
<YYINITIAL>"~"  { return new Symbol(TokenConstants.NEG); }
<YYINITIAL>"{"  { return new Symbol(TokenConstants.LBRACE); }
<YYINITIAL>"}"  { return new Symbol(TokenConstants.RBRACE); }
<YYINITIAL>"@"  { return new Symbol(TokenConstants.AT); }

<YYINITIAL>{STRINGBEGIN}             { string_buf.setLength(0); yybegin(YYSTRING); }
<YYSTRING>\x00                       { yybegin(YYSTRING_NULL_ERR);
                                           return new Symbol(TokenConstants.ERROR, "String contains null character"); }
<YYSTRING>\\b                        { string_buf.append("\b"); }
<YYSTRING>\\f                        { string_buf.append("\f"); }
<YYSTRING>\\t                        { string_buf.append("\t"); }
<YYSTRING>\\\\n                      { string_buf.append("\\n"); }
<YYSTRING>\\n                        { string_buf.append("\n"); }
<YYSTRING>\\\n                       { string_buf.append("\n"); }
<YYSTRING>\\\"                       { string_buf.append("\""); }
<YYSTRING>\\\\                       { string_buf.append("\\"); }
<YYSTRING>\\                         { ; }
<YYSTRING>{STRINGCHARS}              { string_buf.append(yytext()); }
<YYSTRING>\n                         { string_buf.setLength(0);
                                           yybegin(YYINITIAL);
                                           return new Symbol(TokenConstants.ERROR, "Unterminated string constant"); }

<YYSTRING>{STRINGEND}               { yybegin(YYINITIAL);
                                      String s = string_buf.toString();
                                      if(s.length() >= MAX_STR_CONST) {
                                            return new Symbol(TokenConstants.ERROR, "String constant too long");
                                      } else {
                                            return new Symbol(TokenConstants.STR_CONST,
                                                new StringSymbol(s, s.length(), s.hashCode()));
                                      }
                                    }
<YYSTRING_NULL_ERR>\n               { yybegin(YYINITIAL); }
<YYSTRING_NULL_ERR>\"               { yybegin(YYINITIAL); }
<YYSTRING_NULL_ERR>.                { ; }
\n                                  { curr_lineno++; }

<YYINITIAL>{DIGIT}+                 { return new Symbol(TokenConstants.INT_CONST,
                                         new IntSymbol(yytext(), yytext().length(), yytext().hashCode())); }
<YYINITIAL>{TYPEID}                 { return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
<YYINITIAL>{OBJECTID}                 { return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }

" " {;}
\t  {;}

<YYINITIAL>error                    { return new Symbol(TokenConstants.error); }

.|\n                                { return new Symbol(TokenConstants.ERROR, yytext()); }
