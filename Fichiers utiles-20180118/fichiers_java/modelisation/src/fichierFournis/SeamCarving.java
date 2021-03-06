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
            System.out.println("traitement image");
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
	   int largeur ,hauteur;
	   largeur = image[0].length;
	   hauteur = image.length;
	   int[][] interet = new int[hauteur][largeur] ;
	   //i lignes j colonnes
	   for (int i=0;i<image.length;i++){
		   for(int j=0;j<image[0].length;j++){
			   pixelCourant = image[i][j];
			   if((j>0) && (j<largeur-1)){
				   pixelSuivant = image[i][j+1];
				   pixelPrecedent = image[i][j-1];
				   moyPrecSuivant = ((pixelSuivant+pixelPrecedent)/2);
				   
				   interet[i][j] = Math.abs(pixelCourant-moyPrecSuivant);
				   
				   
			   }
			   else if(j>0){
				   pixelPrecedent = image[i][j-1];
				   interet[i][j] = Math.abs(pixelCourant-pixelPrecedent);
			   }
			   else {
				   pixelSuivant = image[i][j+1];
				   interet[i][j] = Math.abs(pixelCourant-pixelSuivant);
			   }
			   //System.out.print(interet[i][j]+" ");
			   
			  
		   }
		   //System.out.println(" ");
	   }
	   return interet;
   }
   

   //fonction toGraph optimisé avec fonction d'énergie avant
   //pour la partie 1
   public static Graph tographOpti(int[][] itr) {
	   int hauteur = itr.length;
	   int largeur = itr[0].length;
	   int n = hauteur*largeur+2;//taille du graphe
	   Graph graph = new Graph(n*2);
	   int pixelSuivant,pixelPrecedent,pixelDessous;
	  	   
	   for(int i=0; i<hauteur-1;i++){
		   for(int j =0; j<largeur;j++){
			   
				   pixelDessous = itr[i+1][j];
			   
				   if(j==0) {
					   pixelPrecedent = 0;
				   }else {
					   pixelPrecedent = itr[i][j-1];
				   }//fin if j==0
				   
				   if(j==largeur-1) {
					   pixelSuivant = 0;
				   }else {
				   
					   pixelSuivant = itr[i][j+1];
				   }//fin if j==largeur-1
				   
				   
				   if(j==0) {
					   pixelSuivant = itr[i][j+1];
					   graph.addEdge(new Edge(i*largeur+j,(1+i)*largeur+j,Math.abs( pixelSuivant)));//source destination cout, i+1,j
					   //System.out.println("vers i+1 ,j="+pixelSuivant);
					   graph.addEdge(new Edge(i*largeur+j, (1+i)*largeur+(j+1),Math.abs( pixelSuivant-pixelDessous)));
					   //System.out.println("vers i+1,j+1="+Math.abs( pixelSuivant-pixelDessous));
				   }else if(j==largeur-1) {
					   pixelPrecedent = itr[i][j-1];
					   graph.addEdge(new Edge(i*largeur+j,(1+i)*largeur+j, pixelPrecedent));//i+1,j
					   graph.addEdge(new Edge(i*largeur+j, (1+i)*largeur+(j-1), Math.abs(pixelPrecedent-pixelDessous)));//i+1,j-1
					   
				   }else {
	
					   graph.addEdge(new Edge(i*largeur+j, (1+i)*largeur+(j+1), Math.abs(pixelSuivant-pixelDessous)));
					   graph.addEdge(new Edge(i*largeur+j,(1+i)*largeur+j, Math.abs(pixelSuivant-pixelPrecedent)));//i+1,j
					   graph.addEdge(new Edge(i*largeur+j, (1+i)*largeur+(j-1),Math.abs( pixelPrecedent-pixelDessous)));//i+1,j-1
				   }//fin if j==0
		
			   
		   
		   }//fin for j
	   }//fin for i
	   for(int j=0;j<itr[0].length;j++){
		   if(j==0) {
			   pixelPrecedent = 0;
		   }else {
			   pixelPrecedent = itr[hauteur-1][j-1];
		   }//fin if j==0
		   if(j==largeur-1) {
			   pixelSuivant = 0;
		   }else {
		   
			   pixelSuivant = itr[hauteur-1][j+1];
		   }//fin j =largeur-1
		   
		   //System.out.println(((largeur)*(hauteur-1))+j);
		   graph.addEdge(new Edge(graph.vertices()-1,j,0 ));//premiere ligne
		   graph.addEdge(new Edge(((hauteur-1)*largeur)+j,hauteur*largeur,Math.abs(pixelPrecedent-pixelSuivant)));  //derniere ligne

	   }
	   
	   return graph;
   }
   
    //fonction toGraph sans optimisation demandé dans la partie 1 
   //avec modification de la partie 2
   public static Graph tograph(int[][] itr){
	   int hauteur = itr.length;
	   int largeur = itr[0].length;
	   int n = hauteur*largeur+2;//taille du graphe
	   Graph graph = new Graph(n*2);

	   int i = 0;
		   
	   for(int k=0; k<hauteur*2-1;k++){
			   
		   if(k%2!=0 && i<hauteur-1) {
			   i=i+1;
			   
		   }
		
		   for(int j =0; j<largeur;j++){
			
			   if(k%2==1 ) {
				   graph.addEdge(new Edge(k*largeur+j, (1+k)*largeur+j, 0));   
				   
			   }//fin if k
				
			   else {
				   graph.addEdge(new Edge(k*largeur+j,(1+k)*largeur+j, itr[i][j]));//source destination cout
					   
				   if(j==0){
					   graph.addEdge(new Edge(k*largeur+j, (1+k)*largeur+(j+1), itr[i][j]));//arete droite
						   
				   }else if(j==itr[0].length-1){
					
					   graph.addEdge(new Edge(k*largeur+j, (1+k)*largeur+(j-1), itr[i][j]));//arete gauche
					   }else{
						 
						   graph.addEdge(new Edge(k*largeur+j, (1+k)*largeur+(j-1),itr[i][j] ));
						   graph.addEdge(new Edge(k*largeur+j, (1+k)*largeur+(j+1), itr[i][j]));
					   }//fin if j==0
				  
			   }//fin if k%2
				  
		   }//fin for j
			   
	   }//fin for i
	   for(int j=0;j<itr[0].length;j++){
		   //System.out.println(((largeur)*(hauteur-1))+j);
		   graph.addEdge(new Edge(graph.vertices()-1,j,0 ));//premiere ligne
		   graph.addEdge(new Edge(((2*hauteur-1)*largeur)+j,2*hauteur*largeur,itr[hauteur-1][j]));  

	   }
	   
	   return graph;
	   
   }
   
   //toGraph optimisé 
   public static Graph tographOptimise(int[][] itr){
	   int hauteur = itr.length;
	   int largeur = itr[0].length;
	   int n = hauteur*largeur+2;//taille du graphe
	   Graph graph = new Graph(n*2);
	   int pixelSuivant,pixelPrecedent,pixelDessous;
	   int i = 0;
		   
	   for(int k=0; k<hauteur*2-1;k++){
			   
		   if(k%2!=0 && i<hauteur-1) {
			   i=i+1;
			   
		   }
		
		   for(int j =0; j<largeur;j++){
			   if(i<hauteur-1) {
			   pixelDessous = itr[i+1][j];}
			   else {
				   pixelDessous = 0;
			   }
				if(j==0) {
				    pixelPrecedent = 0;
				}else {
				    pixelPrecedent = itr[i][j-1];
				}
				if(j==largeur-1) {
				    pixelSuivant = 0;
				}else {
					   
				    pixelSuivant = itr[i][j+1];
				}
			   if(k%2==1 ) {
				   graph.addEdge(new Edge(k*largeur+j, (1+k)*largeur+j, 0));   
				   
			   }//fin if k
				
			   else {
				   //graph.addEdge(new Edge(k*largeur+j,(1+k)*largeur+j, itr[i][j]));//source destination cout
					   
				   if(j==0){
					   pixelSuivant = itr[i][j+1];
					   graph.addEdge(new Edge(k*largeur+j, (1+k)*largeur+(j+1), Math.abs(pixelSuivant)));//arete droite
						   
				   }else if(j==itr[0].length-1){
					   pixelPrecedent = itr[i][j-1];
					   graph.addEdge(new Edge(k*largeur+j, (1+k)*largeur+j, pixelPrecedent));
					   graph.addEdge(new Edge(k*largeur+j, (1+k)*largeur+(j-1), Math.abs(pixelPrecedent-pixelDessous)));//arete gauche
					   }else{
						   graph.addEdge(new Edge(k*largeur+j,(1+k)*largeur+j,  Math.abs(pixelSuivant-pixelPrecedent)));
						   graph.addEdge(new Edge(k*largeur+j, (1+k)*largeur+(j-1),Math.abs( pixelPrecedent-pixelDessous)));
						   graph.addEdge(new Edge(k*largeur+j, (1+k)*largeur+(j+1), Math.abs(pixelSuivant-pixelDessous)) );
					   }//fin if j==0
				  
			   }//fin if k%2
				  
		   }//fin for j
			   
	   }//fin for i
	   for(int j=0;j<itr[0].length;j++){
		    if(j==0) {
				pixelPrecedent = 0;
			    }else {
				pixelPrecedent = itr[hauteur-1][j-1];
			    }
			    if(j==largeur-1) {
				pixelSuivant = 0;
			    }else {
				   
				pixelSuivant = itr[hauteur-1][j+1];
			    }
		   //System.out.println(((largeur)*(hauteur-1))+j);
		   graph.addEdge(new Edge(graph.vertices()-1,j,0 ));//premiere ligne
		   graph.addEdge(new Edge(((2*hauteur-1)*largeur)+j,2*hauteur*largeur,Math.abs(pixelPrecedent-pixelSuivant)));  

	   }
	   
	   return graph;
	   
   }

   
   
   public static ArrayList<Integer> dijkstra(Graph g, int s, int t){

	   int[] predecesseur = new int[g.vertices()];
	   ArrayList<Integer> suite = new ArrayList<>();
	   Heap tas = new Heap(g.vertices());
	   tas.decreaseKey(g.vertices()-1, 0);
	   int min, tfin;
	   while(!tas.isEmpty()){
		   
		   min = tas.pop();
		   for(Edge e : g.adj(min)){
			   if(tas.priority(e.to)>tas.priority(min)+e.cost){
				   tas.decreaseKey(e.to, tas.priority(min)+e.cost);
				   predecesseur[e.to] = min;
			   }
		   }
	   }
	   tfin = predecesseur[t];
	   while(tfin != s){
		   suite.add(tfin);
		   tfin = predecesseur[tfin];
	   }	   
	   return suite;

   }
   
   public static ArrayList<Integer> twopath(Graph g, int s, int t){
	   ArrayList<Integer> chemins = new ArrayList<>();
	   
	   
	   return chemins;
   }
   

   
}
