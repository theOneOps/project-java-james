package views;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import javafx.scene.text.Font;

public class EntrepriseView extends HomePageView {

    public EntrepriseView(Stage stage) {
        super(stage);
        stage.setTitle("Entreprise View");
        initializeMainContent();
    }

    public void initializeMainContent() {
        paramButton.setOnAction(e -> {
            ParameterView parameterView = new ParameterView(stage);
            VBox vBox = parameterView.mainContent;
            switchToView(vBox);
        });
        homeButton.setOnAction(e -> {
            HomePageView homePageView = new HomePageView(stage);
            VBox vBox = homePageView.mainContent;
            homePageView.switchToView(vBox);
            System.out.println("homeButton dans entreprise view");
        });

        // Font Style
        Font titleFront = new Font(26);
        Font text = new Font(15);
        Font btnFont = new Font(16);

        // Top Element
        VBox topBox = new VBox(6);
        topBox.setAlignment(Pos.CENTER);
        // Create Elements
        Label title = new Label("Title");
        title.setFont(titleFront);
        Separator separator1 = new Separator();

        topBox.getChildren().addAll(title, separator1);

        /*--- MID Element ---*/
        HBox midBox = new HBox(6);
        // TableView

        // Person Detail
        GridPane detailBox = new GridPane();

        double rowDetailSize = (double) 70/6;
        System.out.println(rowDetailSize);
        RowConstraints rowDetail1 = new RowConstraints();
        rowDetail1.setPercentHeight(rowDetailSize);
        RowConstraints rowDetail2 = new RowConstraints();
        rowDetail2.setPercentHeight(rowDetailSize);
        RowConstraints rowDetail3 = new RowConstraints();
        rowDetail3.setPercentHeight(rowDetailSize);
        RowConstraints rowDetail4 = new RowConstraints();
        rowDetail4.setPercentHeight(rowDetailSize);
        RowConstraints rowDetail5 = new RowConstraints();
        rowDetail5.setPercentHeight(rowDetailSize);
        RowConstraints rowDetail6 = new RowConstraints();
        rowDetail6.setPercentHeight(rowDetailSize);
        RowConstraints rowDetail7 = new RowConstraints();
        rowDetail7.setPercentHeight(30);
        //Column
        ColumnConstraints columnDetail1 = new ColumnConstraints();
        columnDetail1.setPercentWidth(50);
        ColumnConstraints columnDetail2 = new ColumnConstraints();
        columnDetail2.setPercentWidth(50);
        // Add Row & Column
        detailBox.getColumnConstraints().addAll(columnDetail1, columnDetail2);
        detailBox.getRowConstraints().addAll(rowDetail1, rowDetail2, rowDetail3, rowDetail4,
                rowDetail5, rowDetail6, rowDetail7);

        // Create Element: Label & Btn
        Label subTitle = new Label("Person Details");
        subTitle.setFont(titleFront);

        Label name = new Label("Name"); Label nameVarText = new Label("...");
        name.setFont(text); nameVarText.setFont(text);

        Label prename = new Label("Prename"); Label prenameVarText = new Label("...");
        prename.setFont(text); prenameVarText.setFont(text);

        Label job = new Label("Job");  Label jobVarText = new Label("...");
        job.setFont(text); jobVarText.setFont(text);

        Label workHourStart = new Label("Work Hour (Start):");
        workHourStart.setFont(text);
        Label workHourStartVarText = new Label("...");
        workHourStartVarText.setFont(text);

        Label workHourEnd = new Label("Work Hour (End):");
        workHourEnd.setFont(text);
        Label workHourEndVarText = new Label("...");
        workHourEndVarText.setFont(text);

        Button editBtn = new Button("Edit");

        // Put Element into Grid
        GridPane.setRowIndex(subTitle, 0);
        GridPane.setColumnIndex(subTitle, 0);
        GridPane.setHalignment(subTitle, HPos.LEFT);

        GridPane.setRowIndex(name, 1);
        GridPane.setColumnIndex(name, 0);
        GridPane.setHalignment(name, HPos.LEFT);

        GridPane.setRowIndex(nameVarText, 1);
        GridPane.setColumnIndex(nameVarText, 1);
        GridPane.setHalignment(nameVarText, HPos.LEFT);

        GridPane.setRowIndex(prename, 2);
        GridPane.setColumnIndex(prename, 0);
        GridPane.setHalignment(prename, HPos.LEFT);

        GridPane.setRowIndex(prenameVarText, 2);
        GridPane.setColumnIndex(prenameVarText, 1);
        GridPane.setHalignment(prenameVarText, HPos.LEFT);

        GridPane.setRowIndex(job, 3);
        GridPane.setColumnIndex(job, 0);
        GridPane.setHalignment(job, HPos.LEFT);

        GridPane.setRowIndex(jobVarText, 3);
        GridPane.setColumnIndex(jobVarText, 1);
        GridPane.setHalignment(jobVarText, HPos.LEFT);

        GridPane.setRowIndex(workHourStart, 4);
        GridPane.setColumnIndex(workHourStart, 0);
        GridPane.setHalignment(workHourStart, HPos.LEFT);

        GridPane.setRowIndex(workHourStartVarText, 4);
        GridPane.setColumnIndex(workHourStartVarText, 1);
        GridPane.setHalignment(workHourStartVarText, HPos.LEFT);

        GridPane.setRowIndex(workHourEnd, 5);
        GridPane.setColumnIndex(workHourEnd, 0);
        GridPane.setHalignment(workHourEnd, HPos.LEFT);

        GridPane.setRowIndex(workHourEndVarText, 5);
        GridPane.setColumnIndex(workHourEndVarText, 1);
        GridPane.setHalignment(workHourEndVarText, HPos.LEFT);

        GridPane.setRowIndex(editBtn, 6);
        GridPane.setColumnIndex(editBtn, 1);
        GridPane.setHalignment(editBtn, HPos.LEFT);

        detailBox.getChildren().addAll(subTitle, name, nameVarText, prename,
                prenameVarText, job, jobVarText, workHourStart, workHourStartVarText,
                workHourEnd, workHourEndVarText, editBtn);

        // Add grid into midBox
        midBox.getChildren().addAll(detailBox);



        // MAIN GRID
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(15);
        gridPane.setHgap(15);
        // Size column & row
        double sizeColumn = (double) 50/(50+50+50);
        // Row
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(20);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(65);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(15);
        //Column
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(sizeColumn * 100.00);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(sizeColumn * 100.00);
        ColumnConstraints column3 = new ColumnConstraints();
        column2.setPercentWidth(sizeColumn * 100.00);
        // Add Row & Column
        gridPane.getColumnConstraints().addAll(column1, column2, column3);
        gridPane.getRowConstraints().addAll(row1, row2, row3);

        // Pos Element into GridPane
        GridPane.setRowIndex(topBox, 0);
        GridPane.setColumnSpan(topBox, 2);
        GridPane.setHalignment(topBox, HPos.LEFT);

        GridPane.setRowIndex(midBox, 1);
        GridPane.setColumnSpan(midBox, 3);
        GridPane.setHalignment(midBox, HPos.RIGHT);


        // Add element into grid
        gridPane.getChildren().addAll(topBox, midBox);
        VBox finalBox = new VBox();
        finalBox.backgroundProperty().setValue(Background.fill(Color.GREEN));
        finalBox.getChildren().add(gridPane);
        switchToView(finalBox);
    }

}
