package battleroyale;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Ciao");
        
        Giocatore gg1 = new Giocatore("Adrian", 50, null, null);
        Giocatore gg2 = new Giocatore("Scino", 50, null, null);
        
        Partita partita = new Partita(new Giocatore[] {gg1, gg2});
        
        BufferedReader tast = new BufferedReader(new InputStreamReader(System.in));
        char scelta;
        
        do
        {
        	try
        	{
        		System.out.print("-- Menu --\n"
        				+ "[1] Cambia turno\n"
        				+ "[2] Riepilogo\n"
        				+ "[3] Attacca\n"
        				+ "[4] Aggiungi carta nel campo da battaglia\n"
        				+ "[5] Mostra mazzo\n"
        				+ "[6] Mostra campo battaglia\n"
        				+ "\nScelta: ");
        		scelta = tast.readLine().charAt(0);
        		
        		switch(scelta)
        		{
        			case '1':
        				partita.cambiaTurno();
        				break;
        			case '2':
        				partita.riepilogoPartita();
        				break;
        			case '3':
        				if(partita.carteNelCampo[partita.turno].size() > 0)
        				{
        					partita.mostraCampoBattaglia();
        					
        					System.out.print("Scegli la tua carta (-1 per annullare attacco)"
        	        				+ "\nScelta: ");
        					scelta = tast.readLine().charAt(0);
        					
        					if(scelta == '-')
        					{
        						scelta = '3'; // Per ritornare al menu di prima
        						break;
        					}
        					else
        					{
        						int cartaAtt = Integer.parseInt("" + scelta);
        						int avversario = partita.turno + 1 == partita.NUM_GG ? 0 : partita.turno + 1;
            					
            					System.out.print("Scegli la carta da attaccare (-1 per annullare attacco)"
            	        				+ "\nScelta: ");
            					scelta = tast.readLine().charAt(0);
            					
            					if(scelta == '-')
            					{
            						scelta = '3'; // Per ritornare al menu di prima
            						break;
            					}
            					else
            					{
            						int cartaAvv = Integer.parseInt("" + scelta);
            						partita.attacca(cartaAtt, cartaAvv);
            					}
        					}
        				}
        				else
        				{
        					System.out.println("Aggiungi almeno una carta nel campo di battaglia\n");
        				}
        				break;
        			case '4':
        				if(partita.mazzo[partita.turno].size() > 0)
        				{
        					partita.mostraMazzo(partita.turno);
        					
        					System.out.print("Scegli la carta da aggiungere nel campo di battaglia\n(-1 per annullare attacco)"
        	        				+ "\n\nScelta: ");
        					scelta = tast.readLine().charAt(0);
        					
        					if(scelta == '-')
        					{
        						scelta = '3'; // Per ritornare al menu di prima
        						break;
        					}
        					else
        					{
        						int posCarta = Integer.parseInt("" + scelta);
        						partita.aggiungiCartaSulCampo(posCarta);
        					}
        				}
        				else
        				{
        					System.out.println("Non hai nessuna carta nel mazzo\n");
        				}
        				break;
        			case '5':
        				partita.mostraMazzo(partita.turno);
        				break;
        			case '6':
        				partita.mostraCampoBattaglia();
        				break;
        			default:
        				System.out.println("\nCoes ?\n");
        				break;
        		}
        	}
        	catch(Exception e)
        	{
        		System.out.println(e);
        	}
        }
        while(true);
    }
}