package domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;

import main.Pesa;

public class Detector16S extends Thread{
	private static int abundancia[] = new int[10];
	private int ID;
	private BufferedReader lector;
	private Sincroniza sincro;
	private boolean funciona = true;
	
	public Detector16S(PipedReader receptor, int id, Sincroniza sincro) {
		this.ID = id;
		this.lector = new BufferedReader(receptor);
		this.sincro = sincro;
		this.start();
	}

	@Override
	public void run() {
		//sincro.empezarDetectores();
		sincro.inicioDetector();
		System.out.println("Iniciado el Detector " + this.ID);
		while(funciona) {
			buscarMensaje();
			try {
				Thread.sleep((long)Math.random()*300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sincro.finDetector();
	}
	
	public String obtenerMensaje() {
		String msj = "";
		try {
			// Recibir mensaje
			msj = lector.readLine();
			// Mostrar mensaje
		} catch (IOException e) {
			e.printStackTrace();			
			}
		return msj;
	}
	
	public void buscarMensaje() {
		sincro.cogerAbundancia();
		String [][] a = Pesa.getOTUS();
		String msj = obtenerMensaje();
		for (int i = 0; i< a.length; i++) {
			if(a[i][0].equals(msj)) {
				abundancia[i] += 1;
				System.out.println("Detector " + ID + " identifica " + a[i][1] );
				sincro.soltarAbundancia();
				return;
			}
		}
		funciona = false;
		sincro.soltarAbundancia();
	}
	
	public static int [] getAbdundancia () {
		return abundancia;
	}
}
