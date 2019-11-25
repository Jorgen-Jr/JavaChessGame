package logica;
/*
 * Essa classe torna mais fácil de armazenar as
 * informações relacionadas a movimentos.
 */
public class Movimento {
	public int linhaOrigem;
	public int colunaOrigem;
	public int linhaAlvo;
	public int colunaAlvo;
	
	public Movimento(int linhaOrigem, int colunaOrigem, int linhaAlvo, int colunaAlvo){
		this.linhaOrigem = linhaOrigem;
		this.colunaOrigem = colunaOrigem;
		this.linhaAlvo = linhaAlvo;
		this.colunaAlvo = colunaAlvo;
	}
}
