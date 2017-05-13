package ru.atom.model;

import ru.atom.geometry.Point;

public class Tile extends PositionableObject {

    public enum Type {
        Wood, Wall, grass
    }
    
    private Type type;
	
	public Tile(int x, int y) {
        this.setPosition(new Point(x, y));
    }
	
	public Tile(int x, int y, Type type, int id) {
		this.setPosition(new Point(x, y));
		this.type = type;
		this.setId(id);
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	
	public String toJSON() {
		Point pos = getPosition();
		String obj = "{\"type\":\"" + type.name() + "\",\"id\":" + this.getId() + ",\"position\":{\"x\":" + pos.getX() + ",\"y\":" + pos.getY() + "}}";
		return obj;
	}
	
}
