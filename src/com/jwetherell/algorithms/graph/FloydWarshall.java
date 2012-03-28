package com.jwetherell.algorithms.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jwetherell.algorithms.data_structures.Graph;


/**
 * Floyd–Warshall algorithm is a graph analysis algorithm for finding shortest paths in a weighted 
 * graph (with positive or negative edge weights).
 * 
 * Worst case: O(V^3)
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class FloydWarshall {

    private FloydWarshall() { }
    
    public static Map<Graph.Vertex, Map<Graph.Vertex, Integer>> getAllPairsShortestPaths(Graph g) {
        Map<Graph.Vertex, Map<Graph.Vertex, Integer>> allShortestPaths = new HashMap<Graph.Vertex, Map<Graph.Vertex, Integer>>();

        List<Graph.Vertex> vertices = g.getVerticies();
        int[][] sums = new int[vertices.size()][vertices.size()];
        
        for (int i=0; i<sums.length; i++) {
            for (int j=0; j<sums[i].length; j++) {
                sums[i][j] = Integer.MAX_VALUE;
            }
        }
        
        List<Graph.Edge> edges = g.getEdges();
        for (Graph.Edge e : edges) {
            int indexOfFrom = vertices.indexOf(e.getFromVertex());
            int indexOfTo = vertices.indexOf(e.getToVertex());
            sums[indexOfFrom][indexOfTo] = e.getCost();
        }
        
        for (int k=0; k<vertices.size(); k++) {
            for (int i=0; i<vertices.size(); i++) {
                for (int j=0; j<vertices.size(); j++) {
                    if (i==j) {
                        sums[i][j] = 0;
                    } else {
                        int ijCost = sums[i][j];
                        int ikCost = sums[i][k];
                        int kjCost = sums[k][j];
                        int summed = (ikCost!=Integer.MAX_VALUE && kjCost!=Integer.MAX_VALUE)?(ikCost + kjCost):Integer.MAX_VALUE;
                        if (ijCost>summed) sums[i][j] = summed;
                    }
                }
            }
        }

        for (int i=0; i<sums.length; i++) {
            for (int j=0; j<sums[i].length; j++) {
                Graph.Vertex from = vertices.get(i);
                Graph.Vertex to = vertices.get(j);
                Map<Graph.Vertex, Integer> map = allShortestPaths.get(from);
                if (map==null) map = new HashMap<Graph.Vertex, Integer>();
                int cost = sums[i][j];
                if (cost!=Integer.MAX_VALUE) map.put(to, cost);
                allShortestPaths.put(from, map);
            }
        }
        
        return allShortestPaths;
    }
    
}