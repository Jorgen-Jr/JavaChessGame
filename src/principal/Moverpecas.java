package principal;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import logica.JogoXadrez;
import logica.Peca;

public class Moverpecas implements MouseListener, MouseMotionListener{

	private List<GuiPeca> guiPecas;
	private Interface gui;

	private GuiPeca moverpeca;
	private int moverOffsetX;
	private int moverOffsetY;
	
	public Moverpecas (List<GuiPeca> guiPecas, Interface gui){
		this.guiPecas = guiPecas;
		this.gui = gui;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

@Override
public void mousePressed(MouseEvent evt){
	int x = evt.getPoint().x;
	int y = evt.getPoint().y;
/*Procurar qual peça mover
 * Checar a lista de cima pra baixo.
 * (E então fazer o inverso)
 */
	for (int i = this.guiPecas.size()-1; i >= 0; i--) {
		GuiPeca guiPeca = this.guiPecas.get(i);
		if (guiPeca.estaCapturado()) continue;

		if(mouseSobrePeca(guiPeca,x,y)){
			
			if( (	this.gui.getEstado() == JogoXadrez.estado_Branca
					&& guiPeca.getCor() == Peca.cor_Branca
				) ||
				(	this.gui.getEstado() == JogoXadrez.estado_Preta
						&& guiPeca.getCor() == Peca.cor_Preta
					)
				){
				// Calcular o offset para evitar que a peça salte para o canto
				// da posição atual do mouse
				this.moverOffsetX = x - guiPeca.getX();
				this.moverOffsetY = y - guiPeca.getY();
				this.gui.setMoverPeca(guiPeca);
				this.gui.repaint();
				break;			
			}
		}
	}
// Move a peça para o topo da lista.
	if(this.moverpeca != null){
		this.guiPecas.remove(this.moverpeca);
		this.guiPecas.add(this.moverpeca);
	}
}
/**
 * Checar se o mouse está sobre a peça
 * @param peca a peça a ser jogada
 * @param x x coordenadas do mouse
 * @param y y coordenadas do mouse
 * @return true se o mouse estiver sobre a peça
 */
private boolean mouseSobrePeca(GuiPeca guiPeca, int x, int y){
	return guiPeca.getX() <= x
		&& guiPeca.getX()+guiPeca.getWidth() >=x
		&& guiPeca.getY() <= y
		&& guiPeca.getY()+guiPeca.getHeight() >=y;
}
@Override
public void mouseReleased(MouseEvent evt){
	if( this.gui.getMoverPeca() != null){
		int x = evt.getPoint().x - this.moverOffsetX;
		int y = evt.getPoint().y - this.moverOffsetY;
		
		// Escolhe a localização da peça no tabuleiro se for um movimento
		//válido
		gui.setNovaLocalizacaoPeca(this.gui.getMoverPeca(), x, y);
		this.gui.repaint();
		this.gui.setMoverPeca(null);
	}
}
@Override
public void mouseDragged(MouseEvent evt){
	if(this.gui.getMoverPeca() != null){
		
		int x = evt.getPoint().x - this.moverOffsetX;
		int y = evt.getPoint().y - this.moverOffsetY;
		
		System.out.println(
				"linha:"+Interface.converterYParaLinha(y)
				+" coluna:"+Interface.converterXParaColuna(x));
		GuiPeca moverPeca = this.gui.getMoverPeca();
		moverPeca.setX(x);
		moverPeca.setY(y);
		
		this.gui.repaint();
	}
}
@Override
public void mouseMoved(MouseEvent arg0) {}
}