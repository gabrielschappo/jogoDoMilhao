package br.com.milhao.model;

import java.util.Collections;
import java.util.List;

public class Pergunta {
    private final NivelDificuldade nivel;
    private final String enunciado;
    private final List<String> alternativas;
    private final int respostaCorreta;

    public Pergunta(NivelDificuldade nivel, String enunciado, List<String> alternativas, int respostaCorreta) {
        this.nivel = nivel;
        this.enunciado = enunciado;
        this.alternativas = alternativas;
        this.respostaCorreta = respostaCorreta;
    }

   
    public NivelDificuldade getNivel() { return nivel; }
    public String getEnunciado() { return enunciado; }
    public List<String> getAlternativas() { return alternativas; }
    public int getRespostaCorreta() { return respostaCorreta; }

    public void exibirPergunta() {
        exibirPergunta(Collections.emptyList());
    }

    public void exibirPergunta(List<Integer> indicesOcultos) {
        System.out.println("----------------------------------------");
        System.out.println("Nível: " + nivel);
        System.out.println("Pergunta: " + enunciado);
        System.out.println("----------------------------------------");
        char opcao = 'A';
        for (int i = 0; i < alternativas.size(); i++) {
            if (!indicesOcultos.contains(i)) { // Só exibe se o índice NÃO ESTIVER na lista de ocultos
                System.out.println(opcao + ") " + alternativas.get(i));
            }
            opcao++;
        }
        System.out.println("----------------------------------------");
    }
}