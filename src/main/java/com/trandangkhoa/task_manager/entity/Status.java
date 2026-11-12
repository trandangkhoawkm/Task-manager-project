package com.trandangkhoa.task_manager.entity;

public enum Status {
    TODO, DOING, DONE;

    public Status next() {
        return values()[(ordinal() + 1) % values().length];
    }
}