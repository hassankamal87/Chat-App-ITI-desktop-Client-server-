package com.whisper.client.presentation.services;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SceneManager {
    private static final SceneManager instance = new SceneManager();

    private Stage primaryStage;

    private final Map<String, Scene> scenes = new HashMap<>();
    private final Map<String, Parent> panes = new HashMap<>();
    private final Set<String> mustLoadAgain = new HashSet<>();
    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 600;

    public static SceneManager getInstance() {
        return instance;
    }

    public void initStage(Stage stage) {
        if (primaryStage != null) {
            throw new IllegalArgumentException("The Stage has already been initialized");
        }
        primaryStage = stage;
        mustLoadAgain.add("requestView");
    }

    public Stage getStage() {
        if (primaryStage == null) {
            System.out.println("Exception in SceneManager Stage is null");
        }
        return primaryStage;
    }

    public void loadView(String name) {
        System.out.println(Thread.currentThread().getName());
        if (primaryStage == null) {
            throw new RuntimeException("Stage Coordinator should be " +
                    "initialized with a Stage before it could be used");
        }
        Platform.runLater(() -> {
            if (!scenes.containsKey(name)) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                            .getResource(String.format("/com/whisper/client/views/%s.fxml", name)));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
                    scenes.put(name, scene);
                    primaryStage.setScene(scenes.get(name));
                    primaryStage.show();
                    System.out.println("Scene loaded");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }else{
                System.out.println("Scene already loaded " + name + " " + scenes.get(name));
                primaryStage.setScene((Scene)scenes.get(name));
                primaryStage.show();
            }

        });
    }

    public Parent loadPane(String name) {
        if (primaryStage == null) {
            throw new RuntimeException("Stage Coordinator should be " +
                    "initialized with a Stage before it could be used");
        }
        if (!panes.containsKey(name) || mustLoadAgain.contains(name)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                        .getResource(String.format("/com/whisper/client/views/%s.fxml", name)));
                Parent root = fxmlLoader.load();
                panes.put(name, root);
                return root;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            System.out.println("Scene already loaded " + name + " " + panes.get(name));
        }
        return panes.get(name);
    }

    public void clearPanes() {
        panes.clear();
        scenes.clear();
    }
}
