package AgenceLoation;
import java.time.*;
import java.util.ArrayList;

public class AgenceLocation {
	static Boolean is_created=false;
	public class Voiture{
		   public class Reservations{
		    	int ID;
		    	Clients client;
		    	LocalDate date_reservation,date_afectation,date_retoure;
		    	double prix;
		    	String statut;
		    	 Reservations(int ID,Clients client,Voiture voiture,LocalDate date_reservation,LocalDate date_afectation ,LocalDate date_retoure,double prix,String statut){
		    		 this.ID=ID;
		    		 this.client=client;
		    		 this.date_reservation=date_reservation;
		    		 this.date_afectation=date_afectation;
		    		 this.date_retoure=date_retoure;
		    		 this.prix=prix;
		    		 this.statut=statut;
		    		 
		    	 }
		    	 void afecter() {
		    		 if (this.date_afectation==LocalDate.now()) {
		    			 this.client.Alocate(voiture, date_afectation, date_retoure);
		    		 }
		    		 /*exeption if not but I'm gonna let it simple for this prototipe (3jazt ndirha doka)*/
		    	 }
		    	
		    	
		    	
		    	
		    	
		    }
		int ID;
		String marque;
		String modele;
		double prix;
		Boolean disponible;
		ArrayList <Reservations> reseve;
		public Voiture(int ID,String modele,String marque,double prix,Boolean disponible ){
			this.ID=ID;
			this.marque=marque;
			this.modele=modele;
			this.prix=prix;
			this.disponible=disponible;
			
		}
		void seeVoiture() {
			System.out.println(ID);
			System.out.println(modele);
			System.out.println(marque);
			System.out.println(prix);
			System.out.println("disponible"+disponible);
			
		}
				
	
		
	}
	public class Clients{
		int ID,num_tell,num_permis;
		String f_name,l_name,adress;
		public class Alocation{
			Voiture voiture_alouer;
			LocalDate date_alocation,date_retoure;
			Alocation(Voiture voiture_alouer,LocalDate date_alocation,LocalDate date_retoure){
				this.voiture_alouer=voiture_alouer;
				this.date_alocation=date_alocation;
				this.date_retoure=date_retoure;}
			void seealocation() {
				this.voiture_alouer.seeVoiture();
				System.out.println("from "+this.date_alocation +" to "+this.date_retoure);
			}
			
		}
		ArrayList<Alocation> historique=new ArrayList<Alocation>();
		Clients(String f_name, String l_name,String adress,int ID,int num_tell,int num_permis ){
			this.ID=ID;
			this.adress=adress;
			this.f_name=f_name;
			this.l_name=l_name;
			this.num_permis=num_permis;
			this.num_tell=num_tell;
			/*for historique I'll update it whene I know how to fill it frome the db (it'll take some time )   :( :/ */
	
			
			
		}
		void Alocate(Voiture voiture_alouer,LocalDate date_alocation,LocalDate date_retoure){
			if (date_alocation.isBefore(date_retoure)) {
				this.historique.add(new Alocation(voiture_alouer,date_alocation,date_retoure));
				
				
			}
		}
		void seeClient() {
			System.out.println(f_name+" " +l_name);
			System.out.println("permis : "+num_permis);
			System.out.println("adress: "+adress);
			System.out.println("tellephon: "+ num_tell);
		}
		void seehistorique() {
			for (Alocation aloc :this.historique) {
				aloc.seealocation();
			}
				
			
		}
	}
 
    Voiture voiture;/*we will treat them one at the time */
    Clients client;
    
    AgenceLocation( Voiture voiture,Clients client){
    	if (!is_created) {
    	this.client=client;
    	this.voiture=voiture;
    	
    	is_created=true;
    	}
    	else {
    		throw new RuntimeException("AgenceLoation must be unique");
    		
    	}
    
    	
    }
    void addcar(int ID,String modele,String marque,double prix,Boolean disponible) {
    	this.voiture=new Voiture(ID,modele,marque,prix,disponible);
    	
    }
   
    
    
}
