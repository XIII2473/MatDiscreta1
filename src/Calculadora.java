import java.util.Scanner;

public class Calculadora {
   private final String charWhitelist = "ABCDEFGHIJKLMNOPQRSTUVWXYZv¬~^v<>()";
   private final Scanner scanner = new Scanner(System.in);

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
         System.out.println("Símbolo inválido, tente novamente");
         return comecar();
      }

      // TODO Cálculo tautológico •> "Etapa III: Provador de Tautologia"
      return "Calculadora - Fim";
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

         if (esperaOperando) {

            if (Character.isUpperCase(c)) {
               esperaOperando = false;
            } else if (c == '(') {
               parenteses++;
            } else if (c == '¬' || c == '~') {
            } else {
               return false;
            }

         } else {

            if (c == '^' || c == 'v' ||  c == '>' || c == '~' || c == '¬') {
               esperaOperando = true;
            } else if (c == '<') {
            } else if (c == ')') {
               parenteses--;
               if (parenteses < 0) return false;
            } else {
               return false;
            }
         }
      }

      return !esperaOperando && parenteses == 0;
   } // Análise Sintática
}


