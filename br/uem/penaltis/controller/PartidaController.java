package br.uem.penaltis.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.uem.penaltis.model.Batedor;
import br.uem.penaltis.model.Goleiro;
import br.uem.penaltis.model.Jogador;
import br.uem.penaltis.model.Observer;
import br.uem.penaltis.model.Parametros;
import br.uem.penaltis.model.Ponto;
import br.uem.penaltis.model.Time;
import br.uem.penaltis.model.Torcida;
import br.uem.penaltis.model.Util;
import br.uem.penaltis.view.PartidaView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PartidaController {

	public static final int TIME_JOGADOR = 0;
	public static final int TIME_MAQUINA = 1;
	private final int NUMERO_COBRANCAS = 5;
	private ArrayList<Observer> torcedores = new ArrayList<Observer>();
	private boolean jogadorChutando;
	private boolean jogadorIniciouCobrancas;

	public boolean isJogadorIniciouCobrancas() {
		return jogadorIniciouCobrancas;
	}

	public void setJogadorIniciouCobrancas(boolean jogadorIniciouCobrancas) {
		this.jogadorIniciouCobrancas = jogadorIniciouCobrancas;
	}

	public boolean isJogadorChutando() {
		return jogadorChutando;
	}

	public void setJogadorChutando(boolean jogadorChutando) {
		this.jogadorChutando = jogadorChutando;
	}

	private Time[] timesDaPartida = new Time[2];

	public Time[] getTimesDaPartida() {
		return timesDaPartida;
	}

	private void AdicionaTorcedoresAoObserver(Observer torcida) {
		torcedores.add(torcida);
	}

	public static List<Time> PegaTimes() {
		String dir = ("c:/ra88408-ra89354-ra90253/penaltis/");
		List<Time> timesParaSelecao = new ArrayList<Time>();
		boolean encontrouArquivo = false;
		do {
			try {
				File diretorio = new File(dir);
				for (File f : diretorio.listFiles()) {
					if (f.isFile()) {
						List<Jogador> jogadores = new ArrayList<Jogador>();
						BufferedReader br = new BufferedReader(new FileReader(f));
						String linha = br.readLine();
						while (linha != null) {
							if (jogadores.isEmpty())
								jogadores.add(new Goleiro(linha));
							else
								jogadores.add(new Batedor(linha));

							linha = br.readLine();
						}
						String[] nomeTime = f.getName().split(".txt");
						timesParaSelecao.add(new Time(nomeTime[0], jogadores));
					}
				}
				encontrouArquivo = true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Diretorio :" + dir + " não encontrado");
			}
		} while (!encontrouArquivo);
		return timesParaSelecao;
	}

	public void iniciaPartida(Time timeJogador, Time timeAdversario) {
		this.timesDaPartida[TIME_JOGADOR] = timeJogador;
		this.timesDaPartida[TIME_MAQUINA] = timeAdversario;
		AdicionaTorcedoresAoObserver(this.timesDaPartida[TIME_JOGADOR].getTorcida());
		AdicionaTorcedoresAoObserver(this.timesDaPartida[TIME_MAQUINA].getTorcida());

		Parametros.setInicioAltomatico(true);
		decideInicio();
		iniciaPartidaView();

	}

	private void atualizaTorcedores(String msg) {
		for (Observer o : this.torcedores) {
			o.update(msg);
		}
	}

	private void iniciaPartidaView() {
		try {
			PartidaView partidaView = new PartidaView(this.timesDaPartida[TIME_JOGADOR],
					this.timesDaPartida[TIME_MAQUINA], this);
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/partidaView.fxml"));
			fxmlLoader.setController(partidaView);
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void decideInicio() {
		if (!Parametros.isInicioAltomatico()) {
			setJogadorIniciouCobrancas(true);
			setJogadorChutando(true);
			return;
		}

		int timeInicio = Util.random(2);
		setJogadorIniciouCobrancas(timeInicio != 1);
		setJogadorChutando(timeInicio != 1);

	}

	public void chuteJogador(int ponto, int indexJogador) {
		Batedor jogador;
		if (this.isJogadorChutando()) {
			jogador = (Batedor) this.timesDaPartida[TIME_JOGADOR].getJogadores().get(indexJogador);
		} else {
			jogador = (Batedor) this.timesDaPartida[TIME_MAQUINA].getJogadores().get(indexJogador);
		}
		Time timeAdversario = retornaTimeAdversario(jogador.getTime());
		int defesaGoleiro = Util.random(5);
		pressaoTorcida(jogador, timeAdversario);
		if (isGol(jogador.direcionar(ponto), timeAdversario.getGoleiro().direcionar(defesaGoleiro), jogador.chutar(),
				timeAdversario.getGoleiro().defender(), jogador.getPerfil().getQualidade(),
				timeAdversario.getGoleiro().getPerfil().getQualidade())) {

			jogador.getTime().marcaGol();
			jogador.getTime().getTorcida().comemorar();
			atualizaTorcedores(Torcida.GOL);
		} else {
			jogador.getTime().marcaErro();
			atualizaTorcedores(Torcida.ERRO);
		}
	}

	public void defesaGoleiro(int ponto, int indexBatedor) {
		Goleiro goleiro;
		if (this.isJogadorChutando()) {
			goleiro = this.timesDaPartida[TIME_MAQUINA].getGoleiro();
		} else {
			goleiro = this.timesDaPartida[TIME_JOGADOR].getGoleiro();
		}
		Time timeAdversario = retornaTimeAdversario(goleiro.getTime());
		Ponto chuteJogador = Ponto.values()[Util.random(5)];
		Ponto defesaGoleiro = Ponto.values()[ponto];
		Batedor batedor = (Batedor) timeAdversario.getJogadores().get(indexBatedor);
		pressaoTorcida(batedor, timeAdversario);
		if (isGol(chuteJogador, defesaGoleiro, Ponto.errar(), Ponto.errar(), batedor.getPerfil().getQualidade(),
				goleiro.getPerfil().getQualidade())) {
			batedor.getTime().marcaGol();
		} else {
			batedor.getTime().marcaErro();
		}
	}

	public boolean isGol(Ponto chuteJogador, Ponto defesaGoleiro, boolean jogadorErrou, boolean goleiroErrou,
			float qualidadeJogador, float qualidadeGoleiro) {

		if (chuteJogador != defesaGoleiro) {
			return !jogadorErrou;

		} else {
			if (!goleiroErrou && !jogadorErrou) {
				if (qualidadeGoleiro > qualidadeJogador) {
					return false;
				}
				return true;

			} else if (!goleiroErrou && jogadorErrou) {
				return false;

			} else if (goleiroErrou && !jogadorErrou) {
				return true;
			} else {
				return false;
			}
		}
	}

	private void pressaoTorcida(Jogador jogador, Time timeAdversario) {
		float apoioTorcida = jogador.getTime().getTorcida().aplaudir();
		float vaiaAdversario = timeAdversario.getTorcida().vaiar();
		jogador.getPerfil().sofrePressao(vaiaAdversario, apoioTorcida);
		jogador.getPerfil().medeQualidade();
	}

	private Time retornaTimeAdversario(Time time) {
		if (time == this.timesDaPartida[TIME_JOGADOR]) {
			return this.timesDaPartida[TIME_MAQUINA];
		}
		return this.timesDaPartida[TIME_JOGADOR];
	}

	public boolean isFimJogo() {
		if (isJogadorIniciouCobrancas() ^ !isJogadorChutando()) {
			int diffGols = Math
					.abs(this.timesDaPartida[TIME_JOGADOR].getGols() - this.timesDaPartida[TIME_MAQUINA].getGols());
			if (this.timesDaPartida[TIME_JOGADOR].getCobrancas() <= NUMERO_COBRANCAS) {
				if (diffGols > (NUMERO_COBRANCAS - this.timesDaPartida[TIME_JOGADOR].getCobrancas())) {
					if (this.timesDaPartida[TIME_JOGADOR].getGols() > this.timesDaPartida[TIME_MAQUINA].getGols()) {
						JOptionPane.showMessageDialog(null, "vitoria jogador");
					} else {
						JOptionPane.showMessageDialog(null, "vitoria maquina");
					}
					return true;
				}
			} else {
				if (diffGols == 1) {
					if (this.timesDaPartida[TIME_JOGADOR].getGols() > this.timesDaPartida[TIME_MAQUINA].getGols()) {
						JOptionPane.showMessageDialog(null, "vitoria jogador");
					} else {
						JOptionPane.showMessageDialog(null, "vitoria maquina");
					}
					return true;
				}
			}

		}
		return false;

	}
}
