package birds;

/**
 * This class represents a bird record in the database. Each record consists of two
 * parts: a DataKey and the data associated with the DataKey.
 */
public class BirdRecord {
    DataKey key;
    String about, sound, image;

 

    // default constructor
    public BirdRecord() {
    }

     // Other constructors
    public BirdRecord(DataKey key, String about, String sound, String image){
        this.key = key;
        this.about = about;
        this.sound = sound;
        this.image = image;
    }
    
    public String getAbout(){
        return about;
    }
    
    public String getSound(){
        return sound;
    }
    
    public String getImage(){
        return image;
    }
    


}
