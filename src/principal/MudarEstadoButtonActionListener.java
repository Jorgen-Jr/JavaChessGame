package principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MudarEstadoButtonActionListener implements ActionListener{

	private Interface gui;

	public MudarEstadoButtonActionListener(Interface gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		//Mudas estado do jogo quando o botão for pressionado
		//
		this.gui.mudarEstado();
	}
}