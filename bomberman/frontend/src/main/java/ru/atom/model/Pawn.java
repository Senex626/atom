package ru.atom.model;

import ru.atom.geometry.Point;

public class Pawn extends PositionableObject implements Movable {

    private Direction direction;

    public Pawn(int x, int y) {
        this.setPosition(new Point(x, y));
    }
    
    public Pawn(int x, int y, int id) {
    	this.setPosition(new Point(x, y));
    	this.setId(id);
    }

    public Pawn(Point pos) {
        this.setPosition(pos);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    
    public Direction getDirection() {
        return direction;
    }
    
    @Override
    public void tick(long elapsed) {
        move(direction);
    }

    @Override
    public Point move(Direction direction) {

        if (direction == Direction.UP) {
            this.setPosition(new Point(this.getPosition().getX(), this.getPosition().getY() + 1));
        } else if (direction == Direction.DOWN) {
            this.setPosition(new Point(this.getPosition().getX(), this.getPosition().getY() - 1));
        } else if (direction == Direction.RIGHT) {
            this.setPosition(new Point(this.getPosition().getX() + 1, this.getPosition().getY()));
        } else if (direction == Direction.LEFT) {
            this.setPosition(new Point(this.getPosition().getX() - 1, this.getPosition().getY()));
        }

        return this.getPosition();
    }
    
    public String toJSON() {
    	Point pos = getPosition();
    	String obj = "{\"type\":\"" + this.getClass().getSimpleName() + "\",\"id\":" + this.getId() + ",\"position\":{\"x\":" + pos.getX() + ",\"y\":" + pos.getY() + "}}";
    	return obj;
    }

}