import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class Main {
    private static HashMap<String, Integer> symbolTable = new HashMap<>();

    
    /** 
     * @param symbol
     */
    public static void addSymbolToTable(Symbol symbol){
        if (symbol.getType() == LexicalUnit.VARNAME && !symbolTable.containsKey(symbol.getValue())) {
            String varName = (String) symbol.getValue();
            symbolTable.put(varName, symbol.getLine());
        }
    }

    /**
     * Print the symbol table
     */
    public static void printSymbolTable() {
        System.out.println("\nVariables");
        for (String varName : symbolTable.keySet()) {
            System.out.println(varName + "   " + symbolTable.get(varName));
        }
    }
    /**
     * Run the main
     * @param args
     */
    public static void main(String[] args){
        if (args.length != 1){
            System.out.println("Usage: java Main <input_file>");
            System.exit(1);
        }
        File input = new File(args[0]);
        if (!input.exists()){
            System.out.println("File not found: "+args[0]);
            System.exit(1);
        }
        String inputPath = args[0];
        TreeMap<String, Symbol> variableTable = new TreeMap<>();

        try{
            Parser parser = new Parser(new LexicalAnalyzer(new FileReader(inputPath)));
            ParseTree parseTree = parser.startParsing();

            /*Symbol symbol = lexicalAnalyzer.nextSymbol();
            while(symbol.getType() != LexicalUnit.EOS){
                System.out.println(symbol.toString());
                addSymbolToTable(symbol);
                symbol = lexicalAnalyzer.nextSymbol();
            }*/
            //printSymbolTable(); //print the symbol table
            // Retrieve the variable table
            variableTable = parser.getVariableTable();
            
        }
        catch(IOException e){
            System.out.println("Error while opening file: "+args[0]);
            System.exit(1);
        }

        if (!variableTable.isEmpty()) {
            System.out.println("\nVariables");

            for (Map.Entry<String, Symbol> variable : variableTable.entrySet()) {
                System.out.println(variable.getKey() + "\t" + variable.getValue().getLine());
            }
        }
    
    }
}