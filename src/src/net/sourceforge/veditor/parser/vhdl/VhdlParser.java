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

package net.sourceforge.veditor.parser.vhdl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.veditor.VerilogPlugin;
import net.sourceforge.veditor.parser.HdlParserException;
import net.sourceforge.veditor.parser.IParser;
import net.sourceforge.veditor.parser.OutlineContainer;
import net.sourceforge.veditor.parser.OutlineDatabase;
import net.sourceforge.veditor.parser.OutlineElementFactory;
import net.sourceforge.veditor.parser.OutlineContainer.Collapsible;
import net.sourceforge.veditor.parser.vhdl.VhdlParserCore;
import net.sourceforge.veditor.semanticwarnings.SemanticWarnings;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;


/**
 * implementation class of VhdlParserCore<p/>
 * for separating definition from JavaCC code
 */
public class VhdlParser extends VhdlParserCore implements IParser
{
	private IFile m_File;
	private Reader m_Reader;
	private static OutlineElementFactory m_OutlineElementFactory=new VhdlOutlineElementFactory();
	//Minimum number of comment lines before they are collapsible
	private final int COMMENT_LINE_GROUP=5;
	private int m_PrevCommentLine;
	private int m_StartCommentBlock;
	private int m_LastCommentLine;
	private int m_EndCommentBlock;
	private StringBuffer m_Comment;
	private OutlineContainer m_OutlineContainer;
	private Pattern[] taskTokenPattern;


	public VhdlParser(Reader reader, IProject project, IFile file)
	{
		super(reader);
		m_Reader = reader;
		m_File = file;	
		m_LastCommentLine=-1;
		m_PrevCommentLine=-1;
		m_Comment=new StringBuffer();		
		OutlineDatabase database = OutlineDatabase.getProjectsDatabase(project);		
		if(database != null){
			m_OutlineContainer = database.getOutlineContainer(file);
		}
		
		taskTokenPattern=new Pattern[taskCommentTokens.length];
		for(int i=0; i< taskCommentTokens.length;i++){
		    String regex=".*\\b("+taskCommentTokens[i]+")(\\b.*)";
		    taskTokenPattern[i]= Pattern.compile(regex);
		}
		
	}
	
	protected void addCollapsible(int startLine,int endLine){		
		if(m_OutlineContainer!=null){
			Collapsible c= m_OutlineContainer.new Collapsible(startLine,endLine);
			m_OutlineContainer.addCollapsibleRegion(c);
		}
	}
	protected void beginOutlineElement(int begin,int col,String name,String type){
		
		if(m_OutlineContainer!=null)
			m_OutlineContainer.beginElement(name, type, begin, col, m_File, m_OutlineElementFactory);
	}
	protected void endOutlineElement(int end,int col,String name,String type){
		if(m_OutlineContainer!=null)
			m_OutlineContainer.endElement(name, type, end, col, m_File);
	}
		
	/** 
	 * Parses the file and updates the outline
	 */	
	public void parse() throws HdlParserException
	{
		try {
			m_Reader.reset();
		} catch (IOException e1) {			
		}
		
		try
		{	
			//clear the tree
			m_OutlineContainer.clear();
			//start by looking for a design file entity;

			boolean ignorefile = false;
			int filesize=0;
			{
				InputStreamReader reader;
				try {
					reader = new InputStreamReader(m_File.getContents());
					BufferedReader breader = new BufferedReader(reader);
					
					try {
						while(true) {
							String line = breader.readLine();
							if(line==null) {
								break;
							}
							filesize+=line.length();
							if(line.contains("-- turn off superfluous VHDL processor warnings")) {
								ignorefile=true;
							}
						}
					} catch(IOException e) {
						ignorefile=true;	
					}
				} catch (CoreException e1) {
					ignorefile=true;
				}
			}
			
			if(!ignorefile && filesize < 500000) {
				ASTdesign_file designFile=design_file();
				updateOutline(designFile);
				SemanticWarnings warn = new SemanticWarnings(m_File);
				warn.check(designFile);
			} else {
				ASTdesign_file designFile=new ASTdesign_file(JJTDESIGN_FILE);
				updateOutline(designFile);
			}
			
		}
		catch (ParseException e){
			//convert the exception to a generic one			
			HdlParserException hdlParserException =new HdlParserException(e);
			errs.Error("Parser cannot recover from previous error(s).", hdlParserException);
			updateMarkers();
			throw hdlParserException;
		}
		catch (TokenMgrError e){			
			HdlParserException hdlParserException =new HdlParserException(e);
			errs.Error("Parser cannot recover from previous error(s).Unexpected character", hdlParserException);
			updateMarkers();			
			throw hdlParserException;
		}		
		parseLineComment();		
		updateMarkers();
	}

