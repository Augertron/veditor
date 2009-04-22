/* Generated By:JavaCC: Do not edit this line. VerilogParserCoreConstants.java */
package net.sourceforge.veditor.parser;

public interface VerilogParserCoreConstants {

  int EOF = 0;
  int MULTI_LINE_COMMENT = 2;
  int ENDIF = 4;
  int SINGLE_LINE_COMMENT = 11;
  int DELSE = 12;
  int DIRECTIVE = 13;
  int MODULE = 14;
  int ENDMODULE = 15;
  int BEGIN = 16;
  int END = 17;
  int FORK = 18;
  int JOIN = 19;
  int SPECIFY = 20;
  int ENDSPECIFY = 21;
  int IF = 22;
  int ELSE = 23;
  int WHILE = 24;
  int FOR = 25;
  int FOREVER = 26;
  int REPEAT = 27;
  int CASE = 28;
  int CASEX = 29;
  int ENDCASE = 30;
  int FUNCTION = 31;
  int ENDFUNCTION = 32;
  int TASK = 33;
  int ENDTASK = 34;
  int GENERATE = 35;
  int ENDGENERATE = 36;
  int INPUT = 37;
  int OUTPUT = 38;
  int INOUT = 39;
  int INTEGER = 40;
  int WIRE = 41;
  int REG = 42;
  int EVENT = 43;
  int GENVAR = 44;
  int REAL = 45;
  int PARAMETER = 46;
  int SIGNED = 47;
  int STMT_HEAD = 48;
  int BLOCK_HEAD = 49;
  int LPAREN = 50;
  int RPAREN = 51;
  int LBRACE = 52;
  int RBRACE = 53;
  int LBRACKET = 54;
  int RBRACKET = 55;
  int EOS = 56;
  int PARA = 57;
  int AT = 58;
  int BQ = 59;
  int DOLLAR = 60;
  int COMMA = 61;
  int COLON = 62;
  int EQUAL = 63;
  int IDENT = 64;
  int SPC_CHAR = 65;
  int OTHER = 66;

  int DEFAULT = 0;
  int IN_MULTI_LINE_COMMENT = 1;
  int IN_ELSE_BLOCK = 2;

  String[] tokenImage = {
    "<EOF>",
    "\"/*\"",
    "\"*/\"",
    "<token of kind 3>",
    "\"`endif\"",
    "<token of kind 5>",
    "\" \"",
    "\"\\t\"",
    "\"\\r\"",
    "\"\\n\"",
    "\"\\f\"",
    "<SINGLE_LINE_COMMENT>",
    "\"\\n`else\"",
    "<DIRECTIVE>",
    "\"module\"",
    "\"endmodule\"",
    "\"begin\"",
    "\"end\"",
    "\"fork\"",
    "\"join\"",
    "\"specify\"",
    "\"endspecify\"",
    "\"if\"",
    "\"else\"",
    "\"while\"",
    "\"for\"",
    "\"forever\"",
    "\"repeat\"",
    "\"case\"",
    "\"casex\"",
    "\"endcase\"",
    "\"function\"",
    "\"endfunction\"",
    "\"task\"",
    "\"endtask\"",
    "\"generate\"",
    "\"endgenerate\"",
    "\"input\"",
    "\"output\"",
    "\"inout\"",
    "\"integer\"",
    "\"wire\"",
    "\"reg\"",
    "\"event\"",
    "\"genvar\"",
    "\"real\"",
    "\"parameter\"",
    "\"signed\"",
    "<STMT_HEAD>",
    "<BLOCK_HEAD>",
    "\"(\"",
    "\")\"",
    "\"{\"",
    "\"}\"",
    "\"[\"",
    "\"]\"",
    "\";\"",
    "\"#\"",
    "\"@\"",
    "\"`\"",
    "\"$\"",
    "\",\"",
    "\":\"",
    "\"=\"",
    "<IDENT>",
    "<SPC_CHAR>",
    "\"[.]\"",
  };

}