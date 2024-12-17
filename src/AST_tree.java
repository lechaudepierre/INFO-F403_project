import java.util.ArrayList;
import java.util.List;

public class AST_tree {

    /**
     * @param parseTree the node of the tree that we want to cast to AST
     * @param context the context of that node, i.e. what it needs from the parent
     * @return the AST
     */
    public ParseTree castParseTreeToAST(ParseTree parseTree, ParseTree context) {

        Symbol currentSymbol = parseTree.getLabel();

        List<ParseTree> children = new ArrayList<>();

        switch(currentSymbol.getType()) {
            case Program -> {
                children.add(castParseTreeToAST(parseTree.getChildren().get(3), null));
                parseTree.setChildren(children);
            }
            case Code -> {
                children.add(castParseTreeToAST(parseTree.getChildren().get(0), null));
                if (parseTree.getChildren().get(2).getLabel().getType() != LexicalUnit.EPSILON) {
                    children.add(castParseTreeToAST(parseTree.getChildren().get(2), null));
                }
                parseTree.setChildren(children);
            }
            case Instruction -> {
                parseTree = castParseTreeToAST(parseTree.getChildren().get(0), null);
            }
            case Assign -> {
                children.add(parseTree.getChildren().get(0)); // VARNAME
                children.add(castParseTreeToAST(parseTree.getChildren().get(2), null)); // <ExprArith>
                parseTree = parseTree.getChildren().get(1); // "="
                parseTree.setChildren(children);
            }
            case ExprArith, Prod -> {
                if (parseTree.getChildren().get(1).getLabel().getType() == LexicalUnit.EPSILON) {
                    parseTree = castParseTreeToAST(parseTree.getChildren().get(0), null);
                } else {
                    context = castParseTreeToAST(parseTree.getChildren().get(0), null);
                    parseTree = castParseTreeToAST(parseTree.getChildren().get(1), context);
                }
            }
            case ExprArith2, Prod2 -> {
                if (parseTree.getChildren().get(2).getLabel().getType() == LexicalUnit.EPSILON) {
                    children.add(context);
                    children.add(castParseTreeToAST(parseTree.getChildren().get(1), null));
                    parseTree = new ParseTree(parseTree.getChildren().get(0).getLabel(), children);
                } else {
                    children.add(context);
                    children.add(castParseTreeToAST(parseTree.getChildren().get(1), null));
                    context = new ParseTree(parseTree.getChildren().get(0).getLabel(), children);
                    parseTree = castParseTreeToAST(parseTree.getChildren().get(2), context);
                }
            }
            case Atom -> {
                if (parseTree.getChildren().size() == 1) {
                    parseTree = parseTree.getChildren().get(0);
                } else if (parseTree.getChildren().size() == 2) {
                    children.add(castParseTreeToAST(parseTree.getChildren().get(1), null));
                    parseTree.setLabel(parseTree.getChildren().get(0).getLabel());
                    parseTree.setChildren(children);
                } else if (parseTree.getChildren().size() == 3) {
                    parseTree = castParseTreeToAST(parseTree.getChildren().get(1), null);
                }
            }
            case If -> {
                children.add(castParseTreeToAST(parseTree.getChildren().get(2), null));
                children.add(castParseTreeToAST(parseTree.getChildren().get(5), null));
                if (parseTree.getChildren().get(6).getChildren().get(0).getLabel().getType() != LexicalUnit.END) {
                    children.add(castParseTreeToAST(parseTree.getChildren().get(6), null));
                }
                parseTree.setChildren(children);
            }
            case EndIf -> {
                parseTree = castParseTreeToAST(parseTree.getChildren().get(1), null);
            }
            case Cond -> {
                if (parseTree.getChildren().get(1).getLabel().getType() == LexicalUnit.EPSILON) {
                    parseTree = castParseTreeToAST(parseTree.getChildren().get(0), null);
                }
                else {
                    children.add(castParseTreeToAST(parseTree.getChildren().get(0), null)); // <CondAtom>
                    children.add(castParseTreeToAST(parseTree.getChildren().get(1), null)); // <CondImpl>
                    
                }
                parseTree.setChildren(children);
            }
            case CondImpl -> {
                
                children.add(castParseTreeToAST(parseTree.getChildren().get(1), null)); // <CondAtom>
                children.add(castParseTreeToAST(parseTree.getChildren().get(2), null)); // <CondImpl>
                parseTree = parseTree.getChildren().get(0);
                parseTree.setChildren(children);
            }


            case CondAtom -> {
                if (parseTree.getChildren().get(0).getLabel().getType() == LexicalUnit.PIPE &&
                parseTree.getChildren().get(2).getLabel().getType() == LexicalUnit.PIPE) {
                    // |<Cond>|
                    children.add(castParseTreeToAST(parseTree.getChildren().get(1), null)); // <Cond>
                    
                } else {
                    // <ExprArith> <Comp> <ExprArith>
                    children.add(castParseTreeToAST(parseTree.getChildren().get(0), null)); // <ExprArith>
                    children.add(castParseTreeToAST(parseTree.getChildren().get(1), null)); // <Comp>
                    children.add(castParseTreeToAST(parseTree.getChildren().get(2), null)); // <ExprArith>
                }
                parseTree.setChildren(children);
             }

            case Comp -> {
                parseTree = parseTree.getChildren().get(0);
            }
            case While -> {
                children.add(castParseTreeToAST(parseTree.getChildren().get(2), null));
                children.add(castParseTreeToAST(parseTree.getChildren().get(5), null));
                parseTree.setChildren(children);
            }
            case Input, Output -> {
                children.add(parseTree.getChildren().get(2));
                parseTree.setChildren(children);
            }
        }

        return parseTree;
    }
}