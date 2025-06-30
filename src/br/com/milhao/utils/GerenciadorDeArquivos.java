package br.com.milhao.utils;

import br.com.milhao.model.Jogador;
import br.com.milhao.model.NivelDificuldade;
import br.com.milhao.model.Pergunta;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GerenciadorDeArquivos {

    public List<Pergunta> carregarPerguntas(String caminho) {
        List<Pergunta> perguntas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 7) {
                    NivelDificuldade nivel = NivelDificuldade.valueOf(dados[0].toUpperCase());
                    String enunciado = dados[1];
                    List<String> alternativas = Arrays.asList(dados[2], dados[3], dados[4], dados[5]);
                    int respostaCorreta = Integer.parseInt(dados[6]);
                    perguntas.add(new Pergunta(nivel, enunciado, alternativas, respostaCorreta));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo de perguntas: " + e.getMessage());
        }
        return perguntas;
    }

    @SuppressWarnings("unchecked")
    public List<Jogador> carregarRanking(String caminho) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            return (List<Jogador>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>(); 
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar o ranking: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    public void salvarRanking(String caminho, List<Jogador> jogadores) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            Collections.sort(jogadores); 
            oos.writeObject(jogadores);
        } catch (IOException e) {
            System.err.println("Erro ao salvar o ranking: " + e.getMessage());
        }
    }
}