	/**
	 * Updates the error and warning markers
	 */
	protected void updateMarkers(){
		for(ErrorHandler.Error error:getErrorHandler().getErrors()){
			VerilogPlugin.setErrorMarker(m_File, error.getLine(), error.getMessage());
		}
		for(ErrorHandler.Error error:getErrorHandler().getWarnings()){
			VerilogPlugin.setErrorMarker(m_File, error.getLine(), error.getMessage());
		}
	}
	
	/**
	 * Checks to see if a comment starts with one of the task tokens
	 * @param comment The comment to check
	 * @param msg String array to receive the message type associated with the string
	 * @return The string of the Token. null if the comment does not have a task token
	 */
	protected String getTaskToken(String comment,String []msg ){
	    	    
	    for(Pattern pattern:taskTokenPattern){	        
	        Matcher matcher= pattern.matcher(comment);
	        if(matcher.find()){	            
	            msg[0]=matcher.group(1)+matcher.group(2);
	            return matcher.group(1);
	        }
	    }
	    //if we get here, no task was found
	    return null;
	}
	
	/**
	 * Adds a task to the given line based on the comment token
	 * @param type
	 * @param line
	 */
	protected void addTaskToLine(String type,String msg, int line){
	    //The first marker is considered high priority
	    if(type.startsWith(taskCommentTokens[0])){
	        VerilogPlugin.setTaskMarker(m_File, line, msg,IMarker.PRIORITY_HIGH);
	    }
	    else{
	        VerilogPlugin.setTaskMarker(m_File, line, msg,IMarker.PRIORITY_NORMAL);
	    }
	}
	
	/**
	 * Removes a task from the given line
	 * @param line Line number to remove the task from
	 */
	protected void removeTaskFromLine(int line){
	    VerilogPlugin.clearAutoTaskMarker(m_File, line);
	}
	
	/**
	 * Removes all the auto generated tasks from the file
	 */
	protected void clearAutoTasks(){
	    VerilogPlugin.clearAllAutoTaskMarkers(m_File);
	}
	/**
	 * parse line comment for content outline
	 */
	protected void parseLineComment() {
		try {
			m_Reader.reset();
			clearAutoTasks();

			int line = 1;
			int column = 0;
			int []c = new int[2];
			int firstNonSpace=0;
			
			c[1]= m_Reader.read();		
			while ( c[1] != -1) {				
				if(!Character.isWhitespace( c[1] ) && firstNonSpace==0){
					firstNonSpace=column;
				}
				switch ( c[1] ) {
					case '\n' :
						line++;
						column = 0;
						firstNonSpace=0;
						break;
					case '-' :
						//comment
					if (c[0] == '-') {
							String comment = getLineComment(m_Reader);
							if (comment != null){
							    String []msg=new String[1];
								addComment(line, comment);
								//check to see if we need to add a task
								String taskToken=getTaskToken(comment,msg);
								if(taskToken != null){								    
								    addTaskToLine(taskToken, msg[0], line);
								}								
							}
    						//if the beginning of the comment (the first "-" of "--") is
    						//the first non space character of the line 
    						if (column-1 == firstNonSpace) {
    							coalesceComments(line);
    							}
    						// increment the line counter because getLineComment
    						// consumes the new line character
							line++;
							column = 0;
							c[0]=0;
							c[1]=0;
							firstNonSpace=0;
						}
						break;
					default :
						break;
				}
				c[0] = c[1];
				c[1] = m_Reader.read();
				column++;
			}
			//call one last time to catch the last comment
			coalesceComments(Integer.MAX_VALUE);
		} catch (IOException e) {
		}
	}

