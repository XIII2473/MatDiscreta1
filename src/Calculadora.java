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
         System.out.println("Fórmula \"" + input + "\" contém caracteres inválidos, tente novamente");
         return comecar();
      }

      // TODO Validez de sintaxe •> "Etapa II: Analizador Sintático"
      // TODO Cálculo tautológico •> "Etapa III: Provador de Tautologia"

      return "Calculadora - Fim";
   }

   private boolean checkChars(String input) {
      int i = 0;
      for (int j = 0; j < charWhitelist.length(); ) {
//         System.out.println("⛧for loop (i⋅" + i + " ⋅ j⋅" + j + ") ⋅ char \"" + input.charAt(i) + "\" ⋅ charWhitelist \"" + charWhitelist.charAt(j) + "\"");
         if (input.charAt(i) == charWhitelist.charAt(j)) {
//            System.out.println("★SUCCESS | char \"" + input.charAt(i) + "\" i = " + i + " | charWhitelist \"" + charWhitelist.charAt(j) + "\" j = " + j);
            j = 0;
            i++;
            if (i == input.length()) {
               break;
            }
//            System.out.println(" flush");
            continue;
         }
         if (j == charWhitelist.length() - 1) {
//            System.out.println("ERROR | char \"" + input.charAt(i) + "\" i = " + i);
            return false;
         }
         j++;
      }
      return true;
   } // "Análise Léxica"
}
