package br.com.milhao.game;

import br.com.milhao.model.Jogador;
import br.com.milhao.utils.GerenciadorDeArquivos;

import java.util.Collections;
import java.util.List;

public class Ranking {
    private static final String ARQUIVO_RANKING = "ranking.dat";
    private final GerenciadorDeArquivos gerenciador;
    private List<Jogador> jogadores;

    public Ranking() {
        this.gerenciador = new GerenciadorDeArquivos();
        this.jogadores = gerenciador.carregarRanking(ARQUIVO_RANKING);
    }

    public void adicionarJogador(Jogador jogador) {
        if (jogador.getPontuacao() > 0) {
            jogadores.add(jogador);
            Collections.sort(jogadores); // Ordena usando o compareTo da classe Jogador
            if (jogadores.size() > 10) { // Mantém apenas o top 10
                jogadores = jogadores.subList(0, 10);
            }
            salvar();
        }
    }
    
    public void exibir() {
        System.out.println("\n🏆 --- RANKING TOP 10 --- 🏆");
        if (jogadores.isEmpty()) {
            System.out.println("Nenhum jogador no ranking ainda.");
        } else {
            int pos = 1;
            for (Jogador j : jogadores) {
                System.out.printf("%2d. %-20s - R$ %,d\n", pos++, j.getNome(), j.getPontuacao());
            }
        }
        System.out.println("-----------------------------\n");
    }

    public void salvar() {
        gerenciador.salvarRanking(ARQUIVO_RANKING, jogadores);
    }
}