	private String getLineComment(Reader reader) throws IOException
	{
		StringBuffer str = new StringBuffer();
		boolean enable = false;

		//  copy to StringBuffer
		int c = reader.read();
		while (c != '\n' && c != -1)
		{
			if (Character.isLetterOrDigit((char)c) || enable)
			{
				str.append((char)c);
				enable = true;
			}
			c = reader.read();
		}

		// delete tail
		for (int i = str.length() - 1; i >= 0; i--)
		{
			char ch = str.charAt(i);
			if (Character.isLetterOrDigit(ch))
				break;
			else
				str.deleteCharAt(i);
		}

		if (str.length() != 0)
			return str.toString();
		else
			return null;
	}

	private void addComment(int line, String comment)
	{

		//continuous comments
		if (m_PrevCommentLine + 1 == line){
			m_PrevCommentLine = line;			
		}
		else{
			//if starting a new block
			m_OutlineContainer.addComment(m_PrevCommentLine, m_Comment.toString());
			m_Comment=new StringBuffer();			
			m_PrevCommentLine = line;			
		}
		m_Comment.append(comment);
		m_Comment.append("\n");
	}
	/**
	 * This function keeps track of contiguous comment blocks and 
	 * adds a collapsible section if they exceed a threshold
	 * @param line the line number of a complete comment line
	 */
	private void coalesceComments(int line){
		//contiguous blocks
		if (m_LastCommentLine + 1 == line)
		{
			//grow the block
			m_EndCommentBlock=line;
		}
		else{
			//if starting a new block
			if((m_EndCommentBlock - m_StartCommentBlock+1) >= COMMENT_LINE_GROUP){
				addCollapsible(m_StartCommentBlock, m_EndCommentBlock);
			}
			m_StartCommentBlock=line;
			m_EndCommentBlock=line;
		}
		m_LastCommentLine=line;
	}
	
	/**
	 * Recursively updates the outline database
	 * @param node top level node element;
	 */
	protected void updateOutline(SimpleNode node) {
		int childNum = 0;
		StringBuffer name = new StringBuffer();
		StringBuffer type =  new StringBuffer();;
		Boolean bNeetToOutline = false;
		Boolean bIsCollapsible = false;
	
		// end of the tree
		if (node == null) {
			return;
		} else if (node instanceof ASTarchitecture_body) {
			bNeetToOutline = true;
			bIsCollapsible = true;
			childNum += examineArchitecture((ASTarchitecture_body) node, name,
					type);

		} else if (node instanceof ASTpackage_declaration) {
			bNeetToOutline = true;
			bIsCollapsible = true;
			childNum += examinePackageDecl((ASTpackage_declaration) node, name,
					type);
		} else if (node instanceof ASTpackage_body) {
			bNeetToOutline = true;
			bIsCollapsible = true;
			childNum += examinePackageBody((ASTpackage_body) node, name, type);
		} else if (node instanceof ASTprocess_statement) {
			bNeetToOutline = true;
			childNum += examineProcess((ASTprocess_statement) node, name, type);
		} else if (node.id == JJTSUBPROGRAM_SPECIFICATION) {
			bNeetToOutline = true;
			bIsCollapsible = true;
			childNum += examineSubProgramSpec((ASTsubprogram_specification)node,name,type);
		} else if (node.id == JJTSUBPROGRAM_BODY) {
			bNeetToOutline = true;
			bIsCollapsible = true;
			childNum += examineSubProgramBody((ASTsubprogram_body)node,name,type);
		} else if (node instanceof ASTcomponent_instantiation_statement) {
			bNeetToOutline = true;
			bIsCollapsible = true;
			childNum += examineComponentInst((ASTcomponent_instantiation_statement) node,
					name, type);
		} else if (node instanceof ASTcomponent_declaration) {
			bNeetToOutline = true;
			bIsCollapsible = true;
			childNum += examineComponentDecl((ASTcomponent_declaration) node,
					name, type);
		} else if (node instanceof ASTentity_declaration) {
			bNeetToOutline = true;
			bIsCollapsible = true;
			childNum += examineEntityDecl((ASTentity_declaration) node, name,
					type);

		} else if (node instanceof ASTport_clause) {
			childNum += examinePortClause((ASTport_clause) node);
		} else if (node instanceof ASTgeneric_clause) {
			childNum += examineGenericClause((ASTgeneric_clause) node);
		} else if (node instanceof ASTvariable_declaration) {
			childNum += examineDecl(node);
		} else if (node instanceof ASTfile_declaration) {
			childNum += examineDecl(node);
		} else if (node instanceof ASTalias_declaration) {
			childNum += examineAlias((ASTalias_declaration) node);
		} else if (node instanceof ASTsignal_declaration) {
			childNum += examineDecl(node);
		} else if (node instanceof ASTconstant_declaration) {
			childNum += examineDecl(node);
		} else if (node instanceof ASTblock_statement) {
			bIsCollapsible = true;
		} else if (node instanceof ASTprocess_statement) {
			bIsCollapsible = true;
		}

		// add the begin clause
		if (bNeetToOutline) {
			beginOutlineElement(node.getFirstToken().beginLine, node
					.getFirstToken().beginColumn, name.toString(), type.toString());

		}
		// now process all the children
		// here, we do not initialize the loop counter because some of
		// the children may have been used up above
		for (; childNum < node.getChildCount(); childNum++) {
			updateOutline(node.getChild(childNum));
		}

		// add the end clause
		if (bNeetToOutline) {
			endOutlineElement(node.getLastToken().endLine,
					node.getLastToken().endColumn, name.toString(), type.toString());
		}
		// add the item to the collapsible list
		if (bIsCollapsible) {
			addCollapsible(node.getFirstToken().beginLine,
					node.getLastToken().endLine);
		}
	}
	
