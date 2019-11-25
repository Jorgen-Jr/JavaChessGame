package logica;

public class Peca {
	private int cor;
	public static final int cor_Branca = 0;
	public static final int cor_Preta = 1;
	
	private int tipo;
	public static final int tipo_Torre = 1;
	public static final int tipo_Cavalo = 2;
	public static final int tipo_Bispo = 3;
	public static final int tipo_Rainha = 4;
	public static final int tipo_Rei = 5;
	public static final int tipo_Peao = 6;
	
/*Xadrez é um jogo com oito linhas (de 1 à 8)
 * E oito colunas(De "a" à "h")
 */
	private int linha;
	public static final int linha_1 = 0;
	public static final int linha_2 = 1;
	public static final int linha_3 = 2;
	public static final int linha_4 = 3;
	public static final int linha_5 = 4;
	public static final int linha_6 = 5;
	public static final int linha_7 = 6;
	public static final int linha_8 = 7;
	
	private int coluna;
	public static final int coluna_A = 0;
	public static final int coluna_B = 1;
	public static final int coluna_C = 2;
	public static final int coluna_D = 3;
	public static final int coluna_E = 4;
	public static final int coluna_F = 5;
	public static final int coluna_G = 6;
	public static final int coluna_H = 7;
	
	private boolean estaCapturado = false;
	
	public Peca(int cor, int tipo, int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
		this.cor = cor;
		this.tipo = tipo;
}

public int getLinha() {
	return linha;
}

public int getColuna() {
	return coluna;
}

public void setLinha(int linha) {
	this.linha = linha;
}

public void setColuna(int coluna) {
	this.coluna = coluna;
}

public int getCor(){
	return this.cor;
}
@Override
public String toString() {
	String sCor = (this.cor==cor_Branca?"branca":"preta");
	String sTipo = "desconhecido";
	switch (this.tipo) {
		case tipo_Bispo: sTipo = "b";break;
		case tipo_Rei: sTipo = "k";break;
		case tipo_Cavalo: sTipo = "c";break;
		case tipo_Peao: sTipo = "p";break;
		case tipo_Rainha: sTipo = "ra";break;
		case tipo_Torre: sTipo = "t";break;
	}
	String sLinha = getLinha(this.linha);
	String sColuna = getColuna(this.coluna);
	return sCor +" "+sTipo+" "+sLinha+"/"+sColuna;
}

public int getTipo() {
	return this.tipo;
}

public static String getLinha(int linha){
	String sLinha = "Desconhecido";
	switch (linha) {
		case linha_1: sLinha = "1";break;
		case linha_2: sLinha = "2";break;
		case linha_3: sLinha = "3";break;
		case linha_4: sLinha = "4";break;
		case linha_5: sLinha = "5";break;
		case linha_6: sLinha = "6";break;
		case linha_7: sLinha = "7";break;
		case linha_8: sLinha = "8";break;
	}
	return sLinha;
}

public static String getColuna(int coluna){
	String sColuna = "Desconhecido";
	switch (coluna) {
		case coluna_A: sColuna = "A";break;
		case coluna_B: sColuna = "B";break;
		case coluna_C: sColuna = "C";break;
		case coluna_D: sColuna = "D";break;
		case coluna_E: sColuna = "E";break;
		case coluna_F: sColuna = "F";break;
		case coluna_G: sColuna = "G";break;
		case coluna_H: sColuna = "H";break;
	}
	return sColuna;
}

public void estaCapturado(boolean estaCapturado) {
	this.estaCapturado = estaCapturado;
}

public boolean estaCapturado() {
	return this.estaCapturado;
}

}
