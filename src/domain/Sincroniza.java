package domain;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Sincroniza {
	private CountDownLatch esperaDetectoresInicio;
	private CountDownLatch esperaDetectoresFin;
	private CyclicBarrier inicioDetectores;
	private Semaphore accesoAbundancia = new Semaphore (1);
	
	public Sincroniza (int n) {
		esperaDetectoresInicio = new CountDownLatch(n);
		esperaDetectoresFin = new CountDownLatch(n);
		inicioDetectores = new CyclicBarrier(n);
	}
	
	public void empezarDetectores () {
		try {
			this.inicioDetectores.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void esperaDetectoresFin() {
		try {
			this.esperaDetectoresFin.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void finDetector() {
		this.esperaDetectoresFin.countDown();
	}
	
	public void esperaDetectoresInicio() {
		try {
			this.esperaDetectoresInicio.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void inicioDetector() {
		this.esperaDetectoresInicio.countDown();
	}
	
	public void cogerAbundancia() {
		try {
			this.accesoAbundancia.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void soltarAbundancia() {
		this.accesoAbundancia.release();
	}
}
