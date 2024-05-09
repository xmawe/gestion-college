package application.controllers;

import java.util.Map;

import application.controllers.AdminstrateurBackofficeSceneSubController.AccueilPaneController;
import application.controllers.AdminstrateurBackofficeSceneSubController.AffectationPaneController;
import application.controllers.AdminstrateurBackofficeSceneSubController.ClassesPaneController;
import application.controllers.AdminstrateurBackofficeSceneSubController.ParametresPaneController;
import application.controllers.AdminstrateurBackofficeSceneSubController.SallesPaneController;
import application.controllers.AdminstrateurBackofficeSceneSubController.SidebarController;
import application.model.Classe;
import application.model.Enseignant;
import application.model.Horaires;
import application.model.Salle;
import application.model.TypeCours;
import application.model.enums.JoursSemaine;
import application.services.ClasseService;
import application.services.SallesService;
import application.utilities.ButtonClickHandler;
import application.utilities.CustomClasseCellButton;
import application.utilities.CustomSalleCellButton;
import application.utilities.ET0810;
import application.utilities.ET1012;
import application.utilities.ET1416;
import application.utilities.ET1618;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class AdminstrateurBackofficeSceneController {

    // sideBar buttons
    @FXML
    private Button accueilButton, classesButton, deconnecterButton, sallesButton, parametresButton, affectationButton,
            annulerButtonParametres;

    // application Panes
    @FXML
    private Pane affectationPane, accueilPane, classesPane, sallesPane, parametresPane, rootPane, activeClassePane,
            activeSallePane;

    // parameters Pane buttons
    @FXML
    private Button sauvegarderButtonInfosParametres, sauvegarderButtonSecurityParametres;

    // affectation Pane buttons
    @FXML
    private Button handleAffecterButton, handleAnnulerAffectation;

    // affectation Pane comboBoxes
    @FXML
    private ComboBox<Salle> sallesComboAffectation;
    @FXML
    private ComboBox<Horaires> horairesComboAffectation;
    @FXML
    private ComboBox<JoursSemaine> joursComboAffectation;
    @FXML
    private ComboBox<Enseignant> enseignantComboAffectation;
    @FXML
    private ComboBox<TypeCours> typeCoursComboAffectation;

    // application Tableviews
    @FXML
    private TableView<Map<String, String>> coursEncoursTableView, classesTableView, sallesTableView,
            classeEmploiTableView,salleEmploiTableView ;

    @FXML
    private TableColumn<Map<String, String>, String> coursEncoursSalleColumn, coursEncoursHorairesColumn,
            coursEncoursClasseColumn, coursEncoursCourNomColumn, coursEncoursEffectifColumn, classesClasseColumn,
            classesEffectifColumn, classesStatusColumn, classesSalleColumn, classesCoursColumn, classesActionColumn,
            sallesColumn, sallesCapaciteColumn, sallesStatutColumn, sallesCoursColumn, sallesClassesColumn,
            sallesActionColumn;
    @FXML
    private TableColumn<Map<String, String>, Map<String, String>> coursEncoursProfesseurColumn, classesProfesseurColumn;

    @FXML
    private TableColumn<Map<String, String>, Map<String, String>> classeEmploi8_10Column,
            classeEmploi10_12Column, classeEmploi14_16Column, classeEmploi16_18Column;

     @FXML
    private TableColumn<Map<String, String>, Map<String, String>> salleEmploi8_10Column,
            salleEmploi10_12Column, salleEmploi14_16Column, salleEmploi16_18Column;
    @FXML
    private TableColumn<Map<String, String>, String> classeEmploiJourColumn;
    @FXML
    private TableColumn<Map<String, String>, String> salleEmploiJourColumn;

    @FXML
    private ComboBox<Classe> classesComboAffectation;

    @FXML
    private TextField nomFieldParametres, prenomFieldParametres, telephoneFieldParametres, emailFieldParametres,
            oldPasswordParametres,
            newPasswordParametres, nomCoursFieldAffectation;

    @FXML
    private DatePicker dateBirthDatePickerParametres;

    @FXML
    private Label errorPasswordUpdate;

    private int currentAdminId;

    @FXML
    private Text SallesDisponibles, coursEnCours, effectifEnCours, activeClasseLabel, activeSalleLabel, tempText,
            tempText1;

    @FXML
    private Label nombreLaboratoiresDisponibles, nombreSalleCoursDisponibles, nombreSalleDeSportDisponibles,
            nombreClasse3EnCours, nombreClasse4EnCours, nombreClasse5EnCours, nombreClasse6EnCours,
            nombreAbscencesNonExcuses,
            nombre3emeEnCours, nombre4emeEnCours, nombre5emeEnCours, nombre6emeEnCours,
            nombreLabDisponibles, nombreSalleCourDisponibles, nombreSalleSportDisponibles;

    private SidebarController sidebarController;
    private ParametresPaneController parametresPaneController;
    private AffectationPaneController affectationPaneController;
    private AccueilPaneController accueilPaneController;
    private ClassesPaneController classesPaneController;
    private SallesPaneController sallesPaneController;

    public void initialize(int adminId) {
        this.currentAdminId = adminId;
        sidebarController = new SidebarController(this);
        parametresPaneController = new ParametresPaneController(this);
        affectationPaneController = new AffectationPaneController(this);
        classesPaneController = new ClassesPaneController(this);
        sallesPaneController = new SallesPaneController(this);
        accueilPaneController = new AccueilPaneController();

        // accueilPaneController usage
        accueilPaneController.updateCoursEnCoursLabels(nombreClasse3EnCours, nombreClasse4EnCours, nombreClasse5EnCours,
                nombreClasse6EnCours);
        accueilPaneController.updateEffectifEnCours(effectifEnCours);
        accueilPaneController.updateSallesOccupesLabels(nombreLaboratoiresDisponibles, nombreSalleCoursDisponibles,
                nombreSalleDeSportDisponibles);
        accueilPaneController.updateCoursEnCours(coursEnCours);
        accueilPaneController.updateSallesDisponibles(SallesDisponibles);

        //
        sidebarController.initialize();
        parametresPaneController.initialize();
        parametresPaneController.loadAdminData();
        affectationPaneController.initialize();
        classesPaneController.initialize();
        sallesPaneController.initialize();
        accueilPaneController.fillCurrentSeances(coursEncoursTableView, coursEncoursSalleColumn,
                coursEncoursHorairesColumn,
                coursEncoursClasseColumn, coursEncoursCourNomColumn, coursEncoursEffectifColumn,
                coursEncoursProfesseurColumn);
        accueilPaneController.fillClassesWithSeances(classesTableView, classesSalleColumn, classesStatusColumn,
                classesClasseColumn, classesEffectifColumn, classesCoursColumn, classesActionColumn,
                classesProfesseurColumn);
        classesActionColumn.setCellFactory(new CustomClasseCellButton(activeClassePane, clickHandler));
        sallesPaneController.fillSallesWithSeances(sallesTableView, sallesColumn, sallesStatutColumn,
                sallesClassesColumn, sallesCapaciteColumn, sallesCoursColumn, sallesActionColumn);
        sallesActionColumn.setCellFactory(new CustomSalleCellButton(activeSallePane, clickHandler2));
    }

    ButtonClickHandler clickHandler = rowData -> {
        // Perform actions here based on the row data
        // For example:
        activeClasseLabel.setText(rowData.get("classeNom"));
        StringBuilder builder = new StringBuilder();

        // Iterate over the entries of the rowData map
        for (Map.Entry<String, String> entry : rowData.entrySet()) {
            // Concatenate the key-value pair into the StringBuilder
            builder.append(entry.getKey()).append(": ").append(entry.getValue());
        }
        ObservableList<Map<String, String>> data = FXCollections.observableArrayList();
        data.addAll(ClasseService.getEmploiDeTemps(rowData.get("classeId")));
        classeEmploiJourColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(
                cellData.getValue().get("Day").substring(0, 3)));
        // 8-10
        classeEmploi8_10Column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        classeEmploi8_10Column.setCellFactory(new ET0810());
        // 10-12
        classeEmploi10_12Column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        classeEmploi10_12Column.setCellFactory(new ET1012());
        // 14-16
        classeEmploi14_16Column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        classeEmploi14_16Column.setCellFactory(new ET1416());
        // 16-18
        classeEmploi16_18Column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        classeEmploi16_18Column.setCellFactory(new ET1618());
        // Set the concatenated string to the tempText
        tempText.setText(builder.toString());
        classeEmploiTableView.setItems(data);    
    };

    ButtonClickHandler clickHandler2 = rowData -> {
        // Perform actions here based on the row data
        // For example:
        activeSalleLabel.setText(rowData.get("salleNom"));
        StringBuilder builder = new StringBuilder();
    
        // Iterate over the entries of the rowData map
        for (Map.Entry<String, String> entry : rowData.entrySet()) {
            // Concatenate the key-value pair into the StringBuilder
            builder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        
        ObservableList<Map<String, String>> data = FXCollections.observableArrayList();
        data.addAll(SallesService.getEmploiDeTempsSalle(rowData.get("salleId")));
        salleEmploiJourColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(
                cellData.getValue().get("Day").substring(0, 3)));
        // 8-10
        salleEmploi8_10Column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        salleEmploi8_10Column.setCellFactory(new ET0810());
        // 10-12
        salleEmploi10_12Column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        salleEmploi10_12Column.setCellFactory(new ET1012());
        // 14-16
        salleEmploi14_16Column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        salleEmploi14_16Column.setCellFactory(new ET1416());
        // 16-18
        salleEmploi16_18Column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        salleEmploi16_18Column.setCellFactory(new ET1618());
        
        // Set the concatenated string to the tempText
        tempText.setText(builder.toString());
        salleEmploiTableView.setItems(data);    
    };
    
    

    public void setSallesDisponiblesText(String text) {
        SallesDisponibles.setText(text);
    }

    public void setCoursEnCoursText(String text) {
        coursEnCours.setText(text);
    }

    public void setNombreLaboratoiresOccupesText(String text) {
        nombreLaboratoiresDisponibles.setText(text);
    }

    public void setNombreSalleCoursOccupesText(String text) {
        nombreSalleCoursDisponibles.setText(text);
    }

    public void setNombreSalleDeSportOccupesText(String text) {
        nombreSalleDeSportDisponibles.setText(text);
    }

    public void setCoursEnCours6emeText(String text) {
        nombreClasse6EnCours.setText(text);
    }

    public void setCoursEnCours5emeText(String text) {
        nombreClasse5EnCours.setText(text);
    }

    public void setCoursEnCours4emeText(String text) {
        nombreClasse4EnCours.setText(text);
    }

    public void setCoursEnCours3emeText(String text) {
        nombreClasse3EnCours.setText(text);
    }

    public void setEffectifEnCoursText(String text) {
        effectifEnCours.setText(text);
    }

    public void setNombre3emeEnCoursText(String text) {
        nombre3emeEnCours.setText(text);
    }

    public void setNombre4emeEnCoursText(String text) {
        nombre4emeEnCours.setText(text);
    }

    public void setNombre5emeEnCoursText(String text) {
        nombre5emeEnCours.setText(text);
    }

    public void setNombre6emeEnCoursText(String text) {
        nombre6emeEnCours.setText(text);
    }

    public void setNombreLabOccupesText(String text) {
        nombreLabDisponibles.setText(text);
    }

    public void setSalleDisponiblesText(String text) {
        nombreSalleCourDisponibles.setText(text);
    }

    public void setNombreSalleSportOccupesText(String text) {
        nombreSalleSportDisponibles.setText(text);
    }

    @FXML
    public void handleSideBarButtonAction(ActionEvent event) {
        sidebarController.handleSideBarButtonAction(event);
    }

    @FXML
    public void handleCancelParametresChanges(ActionEvent event) {
        parametresPaneController.annulerLesModifications();
    }

    @FXML
    public void handleAnnulerAffectation(ActionEvent event) {
        affectationPaneController.annulerAffectation();
    }

    @FXML
    public void handleAffecterButton(ActionEvent event) {
        affectationPaneController.affecterUneSeance();
        accueilPaneController.fillCurrentSeances(coursEncoursTableView, coursEncoursSalleColumn,
                coursEncoursHorairesColumn,
                coursEncoursClasseColumn, coursEncoursCourNomColumn, coursEncoursEffectifColumn,
                coursEncoursProfesseurColumn);
    }

    @FXML
    public void handleSauvegarderButtonSecurityParameters(ActionEvent event) {
        parametresPaneController.handleSauvegarderButtonSecurityParameters();
    }

    @FXML
    public void handleSauvegarderButtonInfosParameters(ActionEvent event) {
        parametresPaneController.handleSauvegarderButtonInfosParameters();
    }

    // Methods to access UI elements SubControllers

    public int getCurrentAdminId() {
        return currentAdminId;
    }

    public Button getAccueilButton() {
        return accueilButton;
    }

    public Button getSallesButton() {
        return sallesButton;
    }

    public Button getClassesButton() {
        return classesButton;
    }

    public Button getParametresButton() {
        return parametresButton;
    }

    public Button getDeconnecterButton() {
        return deconnecterButton;
    }

    public Pane getAccueilPane() {
        return accueilPane;
    }

    public Pane getSallesPane() {
        return sallesPane;
    }

    public Pane getAffectationPane() {
        return affectationPane;
    }

    public Pane getClassesPane() {
        return classesPane;
    }

    public Pane getParametresPane() {
        return parametresPane;
    }

    public Button getAnnulerButton() {
        return annulerButtonParametres;
    }

    public Button getAffectationButton() {
        return affectationButton;
    }

    public TextField getNomFieldParametres() {
        return nomFieldParametres;
    }

    public TextField getPreomFieldParametres() {
        return prenomFieldParametres;
    }

    public TextField getEmailFieldParametres() {
        return emailFieldParametres;
    }

    public TextField getTelephoneFieldParametres() {
        return telephoneFieldParametres;
    }

    public DatePicker getDateBirthDatePickerParametres() {
        return dateBirthDatePickerParametres;
    }

    public TextField getOldPasswordParametres() {
        return oldPasswordParametres;
    }

    public TextField getNomCoursFieldAffectation() {
        return nomCoursFieldAffectation;
    }

    public TextField getNewPasswordParametres() {
        return newPasswordParametres;
    }

    public ComboBox<Salle> getSallesComboAffectation() {
        return sallesComboAffectation;
    }

    public ComboBox<JoursSemaine> getJoursComboAffectation() {
        return joursComboAffectation;
    }

    public ComboBox<Classe> getClassesComboAffectation() {
        return classesComboAffectation;
    }

    public ComboBox<Horaires> getHorairesComboAffectation() {
        return horairesComboAffectation;
    }

    public ComboBox<TypeCours> getTypeCoursComboAffectation() {
        return typeCoursComboAffectation;
    }

    public Scene getScene() {
        return rootPane.getScene();
    }

    public ComboBox<Enseignant> getEnseignantComboAffectation() {
        return enseignantComboAffectation;
    }

}