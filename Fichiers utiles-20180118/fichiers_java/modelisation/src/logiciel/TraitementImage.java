/**
 * 
 */
package logiciel;

import java.util.ArrayList;

import fichierFournis.Graph;
import fichierFournis.SeamCarving;

/**
 * @author damien
 *
 */
public class TraitementImage {
	
	public TraitementImage(String image, int pixelSuppr) {
		
		String nom = "copie_"+image; //nom de la nouvelle image
		
		int hauteur,largeur;
		//System.out.println("traitement image");
		int[][] imageModifiée = SeamCarving.readpgm(image); //copie de l'image 
		//imageModifiée = quartDeTour(imageModifiée);
		hauteur = imageModifiée.length;
		largeur = imageModifiée[0].length;
		System.out.println("taille image initial (hauteur * largeur) : "+hauteur+" * "+largeur);
		System.out.println("nombre de pixel à enlever :"+pixelSuppr+" pixels par ligne");
		ArrayList<Integer> colonne = new ArrayList<>(); 
		
		
		/*traitement de l'image colonne par colonne*/
		for(int i = 0; i<pixelSuppr; i++) {
			System.out.println("colonne suppr : "+i);
			colonne = traitementColonne(imageModifiée);
			imageModifiée = arrayListToTabImage(colonne, hauteur, largeur-(i+1));
		}//fin for
		//imageModifiée = quartDeTour(imageModifiée);
		
		/******************/
		/*ligne*/
		/*******************/
		
		imageModifiée = quartDeTour(imageModifiée);
		hauteur = imageModifiée.length;
		largeur = imageModifiée[0].length;
		System.out.println("taille image initial (hauteur * largeur) : "+hauteur+" * "+largeur);
		System.out.println("nombre de pixel à enlever :"+pixelSuppr+" pixels par colonnes");
		//colonne = new ArrayList<>(); 
		
		/*traitement de l'image colonne par colonne*/
		
		for(int i = 0; i<pixelSuppr; i++) {
			
			colonne = traitementColonne(imageModifiée);
			imageModifiée = arrayListToTabImage(colonne, hauteur, largeur-(i+1));
		}//fin for
		imageModifiée = quartDeTour(imageModifiée);
		
		/******************/
		/*fin ligne*/
		/*******************/
		SeamCarving.writepgm(imageModifiée, nom);
		System.out.println(nom+" créé");
		hauteur = imageModifiée.length;
		largeur = imageModifiée[0].length;
		System.out.println("taille de "+nom+" (hauteur * largeur) : "+hauteur+" * "+largeur);
	}
	

	
	private int[][] quartDeTour(int[][] tab){
		int [][] t= new int[tab[0].length][tab.length];
		for(int i =0; i<tab.length;i++) {
			for(int j =0 ;j<tab[0].length;j++) {
				t[j][i] = tab[i][j];
			}
		}
		return t;
	}
	/*
	  private ArrayList<Integer> traitementLigne(int[][] image) {
		   
		   ArrayList<Integer> quovadis = new ArrayList<>();
		   Graph g;
		   ArrayList<Integer> tDijkstra = new ArrayList<>();
		   int [][]images = quartDeTour(image);
		   int hauteur = images.length;
		   int largeur = images[0].length;
		   int[][] test = new int[largeur][hauteur];
		   int y;
		   
		   test = SeamCarving.interest(images);
		   g=SeamCarving.tograph(test);
		   tDijkstra = SeamCarving.dijkstra(g, g.vertices()-1, g.vertices()-2);
		   
		   for(int i = 0; i<hauteur;i++) {
			   
			   for(int j = 0 ;j< largeur ; j++) {
			   	   y = i*largeur+j;
			   	   
				   if(!(y == tDijkstra.get(hauteur-i-1))) {
					   quovadis.add(images[i][j]);
					   }//fin if
			   }//fin for largeur
		   }//fin for hauteur
		   return quovadis;
	   }
	
	   
	*/
	  private ArrayList<Integer> traitementColonne(int[][] images) {
		   
		   ArrayList<Integer> quovadis = new ArrayList<>();
		   Graph g;
		   ArrayList<Integer> tDijkstra = new ArrayList<>();
		   int hauteur = images.length;
		   int largeur = images[0].length;
		   int[][] test = new int[hauteur][largeur];
		   int y;
		   test = SeamCarving.interest(images);
		   g=SeamCarving.tograph(test);
		   tDijkstra = SeamCarving.dijkstra(g, g.vertices()-1, g.vertices()-2);
		   
		   for(int i = 0; i<hauteur;i++) {
			   
			   for(int j = 0 ;j< largeur ; j++) {
			   	   y = i*largeur+j;
			   	   
				   if(!(y == tDijkstra.get(hauteur-i-1))) {
					   quovadis.add(images[i][j]);
					   }//fin if
			   }//fin for largeur
		   }//fin for hauteur
		   return quovadis;
	   }
	  
	  private int[][] arrayListToTabImage(ArrayList<Integer> image, int hauteur, int largeur){
		   
		   int[][] nouvelleImage = new int[hauteur][largeur];
		   nouvelleImage = new int[hauteur][largeur];
		   for(int i = 0; i<hauteur; i++) {
			   
			   for(int j = 0; j<largeur-1 ; j++) {	  
				   nouvelleImage[i][j] = image.get((i*largeur+j));
			   }//fin for j
		   }//fin for i
		   return nouvelleImage;
	   }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int nbPixelSuppr = 50;
		 if (args.length != 1) {
			 if(args.length == 2) {
				 nbPixelSuppr = Integer.parseInt(args[1]);
				 
			 }
			 else {
	            System.err.println("Nombre incorrect d'arguments") ;
	            System.err.println("\tjava -jar traitementImage.jar <fichierImage.pgm>") ;
	            
	            System.exit(1) ;
	        }
		 }
		 
		new TraitementImage(args[0],nbPixelSuppr);

	}

}
