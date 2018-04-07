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
public class OrderedDictionary implements OrderedDictionaryADT {
    Node root;
    Node past;
    
    OrderedDictionary(){
        root = null;
    }

    @Override
    public BirdRecord find(DataKey k) throws DictionaryException {
        Node x = findNode(root,k);
        
        if(x!=null){
            return x.getData();
        } else{
            throw new DictionaryException("There is no record matches the given key");
        }
    }
    
    public Node findNode(Node root,DataKey k){
        Node current = this.root;
        
        while(current != null && current.getData().getDataKey().compareTo(k) != 0)
        {
            if(current.getData().getDataKey().compareTo(k) == 1)
            {
                current = current.getLeft();
               
            }else{
                current = current.getRight();
               
            }
        }
            if(current != null){
                return current;
            }
            else{
                return null;
            }
    }
   
   

    @Override
    public void insert(BirdRecord r) throws DictionaryException {
        Node checkNode = findNode(root,r.getDataKey());
        
        if(checkNode == null){
            root = insertRec(root,r);
        }else{
            throw new DictionaryException("Record with the same name already exists");
        }
       
    }
    private Node insertRec(Node node, BirdRecord r){
        if(node == null){
            node = new Node(new BirdRecord(r.getDataKey(),r.getAbout(),r.getSound(),r.getImage()));
            return node;
        }else{
            Node temp = null;
            
            if (r.getDataKey().compareTo(node.getData().getDataKey())== -1){
                temp = insertRec(node.getLeft(),r);
                node.setLeft(temp);
                temp.setParent(node);
            }else{
                temp = insertRec(node.getRight(),r);
                node.setRight(temp);
                temp.setParent(node);
            }
            return node;
        }
    }

        
    @Override
    public void remove(DataKey k) throws DictionaryException
    {
        Node deleteThis = findNode(root,k);
        
        if (deleteThis==null){
            throw new DictionaryException ("No such record key exists");
        }
        
        deleteRec(deleteThis);
      
    }
    
    private void deleteRec(Node node){
         // Case 1: node does not have a child, just delete it
        if (node.getLeft() == null && node.getRight() == null)
        {
            if (node.getParent() != null && node.getParent().getLeft() == node)
                node.getParent().setLeft(null);
            else if (node.getParent() != null && node.getParent().getRight() == node)
                node.getParent().setRight(null);     

            else{
                node = null;
                root = null;
            }
        }
        //else if its just no parent: just root -> set all to null

        // Case 2: node has only one child, splice the child with its parent
        else if (node.getLeft() == null || node.getRight() == null)
        {
            if (node.getParent() != null)
            {
                Node x;
                if(node.getLeft() == null){
                    x = node.getRight();
                }else{
                    x = node.getLeft();
                }

                x.setParent(node.getParent());

                if (node.getParent().getLeft() == node)
                    node.getParent().setLeft(x);
                else
                    node.getParent().setRight(x);
            }else{

                Node x;
                if(node.getLeft() == null){
                    x = node.getRight();
                }else{
                    x = node.getLeft();
                }
                node = null;
                x.setParent(null);

                root = x;


            }         
        }
    // Case 3: node has both children, set the successor of the node to its parent
    else
        {
            
            Node x = successorHelper(node);
            
            node.setData(x.getData());
            
            if(x.getLeft() == null && x.getRight() == null){
                if (x.getParent() != null && x.getParent().getLeft() == x)
                    x.getParent().setLeft(null);
                else if (x.getParent() != null && x.getParent().getRight() == x)
                    x.getParent().setRight(null);    
            }
            
            else if(x.getParent()!=null){
                Node y;
                
                y = x.getRight();
                
                y.setParent(x.getParent());
                
                if(x.getParent().getLeft() == x){
                    x.getParent().setLeft(y);
                }else{
                    x.getParent().setRight(y);
                }   
            }
        }     
    }

    @Override
    public BirdRecord successor(DataKey k) throws DictionaryException{
        Node birdKey = findNode(root,k);
             
        if(birdKey != null){
            
            Node success = successorHelper(birdKey);
           
            
            if(success != null){
                return success.getData();
            }else{
                throw new DictionaryException ("There is no successor for the given record key");
            }
        }else{
            //only runs for tests (if data key isnt there: still gets successor)
            BirdRecord temp = new BirdRecord(k,null,null,null);
            insert(temp);
            Node tempSuccess = successorHelper(findNode(root,temp.getDataKey()));
            remove(temp.getDataKey());
            return tempSuccess.getData();
        }
    }
    
    private Node successorHelper(Node node){
        
        if (node == null){
            return null;
        }
        if(node.getRight()!=null){
           
            return smallestRec(node.getRight());
        }
        
        Node b = node.getParent();
        Node a = node;
        while(b!= null && a == b.getRight()){
            a = b;
            b = b.getParent();
        }
       
        return b;
        
    }
    
    

    @Override
    public BirdRecord predecessor(DataKey k) throws DictionaryException{
       Node birdKey = findNode(root,k);
             
        if(birdKey != null){
            
            Node pre = preHelper(birdKey);
           
            
            if(pre != null){
                return pre.getData();
            }else{
                throw new DictionaryException("There is no predecessor for the given record key");
            }
        }else{
            //only runs for tests (if data key isnt there: still gets predeccessor)
            BirdRecord temps = new BirdRecord(k,null,null,null);
            insert(temps);
            Node tempPrevious = preHelper(findNode(root,temps.getDataKey()));
            System.out.println(tempPrevious.getData().getDataKey().getBirdName());
            remove(temps.getDataKey());
            return tempPrevious.getData();
        }
    }
    
    private Node preHelper(Node node){
        if(node == null){   
            return null;
        }
        if(node.getLeft() !=null){
            return largestRec(node.getLeft());
        }
        
        Node parent = node.getParent();
        Node b = parent;
        Node a = node;
        
        while(b!=null && a == b.getLeft()){
            a = b;
            b = b.getParent();
        }
        
       
        
        return b;
        
    }
    
    @Override
    public BirdRecord smallest() throws DictionaryException {
        Node x = root;
        
        if(root!= null){
            while(x.getLeft()!=null){
                x = x.getLeft();
            }
            return x.getData();
        }else{
            throw new DictionaryException("Dictionary is empty");
        }
    }
    
    private Node smallestRec(Node root){
        if(root.getLeft() == null){
            return root;
        }
        return smallestRec(root.getLeft());
    }

    @Override
    public BirdRecord largest() throws DictionaryException {
        Node x = root;
        
        if(root != null){   
            while(x.getRight()!=null){
                x = x.getRight();
            }
            return x.getData();
        }else{
            throw new DictionaryException("Dictionary is empty");
        }
    }
    
    private Node largestRec(Node root){
        if(root.getRight() == null){
            return root;
        }
        return largestRec(root.getRight());
    }

    @Override
    public boolean isEmpty() {
        if(root==null){
            return true;
        }else{
            return false;
        }
    }    
}
