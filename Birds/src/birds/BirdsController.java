/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package birds;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

/**
 *
 * @author Abdelkader
 */
public class BirdsController implements Initializable {

    @FXML
    private MenuBar mainMenu;
    

    @FXML
    public void exit() {
        Stage stage = (Stage) mainMenu.getScene().getWindow();
        stage.close();
    }
    
    public void fillDictionary(){
        
        loadDictionary("BirdsDatabase.txt");
    }
    
    //Add to Ordered Dictionary 
    public void loadDictionary(String fileName){
        Scanner input;
        String size,name,description;
        try{
        input = new Scanner(new File(fileName));
        
        while(input.hasNext()){
            size = input.nextLine();
            name = input.nextLine();
            description = input.nextLine();
            
            DataKey key = new DataKey(name,Integer.parseInt(size));
            BirdRecord record = new BirdRecord(key,description,"/src/sound/"+name+".jpg","/src/images/"+name+".mp3");
            System.out.println("LOL: " + record.getImage());
        }
        
        }catch(Exception e){
            System.out.println("Failed find: " + e.toString());
        }
        
        
   
    
    }
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

}
