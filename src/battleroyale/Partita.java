package battleroyale;

import java.util.ArrayList;
import java.util.Random;

import battleroyale.Carta.TipoCarta;

public class Partita
{
	public static final int MAX_GIOCATORI = 2;
	
	public final int NUM_GG;
	public final Giocatore[] giocatori;
	
	// Il mana dei 2 o piu giocatori
	public int[] manaGiocatori = new int[MAX_GIOCATORI];
	
	// Le carte piazzate dai giocatori nel campo di battaglia
	public ArrayList<Carta>[] carteNelCampo = new ArrayList[MAX_GIOCATORI];
	
	// Quale giocatore sta giocando adesso
	public int turno = 0;
	
	public Partita(Giocatore[] giocatori)
	{
		NUM_GG = giocatori.length;
		this.giocatori = giocatori;
		
		this.turno = new Random().nextInt(NUM_GG);
		
		// Inizializza i giocatori
		for(int i = 0; i < NUM_GG; i++)
		{
			if(i == turno) manaGiocatori[i] = 1;
			else manaGiocatori[i] = 0;
			
			carteNelCampo[i] = new ArrayList<Carta>();
		}
		
		System.out.println("E' cominciata una nuova partita.");
		System.out.println("Gioca per primo " + giocatori[turno].nomeGiocatore + ", cio� il giocatore numero " + turno);
		
		riepilogoPartita();
	}
	
	public void cambiaTurno()
	{
		turno++;
		if(turno == MAX_GIOCATORI) turno = 0;

		controllaEffettiCarte();
		manaGiocatori[turno]++;
		
		notificaClient();
		System.out.println("\n-- Turno cambiato --");
		System.out.println("Tocca giocare a " + giocatori[turno].nomeGiocatore);
		
		riepilogoPartita();
	}
	
	public void controllaEffettiCarte()
	{
		for (int i = 0; i < carteNelCampo.length; i++)
		{
			for (int j = 0; j < carteNelCampo[i].size(); j++)
			{
				/*if(carteNelCampo[i].get(j).)
				{
					System.out.println("");
				}*/
				// TODO: Eseguire i effetti della carta
			}
		}
	}
	
	// Aggiungi una carta nel campo di battaglia
	public void aggiungiCarta(int id)
	{
		// Controlla che la carta non ci sia gi� nel campo da battaglia
		for (int i = 0; i < carteNelCampo[turno].size(); i++)
		{
			if(carteNelCampo[turno].get(i).Id == id)
			{
				System.out.println("La carta esiste gi�, non la puoi rimettere sul campo da battaglia");
				return;
			}
		}
		
		Carta nuovaCarta = CollezioneCarte.collezione_carte[id];
		
		if(nuovaCarta.costoMana > manaGiocatori[turno]) // Se il giocatore ha abbastanza mana
		{
			System.out.println(giocatori[turno].nomeGiocatore + " non ha abbastanza mana per aggiungere " + nuovaCarta.nome + " (costa " + nuovaCarta.costoMana + " e hai " + manaGiocatori[turno] + ")");
			return;
		}
		else
		{
			manaGiocatori[turno] -= nuovaCarta.costoMana;
			carteNelCampo[turno].add(nuovaCarta);
			
			notificaClient();
			
			System.out.println(giocatori[turno].nomeGiocatore + " (" + turno + "), ha aggiunto una nuova carta, cio� "
					+ nuovaCarta.nome);
			
			System.out.println(giocatori[turno].nomeGiocatore + " (" + turno + ") ha " + carteNelCampo[turno].size() + " "
					+ "carte nel campo da battaglia:");
			for (int i = 0; i < carteNelCampo[turno].size(); i++)
			{
				System.out.println("Carta " + i + "\n"
						+ "Nome: " + carteNelCampo[turno].get(i).nome + "\n"
						+ "Salute: " + carteNelCampo[turno].get(i).salute + "\n"
						+ "Attacco: " + carteNelCampo[turno].get(i).attacco + "\n");
			}
		}
	}
	
	// Quando il giocatore attacca una carta dell'avversario
	public void attacca(int idCartaAtt, int idCartaAvv)
	{
		int avversario = turno + 1;
		if(avversario == NUM_GG) avversario = 0;
		
		System.out.println(turno + " " + avversario);
		
		if(carteNelCampo[turno].get(idCartaAtt) != null && carteNelCampo[avversario].get(idCartaAvv) != null) // Se le carte esistono veramente
		{
			Carta att = carteNelCampo[turno].get(idCartaAtt);
			Carta avv = carteNelCampo[avversario].get(idCartaAvv);
			
			System.out.println(giocatori[turno].nomeGiocatore + " (" + att.nome + ") sta attcando " + giocatori[avversario].nomeGiocatore + " (" + avv.nome + ")");
			
			// Rimuovi la vita della carta dell'avversario
			avv.salute -= att.attacco;
			System.out.println("La carta dell'avversario ha perso " + att.attacco + " di vita");
			
			// Rimuovi la carta dell'avversario se la sua salute � <= 0
			if(avv.salute <= 0)
			{
				carteNelCampo[avversario].remove(idCartaAvv);
				System.out.println("La carta dell'avversario � stata distrutta");
			}
			else
			{
				System.out.println("Gli sono rimasti " + avv.salute + " punti di vita");
			}
			
			notificaClient();
		}
		else System.out.println("C'� qualcosa che non quadra.");
		
		riepilogoPartita();
	}
	
	public void riepilogoPartita()
	{
		String riepilogo = "\n-- Riepilogo partia --\n";
		
		//riepilogo += "Ci sono " + NUM_GG + " giocatori\n";
		
		for (int i = 0; i < NUM_GG; i++)
		{
			riepilogo += "Giocatore " + i + ", " + giocatori[i].nomeGiocatore + ":\n";
			riepilogo += "Ha " + manaGiocatori[i] + " mana\n";
			
			riepilogo += "Ha " + carteNelCampo[i].size() + " carte nel campo da battaglia:\n";
			for (int j = 0; j < carteNelCampo[i].size(); j++)
			{
				riepilogo += "Carta " + j + "\n"
						+ "Nome: " + carteNelCampo[i].get(j).nome + "\n"
						+ "Salute: " + carteNelCampo[i].get(j).salute + "\n";
			}
			
			riepilogo += "\n";
		}
		
		System.out.println(riepilogo);
	}
	
	public void notificaClient()
	{
		// TODO: Notifica i client
	}
}