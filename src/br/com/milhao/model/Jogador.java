package br.com.milhao.model;

import java.io.Serializable;

public class Jogador implements Serializable, Comparable<Jogador> {
    private static final long serialVersionUID = 1L;
    private final String nome;
    private int pontuacao;

    public Jogador(String nome) {
        this.nome = nome;
        this.pontuacao = 0;
    }

    public String getNome() {
        return nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    @Override
    public int compareTo(Jogador outro) {
        // Ordena da maior pontuação para a menor
        return Integer.compare(outro.getPontuacao(), this.pontuacao);
    }

    @Override
    public String toString() {
        return "Jogador: " + nome + " - Pontuação: R$ " + pontuacao;
    }
}