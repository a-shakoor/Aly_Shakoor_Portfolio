
package cs1302.gallery;


import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.awt.event.ActionEvent;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.util.Duration;


/**
 * Class that queries iTunes API using search terms
 * Retrieves images from API response
 * and Presents in a Graphical User Interface implementation
 */
public class GalleryApp extends Application {

    Timeline timeline;
    int nextImageIndex;
    VBox rootPane;
    MenuBar menuBar;
    HBox contentBar;
    FlowPane flow;
    Button updateButton;
    TextField textField;
    Button pausePlay;
    boolean isPlay;
    Scene scene;
    HBox progressHBox;
    ProgressBar progressBar;
    int currentI;

    /**
     * Called upon application launch
     * Sets up MenuBar, ContentBar (HBox), and FlowPane
     * Calls Initial Query with search term "Will Smith"
     */
    @Override
    public void start(Stage stage) {
	rootPane = new VBox();
	rootPane.setMinSize(800, 400);
	
	addMenuBar(rootPane);
	addContentBar(rootPane);
	addProgressHBox(rootPane);

	flow = addFlowPane(rootPane);

	timeline = new Timeline();
	
        scene = new Scene(rootPane);
	stage.setMaxWidth(640);
	stage.setMaxHeight(480);
        stage.setTitle("[XYZ] Gallery!");
        stage.setScene(scene);
	// stage.sizeToScene();
        stage.show();

	newQuery("Will Smith");
    } // start

    /**
     * Adds MenuBar with a File and Help tab.
     * MenuItem File has an exit option
     * MenuItem Help has an about option
     */
    public void addMenuBar(VBox pane) {
	MenuItem exit = new MenuItem("Exit");
	exit.setOnAction(e -> System.exit(0));
       	Menu file = new Menu("File");
	file.getItems().add(exit);

	MenuItem white = new MenuItem("White");
	white.setOnAction(e -> whiteTheme());
	MenuItem peach = new MenuItem("Peach");
	peach.setOnAction(e -> peachTheme());
	Menu themes = new Menu("Themes");
	themes.getItems().addAll(white, peach);
	
	MenuItem about = new MenuItem("About");
	about.setOnAction(e -> showAbout());
	Menu help = new Menu("Help");
	help.getItems().add(about);

	
	menuBar = new MenuBar(file, themes, help);
	pane.getChildren().addAll(menuBar);
    }

    /** sets up progress bar pane. called in start() */
    public void addProgressHBox(VBox pane) {
	Text t = new Text();
	t.setText("Progress Bar");

	progressBar = new ProgressBar();
	pane.getChildren().addAll(t, progressBar);
	
    }
    
    /** Sets FlowPane MenuBar and ContentBar background to white color */
    public void whiteTheme() {
	flow.setStyle("-fx-background-color: #FFFFFF;");
	contentBar.setStyle("-fx-background-color: #FFFFFF;");
	menuBar.setStyle("-fx-background-color: #FFFFFF;");
    }

    /** Sets FlowPane MenuBar and ContentBar background to peach color */
    public void peachTheme() {
	flow.setStyle("-fx-background-color: #FFDAB9;");
	contentBar.setStyle("-fx-background-color: #FFDAB9;");
	menuBar.setStyle("-fx-background-color: #FFDAB9;");
    }

    /**
     * Puts up a dialogue window with name, email, image, version number.
     * Used in handler by the About menu option
     */
    public void showAbout() {
	Alert alert = new Alert(AlertType.INFORMATION);
	Image image = new Image("file:./image/IMG_3295.JPG");
	ImageView imageView = new ImageView(image);
	imageView.setFitWidth(100);
	imageView.setPreserveRatio(true);
	imageView.setRotate(90);
	alert.setGraphic(imageView);
	alert.setTitle("About Aly Shakoor");
	alert.setHeaderText("Aly Shakoor\r\nshakoor@uga.edu\r\nVersion: 7.3");
	alert.showAndWait();
    }

    /** 
     * Adds a content bar using an HBox object
     * Contains Play/Pause buton, Text Field, and Update Button
     */
    public void addContentBar(VBox pane) {
	contentBar = new HBox();
	pausePlay = new Button("Play");
	pausePlay.setOnAction(e -> switchPausePlay());
	textField = new TextField("Will Smith");
	updateButton = new Button("Update Query");
	updateButton.setOnAction(e -> newQuery(textField.getText()));
	contentBar.getChildren().addAll(pausePlay, textField, updateButton);
	contentBar.setSpacing(4);
	pane.getChildren().addAll(contentBar);

    }

    /** Updates button text and timeline cycle based on value of isPlay */
    public void updatePausePlay() {
	if(isPlay) {
	    timeline.play();
	    pausePlay.setText("Pause");
	}
	else {
	    timeline.pause();
	    pausePlay.setText("Play");
	}
     }

    /** Changes pause to play or vice versa */
    public void switchPausePlay() {
	if(isPlay) {
	    isPlay = false;
	    updatePausePlay();
	} else {
	    isPlay = true;
	    updatePausePlay();
	}
    }

    /** Sets up FlowPane for images. Called in start() */
    public FlowPane addFlowPane(VBox pane) {
	FlowPane flow = new FlowPane();
	flow.setVgap(8);
	flow.setHgap(4);
	flow.setPrefWrapLength(300);
	pane.getChildren().addAll(flow);
	return flow;
    }

    /** 
     * Checks String searchTerm for length >= 1
     * returns false and presents alert if empty String
     */
    public boolean checkSearchTerm(String searchTerm) {
	if(searchTerm.length() < 1) {
	    Alert alert = new Alert(AlertType.WARNING);
	    alert.setTitle("Invalid search term");
	    alert.setHeaderText("Please enter a search term");
	    alert.showAndWait();
	    return false;
	}
	return true;
    }

