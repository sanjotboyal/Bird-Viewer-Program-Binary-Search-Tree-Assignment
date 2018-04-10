/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package birds;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Abdelkader
 */
public class BirdsController implements Initializable {

    @FXML
    private MenuBar mainMenu;
    
    //all button components
    @FXML
    private Button firstBtn,nextBtn,previousBtn,lastBtn,deleteBtn,playBtn,stopBtn,findBtn;
    
    //all division pane components
    @FXML
    private Pane controlDIV,contentDIV,mediaDIV, searchDIV;
    
    //all text components
    @FXML
    private Text birdName,birdAbout;
    
    @FXML
    private ImageView birdImage;
   
    @FXML
    private TextField nameEntry;
    
    @FXML
    private ComboBox sizeBox;
    
    //Set some global screen variables
    private  OrderedDictionary tree;
    
    private BirdRecord current,temp;
    
    private MediaPlayer mediaPlayer;
    
  
    @FXML
    public void exit() {
        Stage stage = (Stage) mainMenu.getScene().getWindow();
        stage.close();
    }
    
    //Fill Dictionary calls load with the textfile to load from
    public void fillDictionary(){
        loadDictionary("BirdsDatabase.txt");
        //starts off with the first bird and updates the screen
        try{
        current = tree.smallest();
        updateScreen(current);
        
        }catch(Exception e){
            displayError(e.toString());
        }
        //shows the screen 
        show(true);
    }
    
    //Loads text file- adds to tree
    public void loadDictionary(String fileName){
        Scanner input;
        String size,name,description;
        tree = new OrderedDictionary();
        try{
        input = new Scanner(new File(fileName));
        
        while(input.hasNext()){
            size = input.nextLine();
            name = input.nextLine();
            description = input.nextLine();
            
            DataKey key = new DataKey(name,Integer.parseInt(size));
            BirdRecord record = new BirdRecord(key,description,"src/sounds/"+name+".mp3","images/"+name+".jpg");
            
            try{
                //records added to tree
                tree.insert(record);
            }catch(DictionaryException e){
                displayError(e.toString());
            }
            
        }
        
        }catch(Exception e){
            displayError(e.toString());
        }
    }
    
    //displays the current birdRecord on screen
    public void updateScreen(BirdRecord R){
        stop();
        birdName.setText(R.getDataKey().getBirdName());
        birdAbout.setText(R.getAbout());
        
        try{
            Image bird = new Image(R.getImage());
            birdImage.setImage(bird);
        }catch(Exception e){
            displayError(e.toString());
        }
    }
    
    //initial styling of the application look
    public void setStyling(){
        firstBtn.setStyle("-fx-background-color: #ADFF2F;");
        nextBtn.setStyle("-fx-background-color: #ADFF2F;");
        previousBtn.setStyle("-fx-background-color: #ADFF2F;");
        lastBtn.setStyle("-fx-background-color: #ADFF2F;");
        deleteBtn.setStyle("-fx-background-color: #FF0000;");
        playBtn.setStyle("-fx-background-color: #008000;");
        stopBtn.setStyle("-fx-background-color: #008000;");
        stopBtn.setOpacity(0.7);
       
        findBtn.setStyle("-fx-background-color: #ADD8E6");
        mediaDIV.setStyle("-fx-border-color: black");
        
    }
    
    //Components enable/disable control
    public void show(boolean value){
        stop();
        controlDIV.setDisable(!value);
        controlDIV.setVisible(value);
        
        contentDIV.setDisable(!value);
        contentDIV.setVisible(value);
        
        mediaDIV.setDisable(!value);
        mediaDIV.setVisible(value);
        
        searchDIV.setDisable(!value);
        searchDIV.setVisible(value); 
        
        
    }
    
    //first in the tree displayed on screen
    public void first(){
        try{
            current = tree.smallest();
            updateScreen(current);
        }catch(DictionaryException e){
            displayError(e.toString());   
        }
    }
    
    //Goes to the next in tree
    public void next(){
        try{
            //sets current to next
            current = tree.successor(current.getDataKey());
            //stops and sound if it was playing
            //updates screen
            updateScreen(current);
        }catch(DictionaryException e){
            displayError(e.toString());
        }
        
    }
    
    //Goes to the previous in tree
    public void previous(){
        try{
            current = tree.predecessor(current.getDataKey());
            updateScreen(current);
        }catch(DictionaryException e){
            displayError(e.toString());
        }
    }
    
    //Goes to the last (LARGEST) in tree
    public void last(){
        try{
            current = tree.largest();
            updateScreen(current);
        }catch(DictionaryException e){
            displayError(e.toString());
        }
        
    }
    
    //Delete
    public void delete() throws DictionaryException{
        //Uses temp to find the next or previous for setting current to after delete takes place
     
        temp = null;
        try{
            BirdRecord next = tree.successor(current.getDataKey());
            temp = next;
        }catch(DictionaryException e){
            try{
                BirdRecord prev = tree.predecessor(current.getDataKey());
                temp = prev;
            }catch(DictionaryException ex){
            }
        }
        
        //Remove the element from the tree
        try{
            tree.remove(current.getDataKey());
        }catch(DictionaryException e){
            displayError(e.toString());
        }
        
        
        if(!tree.isEmpty()){
            //if there is a previous or next: set current to that
           if(temp !=null){
               current = temp;
           }
           //update screen 
           updateScreen(current);
        }else{
            //tree is empty: set show to false and display the error: no birds to show
            show(false);
            displayError("No more birds in the database to show");
        }
        
    }
    
    public void play(){
        
        playBtn.setOpacity(0.7);
        stopBtn.setOpacity(1.0);
        
        String soundFile = current.getSound();
        Media hit = new Media(new File(soundFile).toURI().toString());
        mediaPlayer  = new MediaPlayer(hit);
        mediaPlayer.play();
        
       

        
    }
    
    public void stop(){
        if(mediaPlayer!=null){
            stopBtn.setOpacity(0.7);
            playBtn.setOpacity(1.0);

            mediaPlayer.stop();
        }
    }
    
    public void find(){
        
        String searchEntry = nameEntry.getText();
        String size = sizeBox.getValue().toString().toLowerCase();
        int sizeBird = 0;
        
        switch(size){
            case "small": {
                sizeBird = 1;
                break;
            }
            
            case "medium": {
                sizeBird = 2;
                break;
            }
            
            case "large":{
                sizeBird = 3;
                break;
            }   
        }
        try{
            current = tree.find(new DataKey(searchEntry,sizeBird));
            updateScreen(current);
        }catch(DictionaryException e){
            displayError(e.toString());
        }
    }
    
    private void displayError(String errorMsg) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Alert.fxml"));
            Parent AlertError = loader.load();
            AlertController controller = (AlertController) loader.getController();

            Scene scene = new Scene(AlertError);
            Stage stage = new Stage();
            stage.setScene(scene);
            
            String errorMsgRefined = errorMsg.substring(errorMsg.indexOf(":")+1);

            stage.getIcons().add(new Image("file:src/birds/WesternLogo.png"));
            controller.setErrorMessage(errorMsgRefined);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException ex1) {

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sizeBox.getItems().addAll("Small","Medium","Large");
        sizeBox.setValue(sizeBox.getItems().get(0));
        
        setStyling();
        
        show(false); 
    }
    
    

}
