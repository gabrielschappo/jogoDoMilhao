package br.com.milhao.ajuda;

import br.com.milhao.model.Pergunta;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AjudaCartas implements Ajuda {
    @Override
    public String getNome() {
        return "Cartas";
    }

    @Override
    public List<Integer> usar(Pergunta pergunta) {
        System.out.println("\n AJUDA - CARTAS: Eliminando duas alternativas incorretas...");

        List<Integer> indicesIncorretos = new ArrayList<>();
        for (int i = 0; i < pergunta.getAlternativas().size(); i++) {
            if (i != pergunta.getRespostaCorreta()) {
                indicesIncorretos.add(i);
            }
        }
        
        Collections.shuffle(indicesIncorretos);
        
        return indicesIncorretos.subList(0, 2);
    }
}