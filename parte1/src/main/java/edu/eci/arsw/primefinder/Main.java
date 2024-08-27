package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;


public class Main {

	public static void main(String[] args) {
		
		PrimeFinderThread pft=new PrimeFinderThread(0, 10000000);
		PrimeFinderThread pft2 = new PrimeFinderThread(10000001, 20000000);
		PrimeFinderThread pft3 = new PrimeFinderThread(20000001, 30000000);
		
		/* 
		pft.start();
		pft2.start();
		pft3.start();
		*/

		// Para el punto 3
		int total = 0;
		

		LinkedList<PrimeFinderThread> threads= new LinkedList<>();

		threads.add(pft);
		threads.add(pft2);
		threads.add(pft3);

		for (PrimeFinderThread t : threads){
			t.start();
		}
		
		while (total < 30000000){
			try {
				Thread.sleep(5000); // Espera 5 segundos
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	
	
			for (PrimeFinderThread t : threads){
				t.pauseThread();
				total += t.getPrimes().size();
			}
	
			System.out.println("Primos encontrados hasta ahora: " + total);
	
			Scanner scanner = new Scanner(System.in);
			System.out.println("Presione ENTER para reanudar los hilos...");
			String input = scanner.nextLine();
			
			for (PrimeFinderThread t : threads){
				t.resumeThread();
			}
		}
		
	}
	
}