	/**
	 * Breaks out an architecture body
	 * @param archBody
	 * @param name
	 * @param type
	 * @return number of children consumed by this function
	 */
	protected int examineArchitecture(ASTarchitecture_body archBody,StringBuffer name,StringBuffer type){
		//get the name
		name.append(archBody.getIdentifier());		
		//get the entity name			
		type.append("architecture#"+archBody.getEntityName());		
		return 2;
	}	
	/**
	 * Breaks out a package decl
	 * @param packageDecl
	 * @param name
	 * @param type
	 * @return  number of children consumed by this function
	 */
	protected int examinePackageDecl(ASTpackage_declaration packageDecl,StringBuffer name,StringBuffer type){
		//get the name
		name.append(packageDecl.getIdentifier());
		type.append("packageDecl#");
		return 1;
	}
	/**
	 * Breaks out a package body
	 * @param packageBody
	 * @param name
	 * @param type
	 * @return  number of children consumed by this function
	 */
	protected int examinePackageBody(ASTpackage_body packageBody,StringBuffer name,StringBuffer type){
		//get the name
		name.append(packageBody.getIdentifier());		
		type.append("packageBody#");			
		return 1;
	}
	/**
	 * Breaks out a process
	 * @param processStatement
	 * @param name
	 * @param type
	 * @return  number of children consumed by this function
	 */
	protected int examineProcess(ASTprocess_statement processStatement,StringBuffer name,StringBuffer type){
		int results=0;		

		//get the name
		if(processStatement.getIdentifier()==null){
			name.append("<unknown>");
		}
		else{
			name.append(processStatement.getIdentifier());
			results++;
		}
		type.append("process#");
		return results;
	}
	/**
	 * Breaks out a component declaration
	 * @param componentDecl
	 * @param name
	 * @param type
	 * @return  number of children consumed by this function
	 */
	protected int examineComponentDecl(ASTcomponent_declaration componentDecl,StringBuffer name,StringBuffer type){
		name.append(componentDecl.getIdentifier());		
		type.append("componentDecl#");
		return 0;
	}
	/**
	 * Breaks out a component,entity, and configuration instantiation 
	 * @param componentInst
	 * @param name
	 * @param type
	 * @return
	 */
	protected int examineComponentInst(ASTcomponent_instantiation_statement componentInst,StringBuffer name,StringBuffer type){
		
		ASTinstantiated_unit instantiatedUnit=componentInst.getInstatiatedUnit();
		String instantType=instantiatedUnit.getType();
		
		name.append(componentInst.getIdentifier());
		if(instantType.equalsIgnoreCase("entity")){
			type.append("entityInst#");
			type.append(instantiatedUnit.getName());
			type.append("#");
			type.append(instantiatedUnit.getidentifier());
		}else if(instantType.equalsIgnoreCase("configuration")){
			type.append("configurationInst#");
			type.append(instantiatedUnit.getName());
		}else {
			type.append("componentInst#");
			type.append(instantiatedUnit.getName());
		}		
		
		return 1;
	}
	/**
	 * Breaks out a component declaration
	 * @param entityDecl
	 * @param name
	 * @param type
	 * @return  number of children consumed by this function
	 */
	protected int examineEntityDecl(ASTentity_declaration entityDecl,StringBuffer name,StringBuffer type){
		name.append(entityDecl.getIdentifier());
		type.append("entityDecl#");	
		return 0;
	}
	/**
	 * Breaks out a port clause and adds its children to the outline
	 * @param portClause
	 * @return  number of children consumed by this function
	 */
	protected int examinePortClause(ASTport_clause portClause){
		String []names=null;
		String type;
		
		ASTinterface_list interfaceList=portClause.getInterfaceList();
		for(Node c:interfaceList.children){
			SimpleNode child=(SimpleNode)c;
			type="port#";
			if (child instanceof ASTinterface_file_declaration) {
				ASTinterface_file_declaration file = (ASTinterface_file_declaration) child;
				names=file.getIdentifierList();
				type+="# #"+file.getSubType();	
			} else	if (child instanceof ASTinterface_signal_declaration) {
				ASTinterface_signal_declaration signal = (ASTinterface_signal_declaration) child;
				names=signal.getIdentifierList();
				type+=signal.getMode()+"#"+signal.getSubType();					
			} else if (child instanceof ASTinterface_constant_declaration) {
				ASTinterface_constant_declaration constant = (ASTinterface_constant_declaration) child;
				names=constant.getIdentifierList();
				type+="in#"+constant.getSubType();
			} else if (child instanceof ASTinterface_variable_declaration) {
				ASTinterface_variable_declaration var = (ASTinterface_variable_declaration) child;
				names=var.getIdentifierList();
				type+=var.getMode()+"#"+var.getSubType();
			} else{
				continue;
			}				
			//add all the identifiers
			for(String identifier:names){
				beginOutlineElement(
						child.getFirstToken().beginLine,
						child.getFirstToken().beginColumn,
						identifier, 
						type);
				endOutlineElement(
						child.getLastToken().endLine,
						child.getLastToken().endColumn,
						identifier, 
						type);
			}
		}
		return 1;
	}
	/**
	 * Breaks out a generic clause and adds it children to the outline
	 * @param genericClause
	 * @return  number of children consumed by this function
	 */
	protected int examineGenericClause(ASTgeneric_clause genericClause) {
		String[] names = null;
		String type;

		ASTinterface_list interfaceList = genericClause.getInterfaceList();
		for (Node c : interfaceList.children) {
			SimpleNode child = (SimpleNode) c;
			type = "generic#";
			if (child instanceof ASTinterface_file_declaration) {
				ASTinterface_file_declaration file = (ASTinterface_file_declaration) child;
				names = file.getIdentifierList();
				type += "#" + file.getSubType();
			} else if (child instanceof ASTinterface_signal_declaration) {
				ASTinterface_signal_declaration signal = (ASTinterface_signal_declaration) child;
				names = signal.getIdentifierList();
				type += signal.getSubType();
			} else if (child instanceof ASTinterface_constant_declaration) {
				ASTinterface_constant_declaration constant = (ASTinterface_constant_declaration) child;
				names = constant.getIdentifierList();
				type += constant.getSubType();
			} else if (child instanceof ASTinterface_variable_declaration) {
				ASTinterface_variable_declaration var = (ASTinterface_variable_declaration) child;
				names = var.getIdentifierList();
				type += var.getSubType();
			} else {
				continue;
			}
			// add all the identifiers
			for (String identifier : names) {
				beginOutlineElement(child.getFirstToken().beginLine, child
						.getFirstToken().beginColumn, identifier, type);
				endOutlineElement(child.getLastToken().endLine, child
						.getLastToken().endColumn, identifier, type);
			}
		}
		return 1;
	}
	/**
	 * Breaks out an alias declaration
	 * @param alias
	 * @return
	 */
	protected int examineAlias(ASTalias_declaration alias){
		String name = alias.getIdentifier();
		String type = "alias#" + alias.getName();
		beginOutlineElement(alias.getFirstToken().beginLine, alias
				.getFirstToken().beginColumn, name, type);
		endOutlineElement(alias.getLastToken().endLine,
				alias.getLastToken().endColumn, name, type);
		return 1;
	}
	/**
	 * Breaks out signal,variable,constant, and file type declarations and adds
	 * them to the outline
	 * @param node
	 * @return  number of children consumed by this function
	 */
	protected int examineDecl(SimpleNode node){
		String type=null;
		String []names=null;
		
		if(node instanceof ASTvariable_declaration){
			ASTvariable_declaration var = (ASTvariable_declaration) node;		
			names=var.getIdentifierList();
			type="variable#"+var.getSubType();
		}else if(node instanceof ASTsignal_declaration){
			ASTsignal_declaration signal = (ASTsignal_declaration) node;			
			names=signal.getIdentifierList();
			type="signal#"+signal.getSubType();
		}else if(node instanceof ASTconstant_declaration){
			ASTconstant_declaration constant = (ASTconstant_declaration) node;
			names=constant.getIdentifierList();
			type="constant#"+constant.getSubType();
		}else if(node instanceof ASTfile_declaration){
			ASTfile_declaration file = (ASTfile_declaration) node;
			names=file.getIdentifierList();
			type="file#"+file.getSubType();
		}	
		
		for(String identifier:names){
			beginOutlineElement(
					node.getFirstToken().beginLine,
					node.getFirstToken().beginColumn,
					identifier, 
					type);
			endOutlineElement(
					node.getLastToken().endLine,
					node.getLastToken().endColumn,
					identifier, 
					type);
			
		}
		
		return 1;
	}
	
