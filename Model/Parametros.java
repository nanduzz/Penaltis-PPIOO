package Model;

public final class Parametros {

	private static boolean InicioAltomatico = false;
	private static boolean IsChamaProximoFila = false;

	public static boolean isChamaProximoFila() {
		return IsChamaProximoFila;
	}

	public static void setChamaProximoFila(boolean isChamaProximoFila) {
		Parametros.IsChamaProximoFila = isChamaProximoFila;
	}

	public static boolean isInicioAltomatico() {
		return InicioAltomatico;
	}

	public static void setInicioAltomatico(boolean decideInicioAltomatico) {
		InicioAltomatico = decideInicioAltomatico;
	}
	
}
