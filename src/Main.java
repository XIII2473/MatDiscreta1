void main() {
   System.out.println("\nCalculadora lógica\n\n" +
           "São permitidos Letras Maiúsculas, Parênteses ( ) e os seguintes símbolos lógicos: \n" +
           "¬ ou ~ para Negação (Símbolo de Negação ou Acento Til)\n" +
           "^ para Conjunção (Acento Circumflexo)\n" +
           "v para Disjunção (Letra 'v' Minúscula)\n" +
           "> para Condicional (Sinal 'maior que')\n" +
           "<> para Bi-Condicional (Sinais 'menor que' e 'maior que' juntos)\n");
   Calculadora c = new Calculadora();
   System.out.println("Digite sua Fórmula: ");
   System.out.println(c.comecar());
}