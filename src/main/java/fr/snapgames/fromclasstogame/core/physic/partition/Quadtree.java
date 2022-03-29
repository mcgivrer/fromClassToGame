package fr.snapgames.fromclasstogame.core.physic.partition;


public class Quadtree{

    Node rootNote;

    public class Node{
        public Node[] childs = new Node[4];
        public Entity linkedObject;

        public Node(Entity e){
            linkedObject = e;
        }
    }



    public Quadtree(){

    }

    public void addNode(Entity entity){
        if(!objects.contains(entity)){
            if(Optional.ofNullable(rootNode).isEmpty()) {
                rootNode =  new Node(entity);
            }else{
                Node n = new Node(entity);
                insertNode(n);
            }
            objects.add((GameObject)entity);
        }
    }

    private void insertNode(Node n){

    }

    public List<Entity> findNearestEntities(Entity nearNode){
        List<Entity> list = new ArrayList<>();
        return list;
    }

}