    /**
     * called if less than 21 images retrieved from API response
     * 1) prints all images in given ImageView[]
     * 2) presents alert informing that cycling cannot happen
     */
    public void handleIfLessThan21(ImageView[] imageViewList) {
	for(int i = 0; i < imageViewList.length; i++) {
	    flow.getChildren().add(imageViewList[i]);
	    System.out.println("added image to flowpane");
	}
	Alert alert = new Alert(AlertType.WARNING);
	alert.setTitle("Not enough images to cycle");
	alert.setHeaderText("20 or less images collected. Won't cycle");
	alert.showAndWait();
    }

    /** 
     *	Begins a new Query to the iTunes API given a search term
     *  Retrieves image URLs, processes them into an array of ImageView objects
     *  Sets up a timeline to cycle through the images with 20 on screen at a given time
     */
    public void newQuery(String searchTerm) {
	if(!checkSearchTerm(searchTerm)) {  return; }
	timeline.pause();
	timeline.getKeyFrames().clear();
	flow.getChildren().clear();
	String[] imageURLs = returnImageURLs(searchTerm);
	ImageView[] imageViewList = processImageURLs(imageURLs);
	if(imageViewList.length < 21) {
	    handleIfLessThan21(imageViewList);
	    return;
	} //if

	for(int i = 0; i < 20; i++) { 
	    flow.getChildren().add(imageViewList[i]);
	} //for loop. adds first 20 images.
	
	nextImageIndex = 20; //keeps track of what image to add next
	EventHandler handler = event -> { //This handler removes the first image displayed
	    Thread t = new Thread(() -> { //And adds one to the end from the array
		    Platform.runLater(() -> {
			    if(nextImageIndex < imageViewList.length) {
				flow.getChildren().remove(imageViewList[nextImageIndex-20]);
				flow.getChildren().add(imageViewList[nextImageIndex]);
				nextImageIndex++;
			    }
			});
	    });
	    t.setDaemon(true);
	    t.start();
	};
	updateTimeline(handler);
    }

    /**
     * updates timeline variable to reflect new handler
     * also sets isPlay to true and updates GUI accordingly
     * called from newQuery()
     */
    public void updateTimeline(EventHandler handler) {
	KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), handler);
	timeline = new Timeline(); 
	timeline.setCycleCount(Timeline.INDEFINITE);
	timeline.getKeyFrames().add(keyFrame);
	timeline.playFromStart();
	isPlay = true;
	updatePausePlay();
    }

    /** Returns array of ImageView objects given an array of image url's */
    public ImageView[] processImageURLs(String[] imageURLs) {
	ImageView[] imageViewList = new ImageView[imageURLs.length];
	for(int i = 0; i < imageURLs.length; i++) {
	    currentI = i;
	    Image image = new Image(imageURLs[i]);
	    ImageView imageView = new ImageView();
	    imageView.setImage(image);
	    imageViewList[i] = imageView;       
	    double progress = i / ((double) imageURLs.length);
	    progressBar.setProgress(progress);
	}
	return imageViewList;
    }

    /** Helper Method for returnImageUrls(String searchTerm). Encodes url for API Query */
    public static String createEncodedQueryURL(String searchTerm) throws Exception {
	String newURL = "https://itunes.apple.com/search?term=";
	String encoded = URLEncoder.encode(searchTerm, "UTF-8");
	newURL += encoded;
	return newURL;
    }

    /** 
     * Queries the iTunes API given a search term
     * Returns an array of valid image url's from the API response
     */
    public String[] returnImageURLs(String searchTerm) {
	try {
	    String newURL = createEncodedQueryURL(searchTerm);
	    URL url = new URL(newURL);
	    InputStreamReader reader = new InputStreamReader(url.openStream());
	    JsonParser jp = new JsonParser();
	    JsonElement je = jp.parse(reader);
	    JsonObject root = je.getAsJsonObject();                      // root of response
	    JsonArray results = root.getAsJsonArray("results");          // "results" array
	    int numResults = results.size(); // "results" array size
	    int nextEmptyIndex = 0;
	    String[] rawImageList = new String[numResults];
	    for (int i = 0; i < numResults; i++) {
		JsonObject result = results.get(i).getAsJsonObject();    // object i in array
		JsonElement artworkUrl100 = result.get("artworkUrl100"); // artworkUrl100 member
		if (artworkUrl100 != null) {                             // member might not exist
		    String artUrl = artworkUrl100.getAsString();        // get member as string
		    //System.out.println(artUrl);                         // print the string
		    rawImageList[nextEmptyIndex] = artUrl; //nextEmptyIndex ensures there's no gaps
		    nextEmptyIndex++;
		} // if
	    } // for
	    String[] trimmedImageList = new String[nextEmptyIndex]; //trims empty space from end of array
	    for(int i = 0; i < trimmedImageList.length; i++) { trimmedImageList[i] = rawImageList[i]; }
	    return trimmedImageList;
	}
	catch(Exception e) {
	    System.err.println("Exception occurred during API query");
	    System.err.println("Check search term.");
	    return null;
	}
    }

    /** Main method. launches applicaiton */
    public static void main(String[] args) {
	try {
	    Application.launch(args);
	} catch (UnsupportedOperationException e) {
	    System.out.println(e);
	    System.err.println("If this is a DISPLAY problem, then your X server connection");
	    System.err.println("has likely timed out. This can generally be fixed by logging");
	    System.err.println("out and logging back in.");
	    System.exit(1);
	} // try
    } // main

} // GalleryApp

