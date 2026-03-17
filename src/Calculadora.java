import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Calculadora {
   private final String charWhitelist = "ABCDEFGHIJKLMNOPQRSTUVWXYZv¬~^v=>() ";
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
         System.out.println("Fórmula \"" + input + "\" contém sintaxe inválida, tente novamente");
         return comecar();
      }

      // TODO Cálculo tautológico •> "Etapa III: Provador de Tautologia"
      tautologico(input);

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
      input = input.replaceAll("\\s","").replaceAll("¬", "~"); //padronização

      List<Character> vals = new ArrayList<Character>();

      for (int i = 0; i < input.length(); i++) {
         char c = input.charAt(i);
         if (Character.isUpperCase(c) && !vals.contains(c)) {
            vals.add(c); //guarda as letras usadas na fórmula
         }
      }

      return false;
   } // "Provador Tautológico"
}
