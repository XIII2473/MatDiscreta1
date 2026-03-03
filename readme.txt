• Calculadora lógica em CLI

• Apresentação em 17/03

//
Etapa I: Análise Léxica
    1. Determinar símbolos usados para:
        • Negação: ¬, ~ (símbolo de negação, acento til/tilde)
        • Conjunção: ^ (acento circumflexo)
        • Disjunção: v (letra v)
        • Condicional: > (maior que)
        • Bi-condicional: <> (menor que e maior que)
    2. Verificar se os símbolos do input são válidos

Etapa II: Analisador sintático
    1. Verificar se a estrutura do input é válida (FBF - Fórmula Bem Formulada)
        Ex. de FBF: (A > B) ^ (B > A)
        Ex. de não FBF: A)) ^^ > BC

Etapa III: Provador de Tautologia
    1. Verificar se a fórmula proposicional FBF é Verdadeira ou Falsa

