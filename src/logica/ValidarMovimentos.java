package logica;

public class ValidarMovimentos {
	
	private JogoXadrez jogoxadrez;
	private Peca pecaOrigem;
	private Peca pecaAlvo;
	
	public ValidarMovimentos(JogoXadrez jogoxadrez){
		this.jogoxadrez = jogoxadrez;
	}		
/**
 * Objetivos do método:
 * -Verificar se o movimento especificado é legal ou não
 * @param linhaOrigem
 * @param linhaAlvo
 * @param colunaOrigem
 * @param colunaAlvo
 * @return true se o movimento for legal, se não retornar falso.
 */

	public boolean movimentolegal(Movimento mover){
		int linhaOrigem = mover.linhaOrigem;
		int colunaOrigem = mover.colunaOrigem;
		int linhaAlvo = mover.linhaAlvo;
		int colunaAlvo = mover.colunaAlvo;
		
		
		pecaOrigem = jogoxadrez.getPecaNaoCapturadaEm(linhaOrigem, colunaOrigem);
		pecaAlvo = this.jogoxadrez.getPecaNaoCapturadaEm(linhaAlvo, colunaAlvo);
		
		//Se a peça alvo não existir
		if( pecaOrigem == null){
			System.out.println("Peça origem inexistente");
			return false;
		}
		//Se a peça alvo tiver a cor certa
		if( pecaOrigem.getCor() == Peca.cor_Branca && this.jogoxadrez.getEstadoJogo() == JogoXadrez.estado_Branca){
			//Ok, movimento legal para jogo com a vez branca
		}else if(pecaOrigem.getCor() == Peca.cor_Preta && this.jogoxadrez.getEstadoJogo() == JogoXadrez.estado_Preta){
			//Ok, movimento valido para jogo com a vez preta
		}else{
			System.out.println("Não é sua vez de jogar");
			return false;
			//Jogada ilegal
		}
	//Checar se o local alvo está no tabuleiro
		if(linhaAlvo < Peca.linha_1 || linhaAlvo > Peca.linha_8 || colunaAlvo < Peca.coluna_A || colunaAlvo > Peca.coluna_H){
		System.out.println("Linha ou coluna alvos estão fora do tabuleiro");
		return false;
		//Jogada ilegal
		}
	//Verificar e validar os movimentos especiais das peças
		boolean MovimentoPecaLegal = false;
		switch(pecaOrigem.getTipo()){
		case Peca.tipo_Bispo:
			MovimentoPecaLegal = MovimentoBispoLegal(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo);
			break;
		case Peca.tipo_Rei:
			MovimentoPecaLegal = MovimentoReiLegal(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo);
			break;
		case Peca.tipo_Cavalo:
			MovimentoPecaLegal = MovimentoCavaloLegal(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo);
			break;
		case Peca.tipo_Peao:
			MovimentoPecaLegal = MovimentoPeaoLegal(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo);
			break;
		case Peca.tipo_Rainha:
			MovimentoPecaLegal = MovimentoRainhaLegal(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo);
			break;
		case Peca.tipo_Torre:
			MovimentoPecaLegal = MovimentoTorreLegal(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo);
			break;
		default:
			break;
		}
		if ( !MovimentoPecaLegal){
			return false;
		}
		//Como não há mais movimentos ilegais para verificar, o metodo returna true
		return true;
	}
// Verificar se o local é capturavel
private boolean LocalAlvoCapturavel(){
	if (pecaAlvo == null){
		return false;
	}else if( pecaAlvo.getCor() != pecaOrigem.getCor()){
		return true;
	}else{
		return false;
	}
}
//Verificar se o local alvo está livre
private boolean LocalAlvoLivre(){
	return pecaAlvo == null;
}

/*
 * Especificar os movimentos especiais das peças e validar elas
 * com true se for legal e falso se ilegal.
 */
private boolean MovimentoBispoLegal(int linhaOrigem, int colunaOrigem, int linhaAlvo, int colunaAlvo) {
//O bispo se move pelo tabuleiro pelas diagonais, mas não passa por cima
//de outras peças
//Local alvo é possivel?
	if( LocalAlvoLivre() || LocalAlvoCapturavel()){
		//Ok, movimento possivel
	}else{
		System.out.println("Local alvo não pode ser caturado, nem está livre");
		return false;
	}
	
	boolean movimentolegal = false;
	//Primeiro verificamos se o movimento para o alvo está na diagonal
	int razaoLinha = linhaAlvo - linhaOrigem;
	int razaoColuna = colunaAlvo - colunaOrigem;
	
	if (razaoLinha==razaoColuna && razaoColuna >0){
		//Movendo para a direita acima
		movimentolegal = !HaPecasEntreOrigemEAlvo(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo, +1, +1);
	}else if(razaoLinha ==- razaoColuna && razaoColuna >0){
		//Movendo para direita abaixo
		movimentolegal = !HaPecasEntreOrigemEAlvo(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo, -1, +1);
	}else if(razaoLinha == razaoColuna && razaoColuna <0){
		//movendo para esquerda abaixo
		movimentolegal = !HaPecasEntreOrigemEAlvo(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo, -1, -1);
	}else if(razaoLinha ==- razaoColuna && razaoColuna<0){
		//Movendo para acima esquerda
		movimentolegal = !HaPecasEntreOrigemEAlvo(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo, +1, -1);
	}else{
		//Não está movendo diagonalmente
		System.out.println(razaoLinha);
		System.out.println(razaoColuna);
		System.out.println("Não está movendo diagonalmente");
		movimentolegal = false;
	}
	
		return movimentolegal;
	}

private boolean MovimentoReiLegal(int linhaOrigem, int colunaOrigem, int linhaAlvo, int colunaAlvo) {
//O rei só move um quadrado e em qualquer direção
	//Local alvo é possivel?
		if( LocalAlvoLivre() || LocalAlvoCapturavel()){
			//Ok, movimento possivel
		}else{
			System.out.println("Local alvo não pode ser caturado, nem está livre");
			return false;
		}
boolean movimentolegal = true;
if(			linhaOrigem+1 == linhaAlvo && colunaOrigem == colunaAlvo){
	//Movendo para cima
	movimentolegal = true;
}else if(	linhaOrigem+1 == linhaAlvo && colunaOrigem+1 == colunaAlvo){
	//Acima direita
	movimentolegal = true;
}else if(	linhaOrigem == linhaAlvo && colunaOrigem+1 == colunaAlvo){
	//direita
	movimentolegal = true;
}else if(	linhaOrigem-1 == linhaAlvo && colunaOrigem+1 == colunaAlvo){
	//pra baixo direita
	movimentolegal = true;
}else if(	linhaOrigem-1 == linhaAlvo && colunaOrigem == colunaAlvo){
	//pra baixo
	movimentolegal = true;
}else if(	linhaOrigem-1 == linhaAlvo && colunaOrigem-1 == colunaAlvo){
	//esquerda baixo
	movimentolegal = true;
}else if(	linhaOrigem == linhaAlvo && colunaOrigem-1 == colunaAlvo){
	//esquerda
	movimentolegal = true;
}else if(	linhaOrigem+1 == linhaAlvo && colunaOrigem-1 == colunaAlvo){
	//esquerda acima
	movimentolegal = true;
}else{
	System.out.println("Se movendo muito longe");
	movimentolegal = false;
}
	return movimentolegal;
	}

private boolean MovimentoCavaloLegal(int linhaOrigem, int colunaOrigem, int linhaAlvo, int colunaAlvo) {
//O cavalo anda em "L" e pula sobre outras peças.
	//Local alvo é possivel?
		if( LocalAlvoLivre() || LocalAlvoCapturavel()){
			//Ok, movimento possivel
		}else{
			System.out.println("Local alvo não pode ser caturado, nem está livre");
			return false;
		}
		  if(linhaOrigem+2 == linhaAlvo && colunaOrigem+1 == colunaAlvo){
			  //Move cima cima direita
		return true;
	}else if(linhaOrigem+1 == linhaAlvo && colunaOrigem+2 == colunaAlvo){
		//move cima direita direita
		return true;
	}else if(linhaOrigem-1 == linhaAlvo && colunaOrigem+2 == colunaAlvo){
		//move baixo direita direita
		return true;
	}else if(linhaOrigem-2 == linhaAlvo && colunaOrigem+1 == colunaAlvo){
		//move baixo baixo direita
		return true;
	}else if(linhaOrigem-2 == linhaAlvo && colunaOrigem-1 == colunaAlvo){
		//move baixo baixo esquerda
		return true;
	}else if(linhaOrigem-1 == linhaAlvo && colunaOrigem-2 == colunaAlvo){
		//move baixo esquerda esquerda
		return true;
	}else if(linhaOrigem+1 == linhaAlvo && colunaOrigem-2 == colunaAlvo){
		//move cima esquerda esquerda
		return true;
	}else if(linhaOrigem+2 == linhaAlvo && colunaOrigem-1 == colunaAlvo){
		//move cima cima esquerda
		return true;
	}else{
	return false;
	}
}

private boolean MovimentoPeaoLegal(int linhaOrigem, int colunaOrigem, int linhaAlvo, int colunaAlvo) {
/*O peão move para a casa a frente não ocupado
 * que está na mesma coluna, ou em seu primeiro movimento
 * move duas casas
 */
	boolean movimentolegal = false;
	
	if(LocalAlvoLivre()){
		//Mesma coluna
		if(colunaOrigem == colunaAlvo){
			if(pecaOrigem.getCor() == Peca.cor_Branca){
				//Peça branca
				if(linhaOrigem+1 == linhaAlvo){
					//move uma casa acima
					movimentolegal=true;
				}else{
					System.out.println("Não está movendo uma casa acima");
					movimentolegal=false;
				}
			}else{
				//Peca preta
				if(linhaOrigem-1 == linhaAlvo){
					//move uma casa abaixo
					movimentolegal=true;
				}else{
					System.out.println("Não está movendo uma casa abaixo");
					movimentolegal=false;
				}
			}
		}else{
			//Não pertence a mesma coluna
			System.out.println("Nao continua na mesma coluna");
			movimentolegal=false;
		}
/*
 * Ou ele pode mover para uma casa ocupada
 * pelo oponente nas diagonais, comendo aquela peça	
 */
}else if(LocalAlvoCapturavel()){
	if(colunaOrigem+1 == colunaAlvo || colunaOrigem-1 == colunaAlvo){
	//Uma coluna para a esquerda ou direita
		if(pecaOrigem.getCor() == Peca.cor_Branca){
			//Peça branca
			if(linhaOrigem+1 == linhaAlvo){
				//move um acima
				movimentolegal=true;
			}else{
				System.out.println("Não está movendo uma casa acima");
				movimentolegal=false;
			}
		}
		}else{
			//Para as pretas
			if(linhaOrigem-1 == linhaAlvo){
				//movendo casa abaixo
				movimentolegal=true;
			}else{
				System.out.println("Não está movendo uma casa abaixo");
				movimentolegal=false;
		}
	}
}else{
		//Anove uma coluna para esquerda ou direita.
				System.out.println("Não está movendo uma coluna para a esquerda ou direita");
				movimentolegal=false;
}

/*TODO
 * Na primeira jogada o peão move duas casas
 * O peão tem dois especias.
 * O da promoção, e de captura
 * ... Não sei se vou ou quero implementar u_u
 */
	return movimentolegal;
}

private boolean MovimentoRainhaLegal(int linhaOrigem, int colunaOrigem, int linhaAlvo, int colunaAlvo) {
/*A rainha combina os especiais da torre e bispo,
 * e pode se mover em qualquer direção, mas não
 * pula sobre as peças
 */
	boolean movimentolegal = MovimentoBispoLegal(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo);
	movimentolegal |= MovimentoTorreLegal(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo);
	return movimentolegal;

}

private boolean MovimentoTorreLegal(int linhaOrigem, int colunaOrigem, int linhaAlvo, int colunaAlvo) {
/*
 * A torre se move horizontalmente e verticamente, não pula outras peças e
 * e está involvida num movimento especial com o Rei
 */
	//Local alvo é possivel?
		if( LocalAlvoLivre() || LocalAlvoCapturavel()){
			//Ok, movimento possivel
		}else{
			System.out.println("Local alvo não pode ser caturado, nem está livre");
			return false;
		}
		
boolean movimentolegal = false;

//Primeiro verificar se o movimento está na horizontal (Mesmo esquema do bispo so que não)
	int razaoLinha = linhaOrigem - linhaAlvo;
	int razaoColuna = colunaOrigem - colunaAlvo;
	
	if(razaoLinha == 0 && razaoColuna > 0){
		//direita
		movimentolegal = !HaPecasEntreOrigemEAlvo(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo, 0, +1);
	}else if(razaoLinha == 0 && razaoColuna < 0){
		//esquerda
		movimentolegal = !HaPecasEntreOrigemEAlvo(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo, 0, -1);
	}else if(razaoLinha > 0 && razaoColuna == 0){
		//cima
		movimentolegal = !HaPecasEntreOrigemEAlvo(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo, +1, 0);
	}else if(razaoLinha < 0 && razaoColuna == 0){
		//baixo
		movimentolegal = !HaPecasEntreOrigemEAlvo(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo, -1, 0);
	}else{
		System.out.println("Não está movendo horizontalmente ou verticalmente");
		movimentolegal = false;
	}
	return movimentolegal;
}

private boolean HaPecasEntreOrigemEAlvo(int linhaOrigem, int colunaOrigem, int linhaAlvo, int colunaAlvo, int IncrementoLinha, int IncrementoColuna) {
	int linhaAtual = linhaOrigem + IncrementoLinha;
	int colunaAtual = colunaOrigem + IncrementoColuna;
	
	while(true){
		if(linhaAtual == linhaAlvo && colunaAtual == colunaAlvo){
			break;
		}
		if(linhaAtual<Peca.linha_1 || colunaAtual>Peca.linha_8 || colunaAtual < Peca.coluna_A || colunaAtual > Peca.coluna_H){
			break;
		}
		if(this.jogoxadrez.naoEstaCapturadaEm(linhaAtual, colunaAtual)){
			System.out.println("Existe peças entre origem e alvo");
			return true;
		}
		linhaAtual += IncrementoLinha;
		colunaAtual += IncrementoColuna;
	}
	return false;
}

