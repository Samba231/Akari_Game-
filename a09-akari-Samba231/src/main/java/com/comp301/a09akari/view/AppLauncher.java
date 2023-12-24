package com.comp301.a09akari.view;

import javafx.application.Application;
import javafx.stage.Stage;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.AlternateMvcController;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class AppLauncher extends Application {
  Scene scene;
  BoardView board_view;
  ControlsView controls_view;
  Stage stage;
  AlternateMvcController controller;

  @Override
  public void start(Stage stage) {
    // TODO: Create your Model, View, and Controller instances and launch your GUI
    PuzzleLibrary library = new PuzzleLibraryImpl();
    Puzzle puzzle1 = new PuzzleImpl(SamplePuzzles.PUZZLE_01);
    library.addPuzzle(puzzle1);
    Puzzle puzzle2 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);
    library.addPuzzle(puzzle2);
    Puzzle puzzle3 = new PuzzleImpl(SamplePuzzles.PUZZLE_03);
    library.addPuzzle(puzzle3);
    Puzzle puzzle4 = new PuzzleImpl(SamplePuzzles.PUZZLE_04);
    library.addPuzzle(puzzle4);
    Puzzle puzzle5 = new PuzzleImpl(SamplePuzzles.PUZZLE_05);
    library.addPuzzle(puzzle5);

    ModelImpl model = new ModelImpl(library);
    AlternateMvcController controller = new ControllerImpl(model);
    this.controller = controller;
    ModelObserver observer =
        (Model m) -> {
          renderScene();
        };
    model.addObserver(observer);

    stage.setTitle("AKARI");
    this.stage = stage;

    BoardView board_view = new BoardView(controller);
    this.board_view = board_view;
    ControlsView controlsView = new ControlsView(controller);
    this.controls_view = controlsView;

    renderScene();

    stage.show();
  }

  public void renderScene() {
    Parent puzzle_board = board_view.render();
    Parent controls = controls_view.render();
    VBox pane = new VBox();
    pane.setSpacing(20);
    pane.getChildren().add(controls);
    pane.getChildren().add(puzzle_board);
    if (controller.isSolved()) {
      Text text = new Text("\t\t\tSOLVED!");
      text.setFill(Color.LIGHTGREEN);
      text.setScaleX(3);
      text.setScaleY(3);
      pane.getChildren().add(text);
    }
    Scene scene = new Scene(pane, 500, 500);
    scene.setFill(Color.WHITE);
    stage.setScene(scene);
  }
}
