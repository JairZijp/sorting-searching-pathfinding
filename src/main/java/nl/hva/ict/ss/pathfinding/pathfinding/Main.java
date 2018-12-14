package nl.hva.ict.ss.pathfinding.pathfinding;

import nl.hva.ict.ss.pathfinding.tileworld.TileWorldUtil;
import nl.hva.ict.ss.pathfinding.weigthedgraph.EdgeWeightedDigraph;

/**
 * TODO make sure your code is compliant with the HBO-ICT coding conventions!
 * @author Simon & Jaïr
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Make sure that it is writeable and we know where to look for it
        TileWorldUtil.outputDir = "C:/Users/jairz/Downloads/output/";
        
	System.out.printf("ID;\tN-Dij;\tL-Dij;\tC-Dij;\tN-Flo\tL-Flo\tC-Flo\n");
	for (int i = 1; i <= 24; i++) {
            // Read the graph directly from a image
            EdgeWeightedDigraph graafDijkstra = new EdgeWeightedDigraph("i" + i);
            // Get the start and end node
            final int start = graafDijkstra.getStart();
            final int finish = graafDijkstra.getEnd();

            // Run dijkstra
            final Dijkstra dijkstra = new Dijkstra(graafDijkstra, start);
            if (dijkstra.hasPathTo(finish)) {
                // Show the result
		graafDijkstra.tekenPad(dijkstra.pathTo(finish));
		// Save it
		graafDijkstra.save("i" + i + "-dijkstra");
            }

            // Run Floyd-Warshall
            EdgeWeightedDigraph graafFloyd = new EdgeWeightedDigraph("i" + i);
            FloydWarshall floyd = new FloydWarshall(graafFloyd.createAdjMatrixEdgeWeightedDigraph());
            if (floyd.hasPath(start, finish)) {
                // Show the result
                graafFloyd.tekenPad(floyd.path(start, finish));
		// Save it
                graafFloyd.save("i" + i + "-floyd");
            }
            if (dijkstra.hasPathTo(finish)) {
                System.out.printf("i%d;\t%d;\t%d;\t%1.0f;\t\t%d;\t%d;\t%1.0f;\n",
                        i,
                        dijkstra.edgeCount(),
                        length(dijkstra.pathTo(finish)),
                        dijkstra.distTo(finish),
                        floyd.edgeCount(),
                        length(floyd.path(start, finish)),
                        floyd.dist(start, finish)
                );
            } else {
                System.out.printf("i%d;-;-;-;-\n", i);
            }
	}
    }
    
    private static <T> int length(Iterable<T> iterable) {
        int length = 0;
	for (T notNeeded : iterable) {
            length++;
        }
        return length;
    }
}
