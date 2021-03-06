package fichierFournis;

import java.util.ArrayList;

class Test
{
   static boolean visite[];
   public static void dfs(Graph g, int u)
	 {
		visite[u] = true;
		System.out.println("Je visite " + u);
		for (Edge e: g.next(u))
		  if (!visite[e.to])
			dfs(g,e.to);
	 }

   public static void testHeap()
	 {
		// Crée ue file de priorité contenant les entiers de 0 à 9, tous avec priorité +infty
		Heap h = new Heap(10);
		h.decreaseKey(3,1664);
		h.decreaseKey(4,5);
		h.decreaseKey(3,8);
		h.decreaseKey(2,3);
		// A ce moment, la priorité des différents éléments est:
		// 2 -> 3
		// 3 -> 8
		// 4 -> 5
		// tout le reste -> +infini
		int x=  h.pop();
		System.out.println("On a enlevé "+x+" de la file, dont la priorité était " + h.priority(x));
		x=  h.pop();
		System.out.println("On a enlevé "+x+" de la file, dont la priorité était " + h.priority(x));
		x=  h.pop();
		System.out.println("On a enlevé "+x+" de la file, dont la priorité était " + h.priority(x));
		// La file contient maintenant uniquement les éléments 0,1,5,6,7,8,9 avec priorité +infini
	 }
   
   public static void testGraph()
	 {
		int n = 5;
		int i,j;
		Graph g = new Graph(n*n+2);
		
		for (i = 0; i < n-1; i++)
		  for (j = 0; j < n ; j++)
			g.addEdge(new Edge(n*i+j, n*(i+1)+j, 1664 - (i+j)));

		for (j = 0; j < n ; j++)		  
		  g.addEdge(new Edge(n*(n-1)+j, n*n, 666));
		
		for (j = 0; j < n ; j++)					
		  g.addEdge(new Edge(n*n+1, j, 0));
		
		g.addEdge(new Edge(13,17,1337));
		g.writeFile("test.dot");
		// dfs à partir du sommet 3
		visite = new boolean[n*n+2];
		dfs(g, 3);
	 }
   public static void testWritepgm(){
	   SeamCarving.writepgm(SeamCarving.readpgm("test.pgm"), "test1.pgm");
	   System.out.println("test : creation test1.pgm");
   }
   
