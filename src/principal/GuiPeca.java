package principal;

import java.awt.Image;

import logica.Peca;

public class GuiPeca  {

	private  Image img;
	private  int x;
	private  int y;
	private Peca peca;

		public GuiPeca(Image img, Peca peca){
			this.img = img;
			this.peca = peca;
			
			this.ResetarPecaParaPosicaoSubjacente();
		}
		public Image getImage() {
			return img;
		}
		
		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public void setX(int x) {
			this.x = x;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getWidth() {
			return img.getHeight(null);
		}

		public int getHeight() {
			return img.getHeight(null);
		}
		public int getCor(){
			return this.peca.getCor();
}
		@Override
		public String toString() {
			return this.peca+" "+x+"/"+y;
		}
/**
 * Move a interface da peça de volta para
 * as coordenadas correspontentes a linha e coluna
 * anteriores
 */
		public void ResetarPecaParaPosicaoSubjacente(){
			this.x = Interface.converterColunaParaX(peca.getColuna());
			this.y = Interface.converterLinhaParaY(peca.getLinha());
		}

		public Peca getPeca() {
			return peca;
		}

		public boolean estaCapturado() {
			return this.peca.estaCapturado();
		}

}
