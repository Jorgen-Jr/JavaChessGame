package consolegui;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import logica.*;
import principal.*;

public class ConsoleGui {

	private JogoXadrez jogoxadrez;

	public ConsoleGui() {

		// Criar um novo jogo de xadrez
		this.jogoxadrez = new JogoXadrez();
	}

	public static void main(String[] args) {
		new ConsoleGui().comecar();
	}

	/**
	 * Contem o loop principal da aplicação. a aplicação deve imprimir o estado
	 * atual do jogo e perguntar qual o proximo movimento do usuário, e se ela digitar
	 * "sair", a aplicação fecha, do contrário a aplicação deve entender a entrada como
	 * um movimento.
	 */
	public void comecar() {

		// se preparar para ler o movimento
		//
		String in = "";
		BufferedReader lerin= new BufferedReader(new InputStreamReader(System.in));

		while (true) {

			// print o estado atual do jogo e pergunta o movimento
			this.printEstado_Jogo_Atual();
			System.out.println("Qual seu movimento? (ex: 'e2-e4'): ");
			try {
				
				// ler entrada do usuario
				in = lerin.readLine();

				// sair se ele digitar 'sair'
				if (in.equalsIgnoreCase("sair")){
					return;
				}else{
					this.manipularMovimento(in);
					this.jogoxadrez.mudarestadoJogo();
				}
			} catch (Exception e) {
				System.out.println(e.getClass() + ": " + e.getMessage());
			}
		}

	}

	/**
	 * Mover peça para o local especificado
	 * @param entrada, movimento valido é: (ex: "e7-e5")
	 */
	private void manipularMovimento(String input) {
		String sColunaOrigem = input.substring(0, 1);
		String sLinhaOrigem = input.substring(1, 2);
		String sColunaAlvo = input.substring(3, 4);
		String sLinhaAlvo = input.substring(4, 5);
		Movimento mover = null;
		int colunaOrigem = 0;
		int linhaOrigem = 0;
		int colunaAlvo = 0;
		int linhaAlvo = 0;

		colunaOrigem = converterStringParaColuna(sColunaOrigem);
		linhaOrigem = converterStringParaLinha(sLinhaOrigem);
		colunaAlvo = converterStringParaColuna(sColunaAlvo);
		linhaAlvo = converterStringParaLinha(sLinhaAlvo);

		jogoxadrez.moverPeca(mover);
	}

	/**
	 * converter coluna para string.
	 * 
	 * @param sColuna converter para uma coluna válida ex: 'a')
	 * @return inteiro da coluna
	 */
	private int converterStringParaColuna(String sColuna) {
		if (sColuna.equalsIgnoreCase("a")) {
			return Peca.coluna_A;
		} else if (sColuna.equalsIgnoreCase("b")) {
			return Peca.coluna_B;
		} else if (sColuna.equalsIgnoreCase("c")) {
			return Peca.coluna_C;
		} else if (sColuna.equalsIgnoreCase("d")) {
			return Peca.coluna_D;
		} else if (sColuna.equalsIgnoreCase("e")) {
			return Peca.coluna_E;
		} else if (sColuna.equalsIgnoreCase("f")) {
			return Peca.coluna_F;
		} else if (sColuna.equalsIgnoreCase("g")) {
			return Peca.coluna_G;
		} else if (sColuna.equalsIgnoreCase("h")) {
			return Peca.coluna_H;
		} else
			throw new IllegalArgumentException("Coluna Inválida: " + sColuna);
	}

	/**
	 * Converter String para linha
	 * @param sLinha uma string linha valida (ex: '1')
	 * @return internal integer representation of the row
	 */
	private int converterStringParaLinha(String sLinha) {
		if (sLinha.equalsIgnoreCase("1")) {
			return Peca.linha_1;
		} else if (sLinha.equalsIgnoreCase("2")) {
			return Peca.linha_2;
		} else if (sLinha.equalsIgnoreCase("3")) {
			return Peca.linha_3;
		} else if (sLinha.equalsIgnoreCase("4")) {
			return Peca.linha_4;
		} else if (sLinha.equalsIgnoreCase("5")) {
			return Peca.linha_5;
		} else if (sLinha.equalsIgnoreCase("6")) {
			return Peca.linha_6;
		} else if (sLinha.equalsIgnoreCase("7")) {
			return Peca.linha_7;
		} else if (sLinha.equalsIgnoreCase("8")) {
			return Peca.linha_8;
		} else
			throw new IllegalArgumentException("Linha Inválida: " + sLinha);
	}

	/**
	 * Imprimir a mesa
	 */
	private void printEstado_Jogo_Atual() {

		System.out.println("  a  b  c  d  e  f  g  h  ");
		for (int linha = Peca.linha_8; linha >= Peca.linha_1; linha--) {

			System.out.println(" +--+--+--+--+--+--+--+--+");
			String sLinha = (linha + 1) + "|";
			for (int coluna = Peca.coluna_A; coluna <= Peca.coluna_H; coluna++) {
				Peca peca = this.jogoxadrez.getPecaNaoCapturadaEm(linha, coluna);
				String sPeca = getNomePeca(peca);
				sLinha += sPeca + "|";
			}
			System.out.println(sLinha + (linha + 1));
		}
		System.out.println(" +--+--+--+--+--+--+--+--+");
		System.out.println("  a  b  c  d  e  f  g  h  ");

		String tranformarCor =
			(jogoxadrez.getEstadoJogo()== jogoxadrez.estado_Branca ? "preta" : "branca");
		System.out.println("Jogada de: " + tranformarCor);

	}

	/**
	 * Captura a peça para impreção
	 * 
	 * A primeira letra representa qual a cor da peça "P" para Preta e "B" Para branca (? para desconhecido)
	 * 
	 * A Segunda letra representa o tipo de peça B para Bispo, R para rei e ra para rainha
	 * 
	 * O espaço vazio de dois caracteres representa que não há peça no local.
	 * 
	 * @param peca como a Peça do jogo
	 * @return string representation of the piece or a two letter empty string
	 *         if the specified piece is null
	 */
	private String getNomePeca(Peca peca) {
		if (peca == null)
			return "  ";

		String sCor = "";
		switch (peca.getCor()) {
			case Peca.cor_Preta:
				sCor = "P";
				break;
			case Peca.cor_Branca:
				sCor = "B";
				break;
			default:
				sCor = "?";
				break;
		}

		String sTipo = "";
		switch (peca.getTipo()) {
			case Peca.tipo_Bispo:
				sTipo = "B";
				break;
			case Peca.tipo_Rei:
				sTipo = "R";
				break;
			case Peca.tipo_Cavalo:
				sTipo = "C";
				break;
			case Peca.tipo_Torre:
				sTipo = "T";
				break;
			case Peca.tipo_Rainha:
				sTipo = "RA";
				break;
			case Peca.tipo_Peao:
				sTipo = "P";
				break;
			default:
				sTipo = "?";
				break;
		}

		return sCor + sTipo;
	}

}