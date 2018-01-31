package fichierFournis;

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
	   SeamCarving.writepgm(SeamCarving.readpgm("ex1.pgm"), "test1.pgm");
	   System.out.println("test : creation test1.pgm");
   }
   
   public static void testInterest(){
	   int[][] test;// = new int[376][476];
	   test = SeamCarving.interest(SeamCarving.readpgm("ex1.pgm"));
	   SeamCarving.writepgm(test, "Testinterest.pgm");
	
   }
   public static void testTographe(){
	   int[][] test = SeamCarving.interest(SeamCarving.readpgm("test.pgm"));
	   System.out.println("taille test[] : "+test.length+"\n" +
	   		"taille test[][] :"+test[0].length);
	   Graph g = SeamCarving.tograph(test);
	   g.writeFile("testpgm.dot");
	   
   }
   public static void main(String[] args)
	 {
		testHeap();
		testGraph();
		//testWritepgm();
	   //testInterest();
		testTographe();
	 }
}
