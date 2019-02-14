import java.util.Random;

import jsjf.CircularArrayQueue;


public class flyko {

	private static class flyAvtar {
		private int ankomst;
		private int nr;
		
		public flyAvtar(int ankomst, int flynr){
			this.ankomst = ankomst;
			this.nr = flynr;
		} 
		
		public int ventetid(int tid){
			return tid - ankomst;
		}
		

	}
	
	private static class flyLander {
		private int ankomst;
		private int nr;
		
		public flyLander(int ankomst, int flynr){
			this.ankomst = ankomst;
			this.nr = flynr;
		}
		public int ventetid(int tid){
			return tid - ankomst;
		}
		
		
	}
	
	public static void simulering(int tidsteg, double sansAnk, double sansAvg){
		CircularArrayQueue<flyAvtar> QAvgang = new CircularArrayQueue<flyAvtar>();
		CircularArrayQueue<flyLander> QLanding = new CircularArrayQueue<flyLander>();
		
		System.out.println("Hvor mange tidsteg skal simmuleringen gå? :" + tidsteg);
		System.out.println("Forventet ankomster pr steg               :" + sansAnk);
		System.out.println("Forventet avganger pr steg                :" + sansAvg);
		
		
		
		Random R1 = new Random();
		Random R2 = new Random();
		int flyAvt = 0;
		int flyLand = 0;
		int flyAvvist = 0;
		int totVentetid = 0;
		int flynr = 1;
		int kolengde = 0;
		float tom = 0;
		
		for (int tid = 0; tid < tidsteg; tid++){
			System.out.println("---------------------------------------------------------------------------------------");
			if (R1.nextDouble() < sansAnk){
				if (!QAvgang.isFull()){
				QAvgang.enqueue(new flyAvtar(tid, flynr));
				System.out.println("Fly " + flynr + " er klar for avgang.");
				flyAvt++;
				flynr++;
				
				}
				else {
					System.out.println("Fly avvist");
					flyAvvist++;
				
				}	
			}
			
			if (R2.nextDouble() < sansAvg){
				if (!QLanding.isFull()){
				QLanding.enqueue(new flyLander(tid, flynr));
				System.out.println("Fly " + flynr + " er klar for landing.");
				flyLand++;
				flynr++;
				}
				else {
					System.out.println("Fly avvist");
					flyAvvist++;
				}
			}
			
			if (QLanding.isEmpty() && !QAvgang.isEmpty()){
				System.out.println("Fly " + QAvgang.first().nr + " har tatt av. " + "Ventetid " + QAvgang.first().ventetid(tid));
				totVentetid += QAvgang.first().ventetid(tid);
				QAvgang.dequeue();
				flyLand++;
			}
			else if (!QLanding.isEmpty()){
				System.out.println("Fly " + QLanding.first().nr + " har landet." + "Ventetid " + QLanding.first().ventetid(tid));
				totVentetid += QLanding.first().ventetid(tid);
				QLanding.dequeue();
				flyAvt++;
			}
			else {
				System.out.println("Flyplassen tom!!!!");
				tom++;
			}
			
			kolengde += QAvgang.size() + QLanding.size();
			
		}

		
			System.out.println("\n \n \n");
			System.out.println("Totale fly ankommet: " + flynr);
			System.out.println("Total ventetid: " + totVentetid);
			System.out.println("Gjennomsnittelig ventetid: " + totVentetid / flynr);
			System.out.println("Fly avvist: " + flyAvvist);
			System.out.println("Totale fly landet: " + flyLand);
			System.out.println("Totale fly lettet: " + flyAvt);
			System.out.println("Gjennomsnittelig kølengde: " + kolengde / tidsteg);
			System.out.println("Flyplassen står tom " + tom / tidsteg * 100 + "% av tiden.");
		
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	System.out.println("Sansynlighet for fly til landing? (0-1): ");
		double sansynligLanding = Double.parseDouble(System.console().readLine());
	System.out.println("Synsynlighet for fly til avgang? (0-1): ");    
		double sansynligAvgang = Double.parseDouble(System.console().readLine());
	System.out.println("Hvor mange tidssteg?: ");
		int tid1 = Integer.parseInt(System.console().readLine());
		
		simulering(tid1, sansynligAvgang, sansynligLanding);
	}

}
