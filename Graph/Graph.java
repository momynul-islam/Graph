import java.util.*;

public class Graph {
    private class Node{
        private String value;
        public Node(String value){
            this.value = value;
        }
        @Override
        public String toString(){
            return value;
        }
    };

    private Map<String,Node> nodes = new HashMap<>();
    private Map<Node, List<Node>> adjacencyList = new HashMap<>();

    public void addNode(String value){
        var node = new Node(value);
        nodes.putIfAbsent(value,node);
        adjacencyList.putIfAbsent(node,new ArrayList<>());
    }
    public void addEdge(String source,String destination){
        var sourceNode = nodes.get(source);
        var destinationNode = nodes.get(destination);

        if(sourceNode==null || destinationNode==null) throw new IllegalArgumentException();
        adjacencyList.get(sourceNode).add(destinationNode);
    }

    public void removeNode(String value){
        var targetNode = nodes.get(value);
        if(targetNode == null) return;

        for(var node: adjacencyList.keySet())
            adjacencyList.get(node).remove(targetNode);

        adjacencyList.remove(targetNode);
        nodes.remove(targetNode);
    }
    public void removeEdge(String source,String destination){
        var sourceNode = nodes.get(source);
        var destinationNode = nodes.get(destination);

        if(sourceNode==null || destinationNode==null) return;
        adjacencyList.get(sourceNode).remove(destinationNode);
    }

    public void depthFirstTraversal(String start){
        var node = nodes.get(start);
        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();

        if(node == null) return;
        stack.add(node);

        while (!stack.isEmpty()){
            var temp = stack.pop();
            System.out.println(temp);
            visited.add(temp);
            for(var list: adjacencyList.get(temp))
                if(!visited.contains(list))
                    stack.add(list);
        }
    }
    public void breadthFirstTraversal(String start){
        var node = nodes.get(start);
        Queue<Node> queue = new ArrayDeque<>();
        Set<Node> visited = new HashSet<>();

        if(node == null) return;
        queue.add(node);

        while (!queue.isEmpty()){
            var temp = queue.remove();
            System.out.println(temp);
            visited.add(temp);
            for(var list: adjacencyList.get(temp))
                if(!visited.contains(list) && !queue.contains(list))
                    queue.add(list);
        }
    }

    public List<String> topologicalSort(){
        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();
        for(var node: nodes.values())
            topologicalSort(node,stack,visited);

        List<String> list = new ArrayList<>();
        while (!stack.isEmpty()){
            list.add(stack.pop().value);
        }
        return list;
    }
    private void topologicalSort(Node node,Stack<Node> stack,Set<Node> visited){
        if(visited.contains(node)) return;
        visited.add(node);

        for(var list: adjacencyList.get(node))
            topologicalSort(list,stack,visited);

        stack.add(node);
    }

    public void print(){
        for(var source: adjacencyList.keySet()){
            var list = adjacencyList.get(source);
            if(!list.isEmpty()) System.out.println(source + " is connected to " + list);
        }
    }
}