	/**
	 * Examines a subprogram specification. There are assumed to occur out side of their body  
	 * @param spec
	 * @param name
	 * @param type
	 * @return number of child elements consumed by this function
	 */
	protected int examineSubProgramSpec(ASTsubprogram_specification spec,StringBuffer name,StringBuffer type){
		String subType=spec.getType().toLowerCase();
		
		name.append(spec.getIdentifier());
		if(subType.contains("function")){
			type.append("functionDecl#");
		}else if(subType.contains("procedure")){
			type.append("procedureDecl#");
		}
		
		ASTformal_parameter_list paramters=spec.getParameters();
		examineParameterList(paramters, type);
		return 1;
	}
	/**
	 * Examines a subprogram specification. There are assumed to occur out side of their body  
	 * @param spec
	 * @param name
	 * @param type
	 * @return number of child elements consumed by this function
	 */
	protected int examineSubProgramBody(ASTsubprogram_body body,StringBuffer name,StringBuffer type){
		ASTsubprogram_specification spec=body.getSpecification();
		String subType=spec.getType().toLowerCase();
		
		name.append(spec.getIdentifier());
		if(subType.contains("function")){
			type.append("function#");
		}else if(subType.contains("procedure")){
			type.append("procedure#");
		}
		
		ASTformal_parameter_list paramters=spec.getParameters();
		examineParameterList(paramters, type);
		
		return 1;
	}

