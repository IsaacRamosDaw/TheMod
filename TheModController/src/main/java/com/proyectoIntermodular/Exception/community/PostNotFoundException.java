package com.proyectoIntermodular.Exception.community;

public class PostNotFoundException extends RuntimeException {

  public PostNotFoundException(Long id) {
    super("Could not find the post with id " + id);
  }

  public PostNotFoundException(String name) {
    super("Could not find the post with name: " + name);
  }
}
