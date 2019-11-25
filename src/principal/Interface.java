package principal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logica.JogoXadrez;
import logica.Movimento;
import logica.Peca;
import logica.ValidarMovimentos;

public class Interface extends JPanel{
	
	/**
	 * SerialVersionUID gerados automaticamente
	 */
	private static final long serialVersionUID = -4613424339897752040L;
	private static final int mesa_Inicio_X = 301;
	private static final int mesa_Inicio_Y = 51;

	private static final int largura_Quadrado = 50;
	private static final int altura_Quadrado = 50;

	private static final int largura_Peca = 48;
	private static final int altura_Peca = 48;
	
	private static final int pecas_Inicio_X = mesa_Inicio_X + (int)(largura_Quadrado/2 - largura_Peca/2);
	private static final int pecas_Inicio_Y = mesa_Inicio_Y + (int)(altura_Quadrado/2 - altura_Peca/2);
	
	private static final int mover_Alvo_X = mesa_Inicio_X - (int)(largura_Peca/2);
	private static final int mover_Alvo_Y = mesa_Inicio_Y - (int)(altura_Peca/2);

	private Image imgBackground; //Imagem de fundo
	
	private JLabel lblEstado;//Painel que mostra o estado do jogo (Preto, branco, fim_
	
	private JogoXadrez jogoxadrez;
	
	
	private List<GuiPeca> pecas = new ArrayList<GuiPeca>();

	private GuiPeca moverpeca;
	
	private Movimento ultimajogada;

//Interface gráfica para ser apresentada ao usuário
	public Interface() {
		this.setLayout(null);
//Carregar a imagem de fundo
		URL urlBackgroundImg = getClass().getResource("/img/mesa.png");
		this.imgBackground = new ImageIcon(urlBackgroundImg).getImage();
//Cria o jogo de xadrez
		this.jogoxadrez = new JogoXadrez();
		
//Juntas as representações gráficas do jogo
		for (Peca peca : this.jogoxadrez.getPecas()) {
			criarEAddGuiPecas(peca);
		}
		
//Adcionar listener para que o usuario possa mover peças
	Moverpecas listener = new Moverpecas(this.pecas, this);
	this.addMouseListener(listener);
	this.addMouseMotionListener(listener);
	
//Botão para mudar o Estado do Jogo
	JButton btnMudarEstado = new JButton("Mudar Jogador");
	btnMudarEstado.addActionListener(new MudarEstadoButtonActionListener(this));
	btnMudarEstado.setBounds(0,0,80,30);
	this.add(btnMudarEstado);
	
	//Painel para mostrar o estado atual de jogo
	String textoEstado = this.getEstadoEmTexto();
	this.lblEstado = new JLabel(textoEstado);
	lblEstado.setBounds(0, 30, 80, 30);
	lblEstado.setForeground(Color.WHITE);
	this.add(lblEstado);
	
//Tornar a aplicação visivel
	JFrame f = new JFrame();
	f.setSize(80, 80);
	f.setVisible(true);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.add(this);
	f.setSize(imgBackground.getWidth(null), imgBackground.getHeight(null));
}
	
//Medoto para capturar o estado do jogo
	private String getEstadoEmTexto(){
		String estado = "Desconhecido";
		switch(this.jogoxadrez.getEstadoJogo()){
		case JogoXadrez.estado_Preta:
			estado = "preta";
			break;
		case JogoXadrez.estado_Jogo_Fim:
			estado = "fim";
			break;
		case JogoXadrez.estado_Branca:
			estado = "branca";
			break;
		}
		return estado;
	}

//Metodo para carregar as imagens das peças
/**
 * Criar peça do jogo
 * @param color cor constante
 * @param type tipo constante
 * @param x x posição do canto superior esquerdo
 * @param y y posição do canto superior esquerdo
 */
	private void criarEAddGuiPecas(Peca peca) {
		Image img = this.getImagemParaPeca(peca.getCor(), peca.getTipo());
		GuiPeca guipeca = new GuiPeca(img, peca);
		this.pecas.add(guipeca);
	}


