package de.matrix.pvnikamod.modutils;

public class CrosshairSquare {
    public float minX;
    public float minY;
    public float maxX;
    public float maxY;

    public CrosshairSquare(){
    }

    public CrosshairSquare(float x1, float y1, float x2, float y2){
        this.minX = x1;
        this.minY = y1;
        this.maxX = x2;
        this.maxY = y2;
    }

    public void setValues(float x1, float y1, float x2, float y2){
        this.minX = x1;
        this.minY = y1;
        this.maxX = x2;
        this.maxY = y2;
    }
}
