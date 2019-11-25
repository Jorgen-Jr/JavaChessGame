package logica;

import java.util.ArrayList;
import java.util.List;

public class JogoXadrez {
	//Variavel para manter controle sobre o estado do jogo /de quem é a vez e etc.
	public int estadoJogo = estado_Branca;
	public static final int estado_Branca = 0;
	public static final int estado_Preta = 1;
	public static final int estado_Jogo_Fim = 2;
	public static final int estado_Jogo_Preta_Vence = 3;
	public static final int estado_Jogo_Branca_Vence = 4;
	public List<Peca> pecas = new ArrayList<Peca>();
	
/*TODO Implementar lista de peças capturadas
	private List<Peca> PecasCapturadas = new ArrayList<Peca>();
*/	
	private ValidarMovimentos validarMovimento;
	

//Inicia uma instância do Jogo
	public JogoXadrez(){
		
			this.validarMovimento = new ValidarMovimentos(this);
		/*Criar e colocar as peças no lugar
		 *
		 * Torre, Cavalo, Bispo, Rainha, Rei, Bisco, Cavalo e Torre
		 */
		criareadcionarpecas(Peca.cor_Branca, Peca.tipo_Torre, Peca.linha_1, Peca.coluna_A);
		criareadcionarpecas(Peca.cor_Branca, Peca.tipo_Cavalo, Peca.linha_1, Peca.coluna_B);
		criareadcionarpecas(Peca.cor_Branca, Peca.tipo_Bispo, Peca.linha_1, Peca.coluna_C);
		criareadcionarpecas(Peca.cor_Branca, Peca.tipo_Rei, Peca.linha_1, Peca.coluna_D);
		criareadcionarpecas(Peca.cor_Branca, Peca.tipo_Rainha, Peca.linha_1, Peca.coluna_E);
		criareadcionarpecas(Peca.cor_Branca, Peca.tipo_Bispo, Peca.linha_1, Peca.coluna_F);
		criareadcionarpecas(Peca.cor_Branca, Peca.tipo_Cavalo, Peca.linha_1, Peca.coluna_G);
		criareadcionarpecas(Peca.cor_Branca, Peca.tipo_Torre, Peca.linha_1, Peca.coluna_H);

		//Peões
		int colunaAtual = Peca.coluna_A;
		for (int i = 0; i < 8; i++) {
			criareadcionarpecas(Peca.cor_Branca, Peca.tipo_Peao, Peca.linha_2, colunaAtual);
			colunaAtual++;
		}
		//Lado preto do tabuleiro
		criareadcionarpecas(Peca.cor_Preta, Peca.tipo_Torre, Peca.linha_8, Peca.coluna_A);
		criareadcionarpecas(Peca.cor_Preta, Peca.tipo_Cavalo, Peca.linha_8, Peca.coluna_B);
		criareadcionarpecas(Peca.cor_Preta, Peca.tipo_Bispo, Peca.linha_8, Peca.coluna_C);
		criareadcionarpecas(Peca.cor_Preta, Peca.tipo_Rei, Peca.linha_8, Peca.coluna_D);
		criareadcionarpecas(Peca.cor_Preta, Peca.tipo_Rainha, Peca.linha_8, Peca.coluna_E);
		criareadcionarpecas(Peca.cor_Preta, Peca.tipo_Bispo, Peca.linha_8, Peca.coluna_F);
		criareadcionarpecas(Peca.cor_Preta, Peca.tipo_Cavalo, Peca.linha_8, Peca.coluna_G);
		criareadcionarpecas(Peca.cor_Preta, Peca.tipo_Torre, Peca.linha_8, Peca.coluna_H);

		//Peões
		colunaAtual = Peca.coluna_A;
		for (int i = 0; i < 8; i++) {
			criareadcionarpecas(Peca.cor_Preta, Peca.tipo_Peao, Peca.linha_7, colunaAtual);
			colunaAtual++;
	}
}
	/**
	 * Criar peça e adcionar a lista de Peças
	 * 
	 * @param cor de peças em Peca.cor_..
	 * @param tipo de peças em Peca.Tipo_..
	 * @param Linha da peca em Peca.Linha_..
	 * @param coluna da peca em Peca.Coluna_..
	 */
		private void criareadcionarpecas(int cor, int tipo, int linha, int coluna){
			Peca peca = new Peca(cor, tipo, linha, coluna);
			this.pecas.add(peca);
		}
		
