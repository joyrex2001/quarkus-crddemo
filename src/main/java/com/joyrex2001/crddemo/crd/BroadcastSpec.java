package com.joyrex2001.crddemo.crd;

public class BroadcastSpec {

    private String message;
    private String color;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (color == null ? 0 : color.hashCode());
        hash = 31 * hash + (message == null ? 0 : message.hashCode());
        return hash;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        BroadcastSpec b = (BroadcastSpec) o;
        return (message.equals(b.message) && color.equals(b.color));
    }
}