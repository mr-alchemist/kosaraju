
import java.util.Arrays;

import storage.FactorArray;;

public class Program {

	public Program() {
		
	}

	public static void main(String[] args) {
		Program program = new Program();
		program.run();
	}
	
	void run() {
		int[][] G = new int[][] {//вектор смежности
			{1,-1,-1},
			{2,4,5},
			{3,6,-1},
			{2,7,-1},
			{0,5,-1},
			{6,-1,-1},
			{5,-1,-1},
			{3,6,-1}
		};
		
		/*int[][] G = new int[][] {//вектор смежности
			{3,-1},
			{0,-1},
			{1,5},
			{4,-1},
			{1,-1},
			{2,-1},
			{-1,-1}
		};*/
		
		int[] component = getComponentsKosaraju(G);
		System.out.println("component:");
		for(int i = 0; i < component.length; i++) {
			System.out.println(component[i]);
		}
	}
	
	int[] getComponentsKosaraju(int[][] G) {
		int[][] H = invertEdges(G);
		
		System.out.println("G:");
		printVector(G);
		System.out.println("");
		System.out.println("H:");
		printVector(H);
		System.out.println("-----");
		
		//step2:
		FactorArray<Integer> order = walkWithDFS( H );
		
		
		//step3:
		int[] component = new int[G.length];
		Arrays.fill(component, -1);
		boolean[] visited = new boolean[G.length];
		Arrays.fill(visited, false);
		int compIndex = 0;
		for(int i = order.size() - 1 ; i >= 0 ;i--) {
			int v = order.get(i);
			if(  !visited[ v ] ) {
				FactorArray<Integer> ord = new FactorArray<Integer>();
				DFS(v, G, visited, ord);
				for(int j = 0 ; j < ord.size();j++) {
					int vc = ord.get(j);
					//System.out.print(vc + " ");
					component[vc] = compIndex;
				}
				//System.out.println("");
				compIndex++;
			}
		}
		return component;
	}
	
	@SuppressWarnings("unchecked")
	int[][] invertEdges(int[][] vector){
		
		FactorArray<Integer>[] result = new FactorArray[vector.length];
		
		for(int i = 0; i < result.length; i++)
			result[i] = new FactorArray<Integer>();
		
		for(int i = 0; i < vector.length;i++) {
			for(int j = 0; j < vector[0].length;j++) {
				int link = vector[i][j];
				if(link == -1)break;
				FactorArray<Integer> row = result[link];
				row.add(i);
			}
		}
		
		int maxS = 0;
		for(int i = 0; i < result.length ;i++) {
			FactorArray<Integer> row = result[i];
			if(row.size() > maxS)
				maxS = row.size(); 
		}
		
		int[][] res = new int[vector.length][maxS];
		for(int i = 0; i < res.length; i++) {
			FactorArray<Integer> resultRow = result[i];
			for(int j = 0; j < res[0].length ;j++) {
				if(j >= resultRow.size()) {
					res[i][j] = -1;
					continue;
				}
				res[i][j] = resultRow.get(j);
			}
		}
		return res;
		
	}
	
	FactorArray<Integer> walkWithDFS(int[][] vector) {
		
		boolean[] visited = new boolean[vector.length];
		Arrays.fill(visited, false);
		FactorArray<Integer> order = new FactorArray<Integer>();
		for(int v = 0; v < visited.length; v++) {
			if(!visited[v])
				DFS(v, vector, visited, order);
		}
		return order;
	}
	
	void DFS(int u, int[][] vector, boolean[] visited, FactorArray<Integer> order) {
		visited[u] = true;
		for(int i = 0; i < vector[0].length && vector[u][i] != -1 ; i++) {
			int w = vector[u][i];
			if( !visited[w] )
				DFS(w, vector, visited, order);
		}
		//System.out.println(u);
		order.add(u);
	}
	
	void printVector(int[][] vector) {
		for(int i = 0;i < vector.length;i++) {
			for(int j = 0; j < vector[0].length ;j++) {
				if(vector[i][j] == -1)
					System.out.print(" - ");
				else
					System.out.print(" " + vector[i][j]+" ");
				
			}
			System.out.println("");
		}
	}
	

}
