package com.whisper.client.presentation.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class SceneManager {
    private static final SceneManager instance = new SceneManager();

    private Stage primaryStage;

    private final Map<String, Scene> scenes = new HashMap<>();

    private final Map <String, Parent> panes = new HashMap<>();
    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 600;

    public static SceneManager getInstance() {
        return instance;
    }
    public void initStage(Stage stage){
        if(primaryStage != null){
            throw new IllegalArgumentException("The Stage has already been initialized");
        }
        primaryStage = stage;
    }

    public void loadView(String name){
        System.out.println("bla bla bla1");
        if(primaryStage==null){
            throw new RuntimeException("Stage Coordinator should be " +
                    "initialized with a Stage before it could be used");
        }
        if(!scenes.containsKey(name)){
            System.out.println("bla bla bla2");
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                        .getResource(String.format("/com/whisper/client/views/%s.fxml", name)));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
                scenes.put(name, scene);
                System.out.println("bla bla bla3");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        primaryStage.setScene(scenes.get(name));
        System.out.println("bla bla bla4");

    }
    public Parent loadPane(String name){
        if(primaryStage==null){
            System.out.println("bla bla bla3");
            throw new RuntimeException("Stage Coordinator should be " +
                    "initialized with a Stage before it could be used");
        }
        if(!panes.containsKey(name)){
            try {
                System.out.println("bla bla bla4");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                        .getResource(String.format("/com/whisper/client/views/%s.fxml", name)));
                Parent root = fxmlLoader.load();
                panes.put(name, root);
                return root;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return panes.get(name);
    }
}
