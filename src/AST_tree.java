import java.util.ArrayList;
import java.util.List;


public class AST_tree {
    public ParseTree castParseTreeToAST(ParseTree parseTree, ParseTree context) {
        Symbol currentSymbol = parseTree.getLabel();
        List<ParseTree> children = new ArrayList<>();

        switch (currentSymbol.getType()) {
            case Program -> {
                children.add(castParseTreeToAST(parseTree.getChildren().get(3), null));
                parseTree.setChildren(children);
            }
            case Code -> {
                if (!parseTree.getChildren().isEmpty()) {
                    children.add(castParseTreeToAST(parseTree.getChildren().get(0), null));
                    if (parseTree.getChildren().size() > 1) {
                        children.add(castParseTreeToAST(parseTree.getChildren().get(2), null));
                    }
                }
                parseTree.setChildren(children);
            }
            case Instruction -> {
                parseTree = castParseTreeToAST(parseTree.getChildren().get(0), null);
            }
            case Assign -> {
                children.add(parseTree.getChildren().get(0));
                children.add(castParseTreeToAST(parseTree.getChildren().get(2), null));
                parseTree = parseTree.getChildren().get(1);
                parseTree.setChildren(children);
            }
            case ExprArith -> {
                if (parseTree.getChildren().size() == 1) {
                    parseTree = castParseTreeToAST(parseTree.getChildren().get(0), null);
                } else {
                    ParseTree left = castParseTreeToAST(parseTree.getChildren().get(0), null);
                    ParseTree right = castParseTreeToAST(parseTree.getChildren().get(2), null);
                    children.add(left);
                    children.add(right);
                    parseTree = parseTree.getChildren().get(1);
                    parseTree.setChildren(children);
                }
            }
            case Prod -> {
                if (parseTree.getChildren().size() == 1) {
                    parseTree = castParseTreeToAST(parseTree.getChildren().get(0), null);
                } else {
                    ParseTree left = castParseTreeToAST(parseTree.getChildren().get(0), null);
                    ParseTree right = castParseTreeToAST(parseTree.getChildren().get(2), null);
                    children.add(left);
                    children.add(right);
                    parseTree = parseTree.getChildren().get(1);
                    parseTree.setChildren(children);
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
                children.add(castParseTreeToAST(parseTree.getChildren().get(4), null));
                if (parseTree.getChildren().size() > 6 && parseTree.getChildren().get(5).getLabel().getType() == LexicalUnit.ELSE) {
                    children.add(castParseTreeToAST(parseTree.getChildren().get(5), null));
                    children.add(castParseTreeToAST(parseTree.getChildren().get(6), null));
                }
                parseTree.setChildren(children);
            }
            case While -> {
                children.add(castParseTreeToAST(parseTree.getChildren().get(2), null));
                children.add(castParseTreeToAST(parseTree.getChildren().get(4), null));
                parseTree.setChildren(children);
            }
            case Output, Input -> {
                children.add(parseTree.getChildren().get(2));
                parseTree.setChildren(children);
            }
            case Cond -> {
                ParseTree left = castParseTreeToAST(parseTree.getChildren().get(0), null);
                if (parseTree.getChildren().size() > 1) {
                    ParseTree right = castParseTreeToAST(parseTree.getChildren().get(2), null);
                    children.add(left);
                    children.add(right);
                    parseTree = parseTree.getChildren().get(1);
                    parseTree.setChildren(children);
                } else {
                    parseTree = left;
                }
            }
            case Comp -> {
                ParseTree left = castParseTreeToAST(parseTree.getChildren().get(0), null);
                ParseTree right = castParseTreeToAST(parseTree.getChildren().get(2), null);
                children.add(left);
                children.add(right);
                parseTree = parseTree.getChildren().get(1);
                parseTree.setChildren(children);
            }
            default -> {
                for (ParseTree child : parseTree.getChildren()) {
                    children.add(castParseTreeToAST(child, null));
                }
                parseTree.setChildren(children);
            }
        }
        return parseTree;
    }
}
