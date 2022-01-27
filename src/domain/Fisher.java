package domain;

import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Random;

public class Fisher extends Thread {
	private String[] cadenaGenerada = {"ATACAGATACAU","TACAGATACAUA","ACAGATACAUAT","CAGATACAUATA","AGATACAUATAC",
			"GATACAUATACA","ATACAUATACAG","TACAUATACAGA","ACAUATACAGAT","CAUATACAGATA"};
	private PrintWriter escritor;
	private Sincroniza sincro;
	private int ID;
	private int nMuestras;
	
	
	public Fisher (int nMuestras, PipedWriter emisor, int id, Sincroniza sincro) {
		this.ID = id;
		this.escritor = new PrintWriter(emisor);
		this.sincro = sincro;
		this.nMuestras = nMuestras;
		this.start();
	}
	
	@Override
	public void run() {
		sincro.esperaDetectoresInicio();
		while (nMuestras>0) {
			try {
				String msg = generarCadenaAleatoria();
				escritor.println(msg);
				System.out.println("Genera para " + ID + " la cadena " + msg);
				Thread.sleep((long)Math.random()*500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			nMuestras--;
		}
		escritor.println("end");
	}
	
	public String generarCadenaAleatoria () {
		return cadenaGenerada[new Random().nextInt(cadenaGenerada.length)];
	}

}