	public static void main(String[] args){
		JogoXadrez jogo = new JogoXadrez();
		ValidarMovimentos mo = new ValidarMovimentos(jogo);
		Movimento mover = null;
		boolean movimentolegal;
		
		int linhaOrigem;
		int colunaOrigem;
		int linhaAlvo;
		int colunaAlvo;
		
		int ContadorTeste = 1;
		
		
		linhaOrigem = Peca.linha_2; colunaOrigem = Peca.coluna_D;
		linhaAlvo = Peca.linha_3; colunaAlvo = Peca.coluna_D;
		mover = new Movimento(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo);
		movimentolegal = mo.movimentolegal(mover);
		jogo.moverPeca(mover);
		System.out.println(ContadorTeste +". Resultado do teste: "+(true==movimentolegal));
		ContadorTeste++;
		
		//Não é a vez das brancas
		linhaOrigem = Peca.linha_2; colunaOrigem = Peca.coluna_B;
		linhaAlvo = Peca.linha_3; colunaAlvo = Peca.coluna_B;
		mover = new Movimento(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo);
		movimentolegal = mo.movimentolegal(mover);
		System.out.println(ContadorTeste +". Resultado do teste: "+(false==movimentolegal));
		ContadorTeste++;
		
		//Ok
		linhaOrigem = Peca.linha_7; colunaOrigem = Peca.coluna_E;
		linhaAlvo = Peca.linha_6; colunaAlvo = Peca.coluna_E;
		mover = new Movimento(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo);
		movimentolegal = mo.movimentolegal(mover);
		jogo.moverPeca(mover);
		System.out.println(ContadorTeste +". Resultado do teste: "+(true==movimentolegal));
		ContadorTeste++;
		
		//Peças no caminho
		linhaOrigem = Peca.linha_1; colunaOrigem = Peca.coluna_F;
		linhaAlvo = Peca.linha_4; colunaAlvo = Peca.coluna_C;
		mover = new Movimento(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo);
		movimentolegal = mo.movimentolegal(mover);
		System.out.println(ContadorTeste +". Resultado do teste: "+(false==movimentolegal));
		ContadorTeste++;

		//Ok
		linhaOrigem = Peca.linha_1; colunaOrigem = Peca.coluna_C;
		linhaAlvo = Peca.linha_4; colunaAlvo = Peca.coluna_F;
		mover = new Movimento(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo);
		movimentolegal = mo.movimentolegal(mover);
		jogo.moverPeca(mover);
		System.out.println(ContadorTeste +". Resultado do teste: "+(true==movimentolegal));
		ContadorTeste++;
		
		//ok
		linhaOrigem = Peca.linha_8; colunaOrigem = Peca.coluna_B;
		linhaAlvo = Peca.linha_6; colunaAlvo = Peca.coluna_C;
		mover = new Movimento(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo);
		movimentolegal = mo.movimentolegal(mover);
		jogo.moverPeca(mover);
		System.out.println(ContadorTeste +". Resultado do teste: "+(true==movimentolegal));
		ContadorTeste++;

		//Jogada com cavalo ilegal
		linhaOrigem = Peca.linha_1; colunaOrigem = Peca.coluna_G;
		linhaAlvo = Peca.linha_3; colunaAlvo = Peca.coluna_H;
		mover = new Movimento(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo);
		movimentolegal = mo.movimentolegal(mover);
		System.out.println(ContadorTeste +". Resultado do teste: "+(false==movimentolegal));
		ContadorTeste++;
		
		//Jogada com cavalo ilegal
		linhaOrigem = Peca.linha_1; colunaOrigem = Peca.coluna_G;
		linhaAlvo = Peca.linha_2; colunaAlvo = Peca.coluna_E;
		mover = new Movimento(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo);
		movimentolegal = mo.movimentolegal(mover);
		System.out.println(ContadorTeste +". Resultado do teste: "+(false==movimentolegal));
		ContadorTeste++;

		//ok
		linhaOrigem = Peca.linha_1; colunaOrigem = Peca.coluna_G;
		linhaAlvo = Peca.linha_3; colunaAlvo = Peca.coluna_H;
		mover = new Movimento(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo);
		movimentolegal = mo.movimentolegal(mover);
		jogo.moverPeca(mover);
		System.out.println(ContadorTeste +". Resultado do teste: "+(true==movimentolegal));
		ContadorTeste++;
		
		//Peças entre Origem e Alvo
		linhaOrigem = Peca.linha_8; colunaOrigem = Peca.coluna_A;
		linhaAlvo = Peca.linha_5; colunaOrigem = Peca.coluna_A;
		mover = new Movimento(linhaOrigem, colunaOrigem, linhaAlvo, colunaAlvo);
		movimentolegal = mo.movimentolegal(mover);
		System.out.println(ContadorTeste +". Resultado do teste: "+(false==movimentolegal));
		ContadorTeste++;
		
		//TODO Implementar o out.print do estado de jogo atual no console
	}
}