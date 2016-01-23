package Model;

public class Time {
	
	private final int TAMANHO_TIME = 11;
	private final int TAMANHO_EQUIPE = 6;
	
	private String nome;
	private Jogador[] jogadores = new Jogador[TAMANHO_TIME];
	private Jogador[] batedores = new Jogador[TAMANHO_EQUIPE - 1];
	private Jogador goleiro;
	private Torcida torcida;
	private int gols;
	
	public Time(String nome){
		this.torcida = new Torcida();
		this.nome = nome;
	}
	
	public Jogador[] getJogadores() {
		return jogadores;
	}
	public void setJogadores(Jogador[] jogadores) {
		this.jogadores = jogadores;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Torcida getTorcida() {
		return torcida;
	}
	public void setTorcida(Torcida torcida) {
		this.torcida = torcida;
	}

	public Jogador selecionaBatedor(int numeroCobranca) {
		return batedores[numeroCobranca];
	}
	
	public void marcaGol(){
		this.gols++;
	}
	public int getGols(){
		return this.gols;
	}

}
