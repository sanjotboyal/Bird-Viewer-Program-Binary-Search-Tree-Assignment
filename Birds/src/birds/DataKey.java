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
    public int compareTo(DataKey k) {
       if (this.birdSize == k.birdSize && this.birdName.equals(k.birdName)){
           return 0;
       }else if(this.birdSize < k.birdSize){
           return -1;
       }else if((this.birdSize == k.birdSize) && (this.birdName.charAt(0) < k.birdName.charAt(0))){
           return -1;
       }else if ((this.birdSize > k.birdSize)){
           return 1;
       }else if((this.birdSize == k.birdSize) && (this.birdName.charAt(0) > k.birdName.charAt(0))){
           return 1;
       }else{
           return -2;
       }
    }
        
}
