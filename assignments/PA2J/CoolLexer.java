/*
 *  The scanner definition for COOL.
 */
import java_cup.runtime.Symbol;


class CoolLexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

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
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	CoolLexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	CoolLexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private CoolLexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */
    // empty for now
	}

	private boolean yy_eof_done = false;
	private final int YYSTRING = 3;
	private final int YYCOMMENT = 2;
	private final int YYSLCOMMENT = 1;
	private final int YYEOF_ERROR = 6;
	private final int YYSTRING_NEWLINE_ERR = 4;
	private final int YYINITIAL = 0;
	private final int YYSTRING_NULL_ERR = 5;
	private final int yy_state_dtrans[] = {
		0,
		71,
		92,
		96,
		99,
		102,
		99
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NOT_ACCEPT,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NOT_ACCEPT,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NOT_ACCEPT,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NOT_ACCEPT,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NOT_ACCEPT,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NO_ANCHOR,
		/* 168 */ YY_NO_ANCHOR,
		/* 169 */ YY_NO_ANCHOR,
		/* 170 */ YY_NO_ANCHOR,
		/* 171 */ YY_NO_ANCHOR,
		/* 172 */ YY_NO_ANCHOR,
		/* 173 */ YY_NO_ANCHOR,
		/* 174 */ YY_NO_ANCHOR,
		/* 175 */ YY_NO_ANCHOR,
		/* 176 */ YY_NO_ANCHOR,
		/* 177 */ YY_NO_ANCHOR,
		/* 178 */ YY_NO_ANCHOR,
		/* 179 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"40,3:8,64,2,3:2,44,3:18,63,3,39,3:5,4,6,5,32,30,1,33,31,45:10,34,28,29,26,2" +
"7,3,38,46,47,48,49,50,20,47,51,52,47:2,53,47,9,54,55,47,56,57,13,58,59,60,4" +
"7:3,3,41,3:2,61,3,19,42,18,23,11,8,62,10,7,62:2,17,62,43,16,15,62,12,14,24," +
"25,22,21,62:3,36,3,37,35,3,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,180,
"0,1,2,1:2,3,4,1,5,6,7,1,8,1:10,9,1:5,10,11,10:2,1:3,12:2,10:3,12,10:9,1:4,1" +
"3,1:3,14,1:4,15,1:6,16,17,18,12,19,12:2,10:2,12:3,10,12:7,3,20,10,21,22,23," +
"24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48," +
"49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,12," +
"73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97," +
"98,99,100,101,102,103,104,105")[0];

	private int yy_nxt[][] = unpackFromString(106,65,
"1,2,3,4,5,6,7,8,72,9,93,167,93,140,93,168,97,141,169,93,73,176,93:2,170,93," +
"10,4,11,12,13,14,15,16,17,18,19,20,21,22,4:2,93,147,-1,23,146:2,150,146,152" +
",146,94,154,98,156,146:4,158,4,93,24,25,-1:66,26,-1:68,27,-1:65,28,-1:65,93" +
",29,30,93:4,177,93:5,29,93:5,-1:16,93,30,-1,93:12,177,93:5,-1:9,146:4,101,1" +
"46:4,104,146:9,-1:16,146:2,-1,146:5,101,146:3,104,146:8,-1:29,33,-1:38,34,-" +
"1:24,35,-1:83,23,-1:26,93:19,-1:16,93:2,-1,93:18,-1:9,93:3,179,93:15,-1:16," +
"93:2,-1,93:6,179,93:11,-1:9,146:19,-1:16,146:2,-1,146:18,-1:3,55,-1,55:36,-" +
"1:3,55:23,-1:2,60,-1:5,61,-1:15,62,-1:14,63,-1,64,65,66,-1:64,67,-1:21,1,51" +
",52,51:41,-1,51:20,-1:7,31,93:11,171,93:6,-1:16,93:2,-1,93,171,93:5,31,93:1" +
"0,-1:9,76,146:18,-1:16,146:2,-1,146:7,76,146:10,-1:9,146:3,128,146:15,-1:16" +
",146:2,-1,146:6,128,146:11,-1:2,1,53,3,53,91,95,53:38,-1,53:20,-1:7,146,74," +
"75,146:4,112,146:5,74,146:5,-1:16,146,75,-1,146:12,112,146:5,-1:8,54,-1:58," +
"1,55,56,55:36,57,58,59,55:23,-1:7,93:13,32,93:5,-1:16,93:2,-1,93:18,-1:9,14" +
"6:13,77,146:5,-1:16,146:2,-1,146:18,-1:2,1,4,3,4:41,-1,4:18,24,25,-1:7,93:6" +
",38,93:10,38,93,-1:16,93:2,-1,93:18,-1:9,146:14,36,146:4,-1:16,146:2,-1,146" +
":15,36,146:2,-1:2,1,68,69,68:36,70,68:4,-1,68:20,-1:7,93:14,78,93:4,-1:16,9" +
"3:2,-1,93:15,78,93:2,-1:9,146:6,37,146:10,37,146,-1:16,146:2,-1,146:18,-1:9" +
",93:6,79,93:10,79,93,-1:16,93:2,-1,93:18,-1:9,146:4,120,146:14,-1:16,146:2," +
"-1,146:5,120,146:12,-1:9,93:11,39,93:7,-1:16,93:2,-1,93:3,39,93:14,-1:9,146" +
":12,148,146:6,-1:16,146:2,-1,146,148,146:16,-1:9,93:4,40,93:14,-1:16,93:2,-" +
"1,93:5,40,93:12,-1:9,146:7,122,146:11,-1:16,146:2,-1,146:12,122,146:5,-1:9," +
"93:10,42,93:8,-1:16,93:2,-1,93:8,42,93:9,-1:9,146:15,149,146:3,-1:16,146:2," +
"-1,146:14,149,146:3,-1:9,93:8,43,93:10,-1:16,93:2,-1,93:10,43,93:7,-1:9,146" +
":6,80,146:10,80,146,-1:16,146:2,-1,146:18,-1:9,93:4,44,93:14,-1:16,93:2,-1," +
"93:5,44,93:12,-1:9,146:9,130,146:9,-1:16,146:2,-1,146:9,130,146:8,-1:9,93:2" +
",83,93:16,-1:16,93,83,-1,93:18,-1:9,132,146:18,-1:16,146:2,-1,146:7,132,146" +
":10,-1:9,93:4,45,93:14,-1:16,93:2,-1,93:5,45,93:12,-1:9,146:2,41,146:16,-1:" +
"16,146,41,-1,146:18,-1:9,93:4,46,93:14,-1:16,93:2,-1,93:5,46,93:12,-1:9,146" +
":4,86,146:14,-1:16,146:2,-1,146:5,86,146:12,-1:9,93:7,47,93:11,-1:16,93:2,-" +
"1,93:12,47,93:5,-1:9,146:11,81,146:7,-1:16,146:2,-1,146:3,81,146:14,-1:9,93" +
":4,48,93:14,-1:16,93:2,-1,93:5,48,93:12,-1:9,146:4,82,146:14,-1:16,146:2,-1" +
",146:5,82,146:12,-1:9,93:16,49,93:2,-1:16,93:2,-1,93:4,49,93:13,-1:9,146:4," +
"134,146:14,-1:16,146:2,-1,146:5,134,146:12,-1:9,93:7,50,93:11,-1:16,93:2,-1" +
",93:12,50,93:5,-1:9,146:8,85,146:10,-1:16,146:2,-1,146:10,85,146:7,-1:9,146" +
":10,84,146:8,-1:16,146:2,-1,146:8,84,146:9,-1:9,146:10,136,146:8,-1:16,146:" +
"2,-1,146:8,136,146:9,-1:9,146:7,87,146:11,-1:16,146:2,-1,146:12,87,146:5,-1" +
":9,146:5,145,146:13,-1:16,146:2,-1,146:11,145,146:6,-1:9,137,146:18,-1:16,1" +
"46:2,-1,146:7,137,146:10,-1:9,146:4,88,146:14,-1:16,146:2,-1,146:5,88,146:1" +
"2,-1:9,146:16,89,146:2,-1:16,146:2,-1,146:4,89,146:13,-1:9,146:6,139,146:10" +
",139,146,-1:16,146:2,-1,146:18,-1:9,146:7,90,146:11,-1:16,146:2,-1,146:12,9" +
"0,146:5,-1:9,146:3,106,146:15,-1:16,146:2,-1,146:6,106,146:11,-1:9,93:4,100" +
",93:4,157,93:9,-1:16,93:2,-1,93:5,100,93:3,157,93:8,-1:9,146:12,124,146:6,-" +
"1:16,146:2,-1,146,124,146:16,-1:9,146:7,126,146:11,-1:16,146:2,-1,146:12,12" +
"6,146:5,-1:9,146:9,131,146:9,-1:16,146:2,-1,146:9,131,146:8,-1:9,138,146:18" +
",-1:16,146:2,-1,146:7,138,146:10,-1:9,93:4,103,93:4,105,93:9,-1:16,93:2,-1," +
"93:5,103,93:3,105,93:8,-1:9,146:7,133,146:11,-1:16,146:2,-1,146:12,133,146:" +
"5,-1:9,146:9,135,146:9,-1:16,146:2,-1,146:9,135,146:8,-1:9,146:10,108,146,1" +
"10,146:6,-1:16,146:2,-1,146,110,146:6,108,146:9,-1:9,93:12,107,93:6,-1:16,9" +
"3:2,-1,93,107,93:16,-1:9,146:7,142,146:2,143,146:8,-1:16,146:2,-1,146:8,143" +
",146:3,142,146:5,-1:9,93:7,109,93:11,-1:16,93:2,-1,93:12,109,93:5,-1:9,146:" +
"4,114,146:4,116,146:9,-1:16,146:2,-1,146:5,114,146:3,116,146:8,-1:9,93:9,11" +
"1,93:9,-1:16,93:2,-1,93:9,111,93:8,-1:9,146:9,144,146:9,-1:16,146:2,-1,146:" +
"9,144,146:8,-1:9,93:9,113,93:9,-1:16,93:2,-1,93:9,113,93:8,-1:9,146:3,118,1" +
"46:15,-1:16,146:2,-1,146:6,118,146:11,-1:9,93:7,115,93:11,-1:16,93:2,-1,93:" +
"12,115,93:5,-1:9,93:4,117,93:14,-1:16,93:2,-1,93:5,117,93:12,-1:9,93:18,119" +
",-1:16,93:2,-1,93:13,119,93:4,-1:9,93:7,121,93:11,-1:16,93:2,-1,93:12,121,9" +
"3:5,-1:9,93:7,123,93:11,-1:16,93:2,-1,93:12,123,93:5,-1:9,93:10,125,93:8,-1" +
":16,93:2,-1,93:8,125,93:9,-1:9,127,93:18,-1:16,93:2,-1,93:7,127,93:10,-1:9," +
"93:6,129,93:10,129,93,-1:16,93:2,-1,93:18,-1:9,93:7,151,93:2,153,93:8,-1:16" +
",93:2,-1,93:8,153,93:3,151,93:5,-1:9,93:9,155,93:9,-1:16,93:2,-1,93:9,155,9" +
"3:8,-1:9,93:10,172,93,159,93:6,-1:16,93:2,-1,93,159,93:6,172,93:9,-1:9,93:3" +
",160,93,161,93:13,-1:16,93:2,-1,93:6,160,93:4,161,93:6,-1:9,93:10,162,93:8," +
"-1:16,93:2,-1,93:8,162,93:9,-1:9,93:12,163,93:6,-1:16,93:2,-1,93,163,93:16," +
"-1:9,164,93:18,-1:16,93:2,-1,93:7,164,93:10,-1:9,93:9,165,93:9,-1:16,93:2,-" +
"1,93:9,165,93:8,-1:9,166,93:18,-1:16,93:2,-1,93:7,166,93:10,-1:9,93:3,173,9" +
"3:15,-1:16,93:2,-1,93:6,173,93:11,-1:9,93:15,174,93:3,-1:16,93:2,-1,93:14,1" +
"74,93:3,-1:9,93:5,175,93:13,-1:16,93:2,-1,93:11,175,93:6,-1:9,93:4,178,93:1" +
"4,-1:16,93:2,-1,93:5,178,93:12,-1:2");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

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
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ return new Symbol(TokenConstants.MINUS); }
					case -3:
						break;
					case 3:
						{ curr_lineno++; }
					case -4:
						break;
					case 4:
						{ return new Symbol(TokenConstants.ERROR, yytext()); }
					case -5:
						break;
					case 5:
						{ return new Symbol(TokenConstants.LPAREN); }
					case -6:
						break;
					case 6:
						{ return new Symbol(TokenConstants.MULT); }
					case -7:
						break;
					case 7:
						{ return new Symbol(TokenConstants.RPAREN); }
					case -8:
						break;
					case 8:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -9:
						break;
					case 9:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -10:
						break;
					case 10:
						{ return new Symbol(TokenConstants.EQ); }
					case -11:
						break;
					case 11:
						{ return new Symbol(TokenConstants.SEMI); }
					case -12:
						break;
					case 12:
						{ return new Symbol(TokenConstants.LT); }
					case -13:
						break;
					case 13:
						{ return new Symbol(TokenConstants.COMMA); }
					case -14:
						break;
					case 14:
						{ return new Symbol(TokenConstants.DIV); }
					case -15:
						break;
					case 15:
						{ return new Symbol(TokenConstants.PLUS); }
					case -16:
						break;
					case 16:
						{ return new Symbol(TokenConstants.DOT); }
					case -17:
						break;
					case 17:
						{ return new Symbol(TokenConstants.COLON); }
					case -18:
						break;
					case 18:
						{ return new Symbol(TokenConstants.NEG); }
					case -19:
						break;
					case 19:
						{ return new Symbol(TokenConstants.LBRACE); }
					case -20:
						break;
					case 20:
						{ return new Symbol(TokenConstants.RBRACE); }
					case -21:
						break;
					case 21:
						{ return new Symbol(TokenConstants.AT); }
					case -22:
						break;
					case 22:
						{ string_buf.setLength(0); yybegin(YYSTRING); }
					case -23:
						break;
					case 23:
						{ return new Symbol(TokenConstants.INT_CONST,
                                         new IntSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -24:
						break;
					case 24:
						{;}
					case -25:
						break;
					case 25:
						{;}
					case -26:
						break;
					case 26:
						{yybegin(YYSLCOMMENT); }
					case -27:
						break;
					case 27:
						{ yybegin(YYCOMMENT); commentDepth++; }
					case -28:
						break;
					case 28:
						{ return new Symbol(TokenConstants.ERROR, "Unmatched *)"); }
					case -29:
						break;
					case 29:
						{ return new Symbol(TokenConstants.IF); }
					case -30:
						break;
					case 30:
						{ return new Symbol(TokenConstants.IN); }
					case -31:
						break;
					case 31:
						{ return new Symbol(TokenConstants.FI); }
					case -32:
						break;
					case 32:
						{ return new Symbol(TokenConstants.OF); }
					case -33:
						break;
					case 33:
						{ return new Symbol(TokenConstants.DARROW); }
					case -34:
						break;
					case 34:
						{ return new Symbol(TokenConstants.ASSIGN); }
					case -35:
						break;
					case 35:
						{ return new Symbol(TokenConstants.LE); }
					case -36:
						break;
					case 36:
						{ return new Symbol(TokenConstants.NEW); }
					case -37:
						break;
					case 37:
						{ return new Symbol(TokenConstants.NOT); }
					case -38:
						break;
					case 38:
						{ return new Symbol(TokenConstants.LET); }
					case -39:
						break;
					case 39:
						{ return new Symbol(TokenConstants.ESAC); }
					case -40:
						break;
					case 40:
						{ return new Symbol(TokenConstants.ELSE); }
					case -41:
						break;
					case 41:
						{ return new Symbol(TokenConstants.THEN); }
					case -42:
						break;
					case 42:
						{ return new Symbol(TokenConstants.POOL); }
					case -43:
						break;
					case 43:
						{ return new Symbol(TokenConstants.LOOP); }
					case -44:
						break;
					case 44:
						{ return new Symbol(TokenConstants.CASE); }
					case -45:
						break;
					case 45:
						{ return new Symbol(TokenConstants.BOOL_CONST, "TRUE"); }
					case -46:
						break;
					case 46:
						{ return new Symbol(TokenConstants.BOOL_CONST, "FALSE"); }
					case -47:
						break;
					case 47:
						{ return new Symbol(TokenConstants.CLASS); }
					case -48:
						break;
					case 48:
						{ return new Symbol(TokenConstants.WHILE); }
					case -49:
						break;
					case 49:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -50:
						break;
					case 50:
						{ return new Symbol(TokenConstants.INHERITS); }
					case -51:
						break;
					case 51:
						{ ; }
					case -52:
						break;
					case 52:
						{yybegin(YYINITIAL); }
					case -53:
						break;
					case 53:
						{ ; }
					case -54:
						break;
					case 54:
						{ commentDepth--; if(commentDepth == 0) { yybegin(YYINITIAL); } }
					case -55:
						break;
					case 55:
						{ string_buf.append(yytext()); }
					case -56:
						break;
					case 56:
						{ string_buf.setLength(0);
                                           yybegin(YYINITIAL);
                                           return new Symbol(TokenConstants.ERROR, "Unterminated string constant"); }
					case -57:
						break;
					case 57:
						{ yybegin(YYINITIAL);
                                      String s = string_buf.toString();
                                      if(s.length() >= MAX_STR_CONST) {
                                            return new Symbol(TokenConstants.ERROR, "String constant too long");
                                      } else {
                                            return new Symbol(TokenConstants.STR_CONST,
                                                new StringSymbol(s, s.length(), s.hashCode()));
                                      }
                                    }
					case -58:
						break;
					case 58:
						{ yybegin(YYSTRING_NULL_ERR);
                                           return new Symbol(TokenConstants.ERROR, "String contains null character"); }
					case -59:
						break;
					case 59:
						{ ; }
					case -60:
						break;
					case 60:
						{ string_buf.append("\n"); }
					case -61:
						break;
					case 61:
						{ string_buf.append("\f"); }
					case -62:
						break;
					case 62:
						{ string_buf.append("\t"); }
					case -63:
						break;
					case 63:
						{ string_buf.append("\""); }
					case -64:
						break;
					case 64:
						{ string_buf.append("\\"); }
					case -65:
						break;
					case 65:
						{ string_buf.append("\b"); }
					case -66:
						break;
					case 66:
						{ string_buf.append("\n"); }
					case -67:
						break;
					case 67:
						{ string_buf.append("\\n"); }
					case -68:
						break;
					case 68:
						{ ; }
					case -69:
						break;
					case 69:
						{ yybegin(YYINITIAL); }
					case -70:
						break;
					case 70:
						{ yybegin(YYINITIAL); }
					case -71:
						break;
					case 72:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -72:
						break;
					case 73:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -73:
						break;
					case 74:
						{ return new Symbol(TokenConstants.IF); }
					case -74:
						break;
					case 75:
						{ return new Symbol(TokenConstants.IN); }
					case -75:
						break;
					case 76:
						{ return new Symbol(TokenConstants.FI); }
					case -76:
						break;
					case 77:
						{ return new Symbol(TokenConstants.OF); }
					case -77:
						break;
					case 78:
						{ return new Symbol(TokenConstants.NEW); }
					case -78:
						break;
					case 79:
						{ return new Symbol(TokenConstants.NOT); }
					case -79:
						break;
					case 80:
						{ return new Symbol(TokenConstants.LET); }
					case -80:
						break;
					case 81:
						{ return new Symbol(TokenConstants.ESAC); }
					case -81:
						break;
					case 82:
						{ return new Symbol(TokenConstants.ELSE); }
					case -82:
						break;
					case 83:
						{ return new Symbol(TokenConstants.THEN); }
					case -83:
						break;
					case 84:
						{ return new Symbol(TokenConstants.POOL); }
					case -84:
						break;
					case 85:
						{ return new Symbol(TokenConstants.LOOP); }
					case -85:
						break;
					case 86:
						{ return new Symbol(TokenConstants.CASE); }
					case -86:
						break;
					case 87:
						{ return new Symbol(TokenConstants.CLASS); }
					case -87:
						break;
					case 88:
						{ return new Symbol(TokenConstants.WHILE); }
					case -88:
						break;
					case 89:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -89:
						break;
					case 90:
						{ return new Symbol(TokenConstants.INHERITS); }
					case -90:
						break;
					case 91:
						{ ; }
					case -91:
						break;
					case 93:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -92:
						break;
					case 94:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -93:
						break;
					case 95:
						{ ; }
					case -94:
						break;
					case 97:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -95:
						break;
					case 98:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -96:
						break;
					case 100:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -97:
						break;
					case 101:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -98:
						break;
					case 103:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -99:
						break;
					case 104:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -100:
						break;
					case 105:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -101:
						break;
					case 106:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -102:
						break;
					case 107:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -103:
						break;
					case 108:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -104:
						break;
					case 109:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -105:
						break;
					case 110:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -106:
						break;
					case 111:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -107:
						break;
					case 112:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -108:
						break;
					case 113:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -109:
						break;
					case 114:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -110:
						break;
					case 115:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -111:
						break;
					case 116:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -112:
						break;
					case 117:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -113:
						break;
					case 118:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -114:
						break;
					case 119:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -115:
						break;
					case 120:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -116:
						break;
					case 121:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -117:
						break;
					case 122:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -118:
						break;
					case 123:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -119:
						break;
					case 124:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -120:
						break;
					case 125:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -121:
						break;
					case 126:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -122:
						break;
					case 127:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -123:
						break;
					case 128:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -124:
						break;
					case 129:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -125:
						break;
					case 130:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -126:
						break;
					case 131:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -127:
						break;
					case 132:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -128:
						break;
					case 133:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -129:
						break;
					case 134:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -130:
						break;
					case 135:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -131:
						break;
					case 136:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -132:
						break;
					case 137:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -133:
						break;
					case 138:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -134:
						break;
					case 139:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -135:
						break;
					case 140:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -136:
						break;
					case 141:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -137:
						break;
					case 142:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -138:
						break;
					case 143:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -139:
						break;
					case 144:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -140:
						break;
					case 145:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -141:
						break;
					case 146:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -142:
						break;
					case 147:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -143:
						break;
					case 148:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -144:
						break;
					case 149:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -145:
						break;
					case 150:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -146:
						break;
					case 151:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -147:
						break;
					case 152:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -148:
						break;
					case 153:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -149:
						break;
					case 154:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -150:
						break;
					case 155:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -151:
						break;
					case 156:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -152:
						break;
					case 157:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -153:
						break;
					case 158:
						{ return new Symbol(TokenConstants.TYPEID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -154:
						break;
					case 159:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -155:
						break;
					case 160:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -156:
						break;
					case 161:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -157:
						break;
					case 162:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -158:
						break;
					case 163:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -159:
						break;
					case 164:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -160:
						break;
					case 165:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -161:
						break;
					case 166:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -162:
						break;
					case 167:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -163:
						break;
					case 168:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -164:
						break;
					case 169:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -165:
						break;
					case 170:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -166:
						break;
					case 171:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -167:
						break;
					case 172:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -168:
						break;
					case 173:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -169:
						break;
					case 174:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -170:
						break;
					case 175:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -171:
						break;
					case 176:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -172:
						break;
					case 177:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -173:
						break;
					case 178:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -174:
						break;
					case 179:
						{ return new Symbol(TokenConstants.OBJECTID,
					 new IdSymbol(yytext(), yytext().length(), yytext().hashCode())); }
					case -175:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
