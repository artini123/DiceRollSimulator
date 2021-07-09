package pdg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pdg.components.AboutComponent;
import pdg.components.ErrorPopupComponent;
import pdg.models.LangEnum;
import pdg.utils.AppConfig;
import pdg.utils.SessionManager;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController extends BaseController {
    public final static String LEADERBOARD_VIEW = "leaderboard";
    public final static String PROFILE_VIEW = "profile";
    public final static String NEW_GAME_VIEW = "new-game";
    public final static String LOG_IN_VIEW = "login";

    private static final String VIEW_PATH = "../views";

    @FXML
    private VBox contentPane;

    @FXML
    private Label loggedInUserLabel;


    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        loggedInUserLabel.setText("@" + SessionManager.user.getUsername() + "   " + formatter.format(date));
    }

    public void loadView(String screen) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent node;
        switch (screen) {
            case LEADERBOARD_VIEW:
                loader.setLocation(getClass().getResource(viewPath(LEADERBOARD_VIEW)));
                node = loader.load();
                break;
            case PROFILE_VIEW:
                loader.setLocation(getClass().getResource(viewPath(PROFILE_VIEW)));
                node = loader.load();
                break;
            case NEW_GAME_VIEW:
                loader.setLocation(getClass().getResource(viewPath(NEW_GAME_VIEW)));
                node = loader.load();
                break;
            case LOG_IN_VIEW:
                loader.setLocation(getClass().getResource(viewPath(LOG_IN_VIEW)));
                node = loader.load();
                break;
            default:
                throw new Exception("ERR_SCREEN_NOT_FOUND");
        }

        ChildController controller = loader.getController();
        controller.setParentController(this);
        ResourceBundle langBundle = getLangBundle();
        controller.loadLangTexts(langBundle);

        contentPane.getChildren().clear();
        contentPane.getChildren().add(node);
        VBox.setVgrow(node, Priority.ALWAYS);


        switch (screen) {
            case LEADERBOARD_VIEW:
                contentPane.setAlignment(Pos.CENTER);
                break;
            case PROFILE_VIEW:
                contentPane.setAlignment(Pos.TOP_LEFT);
                break;
            case LOG_IN_VIEW:
                contentPane.setAlignment(Pos.CENTER);
                break;
            case NEW_GAME_VIEW:
                contentPane.setAlignment(Pos.TOP_CENTER);
                break;
            default:
                throw new Exception("ERR_SCREEN_NOT_FOUND");
        }
    }

    @FXML
    private void onLeaderboardNavClick(ActionEvent event) {
        try {
            this.loadView(LEADERBOARD_VIEW);
        } catch (Exception e) {

        }
    }

    @FXML
    private void onProfileNavClick(ActionEvent event) {
        try {
            this.loadView(PROFILE_VIEW);
        } catch (Exception e) {

        }
    }

    @FXML
    public void onPlayAgainClick(ActionEvent event){
        try {
            this.loadView(PROFILE_VIEW);
        } catch (Exception e) {

        }
    }

    @FXML
    private void onLogoutNavClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(viewPath("login")));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {

        }
    }

    @FXML
    private Label statusLabel;

    @FXML
    private void onLogoutMenuClick(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(viewPath("login")));
            Scene scene = new Scene(parent);

            Stage primaryStage = (Stage) statusLabel.getScene().getWindow();
            primaryStage.setScene(scene);

            SessionManager.user = null;

        } catch (Exception e) {
            ErrorPopupComponent.show(e.toString());
        }
    }

    @FXML
    private void onExitMenuClick(ActionEvent event) {
        try {
            Stage primaryStage = (Stage) statusLabel.getScene().getWindow();
            primaryStage.close();
        } catch (Exception e) {
            ErrorPopupComponent.show(e.toString());
        }
    }

    @FXML
    private void onNewGameMenuClick(ActionEvent event) {
        try {
            this.loadView(NEW_GAME_VIEW);
        } catch (Exception e) {

        }
    }


    @FXML
    private void onAboutClick(ActionEvent event) {
        try {
            new AboutComponent().showDialog();
        } catch (Exception e) {
        }
    }
    @FXML
    public void onAlMenuItemCLick(ActionEvent ev){
        enMenuItem.setSelected(false);
        alMenuItem.setSelected(true);
        updateLanguage();
    }
    @FXML
    public void onEnMenuItemCLick(ActionEvent ev){
        enMenuItem.setSelected(true);
        alMenuItem.setSelected(false);
        updateLanguage();

    }

    private void updateLanguage(){
        try {
            LangEnum lang = enMenuItem.isSelected() ? LangEnum.EN : LangEnum.AL;
            AppConfig conf = AppConfig.get();
            conf.setLanguage(lang);

            ResourceBundle bundle = getLangBundle();
            loadLangTexts(bundle);
            if(this.childController != null) {
                this.childController.loadLangTexts(bundle);
            }
        } catch (Exception ex){
            ErrorPopupComponent.show(ex.toString());
        }
    }
    private ChildController childController = null;




    @FXML
    private SplitMenuButton msLang;
    @FXML
    private Button logOutButt;
    @FXML
    private MenuItem onExitMenuClick;
    @FXML
    private Menu newGameButt;
    @FXML
    private Menu helpButt;
    @FXML
    private Label nav;
    @FXML
    private Label sectionTit;
    @FXML
    private Button msLead;
    @FXML
    private Button msProf;

    @FXML
    private CheckMenuItem enMenuItem;
    @FXML
    private CheckMenuItem alMenuItem;

    private String viewPath(String view) {
        return VIEW_PATH + "/" + view + ".fxml";
    }
    @Override
    public void loadLangTexts(ResourceBundle langBundle) {


        String msLangu = langBundle.getString("ms_lang");
        String logOutButton = langBundle.getString("log_out_button");
        String closeButton = langBundle.getString("close_button");
        String newGameButton = langBundle.getString("new_game_button");
        String helpButton = langBundle.getString("help_button");
        String navigation = langBundle.getString("navigation");
        String sectionTitle = langBundle.getString("section_title");
        String msLeaderboard = langBundle.getString("ms_leaderboard");
        String msProfile = langBundle.getString("ms_profile");






        msLang.setText(msLangu);
        logOutButt.setText(logOutButton);
        onExitMenuClick.setText(closeButton);
        newGameButt.setText(newGameButton);
        helpButt.setText(helpButton);
        nav.setText(navigation);
        sectionTit.setText(sectionTitle);
        msLead.setText(msLeaderboard);
        msProf.setText(msProfile);






        if(this.childController != null){
            this.childController.loadLangTexts(langBundle);
        }


    }
}