   public static void testInterest(){
	   int[][] test = new int[376][476];//{{1,2,3,4},{1,2,3,4},{1,2,3,4}};//s
	   //afficheTableau(test);
	   test = SeamCarving.interest(SeamCarving.readpgm("test.pgm"));
	   SeamCarving.writepgm(test, "Testfeeep.pgm");
	   for (int i=0; i<test.length;i++){
		   for(int j =0; j<test[0].length;j++){
			   
		   
		   System.out.print("["+test[i][j]+"]");
		   }
		   System.out.println("");
	   }
	
   }
   public static void testTographe(){
	   int[][] test = 
		   {{3,11,24,39}
		   ,{8,21,29,39}
		   ,{20,60,25,0}
		   ,{50,45,21,15}
		   ,{8,21,29,39}
		   ,{24,60,25,0}
		   ,{50,15,21,15}
		   /*,{50,4,21,15}*/
		   };//SeamCarving.interest(SeamCarving.readpgm("test.pgm"));
	   afficheTableau(test);
	   System.out.println("taille test[] : "+test.length+"\n" +
	   		"taille test[][] :"+test[0].length);
	   Graph g = SeamCarving.tographOptimise(test);
	   g.writeFile("testpgm.dot");
	   
   }
   
   
   /**
    * 
    * 
    * @param image image a traiter
    * @return
    */
   public static ArrayList<Integer> traitementColone(int[][] images) {
	   
	   ArrayList<Integer> quovadis = new ArrayList<>();
	   Graph g;
	   ArrayList<Integer> tDijkstra = new ArrayList<>();
	   int hauteur = images.length;
	   int largeur = images[0].length;
	  // System.out.println("largeur :"+largeur);
	   int[][] test = new int[hauteur][largeur];
	   int y;
	   test = SeamCarving.interest(images);
	   //afficheTableau(test);
	   g=SeamCarving.tograph(test);
	   tDijkstra = SeamCarving.dijkstra(g, g.vertices()-1, g.vertices()-2);
	   for(int i = 0; i<hauteur;i++) {
		   //test tDijkstra
		   //System.out.println("tDijkstra["+i+"] ="+tDijkstra.get(hauteur-i-1));
		   
		   for(int j = 0 ;j< largeur ; j++) {
		   	   y = i*largeur+j;
			   //System.out.println("\t i*largeur+j="+y);
		   
			   if(!(y == tDijkstra.get(hauteur-i-1))) {
				   quovadis.add(images[i][j]);
		   
			   }//fin if
		   }//fin for largeur
   
	   }//fin for hauteur
	   return quovadis;
   }
   public static int[][] arrayListToTabImage(ArrayList<Integer> image, int hauteur, int largeur){
	   
	   int[][] nouvelleImage = new int[hauteur][largeur];
	   nouvelleImage = new int[hauteur][largeur];
	   for(int i = 0; i<hauteur; i++) {
		   //System.out.println("nouvelle image i="+i);
		   for(int j = 0; j<largeur-1 ; j++) {
			  // System.out.println("nouvelle image j="+j);
			  // System.out.println(i*largeur+j);
			   //System.out.println(image.size());
			   nouvelleImage[i][j] = image.get((i*largeur+j));
		   }
	   }
	   //afficheTableau(nouvelleImage);
	   return nouvelleImage;
   }
   
   public static void testDijkstra(){
		  
	   int pixelSuppr=50;
	   
	   int[][] image = SeamCarving.readpgm("ex1.pgm");
	   int hauteur = image.length;
	   int largeur = image[0].length;
	   ArrayList<Integer> quovadis = new ArrayList<>();
	   
	   System.out.println("taille image initial (hauteur * largeur) : "+hauteur+" * "+largeur);
	   //afficheTableau(image);//affichage du tableau initial
		   
		   
	   for(int pilote = 0 ; pilote < pixelSuppr ; pilote++ ) {
		
		   quovadis = traitementColone(image);
		   image = arrayListToTabImage(quovadis, hauteur,largeur-(pilote+1));
		   
	   }//fin for pilote

	   String nom = "copieEx1.pgm";
		   
	   SeamCarving.writepgm(image, nom);//ecriture de l'image
	
	   hauteur = image.length;
	   largeur = image[0].length;
	   System.out.println("nouvelle image créée!! ");
	   System.out.println("nom : "+nom);
		 
		 
	 //copie du tableau de l'image créée
	   //afficheTableau(image);
	   System.out.println("taille image final  (hauteur * largeur) : "+hauteur+" * "+largeur);
   }
   static void testquartDeTour() {
	   
	   int[][] test = {{3,11,24,39}
	   	,{8,21,29,39}
	   	,{200,60,25,0}};
	   afficheTableau(test);
	   test = quartDeTour(test);
	   afficheTableau(test);
	   
   }
	static int[][] quartDeTour(int[][] tab){
		int [][] t= new int[tab[0].length][tab.length];
		for(int i =0; i<tab.length;i++) {
			for(int j =0 ;j<tab[0].length;j++) {
				t[j][i] = tab[i][j];
			}
		}
		return t;
	}
   
    static void afficheTableau(int[][]tab) {
	   for (int i=0; i<tab.length;i++){
		   for(int j =0; j<tab[0].length;j++){
			   
		   
		   System.out.print("["+tab[i][j]+"]");
		   }
		   System.out.println("");
	   }
   }
   public static void main(String[] args)
	 {
		/*testHeap();
		testGraph();
		testWritepgm();

		testInterest();*/
		testTographe();
	   /*testDijkstra();
	   testquartDeTour();*/

	 }
}
