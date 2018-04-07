package birds;

public class DataKey {
    private String birdName;
    private int birdSize;

    // default constructor
    public DataKey() {

    }

    public DataKey(String birdName,int birdSize){
        this.birdName = birdName;
        this.birdSize = birdSize;
    }

    public String getBirdName(){
        return birdName;
    }

    public int getBirdSize(){
        return birdSize;
    }

    // other constructors

    /**
     * Returns 0 if this DataKey is equal to k, returns -1 if this DataKey is smaller
     * than k, and it returns 1 otherwise. 
     * @param k
     * @return 
     */
    //Fix Compare 
    public int compareTo(DataKey k) {
       if (this.birdSize == k.birdSize && this.birdName.equals(k.birdName)){
           return 0;
       }else if(this.birdSize < k.birdSize){
           return -1;
       }else if((this.birdSize == k.birdSize) && (!alphabetical(this.birdName,k.birdName))){
           return -1;
       }else if ((this.birdSize > k.birdSize)){
           return 1;
       }else if((this.birdSize == k.birdSize) && (alphabetical(this.birdName,k.birdName))){
           return 1;
       }else{
           return -2;
       }
    }
    
    //Check full alphabetical arrangement
    private boolean alphabetical(String bird, String k){
        //returns false if the bird you are comparing to (k) is bigger (next in alphabetical order)
        //true otherwise 
        
        
        //if its the same
        boolean same = false;
        
        int length =0;
        //figure out the shorter word;
        if(bird.length() > k.length()){
            length = k.length();
        }else{
            length = bird.length();
        }
        
        //loop comparison till the shorter word (match each character)
        for(int i = 0; i<length; i++){
            //if the character is the same: sets same bool to true
            if(k.charAt(i) == bird.charAt(i)){
                same = true;
            }
            else if(k.charAt(i) > bird.charAt(i)){
                return false;
            }else if(k.charAt(i) < bird.charAt(i)){
                return true;
            }
        }
        //if every character is still the same: Shorter word is smaller than longer word
        if(same){
            //if bird.length is shorter word, it is smaller (returns false) 
            if(length == bird.length()){
                return false;
            }else{
                return true;
            }
        }
        return true;
    }
        
}
