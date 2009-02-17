/*******************************************************************************
 * Copyright (c) 2004, 2006 KOBAYASHI Tadashi and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    KOBAYASHI Tadashi - initial API and implementation
 *******************************************************************************/

package net.sourceforge.veditor.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;

/**
 * find reserved word
 */
public class HdlScanner extends RuleBasedScanner
{
	public static HdlScanner createForVerilog(ColorManager manager)
	{
		return new HdlScanner(manager, true);
	}
	public static HdlScanner createForVhdl(ColorManager manager)
	{
		return new HdlScanner(manager, false);
	}

	private static final String[] verilogWords = {"always", "and", "assign",
			"attribute", "begin", "buf", "bufif0", "bufif1", "case", "casex",
			"casez", "cmos", "deassign", "default", "defparam", "disable",
			"edge", "else", "end", "endcase", "endfunction", "endmodule",
			"endprimitive", "endspecify", "endtable", "endtask", "event",
			"for", "force", "forever", "fork", "function", "highz0", "highz1",
			"if", "ifnone", "initial", "inout", "input", "integer", "join",
			"medium", "module", "large", "macromodule", "nand", "negedge",
			"nmos", "nor", "not", "notif0", "notif1", "or", "output",
			"parameter", "pmos", "posedge", "primitive", "pull0", "pull1",
			"pulldown", "pullup", "rcmos", "real", "realtime", "reg",
			"release", "repeat", "rnmos", "rpmos", "rtran", "rtranif0",
			"rtranif1", "scalared", "signed", "small", "specify", "specparam",
			"strenght", "strong0", "strong1", "supply0", "supply1", "table",
			"task", "time", "tran", "tranif0", "tranif1", "tri", "tri0",
			"tri1", "triand", "trior", "trireg", "unsigned", "vectoryd",
			"wait", "wand", "weak0", "weak1", "while", "wire", "wor", "xnor",
			"xor",
			"generate", "endgenerate", "genvar", "localparam"};

	private static final String[] verilogDirectives = { "`ifdef", "`else",
			"`endif", "`if", "`define", "`undef", "`timescale", "`include" };

	private static final String[] vhdlWords = { "abs", "access", "after",
			"alias", "all", "and", "architecture", "array", "assert",
			"attribute", "begin", "block", "body", "buffer", "bus", "case",
			"component", "configuration", "constant", "disconnect", "downto",
			"else", "elsif", "end", "entity", "exit", "file", "for",
			"function", "generate", "generic", "guarded", "if", "impure", "in",
			"inertial", "inout", "is", "label", "library", "linkage",
			"literal", "loop", "map", "mod", "nand", "new", "next", "nor",
			"not", "null", "of", "on", "open", "or", "others", "out",
			"package", "port", "postponed", "procedure", "process", "pure",
			"range", "record", "register", "reject", "rem", "report", "return",
			"rol", "ror", "select", "severity", "shared", "signal", "sla",
			"sll", "sra", "srl", "subtype", "then", "to", "transport", "type",
			"unaffected", "units", "until", "use", "variable", "wait", "when",
			"while", "with", "xnor", "xor" };

	private HdlScanner(ColorManager manager, boolean isVerilog)
	{
		IToken keyword = new Token(HdlTextAttribute.KEY_WORD
				.getTextAttribute(manager));
		IToken directive = new Token(HdlTextAttribute.DIRECTIVE
				.getTextAttribute(manager));
		IToken other = new Token(HdlTextAttribute.DEFAULT
				.getTextAttribute(manager));

		List<IRule> rules = new ArrayList<IRule>();

		WordRule wordRule = new WordRule(new WordDetector(isVerilog), other);

		if (isVerilog)
		{
			for (int i = 0; i < verilogDirectives.length; i++)
				wordRule.addWord(verilogDirectives[i], directive);
			for (int i = 0; i < verilogWords.length; i++)
				wordRule.addWord(verilogWords[i], keyword);
		}
		else
		{
			for (int i = 0; i < vhdlWords.length; i++)
				wordRule.addWord(vhdlWords[i], keyword);
			// it is possible to use upper case in VHDL
			for (int i = 0; i < vhdlWords.length; i++)
				wordRule.addWord(vhdlWords[i].toUpperCase(), keyword);
		}

		rules.add(wordRule);

		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		setRules(result);
	}
	
	private static class WordDetector implements IWordDetector {

		private boolean isVerilog;

		public WordDetector(boolean isVerilog) {
			this.isVerilog = isVerilog;
		}

		public boolean isWordPart(char character) {
			return Character.isJavaIdentifierPart(character);
		}

		public boolean isWordStart(char character) {
			if (isVerilog && character == '`')
				return true;
			return Character.isJavaIdentifierStart(character);
		}

	}
}
