package br.com.milhao.main;

import br.com.milhao.game.Jogo;
import br.com.milhao.game.Ranking;
import br.com.milhao.model.Pergunta;
import br.com.milhao.utils.GerenciadorDeArquivos;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GerenciadorDeArquivos gerenciador = new GerenciadorDeArquivos();
        Ranking ranking = new Ranking();

        List<Pergunta> perguntas = gerenciador.carregarPerguntas("perguntas.csv");
        if (perguntas.isEmpty()) {
            System.out.println("Não foi possível carregar as perguntas. O jogo não pode começar.");
            return;
        }

        while (true) {
            System.out.println("===== JOGO DO MILHÃO =====");
            System.out.println("1. Jogar");
            System.out.println("2. Ver Ranking");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    System.out.print("Digite seu nome, jogador: ");
                    String nome = scanner.nextLine();
                    Jogo jogo = new Jogo(nome, new java.util.ArrayList<>(perguntas), scanner); // Passa uma cópia
                    jogo.iniciar();
                    ranking.adicionarJogador(jogo.getJogador());
                    break;
                case "2":
                    ranking.exibir();
                    break;
                case "3":
                    System.out.println("Obrigado por jogar! Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}