		/**
		 * mover a peça para um local e se estiver ocupado por uma peça do oponente
		 * ela será marcada como (Peça Capturada). Se o movimento não tiver
		 * exatidão, returnar 'false' e o estadojogo não muda
		 * @param move Para a execução
		 * @return true , se a peça for movida com exatidão
		 */
		public boolean moverPeca(Movimento mover) {
			
			if(!this.validarMovimento.movimentolegal(mover)){
				System.out.println("Movimento Ilegal");
				return false;
			}
			
			Peca peca = getPecaNaoCapturadaEm(mover.linhaOrigem, mover.colunaOrigem);
			
			//Verifica se a peça está capturando um oponente
			int corOponente = (peca.getCor()==Peca.cor_Preta?Peca.cor_Branca:Peca.cor_Preta);
			if( naoEstaCapturadoEm(corOponente, mover.linhaAlvo, mover.colunaAlvo)){
				Peca PecaOponente = getPecaNaoCapturadaEm( mover.linhaAlvo, mover.colunaAlvo);
				PecaOponente.estaCapturado(true);
			}
			
			peca.setLinha(mover.linhaAlvo);
			peca.setColuna(mover.colunaAlvo);
			
			if(Fim_De_Jogo()){
				this.estadoJogo = estado_Jogo_Fim;
			}else{
				this.mudarestadoJogo();
			}
			return true;
		}
/**
 * Verifica se a condição de fim do jogo chegou,
 * uma certa cor capturou o rei
 * @return true se chegou o fim de jogo.
 */
		private boolean Fim_De_Jogo() {
			for (Peca peca : this.pecas){
				if(peca.getTipo() == Peca.tipo_Rei && peca.estaCapturado()){
					return true;
				}else{
					//Continuar interação
				}
			}
			return false;
		}
		/**
		 * Retorna a peça no lugar especificado como "Capturada"
		 * @param linha um de Peca.Linha_..
		 * @param coluna um de Peca.Coluna_..
		 * @return the first not captured piece at the specified location
		 */
		public Peca getPecaNaoCapturadaEm(int linha, int coluna) {
			for (Peca peca : this.pecas) {
				if( peca.getLinha() == linha
						&& peca.getColuna() == coluna
						&& peca.estaCapturado() == false ){
					return peca;
				}
			}
			return null;
		}

		/**
		 * Checar se tem uma peça no local que não está marcado como capturado e
		 * tem uma cor específica
		 * @param cor uma da Peca.Cor_..
		 * @param linha uma da Peca.Linha_..
		 * @param coluna da Peca.COLUna_..
		 * @return true, se no local tiver uma peça da cor marcado como não capturado
		 */
		private boolean naoEstaCapturadoEm(int cor, int linha, int coluna) {
			for (Peca peca : this.pecas) {
				if( peca.getLinha() == linha
						&& peca.getColuna() == coluna
						&& peca.estaCapturado() == false
						&& peca.getCor() == cor){
					return true;
				}
			}
			return false;
		}
/**
 * Verifica se tem ou não uma peça capturada no quadrado
 * especificado
 * @param linha um da Peca.Linha_..
 * @param column um da Peca.Coluna_..
 * @return true se tiver peça no local
 */
		public boolean naoEstaCapturadaEm(int linha, int coluna){
			for(Peca peca : this.pecas){
				if (peca.getLinha() == linha && peca.getColuna() == coluna && peca.estaCapturado()== false){
					return true;
				}
			}
			return false;
		}
		
		/**
		 * @return o estado atual do jogo em JogoXadrez.estadoJogo_..)
		 */
		public int getEstadoJogo() {
			return this.estadoJogo;
		}

		/**
		 * @return A lista interna de peças
		 */
		public List<Peca> getPecas() {
			return this.pecas;
		}

		/**
		 * Troca o estado do jogo de estado preto para estado branco e vice versa
		 */
		public void mudarestadoJogo() {
			//Verificar se chegou o fim do jogo
			
			if(this.Fim_De_Jogo()){
				if (this.estadoJogo == JogoXadrez.estado_Jogo_Preta_Vence){
					System.out.println("Fim de Jogo! As Pretas Venceram");
				}else{
					System.out.println("Fim de Jogo! As Brancas Venceram");
				}
				this.estadoJogo = JogoXadrez.estado_Jogo_Fim;
				return;
			}
			
			switch (this.estadoJogo) {
				case estado_Preta:
					this.estadoJogo = estado_Branca;
					break;
				case estado_Branca:
					this.estadoJogo = estado_Preta;
					break;
				default:
					throw new IllegalStateException("Estado de jogo desconhecido:" + this.estadoJogo);
			}
		}
		public ValidarMovimentos getValidarMovimentos(){
			return this.validarMovimento;
		}

	}
