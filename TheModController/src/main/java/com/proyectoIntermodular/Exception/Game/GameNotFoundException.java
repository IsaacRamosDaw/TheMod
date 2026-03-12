package com.proyectoIntermodular.Exception.Game;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(Long id) {
        super("Could not find the game with id " + id);
    }

    public GameNotFoundException(String name) {
        super("Could not find the game with name: " + name);
    }
}
