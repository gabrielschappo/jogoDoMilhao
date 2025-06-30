package br.com.milhao.ajuda;

import br.com.milhao.model.Pergunta;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AjudaUniversitarios implements Ajuda {
    @Override
    public String getNome() {
        return "Universitarios";
    }

    @Override
    public List<Integer> usar(Pergunta pergunta) {
        System.out.println("\n🎓 AJUDA - UNIVERSITÁRIOS: Consultando os universitários...");
        Random rand = new Random();
        
        boolean acerta = rand.nextInt(100) < 75; 
        
        int respostaSugerida;
        if (acerta) {
            respostaSugerida = pergunta.getRespostaCorreta();
        } else {
            do {
                respostaSugerida = rand.nextInt(pergunta.getAlternativas().size());
            } while (respostaSugerida == pergunta.getRespostaCorreta());
        }
        
        char opcaoChar = (char) ('A' + respostaSugerida);
        System.out.println("Os universitários sugerem que a resposta correta é a letra: " + opcaoChar);

        return Collections.emptyList();
    }
}