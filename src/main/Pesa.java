package main;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

import domain.Detector16S;
import domain.Fisher;
import domain.Sincroniza;

public class Pesa {
	public static final int maxDetectores = 3;
	public static final int maxSimula= 100;
	
	public static String OTUS[][] = {{"ATACAGATACAU","Collinsellan"},{"TACAGATACAUA","Flavonifractor"},
			{"ACAGATACAUAT","Roseburia"},{"CAGATACAUATA","Megamonas"},{"AGATACAUATAC","Atopobium"},
			{"GATACAUATACA","Cloacibacillus"},{"ATACAUATACAG","Rothiamus"},{"TACAUATACAGA","Clostridium"},
			{"ACAUATACAGAT","Turicibacter"},{"CAUATACAGATA","Rhodobacter"}};
	
	public static void main(String[] args) {
		Sincroniza sincro = new Sincroniza(maxDetectores);
		Fisher[] tests = new Fisher [maxDetectores];
		Detector16S [] analisis = new Detector16S [maxDetectores];
		PipedWriter [] emisores = new PipedWriter  [maxDetectores];
		PipedReader[] receptores= new PipedReader[maxDetectores];
		
		for (int i = 0; i < maxDetectores ; i++) {
			try {
				emisores[i] = new PipedWriter ();
				receptores [i] = new PipedReader (emisores[i]);
				tests[i] = new Fisher(maxSimula * maxDetectores,emisores[i],i, sincro);
				analisis [i] = new Detector16S(receptores[i],i, sincro);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		System.out.println("Iniciando el proceso de Secuenciación Masiva");
		sincro.esperaDetectoresFin();
		System.out.println(generarInforme());
	}
	
	private static String generarInforme() {
		String informe = "\n";
		int[] a = Detector16S.getAbdundancia();
		int b = 0;
		for (int i = 0; i< 10; i++) {
			informe+=OTUS[i][1]+ "  ";
			informe += barra(a[i]) +"  ";
			informe+= a[i];
			informe += "\n";
			b+=a[i];
		}
		return informe +"\nNúmero total de muestras en los resultados: "+b;
	}

	private static String barra(int cant) {
		String longitud = "*";
		for (int i=0;i < (cant/2);i++) {
			longitud = longitud +"*";
		}
		return longitud;
	}
	
	public static String [][] getOTUS () {
		return OTUS;
	}
}
