package com.proyectoIntermodular.Exception.follower;

public class FollowerNotFoundException extends RuntimeException {
    public FollowerNotFoundException(Long id) {
        super("Could not find the follower with id " + id);
    }

    public FollowerNotFoundException(String identifier) {
        super("Could not find the follower with: " + identifier);
    }
}
