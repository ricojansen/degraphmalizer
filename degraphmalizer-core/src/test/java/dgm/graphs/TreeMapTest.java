/*
 * Copyright (C) 2013 All rights reserved
 * VPRO The Netherlands
 */
package dgm.graphs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import dgm.EdgeID;
import dgm.GraphUtilities;
import dgm.ID;
import dgm.Subgraph;
import dgm.trees.Pair;
import dgm.trees.Tree;
import dgm.trees.Trees;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: rico
 * Date: 13/05/2013
 */
@Test
public class TreeMapTest
{
    LocalGraph lg;
    RandomizedGraphBuilder gb;
    ObjectMapper om = new ObjectMapper();
    ExecutorService executor = Executors.newCachedThreadPool();

    @BeforeMethod
    public void clearGraph()
    {
        gb = new RandomizedGraphBuilder(0);
        lg = LocalGraph.localNode();
    }

    @AfterMethod
    public void shutdownGraph()
    {
        lg.G.shutdown();
    }

    public void testOptionalTree() throws ExecutionException, InterruptedException
    {
        final EdgeID e1 = gb.edge("   (a,b,c,1) -- label --> (a,b,d,1)   ");
        final EdgeID e2 = gb.edge("   (a,b,d,1) -- label --> (a,b,e,1)   ");
        final EdgeID e3 = gb.edge("   (a,b,d,1) -- label --> (a,b,f,1)   ");
        final EdgeID e4 = gb.edge("   (a,b,e,1) -- label --> (a,b,g,1)   ");
        final EdgeID e5 = gb.edge("   (a,b,e,1) -- label --> (a,b,h,1)   ");
        final EdgeID e6 = gb.edge("   (a,b,e,1) -- label --> (a,b,f,1)   ");

        MutableSubgraph sg = new MutableSubgraph();
        lg.sgm.commitSubgraph(e1.head(), new MutableSubgraph());
        addEdgeToSubgraph(sg, e1.tail(), e1);
        lg.sgm.commitSubgraph(e1.tail(), sg);

        sg = new MutableSubgraph();
        lg.sgm.commitSubgraph(e2.head(), new MutableSubgraph());
        lg.sgm.commitSubgraph(e3.head(), new MutableSubgraph());

        addEdgeToSubgraph(sg, e2.tail(), e2);
        addEdgeToSubgraph(sg, e3.tail(), e3);
        lg.sgm.commitSubgraph(e2.tail(), sg);

        sg = new MutableSubgraph();
        lg.sgm.commitSubgraph(e4.head(), new MutableSubgraph());
        lg.sgm.commitSubgraph(e5.head(), new MutableSubgraph());
        lg.sgm.commitSubgraph(e6.head(), new MutableSubgraph());
        addEdgeToSubgraph(sg, e4.tail(), e4);
        addEdgeToSubgraph(sg, e5.tail(), e5);
        addEdgeToSubgraph(sg, e6.tail(), e6);
        lg.sgm.commitSubgraph(e4.tail(), sg);

        ID root = e1.tail();

        Vertex rootVertex = GraphUtilities.findVertex(om, lg.G, root);
        Tree<Pair<Edge, Vertex>> tree = GraphUtilities.childrenFrom(rootVertex, Direction.OUT);
        int treeSize = countIterator(Trees.bfsWalk(tree));
        assertThat(treeSize).isEqualTo(countIterator(lg.G.getVertices()));

        System.err.println("Root " + root);
        for (Edge e : lg.G.getEdges())
        {
            final EdgeID edgeId = GraphUtilities.getEdgeID(om, e);
            if (edgeId == null)
                System.err.println(e.toString());
            else
                System.err.println(edgeId.toString());
        }

        final ID missingID = e6.head();
        final Counter visited = new Counter();
        final Function<Pair<Edge, Vertex>, Optional<Node>> pairToNode = new Function<Pair<Edge, Vertex>, Optional<Node>>()
        {
            @Override
            public Optional<Node> apply(Pair<Edge, Vertex> input)
            {
                visited.increment();
                if (GraphUtilities.getID(om, input.b).equals(missingID)) {
                    return Optional.absent();
                }
                return Optional.of(new Node(input.b, input.a));
            }
        };

        Tree<Optional<Node>> nodeTree = Trees.pmap(executor, pairToNode, tree);
        assertThat(visited.count()).isEqualTo(1);

        Iterable<Optional<Node>> nodeIterator = Trees.bfsWalk(nodeTree);
        countIterator(nodeIterator);
        assertThat(visited.count()).isEqualTo(treeSize);

        Optional<Tree<Node>> optionalTree = Trees.optional(nodeTree);
//        Iterable<Node> iterable = Trees.bfsWalk(optionalTree.get());
//        countIterator(iterable);
//        assertThat(optionalTree.isPresent()).isFalse();
    }

    class Node
    {
        Edge edge;
        Vertex vertex;

        Node(Vertex vertex, Edge edge)
        {
            this.vertex = vertex;
            this.edge = edge;
        }

        public Edge edge()
        {
            return edge;
        }

        public Vertex vertex()
        {
            return vertex;
        }
    }

    private int countIterator(Iterable<?> iterable)
    {
        int count = 0;
        for (Object o : iterable)
        {
            count++;
        }
        return count;
    }

    // add the edge to the subgraph, the other side of the edge will be made symbolic
    protected void addEdgeToSubgraph(MutableSubgraph sg, ID sg_id, EdgeID edge_id)
    {
        final Direction d = GraphUtilities.directionOppositeTo(edge_id, sg_id);
        final ID other = GraphUtilities.getSymbolicID(GraphUtilities.getOppositeId(edge_id, sg_id));
        final Subgraph.Direction dd = d.opposite().equals(Direction.IN) ? Subgraph.Direction.INWARDS : Subgraph.Direction.OUTWARDS;
        sg.beginEdge(edge_id.label(), other, dd);
    }

    class Counter
    {
        int count = 0;

        public void increment()
        {
            count++;
        }

        public int count()
        {
            return count;
        }
    }
}
