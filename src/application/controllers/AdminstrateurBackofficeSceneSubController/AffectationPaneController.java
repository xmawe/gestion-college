package application.controllers.AdminstrateurBackofficeSceneSubController;

import java.util.Vector;
import application.controllers.AdminstrateurBackofficeSceneController;
import application.model.Classe;
import application.model.Enseignant;
import application.model.Horaires;
import application.model.Salle;
import application.model.TypeCours;
import application.model.enums.JoursSemaine;
import application.services.ClasseService;
import application.services.EnseignantService;
import application.services.SallesService;
import application.services.TypeCoursService;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class AffectationPaneController {

    private AdminstrateurBackofficeSceneController mainController;

    public AffectationPaneController(AdminstrateurBackofficeSceneController mainController) {
        this.mainController = mainController;
    }

    public void initialize() {
        fillClassesComboBox();
        fillTypeCours();
        fillEnseignants();
        fillJoursSemaine();
        fillHoraires();
        fillSalles();
    }

    public void fillClassesComboBox() {

        Vector<Classe> allClasses = ClasseService.getAllClasses();
        mainController.getClassesComboAffectation().getItems().clear();
        mainController.getClassesComboAffectation().getItems().addAll(allClasses);
        if (!allClasses.isEmpty()) {
            mainController.getClassesComboAffectation().getSelectionModel().select(0);
        }
    }

    public void fillTypeCours() {
        Vector<TypeCours> allTypes = TypeCoursService.getAllTypes();
        mainController.getTypeCoursComboAffectation().getItems().clear();
        mainController.getTypeCoursComboAffectation().getItems().addAll(allTypes);
        if (!allTypes.isEmpty()) {
            mainController.getTypeCoursComboAffectation().getSelectionModel().select(0);
        }
    }

    public void fillHoraires() {
        Vector<Horaires> horaires = Horaires.horairesDisponible();
        mainController.getHorairesComboAffectation().getItems().addAll(horaires);
    }

    public void fillJoursSemaine() {
        mainController.getJoursComboAffectation().getItems().addAll(JoursSemaine.values());
    }

    public void fillSalles() {
        Vector<Salle> salles = SallesService.getAllSalles();
        mainController.getSallesComboAffectation().getItems().clear();
        mainController.getSallesComboAffectation().getItems().addAll(salles);
        if (!salles.isEmpty()) {
            mainController.getSallesComboAffectation().getSelectionModel().select(0);
        }
    }

    public void fillEnseignants() {
        Vector<Enseignant> allEnseignants = EnseignantService.getAllEnseignants();
        ComboBox<Enseignant> enseignantComboBox = mainController.getEnseignantComboAffectation();
        enseignantComboBox.getItems().clear();
        enseignantComboBox.getItems().addAll(allEnseignants);

        // Set custom cell factory to display both full name, email, and photo
        enseignantComboBox.setCellFactory(
                (Callback<ListView<Enseignant>, ListCell<Enseignant>>) new Callback<ListView<Enseignant>, ListCell<Enseignant>>() {
                    @Override
                    public ListCell<Enseignant> call(ListView<Enseignant> param) {
                        return new ListCell<Enseignant>() {
                            @Override
                            protected void updateItem(Enseignant enseignant, boolean empty) {
                                super.updateItem(enseignant, empty);
                                if (empty || enseignant == null) {
                                    setText(null);
                                    setGraphic(null);
                                } else {
                                    // Create a label for the name and email
                                    Label nameLabel = new Label(enseignant.getNom() + " " + enseignant.getPrenom());
                                    Label emailLabel = new Label(enseignant.getEmail());

                                    // Set font weight for email label
                                    emailLabel.setStyle("-fx-font-weight: lighter;");

                                    // Create an image view for the photo
                                    ImageView imageView = new ImageView();
                                    imageView.setImage(new Image(enseignant.getPhotoURL()));

                                    // Set size of the image view
                                    imageView.setFitWidth(32);
                                    imageView.setFitHeight(32);

                                    // Create an HBox to hold the photo and text
                                    HBox hbox = new HBox();
                                    VBox vbox = new VBox(nameLabel, emailLabel);
                                    vbox.setSpacing(3);
                                    hbox.getChildren().addAll(imageView,
                                            vbox);
                                    hbox.setSpacing(10);
                                    hbox.setFillHeight(true);
                                    HBox.setHgrow(nameLabel, Priority.ALWAYS);
                                    // Set the HBox as the graphic of the cell
                                    setGraphic(hbox);
                                }
                            }
                        };
                    }
                });

        if (!allEnseignants.isEmpty()) {
            enseignantComboBox.getSelectionModel().select(0);
        }
    }
}
