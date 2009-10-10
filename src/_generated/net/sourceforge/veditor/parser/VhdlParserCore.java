/* Generated By:JavaCC: Do not edit this line. VhdlParserCore.java */
package net.sourceforge.veditor.parser;

public abstract class VhdlParserCore implements VhdlParserCoreConstants {
        protected abstract void addModule(int begin, String name);
        protected abstract void endModule(int line);
        protected abstract void addPort(int line, String portName);
        protected abstract void addVariable(int line, String varName);
        protected abstract void addParameter(int line, String name);
        protected abstract void addElement(int begin, int end, String type, String name);
        protected abstract void addInstance(int begin, int end, String module, String inst);
        protected abstract void beginStatement();
        protected abstract void endStatement();
        protected abstract void setPrefix(String fix);
        protected abstract void setPostfix(String fix);

        public void parse() throws ParseException
        {
                modules();
        }

  final public void modules() throws ParseException {
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LIBRARY:
        jj_consume_token(LIBRARY);
        skipTo(EOS);
        break;
      case USE:
        jj_consume_token(USE);
        skipTo(EOS);
        break;
      case ENTITY:
        entity();
        break;
      case ARCHITECTURE:
        architecture();
        break;
      case PACKAGE:
        packageDef();
        break;
      default:
        jj_la1[0] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LIBRARY:
      case PACKAGE:
      case USE:
      case ENTITY:
      case ARCHITECTURE:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_1;
      }
    }
  }

  final public void entity() throws ParseException {
        Token name ;
        Token end ;
    jj_consume_token(ENTITY);
    name = identifier();
    jj_consume_token(IS);
                addModule( name.beginLine, name.image );
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case GENERIC:
      case PORT:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case GENERIC:
        genericList();
        break;
      case PORT:
        portList();
        break;
      default:
        jj_la1[3] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ATTRIBUTE:
        ;
        break;
      default:
        jj_la1[4] = jj_gen;
        break label_3;
      }
      attributeList();
    }
    jj_consume_token(END);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ENTITY:
      jj_consume_token(ENTITY);
      break;
    default:
      jj_la1[5] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IDENT:
      identifier();
      break;
    default:
      jj_la1[6] = jj_gen;
      ;
    }
    end = jj_consume_token(EOS);
                endModule( end.beginLine );
  }

  final public void architecture() throws ParseException {
        Token end;
    jj_consume_token(ARCHITECTURE);
    identifier();
    jj_consume_token(OF);
    identifier();
    jj_consume_token(IS);
    moduleBody();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ARCHITECTURE:
      jj_consume_token(ARCHITECTURE);
      break;
    default:
      jj_la1[7] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IDENT:
      identifier();
      break;
    default:
      jj_la1[8] = jj_gen;
      ;
    }
    end = jj_consume_token(EOS);
                endModule( end.beginLine );
  }

  final public void packageDef() throws ParseException {
        Token name ;
        Token end ;
    jj_consume_token(PACKAGE);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case BODY:
      jj_consume_token(BODY);
      break;
    default:
      jj_la1[9] = jj_gen;
      ;
    }
    identifier();
    jj_consume_token(IS);
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CONSTANT:
      case PROCEDURE:
      case FUNCTION:
      case SIGNAL:
      case SHARED:
      case VARIABLE:
      case TYPE:
      case SUBTYPE:
      case FILE:
      case ALIAS:
      case ATTRIBUTE:
      case FOR:
      case DISCONNECT:
      case GROUP:
        packageDeclaration();
        break;
      case COMPONENT:
        jj_consume_token(COMPONENT);
        name = identifier();
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case IS:
          jj_consume_token(IS);
          break;
        default:
          jj_la1[10] = jj_gen;
          ;
        }
                        addModule( name.beginLine, name.image );
        label_5:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case GENERIC:
          case PORT:
            ;
            break;
          default:
            jj_la1[11] = jj_gen;
            break label_5;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case GENERIC:
            genericList();
            break;
          case PORT:
            portList();
            break;
          default:
            jj_la1[12] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        end = jj_consume_token(END);
        jj_consume_token(COMPONENT);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case IDENT:
          identifier();
          break;
        default:
          jj_la1[13] = jj_gen;
          ;
        }
        jj_consume_token(EOS);
                        endModule( end.beginLine );
        break;
      default:
        jj_la1[14] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CONSTANT:
      case PROCEDURE:
      case FUNCTION:
      case SIGNAL:
      case SHARED:
      case VARIABLE:
      case TYPE:
      case SUBTYPE:
      case FILE:
      case ALIAS:
      case ATTRIBUTE:
      case COMPONENT:
      case FOR:
      case DISCONNECT:
      case GROUP:
        ;
        break;
      default:
        jj_la1[15] = jj_gen;
        break label_4;
      }
    }
    jj_consume_token(END);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PACKAGE:
      jj_consume_token(PACKAGE);
      break;
    default:
      jj_la1[16] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case BODY:
      jj_consume_token(BODY);
      break;
    default:
      jj_la1[17] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IDENT:
      identifier();
      break;
    default:
      jj_la1[18] = jj_gen;
      ;
    }
    jj_consume_token(EOS);
  }

  final public void genericList() throws ParseException {
    jj_consume_token(GENERIC);
    jj_consume_token(LPAREN);
    genericElement();
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EOS:
        ;
        break;
      default:
        jj_la1[19] = jj_gen;
        break label_6;
      }
      jj_consume_token(EOS);
      genericElement();
    }
    jj_consume_token(RPAREN);
    jj_consume_token(EOS);
  }

  final public void genericElement() throws ParseException {
        Token port;
        Token postfix;
                setPrefix("port ");
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case CONSTANT:
    case SIGNAL:
    case VARIABLE:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CONSTANT:
        jj_consume_token(CONSTANT);
        break;
      case SIGNAL:
        jj_consume_token(SIGNAL);
        break;
      case VARIABLE:
        jj_consume_token(VARIABLE);
        break;
      default:
        jj_la1[20] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[21] = jj_gen;
      ;
    }
    port = identifier();
                addParameter(port.beginLine, port.image);
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[22] = jj_gen;
        break label_7;
      }
      jj_consume_token(COMMA);
      port = identifier();
                        addParameter(port.beginLine, port.image);
    }
    jj_consume_token(COLON);
    postfix = interfaceElementIndication();
                setPostfix(postfix.image);
  }

  final public void portList() throws ParseException {
    jj_consume_token(PORT);
    jj_consume_token(LPAREN);
    portElement();
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EOS:
        ;
        break;
      default:
        jj_la1[23] = jj_gen;
        break label_8;
      }
      jj_consume_token(EOS);
      portElement();
    }
    jj_consume_token(RPAREN);
    jj_consume_token(EOS);
  }

  final public void attributeList() throws ParseException {
    jj_consume_token(ATTRIBUTE);
    identifier();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OF:
      jj_consume_token(OF);
      entityNameList();
      jj_consume_token(COLON);
      entityClass();
      jj_consume_token(IS);
      break;
    case COLON:
      jj_consume_token(COLON);
      identifier();
      break;
    default:
      jj_la1[24] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    skipTo(EOS);
  }

  final public void entityClass() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ENTITY:
      jj_consume_token(ENTITY);
      break;
    case ARCHITECTURE:
      jj_consume_token(ARCHITECTURE);
      break;
    case CONFIGURATION:
      jj_consume_token(CONFIGURATION);
      break;
    case PACKAGE:
      jj_consume_token(PACKAGE);
      break;
    case PROCEDURE:
      jj_consume_token(PROCEDURE);
      break;
    case FUNCTION:
      jj_consume_token(FUNCTION);
      break;
    case TYPE:
      jj_consume_token(TYPE);
      break;
    case SUBTYPE:
      jj_consume_token(SUBTYPE);
      break;
    case CONSTANT:
      jj_consume_token(CONSTANT);
      break;
    case SIGNAL:
      jj_consume_token(SIGNAL);
      break;
    case VARIABLE:
      jj_consume_token(VARIABLE);
      break;
    case FILE:
      jj_consume_token(FILE);
      break;
    case COMPONENT:
      jj_consume_token(COMPONENT);
      break;
    case LABEL:
      jj_consume_token(LABEL);
      break;
    case LITERAL:
      jj_consume_token(LITERAL);
      break;
    case UNITS:
      jj_consume_token(UNITS);
      break;
    case GROUP:
      jj_consume_token(GROUP);
      break;
    default:
      jj_la1[25] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void entityNameList() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IDENT:
      identifier();
      break;
    case OTHERS:
      jj_consume_token(OTHERS);
      break;
    case ALL:
      jj_consume_token(ALL);
      break;
    default:
      jj_la1[26] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void portElement() throws ParseException {
        Token port;
        Token postfix;
                setPrefix("port ");
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case CONSTANT:
    case SIGNAL:
    case VARIABLE:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CONSTANT:
        jj_consume_token(CONSTANT);
        break;
      case SIGNAL:
        jj_consume_token(SIGNAL);
        break;
      case VARIABLE:
        jj_consume_token(VARIABLE);
        break;
      default:
        jj_la1[27] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[28] = jj_gen;
      ;
    }
    port = identifier();
                addPort(port.beginLine, port.image);
    label_9:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[29] = jj_gen;
        break label_9;
      }
      jj_consume_token(COMMA);
      port = identifier();
                        addPort(port.beginLine, port.image);
    }
    jj_consume_token(COLON);
    postfix = interfaceElementIndication();
                setPostfix(" : " + postfix.image);
  }

  final public void moduleBody() throws ParseException {
        Token   module, inst, iend ;
    label_10:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CONSTANT:
      case PROCEDURE:
      case FUNCTION:
      case SIGNAL:
      case SHARED:
      case VARIABLE:
      case TYPE:
      case SUBTYPE:
      case FILE:
      case ALIAS:
      case ATTRIBUTE:
      case COMPONENT:
      case FOR:
      case DISCONNECT:
      case GROUP:
        ;
        break;
      default:
        jj_la1[30] = jj_gen;
        break label_10;
      }
      declaration();
    }
    jj_consume_token(BEGIN);
    label_11:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PROCESS:
      case BLOCK:
      case ASSERT:
      case WITH:
      case IDENT:
        ;
        break;
      default:
        jj_la1[31] = jj_gen;
        break label_11;
      }
      statement();
    }
    jj_consume_token(END);
  }

  final public Token identifier() throws ParseException {
        Token token ;
    token = jj_consume_token(IDENT);
                {if (true) return token ;}
    throw new Error("Missing return statement in function");
  }

  final public void packageDeclaration() throws ParseException {
        Token variable;
        Token postfix;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case CONSTANT:
    case SIGNAL:
    case SHARED:
    case VARIABLE:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CONSTANT:
        jj_consume_token(CONSTANT);
        break;
      case SIGNAL:
        jj_consume_token(SIGNAL);
        break;
      case SHARED:
      case VARIABLE:
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case SHARED:
          jj_consume_token(SHARED);
          break;
        default:
          jj_la1[32] = jj_gen;
          ;
        }
        jj_consume_token(VARIABLE);
        break;
      default:
        jj_la1[33] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                        setPrefix(token.image + " ");
      variable = identifier();
                        addVariable(variable.beginLine, variable.image);
      label_12:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[34] = jj_gen;
          break label_12;
        }
        jj_consume_token(COMMA);
        variable = identifier();
                                addVariable(variable.beginLine, variable.image);
      }
      jj_consume_token(COLON);
      postfix = interfaceElementIndication();
                        setPostfix(" : " + postfix.image);
      jj_consume_token(EOS);
      break;
    case TYPE:
    case SUBTYPE:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TYPE:
        jj_consume_token(TYPE);
        break;
      case SUBTYPE:
        jj_consume_token(SUBTYPE);
        break;
      default:
        jj_la1[35] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      identifier();
      jj_consume_token(IS);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case RECORD:
        jj_consume_token(RECORD);
        skipEnd();
        jj_consume_token(END);
        jj_consume_token(RECORD);
        jj_consume_token(EOS);
        break;
      default:
        jj_la1[36] = jj_gen;
        skipTo(EOS);
      }
      break;
    case FILE:
    case ALIAS:
    case ATTRIBUTE:
    case FOR:
    case DISCONNECT:
    case GROUP:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case FILE:
        jj_consume_token(FILE);
        break;
      case ALIAS:
        jj_consume_token(ALIAS);
        break;
      case ATTRIBUTE:
        jj_consume_token(ATTRIBUTE);
        break;
      case FOR:
        jj_consume_token(FOR);
        break;
      case DISCONNECT:
        jj_consume_token(DISCONNECT);
        break;
      case GROUP:
        jj_consume_token(GROUP);
        break;
      default:
        jj_la1[37] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      skipTo(EOS);
      break;
    case PROCEDURE:
      jj_consume_token(PROCEDURE);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EOS:
        jj_consume_token(EOS);
        break;
      default:
        jj_la1[40] = jj_gen;
        skipTo(BEGIN);
        skipEnd();
        jj_consume_token(END);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case PROCEDURE:
          jj_consume_token(PROCEDURE);
          break;
        default:
          jj_la1[38] = jj_gen;
          ;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case IDENT:
          identifier();
          break;
        default:
          jj_la1[39] = jj_gen;
          ;
        }
        jj_consume_token(EOS);
      }
      break;
    case FUNCTION:
      jj_consume_token(FUNCTION);
      skipTo(RETURN);
      identifier();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EOS:
        jj_consume_token(EOS);
        break;
      default:
        jj_la1[43] = jj_gen;
        skipTo(BEGIN);
        skipEnd();
        jj_consume_token(END);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case FUNCTION:
          jj_consume_token(FUNCTION);
          break;
        default:
          jj_la1[41] = jj_gen;
          ;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case IDENT:
          identifier();
          break;
        default:
          jj_la1[42] = jj_gen;
          ;
        }
        jj_consume_token(EOS);
      }
      break;
    default:
      jj_la1[44] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void declaration() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case CONSTANT:
    case PROCEDURE:
    case FUNCTION:
    case SIGNAL:
    case SHARED:
    case VARIABLE:
    case TYPE:
    case SUBTYPE:
    case FILE:
    case ALIAS:
    case ATTRIBUTE:
    case FOR:
    case DISCONNECT:
    case GROUP:
      packageDeclaration();
      break;
    case COMPONENT:
      jj_consume_token(COMPONENT);
      skipTo2(END, COMPONENT);
      skipTo(EOS);
      break;
    default:
      jj_la1[45] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void statement() throws ParseException {
        Token ident, module, iend;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IDENT:
      ident = identifier();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COLON:
        jj_consume_token(COLON);
                             beginStatement();
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case ENTITY:
        case IDENT:
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case ENTITY:
            jj_consume_token(ENTITY);
            break;
          default:
            jj_la1[46] = jj_gen;
            ;
          }
          module = identifier();
          iend = skipTo(EOS);
                                        addInstance( ident.beginLine, iend.endLine, module.image, ident.image );
          break;
        case COMPONENT:
          jj_consume_token(COMPONENT);
          module = identifier();
          iend = skipTo(EOS);
                                        addInstance( ident.beginLine, iend.endLine, module.image, ident.image );
          break;
        case FOR:
        case IF:
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case IF:
            jj_consume_token(IF);
            break;
          case FOR:
            jj_consume_token(FOR);
            break;
          default:
            jj_la1[47] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
          skipTo(GENERATE);
          label_13:
          while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case CONSTANT:
            case PROCEDURE:
            case FUNCTION:
            case SIGNAL:
            case SHARED:
            case VARIABLE:
            case TYPE:
            case SUBTYPE:
            case FILE:
            case ALIAS:
            case ATTRIBUTE:
            case COMPONENT:
            case FOR:
            case DISCONNECT:
            case GROUP:
              ;
              break;
            default:
              jj_la1[48] = jj_gen;
              break label_13;
            }
            declaration();
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case BEGIN:
            jj_consume_token(BEGIN);
            label_14:
            while (true) {
              switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
              case PROCESS:
              case BLOCK:
              case ASSERT:
              case WITH:
              case IDENT:
                ;
                break;
              default:
                jj_la1[49] = jj_gen;
                break label_14;
              }
              statement();
            }
            break;
          case PROCESS:
          case BLOCK:
          case ASSERT:
          case WITH:
          case IDENT:
            label_15:
            while (true) {
              statement();
              switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
              case PROCESS:
              case BLOCK:
              case ASSERT:
              case WITH:
              case IDENT:
                ;
                break;
              default:
                jj_la1[50] = jj_gen;
                break label_15;
              }
            }
            break;
          default:
            jj_la1[51] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
          jj_consume_token(END);
          jj_consume_token(GENERATE);
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case IDENT:
            identifier();
            break;
          default:
            jj_la1[52] = jj_gen;
            ;
          }
          jj_consume_token(EOS);
          break;
        case PROCESS:
          jj_consume_token(PROCESS);
          process();
          break;
        default:
          jj_la1[53] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
                         endStatement();
        break;
      default:
        jj_la1[55] = jj_gen;
                         beginStatement();
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case LPAREN:
          jj_consume_token(LPAREN);
          skipParen();
          jj_consume_token(RPAREN);
          break;
        default:
          jj_la1[54] = jj_gen;
          ;
        }
        skipTo(EOS);
                         endStatement();
      }
      break;
    case WITH:
      jj_consume_token(WITH);
                        beginStatement();
      skipTo(EOS);
                                                          endStatement();
      break;
    case PROCESS:
      jj_consume_token(PROCESS);
                           beginStatement();
      process();
                                                         endStatement();
      break;
    case BLOCK:
      jj_consume_token(BLOCK);
                         beginStatement();
      skipTo2(END, BLOCK);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IDENT:
        identifier();
        break;
      default:
        jj_la1[56] = jj_gen;
        ;
      }
      jj_consume_token(EOS);
                                                                      endStatement();
      break;
    case ASSERT:
      jj_consume_token(ASSERT);
                          beginStatement();
      skipTo(EOS);
                                                            endStatement();
      break;
    default:
      jj_la1[57] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void process() throws ParseException {
    skipTo2(END, PROCESS);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IDENT:
      identifier();
      break;
    default:
      jj_la1[58] = jj_gen;
      ;
    }
    jj_consume_token(EOS);
  }

  void unexpectedEof(Token token) throws ParseException {
        ParseException ex = new ParseException("unexpected EOF");
        ex.currentToken = token;
        throw ex;
  }

  Token skipTo(int skip) throws ParseException {
        Token current = token;
        Token token;
        StringBuffer image = new StringBuffer();

        for(;;)
        {
                token = getToken(1);
                if ( token.kind == EOF )
                        unexpectedEof(current);
                if (token.kind == skip)
                {
                        getNextToken();
                        break;
                }

                image.append(token.image);
                getNextToken();
        }
        token.image = image.toString();
        return token;
  }

  void skipTo2(int skip1, int skip2) throws ParseException {
        Token current = token;
        Token token;

        for(;;)
        {
                token = getToken(1);
                if (token.kind == EOF)
                        unexpectedEof(current);
                if (token.kind == skip1)
                {
                        getNextToken();
                        token = getToken(1);
                        if (token.kind == skip2)
                        {
                                getNextToken();
                                break ;
                        }
                }
                else
                        getNextToken();
        }
  }

  void skipParen() throws ParseException {
        Token current = token;
        Token token ;
        int nesting = 1 ;
        for(;;)
        {
                token = getToken(1);
                if (token.kind == EOF)
                        unexpectedEof(current);
                if (token.kind == LPAREN)
                        nesting++;
                if (token.kind == RPAREN)
                {
                        nesting--;
                        if (nesting == 0)
                                break;
                }
                getNextToken();
        }
  }

  void skipEnd() throws ParseException {
        Token current = token;
        Token token;
        int nesting = 1;
        for(;;)
        {
                token = getToken(1);
                if (token.kind == EOF)
                        unexpectedEof(current);
                if (token.kind == BEGIN || token.kind == IF || token.kind == CASE || token.kind == LOOP)
                        nesting++;
                if (token.kind == END)
                {
                        nesting--;
                        if (nesting == 0)
                                break;
                        else
                                getNextToken(); // if or case
                }
                getNextToken();
        }
  }

  Token interfaceElementIndication() throws ParseException {
        Token current = token;
        Token token ;
        StringBuffer image = new StringBuffer();
        int nesting = 1;

        for(;;)
        {
                token = getToken(1);
                if (token.kind == EOF)
                        unexpectedEof(current);
                if (token.kind == LPAREN)
                        nesting++;
                if (token.kind == RPAREN)
                {
                        nesting--;
                        if (nesting == 0)
                                break;
                }
                if (token.kind == EOS)
                {
                        break ;
                }

                image.append(token.image + " ");
                getNextToken();
        }
        token.image = image.toString();
        return token ;
  }

  public VhdlParserCoreTokenManager token_source;
  JavaCharStream jj_input_stream;
  public Token token, jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[59];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static private int[] jj_la1_2;
  static {
      jj_la1_0();
      jj_la1_1();
      jj_la1_2();
   }
   private static void jj_la1_0() {
      jj_la1_0 = new int[] {0xf400,0xf400,0x180000,0x180000,0x0,0x4000,0x0,0x8000,0x0,0x800,0x10000,0x180000,0x180000,0x0,0xff000000,0xff000000,0x1000,0x800,0x0,0x0,0x29000000,0x29000000,0x0,0x0,0x20000,0xef00d000,0x0,0x29000000,0x29000000,0x0,0xff000000,0x200000,0x10000000,0x39000000,0x0,0xc0000000,0x40000,0x0,0x2000000,0x0,0x0,0x4000000,0x0,0x0,0xff000000,0xff000000,0x4000,0x0,0xff000000,0x200000,0x200000,0x600000,0x0,0x204000,0x0,0x0,0x0,0x200000,0x0,};
   }
   private static void jj_la1_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x0,0x4,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x61f,0x61f,0x0,0x0,0x0,0x40000000,0x0,0x0,0x0,0x40000000,0x0,0x3c409,0x600000,0x0,0x0,0x0,0x61f,0x101800,0x0,0x0,0x0,0x0,0x0,0x617,0x0,0x0,0x40000000,0x0,0x0,0x40000000,0x617,0x61f,0x0,0x30,0x61f,0x101800,0x101800,0x101800,0x0,0x38,0x1000000,0x0,0x0,0x101800,0x0,};
   }
   private static void jj_la1_2() {
      jj_la1_2 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x80,0x0,0x80,0x0,0x0,0x0,0x0,0x80,0x0,0x0,0x0,0x0,0x80,0x0,0x0,0x0,0x8,0x0,0x10,0x0,0x80,0x0,0x0,0x8,0x0,0x80,0x0,0x0,0x8,0x0,0x0,0x0,0x0,0x80,0x0,0x0,0x80,0x0,0x0,0x0,0x0,0x0,0x0,0x80,0x80,0x80,0x80,0x80,0x0,0x10,0x80,0x80,0x80,};
   }

  public VhdlParserCore(java.io.InputStream stream) {
     this(stream, null);
  }
  public VhdlParserCore(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new VhdlParserCoreTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 59; i++) jj_la1[i] = -1;
  }

  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 59; i++) jj_la1[i] = -1;
  }

  public VhdlParserCore(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new VhdlParserCoreTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 59; i++) jj_la1[i] = -1;
  }

  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 59; i++) jj_la1[i] = -1;
  }

  public VhdlParserCore(VhdlParserCoreTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 59; i++) jj_la1[i] = -1;
  }

  public void ReInit(VhdlParserCoreTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 59; i++) jj_la1[i] = -1;
  }

  final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.Vector jj_expentries = new java.util.Vector();
  private int[] jj_expentry;
  private int jj_kind = -1;

  public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[74];
    for (int i = 0; i < 74; i++) {
      la1tokens[i] = false;
    }
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 59; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
          if ((jj_la1_2[i] & (1<<j)) != 0) {
            la1tokens[64+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 74; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.addElement(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.elementAt(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  final public void enable_tracing() {
  }

  final public void disable_tracing() {
  }

}