import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Calculadora {
   private final String charWhitelist = "ABCDEFGHIJKLMNOPQRSTUVWXYZv¬~^v=>() ";
   private final Scanner scanner = new Scanner(System.in);
   String formula = "";

   public String comecar() {
      String input = scanner.nextLine();

      // Validez de caracteres •> "Etapa I: Análise Léxica"
      if (input == null || input.isBlank()) {
         return comecar();
      }
      if (!checkChars(input)) {
         System.out.println();
         System.out.println("Fórmula \"" + input + "\" contém caracteres inválidos, tente novamente");
         return comecar();
      }
      // "Etapa II: Análise Sintática"
      if (!checkSintaxe(input)) {
         System.out.println("Fórmula \"" + input + "\" contém sintaxe inválida, tente novamente");
         return comecar();
      }

      formula = input;

      tautologico(input);

      return "# Calculadora - Fim";
   }

   private boolean checkChars(String input) {
      int i = 0;
      for (int j = 0; j < charWhitelist.length(); ) {
         if (input.charAt(i) == charWhitelist.charAt(j)) {
            j = 0;
            i++;
            if (i == input.length()) {
               break;
            }
            continue;
         }
         if (j == charWhitelist.length() - 1) {
            return false;
         }
         j++;
      }
      return true;
   } // "Análise Léxica"

   private boolean checkSintaxe(String input) {

      boolean esperaOperando = true;
      int parenteses = 0;

      for (int i = 0; i < input.length(); i++) {
         char c = input.charAt(i);

         if (c == ' ') {
            continue;
         }
         if (esperaOperando) {
            if (Character.isUpperCase(c)) {
               esperaOperando = false;
            } else if (c == '(') {
               parenteses++;
            } else if (c == '¬' || c == '~') { //vai pro próx carac do input se for
            } else {
               return false;
            }
         } else {
            if (c == '^' || c == 'v' || c == '=' || c == '>' || c == '~' || c == '¬') {
               esperaOperando = true;
            } else if (c == ')') {
               parenteses--;
               if (parenteses < 0) return false;
            } else {
               return false;
            }
         }
      }

      return !esperaOperando && parenteses == 0;
   } // "Análise Sintática"

   private boolean tautologico(String input) {
      input = input.replaceAll("\\s", "").replaceAll("¬", "~"); //padronização

      List<Character> operadores = new ArrayList<Character>();

      for (int i = 0; i < input.length(); i++) {
         char c = input.charAt(i);
         if (Character.isUpperCase(c) && !operadores.contains(c)) {
            operadores.add(c); //guarda as letras usadas na fórmula
         }
      }

      int nOps = operadores.size();
      int nLinhas = (int) Math.pow(2, nOps); // O numero de linhas é o número de Operandos ^ 2
      String postfix = toPostfix(input);
      boolean tautologico = true;

      System.out.println("# Tabela Verdade");
      for (char v : operadores) {
         System.out.print(v + " | "); // mostrar Operando1 | Operando2 |... no topo da tabela
      }
      System.out.println(formula); // formula inserida após os operandos

      // lógica para linhas
      for (int i = 0; i < nLinhas; i++) {

         boolean[] currentOps = new boolean[nOps];

         // lógica binária pra gerar combinações de V e F
         int bin = nLinhas - i - 1;
         for (int j = 0; j < nOps; j++) {
            currentOps[j] = ((bin >> (nOps - 1 - j)) & 1) == 1; // converte valor inteiro de bin para binário
            System.out.print((currentOps[j] ? "V" : "F") + " | ");
         }

         // Avalia a fórmula para a linha atual
         boolean result = evalPostfix(postfix, currentOps, operadores);
         if (!result) {
            tautologico = false;
         }

         System.out.println((result ? "V" : "F") + " | ");
      }

      System.out.println("★★★ A fórmula " + (tautologico ? "É UMA TAUTOLOGIA." : "NÃO é uma tautologia."));

      return false;
   } // "Provador Tautológico"

   private String toPostfix(String infix) {
      StringBuilder postfix = new StringBuilder();
      java.util.Stack<Character> stackParenteses = new java.util.Stack<>();

      for (char c : infix.toCharArray()) {
         if (Character.isUpperCase(c)) {
            postfix.append(c);
         } else if (c == '(') {
            stackParenteses.push(c);
         } else if (c == ')') {
            while (!stackParenteses.isEmpty() && stackParenteses.peek() != '(') {
               postfix.append(stackParenteses.pop()); // coloca ) até não ter mais
            }
            if (!stackParenteses.isEmpty()) stackParenteses.pop();
         } else {
            // se operador
            while (!stackParenteses.isEmpty() && precedencia(stackParenteses.peek()) >= precedencia(c)) {
               if (stackParenteses.peek() == '(') break;
               postfix.append(stackParenteses.pop());
            }
            stackParenteses.push(c);
         }
      }
      while (!stackParenteses.isEmpty()) postfix.append(stackParenteses.pop());
      return postfix.toString();
   }

   private int precedencia(char c) {
      if (c == '~') return 4;
      if (c == '^') return 3;
      if (c == 'v') return 2;
      if (c == '>') return 1;
      if (c == '=') return 0;
      return -1;
   }

   private boolean evalPostfix(String postfix, boolean[] valArray, List<Character> vars) {
      java.util.Stack<Boolean> stack = new java.util.Stack<>();

      for (char c : postfix.toCharArray()) {
         if (Character.isUpperCase(c)) {
            stack.push(valArray[vars.indexOf(c)]);
         } else if (c == '~') {
            stack.push(!stack.pop());
         } else {
            boolean right = stack.pop();
            boolean left = stack.pop();

            if (c == '^') stack.push(left && right);
            else if (c == 'v') stack.push(left || right);
            else if (c == '>') stack.push(!left || right);
            else if (c == '=') stack.push(left == right);
         }
      }
      return stack.pop();
   }
}