	int examineParameterList(ASTformal_parameter_list paramters,
			StringBuffer type) {
		String[] paramNames;
		String paramType;

		if (paramters != null) {
			ASTinterface_list interfaceList = paramters.getInterfaceList();
			for (Node c : interfaceList.children) {
				SimpleNode child = (SimpleNode) c;
				if (child instanceof ASTinterface_file_declaration) {
					ASTinterface_file_declaration file = (ASTinterface_file_declaration) child;
					paramNames = file.getIdentifierList();
					paramType = file.getSubType() + "# ";
				} else if (child instanceof ASTinterface_signal_declaration) {
					ASTinterface_signal_declaration signal = (ASTinterface_signal_declaration) child;
					paramNames = signal.getIdentifierList();
					paramType = signal.getSubType() + "#" + signal.getMode()+ "#";;
				} else if (child instanceof ASTinterface_constant_declaration) {
					ASTinterface_constant_declaration constant = (ASTinterface_constant_declaration) child;
					paramNames = constant.getIdentifierList();
					paramType = constant.getSubType() + "#in#";
				} else if (child instanceof ASTinterface_variable_declaration) {
					ASTinterface_variable_declaration var = (ASTinterface_variable_declaration) child;
					paramNames = var.getIdentifierList();
					paramType = var.getSubType() + "#" + var.getMode()+ "#";;
				} else {
					continue;
				}
				// add all the parameters
				for (String identifier : paramNames) {
					type.append(identifier);
					type.append("#" + paramType);
				}
			}			
		}
		return 1;
	}

	public int getContext()
	{
		return 0;
	}
}






