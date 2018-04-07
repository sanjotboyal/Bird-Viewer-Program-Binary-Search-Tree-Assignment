/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package birds;

/**
 *
 * @author sanjot
 */

//Node class
public class Node{
    private BirdRecord data;
    private Node left;
    private Node right;
    private Node parent;
    
    //Constructors 
    public Node(){
        this(null);
    }
    public Node(BirdRecord data){
        this(data,null,null,null);
    }
    //Stores BirdRecord, left, right and parent references
    public Node(BirdRecord data, Node left, Node right, Node Parent){
        this.data = data;
        this.left = left;
        this.right = right;
        this.parent = Parent;
    }
    
    public BirdRecord getData(){
        return data;
    }
    
    public Node getLeft(){
        return left;
    }
    
    public Node getRight(){
        return right;
    }
    
    public Node getParent(){
        return parent;
    }
   
    public void setData(BirdRecord newData){
        this.data = newData;
    }
    
    public void setLeft(Node left){
        this.left = left;
    }
    
    public void setRight(Node right){
        this.right = right;
    }
    
    public void setParent(Node parent){
        this.parent = parent;
    }
    
    public boolean hasLeftChild(){
        return left !=null;
    }
    
    public boolean isLeaf(){
        return (left ==null) && (right == null);
    }
    
    
    
    
    
    
   
}