	private Image getImagemParaPeca(int cor, int tipo){
//Metodo para capturar a imagem das peças
		String nomearquivo = "";
		nomearquivo += (cor == Peca.cor_Branca ? "b" : "p");
		switch (tipo){
			case Peca.tipo_Bispo:
				nomearquivo += "b";
				break;
			case Peca.tipo_Rei:
				nomearquivo += "r";
				break;
			case Peca.tipo_Cavalo:
				nomearquivo += "c";
				break;
			case Peca.tipo_Peao:
				nomearquivo += "p";
				break;
			case Peca.tipo_Rainha:
				nomearquivo += "ra";
				break;
			case Peca.tipo_Torre:
				nomearquivo += "t";
				break;
			}
		nomearquivo += ".png";
		URL urlPecaImg = getClass().getResource("/img/" + nomearquivo);
		return new ImageIcon(urlPecaImg).getImage();
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		
		//Inserir a imagem do background
		g.drawImage(this.imgBackground, 0, 0, null);
		
		//Inserir a imagem das peças
		for (GuiPeca guiPeca : this.pecas) {
			if( !guiPeca.estaCapturado()){
				g.drawImage(guiPeca.getImage(), guiPeca.getX(), guiPeca.getY(), null);
			}
		}
		
		//Inserir a ultima jogada, se o usuario não estiver movendo peça do jogo
		if( !estaMovendoPeca() && this.ultimajogada !=null){
			int luzOrigemX = converterColunaParaX(this.ultimajogada.colunaOrigem);
			int luzOrigemY = converterLinhaParaY(this.ultimajogada.linhaOrigem);
			int luzAlvoX = converterColunaParaX(this.ultimajogada.colunaAlvo);
			int luzAlvoY = converterLinhaParaY(this.ultimajogada.linhaAlvo);
			
			g.setColor(Color.CYAN);
			g.drawRoundRect(luzOrigemX+4, luzOrigemY+4, largura_Quadrado-10, altura_Quadrado-10, 10, 10);
			g.drawRoundRect(luzAlvoX+4, luzAlvoY+4, largura_Quadrado-10, altura_Quadrado-10, 10, 10);
		}
		
		//Mostrar ao usuario quais são os lugares possiveis de mover a peca.
		if(estaMovendoPeca()){
			ValidarMovimentos validarmovimento = this.jogoxadrez.getValidarMovimentos();
			
			//Pesquisa toda a mesa para checar os locais alvo validos
			for(int coluna = Peca.coluna_A; coluna<=Peca.coluna_H; coluna++){
				for(int linha = Peca.linha_1; linha<=Peca.linha_8; linha++){
					int linhaOrigem = this.moverpeca.getPeca().getLinha();
					int colunaOrigem = this.moverpeca.getPeca().getColuna();
					
					//Verificar agora se o local alvo é realmente um movimento legal
					if(validarmovimento.movimentolegal(new Movimento(linhaOrigem,colunaOrigem, linha,coluna))){
			
						int luzX = converterColunaParaX(coluna);
						int luzY = converterLinhaParaY(linha);
						
						//Desenhar uma sombra preta retangular
						g.setColor(Color.BLACK);
						g.drawRoundRect(luzX+5, luzY+5, largura_Quadrado-10, altura_Peca-10, 10, 10);
						g.setColor(Color.BLUE);
						g.drawRoundRect(luzX+4, luzY+4, largura_Quadrado-10, altura_Peca-10, 10, 10);
					}
				}
			}
		}
		
		this.lblEstado.setText(this.getEstadoEmTexto());
	}
	
	/**
	 * Verificar se o usuario está movendo peça
	 * @param @return true se o usuario estiver movendo uma peça.
	 */
	private boolean estaMovendoPeca(){
		return this.moverpeca != null;
	}
	
	public static void main(String[] args) {
		new Interface();
	}
	
	//Botão para trocar de estado
			public void mudarEstado(){
				this.jogoxadrez.mudarestadoJogo();
				this.lblEstado.setText(this.getEstadoEmTexto());
				}
			
		//Retornar o estado atual de jogo
			public int getEstado(){
				return this.jogoxadrez.getEstadoJogo();
			}
/**
* Converte coluna lógica em coordenadas x
* @param coluna
* @return coordenada x para coluna
*/
	public static int converterColunaParaX(int coluna){
	return pecas_Inicio_X + largura_Quadrado * coluna;
	}
/**
* converter linhas em coordenadas y
* @param linha
* @return coordenada y para linha
*/
	public static int converterLinhaParaY(int linha){
		return pecas_Inicio_Y + altura_Quadrado * (Peca.linha_8 - linha);
	}
	
/**
* convrter coordenadas x em coluna logica
* @param x
* @return coluna para coordenadas x
*/
	public static int converterXParaColuna(int x){
	return(x - mover_Alvo_X)/largura_Quadrado;
	}

	/**
	 * converter y para uma linha
	 * @param y
	 * @return linha logica para coordenadas y
	 */
	public static int converterYParaLinha(int y){
		return Peca.linha_8 - (y - mover_Alvo_Y)/altura_Quadrado;
	}

	/**
	 * Mudar local da peça se for movimento valido, se não for
	 * resetar a posição
	 * @param moverpeca
	 * @param x
	 * @param y
	 */
	public void setNovaLocalizacaoPeca(GuiPeca moverpeca, int x, int y) {
		int linhaAlvo = Interface.converterYParaLinha(y);
		int colunaAlvo = Interface.converterXParaColuna(x);
		
		if( linhaAlvo < Peca.linha_1 || linhaAlvo > Peca.linha_8 || colunaAlvo < Peca.coluna_A || colunaAlvo > Peca.coluna_H){
			// Resetar posição da peça se o movimento for inválido
			moverpeca.ResetarPecaParaPosicaoSubjacente();
		
		}else{
			//Muda a posição e atualiza a interface em seguida
			System.out.println("Movendo peça para "+linhaAlvo+"/"+colunaAlvo);
			Movimento mover = new Movimento(moverpeca.getPeca().getLinha(), moverpeca.getPeca().getColuna(), linhaAlvo, colunaAlvo);
			boolean MovimentoExecutado = this.jogoxadrez.moverPeca(mover);
			
			//Iluminar a ultima jogada se ela for executada com sucesso.
			if (MovimentoExecutado){
				this.ultimajogada = mover;
			}
			moverpeca.ResetarPecaParaPosicaoSubjacente();
		}
	}
	/**
	 * @param GuiPeca - escolher a peca que o usuario está movendo atualmente
	 */
	public void setMoverPeca(GuiPeca peca){
		this.moverpeca = peca;
	}
	/**
	 * 
	 * @return a Peca que o usuario está movendo.
	 */
	public GuiPeca getMoverPeca(){
		return this.moverpeca;
	}
}
