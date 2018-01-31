package fichierFournis;

import java.util.ArrayList;
import java.io.*;
import java.util.*;
public class SeamCarving
{

	/* exemple de pgm trouvé sur wikipedia
	 * 
	 P2
# Affiche le mot "FEEP" (exemple de la page principale de Netpbm à propos de PGM)
24 7
15
0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0
0  3  3  3  3  0  0  7  7  7  7  0  0 11 11 11 11  0  0 15 15 15 15  0
0  3  0  0  0  0  0  7  0  0  0  0  0 11  0  0  0  0  0 15  0  0 15  0
0  3  3  3  0  0  0  7  7  7  0  0  0 11 11 11  0  0  0 15 15 15 15  0
0  3  0  0  0  0  0  7  0  0  0  0  0 11  0  0  0  0  0 15  0  0  0  0
0  3  0  0  0  0  0  7  7  7  7  0  0 11 11 11 11  0  0 15  0  0  0  0
0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0

	 * */
	
	public static void writepgm(int[][] image, String filename){
		int hauteur = image.length;
		//System.out.println(hauteur+" lignes ");
		int largeur = image[0].length;
		//System.out.println(largeur+" colonnes");
		FileWriter fw;
		try {
			fw = new FileWriter(filename);
			fw.write("P2\n");
			fw.write("#image ecrite à l'aide de la fonction writepgm\n");
			fw.write(largeur+" "+hauteur+"\n");
			fw.write("255\n");
			for (int i = 0; i<hauteur;i++){
				for(int j = 0; j<largeur; j++){
					fw.write(image[i][j]+" ");
				}
				fw.write("\n");
				
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
   public static int[][] readpgm(String fn)
	 {		
        try {
            InputStream f = ClassLoader.getSystemClassLoader().getResourceAsStream(fn);
            BufferedReader d = new BufferedReader(new InputStreamReader(f));
            String magic = d.readLine();
            String line = d.readLine();
		   while (line.startsWith("#")) {
			  line = d.readLine();
		   }
		   Scanner s = new Scanner(line);
		   int width = s.nextInt();
		   int height = s.nextInt();		   
		   line = d.readLine();
		   s = new Scanner(line);
		   int maxVal = s.nextInt();
		   int[][] im = new int[height][width];
		   s = new Scanner(d);
		   int count = 0;
		   while (count < height*width) {
			  im[count / width][count % width] = s.nextInt();
			  count++;
		   }
		   return im;
        }
		
        catch(Throwable t) {
            t.printStackTrace(System.err) ;
            return null;
        }
    }

   public static int[][] interest (int[][] image){
	   int pixelCourant, pixelPrecedent, pixelSuivant,moyPrecSuivant;
	   int[][] interet = new int[image.length][image[0].length] ;
	   //i lignes j colonnes
	   for (int i=0;i<image.length;i++){
		   for(int j=0;j<image[0].length;j++){
			   pixelCourant = image[i][j];
			   if((j-1)<0){
				   pixelSuivant = image[i][j+1];
				   interet[i][j] = Math.abs(pixelCourant-pixelSuivant);
			   }
			   else if((j+1)>image.length){
				   pixelPrecedent = image[i][j-1];
				   interet[i][j] = Math.abs(pixelCourant-pixelPrecedent);
			   }
			   else{
				   pixelSuivant = image[i][j+1];
				   pixelPrecedent = image[i][j-1];
				   moyPrecSuivant = Math.abs((pixelSuivant+pixelPrecedent)/2);
				   
				   interet[i][j] = Math.abs(pixelCourant-moyPrecSuivant);
			   }
			   //System.out.print(interet[i][j]+" ");
			   
			  
		   }
		   //System.out.println(" ");
	   }
	   return interet;
   }
   
   public static Graph tograph(int[][] itr){
	   int hauteur = itr.length;
	   int largeur = itr[0].length;
	   Graph graph = new Graph(itr.length*itr[0].length+2);
	   int n = hauteur*largeur+2;//taille du graphe
	   for(int j=0;j<itr[0].length;j++){

		   graph.addEdge(new Edge(graph.vertices()-1,j,0 ));//premiere ligne
		   //g.addEdge(new Edge(n*n+1, j, 0));
	   }
	   for(int i=0; i<hauteur-1;i++){
		   for(int j =0; j<largeur;j++){
			   graph.addEdge(new Edge(i*largeur+j,(1+i)*largeur+j, itr[i][j]));//source destination cout
			   if(j==0){
				   graph.addEdge(new Edge(i*largeur+j, (1+i)*largeur+(j+1), itr[i][j]));//arete gauche
				   
			   }else if(j==itr[0].length-1){
				   
				   graph.addEdge(new Edge(i*largeur+j, (1+i)*largeur+(j-1), itr[i][j]));//arete droite
			   }else{
				   graph.addEdge(new Edge(i*largeur+j, (1+i)*largeur+(j-1),itr[i][j] ));
				   graph.addEdge(new Edge(i*largeur+j, (1+i)*largeur+(j+1), itr[i][j]));
			   }
		   }
	   }

	   for(int j=0;j<itr[0].length;j++){
		   //System.out.println(((largeur)*(hauteur-1))+j);
		  
		   graph.addEdge(new Edge(((hauteur-1)*largeur)+j,hauteur*largeur,itr[hauteur-1][j]));  

	   }
	   
	   return graph;
	   
   }
   

   
   
   public static Heap Dijkstra(Graph g, int s, int t){
	   
	   Heap dijkstra = new Heap(g.vertices());
	   dijkstra.decreaseKey(g.vertices()-1, 0);
	   
	   return dijkstra;
	   /*int[] tab= new int[g.vertices()];
	   for(int i =0; i< tab.length; i++) {
		   tab[i] = 999999999;
	   }
	   tab[g.vertices()-1]=0;*/
	   //while
	  /* au départ : 
	   * d[u] = 0, d[v] = +infini
	   * tant qu'il reste des sommet non visité 
	   * 	on visite le sommet v qui a la plus petite valeur de d[v]. 
	   * 	on met ensuite à jour les valuer d[w] pour les voisin de v : 
	   * 	si d[v]+c(v,w)<d[w] alors on change la valuer de d[w]*/
	   //return tab;
   }

   
}
