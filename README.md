# Graphs
This repo contains implementations of following algorithms:
- RB Tree
- Kruskal's algorithm
- Dijkstra’s algorithm

Consider each separately:
## RB Tree
### RBNode
```java
public class RBNode extends Node 
```
#### public methods:
```java
1.  public RBNode(Integer key, Object data, RBNode leftChild, RBNode rightChild, RBNode parent, boolean color, Boolean nil)
```

constructor
```java
2. public String toStringWithChildren()
```
returns a tree converted to a string below this node: 

![](https://github.com/ilkoch008/Graphs/blob/master/images/Screenshot_3.png?raw=true)

### RBTree
```java
public class RBTree
```
#### public methods:
```java
1. public RBNode getRoot()
```
returns root node of tree and *null* if tree is empty
```java
2. public boolean addElement(RBNode node)
```
returns *true* if element was added and *false* if tree contains node with similar key
```java
3. public boolean deleteElement(Integer key)
```
returns *true* if element with given key was deleted and *false* if element with such key doesn't exist
```java
4. public RBNode search(Integer key)
```
returns node with given key or *null* if node with given key doesn't exist
```java
5. public RBNode search(Integer key, RBNode startNode)
```
returns node with given key which is below *startNode*
```java
6. public Integer getDepth()
```
returns depth of tree
```java
7. public void print()
```
prints tree in the same way as *RBNode.toStringWithChildren()*:

![](https://github.com/ilkoch008/Graphs/blob/master/images/Screenshot_3.png?raw=true)
```java
8. public boolean testIsOK()
```
checks the tree for violations of red-black tree rules:

  * every node is red or black, the root node is black,
  * no two red nodes appear consecutively,
  * the path from the root node to all four null leaf nodes passes through two black nodes (either 2 and then 1 , or 2 and then 3) on the way to the null leaves.
```java  
9. public Integer getBlackDepth(RBNode node)
```
returns black depth of the tree

## Kruskal's algorithm & Dijkstra’s algorithm
The graph is stored as a list of edges.
```java
public class Edge implements Comparable<Edge>{
    private Node startNode;
    private Node endNode;
    private Integer mass;
    public Integer getMass();
    public void setMass(Integer mass) ;
    public Node getEndNode();
    public void setEndNode(Node endNode);
    public Node getStartNode();
    public void setStartNode(Node startNode);
}
    
public class Node {
    private Integer key;
    private Object data;
    public int wayWeight = Integer.MAX_VALUE;
    public int[] way;
    public int wayLength;
    public void setData(Object data)
    public Object getData()
    public void setKey(Integer key)
    public Integer getKey()
}
```
### Graph
```java
public class Graph
```

#### public methods

```java
1. public void init()
```

Creates local arrays. Must be called if you don't set them by hands:

```java
    public ArrayList<Node> getNodes();
    public void setNodes(ArrayList<Node> nodes);
    public ArrayList<Edge> getEdges();
    public void setEdges(ArrayList<Edge> edges);
```

```java
2. public Node getNode(Integer key);
```

returns node by given key

```java
3. public boolean isInitialized();
```

returns true if local arrays were initialized or set and false if they are nulls

```java
4. 
    public boolean addEdge(Edge edge);
    public boolean addNode(Node node);
    public boolean removeEdge(Edge edge);
    public boolean removeNode(Node node);
    public boolean removeNode(Integer key);
```
returns true if action was done and false if not

```java
5.     
    public ArrayList<Edge> getEdgesFromNode(Node node)
    public ArrayList<Edge> getEdgesFromNode(Integer key)
```

returns edges coming out of the node

```java
6.
    public ArrayList<Edge> getEdgesToNode(Node node)
    public ArrayList<Edge> getEdgesToNode(Integer key)
```

returns edges incoming in the node

```java
7. public ArrayList<Edge> getEdges(Integer key);
```

returns all edges connected to the node

```java
8. public Graph getMinSpanningTree();
```
returns minimal spanning tree using **Kruskal's algorithm**

```java
9. public boolean equalsUndirected(Graph graph);
```

compares undirected graph with given. Returns true if keys in their nodes and edges connecting nodes are equal

```java
10. public void printEdgesUndirected();
```

prints edges of graph without direction

```java
11. public void findMinWays(Node rootNode);
```

finds shortest ways using **Dijkstra’s algorithm**. Results are put in nodes

```java
12. public ArrayList<Node> getNeighbours(Node node);
```

returns neighbours ov given node

```java
13. public void printWays();
```
allows to print found ways after calling findMinWays(Node rootNode)

#### misc for testing:
```java
    1. public Graph readAnswerForKraskalFrom(String str);
```

builds graf using following format:
```
numberOfNodes
key11 key12 massOfEdge1
key21 key22 massOfEdge2
 ...   ...     ...
```

```java
    2. public ArrayList<Node> readAnswerForDijkstraFrom(String str);
```
builds nodes using following format:
```
key1 weightOfWay1
key2 weightOfWay2
...      ...
```
