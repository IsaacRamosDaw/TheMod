package com.proyectoIntermodular.Exception.Game;

public class GameCategoryNotFoundException extends RuntimeException {

  public GameCategoryNotFoundException(Long id) {
    super("Could not find the category with id " + id);
  }

  public GameCategoryNotFoundException(String name) {
    super("Could not find the category with name: " + name);
  }
}
