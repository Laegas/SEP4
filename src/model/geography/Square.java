package model.geography;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by kenneth on 22/05/2018.
 */
public class Square {
    LocationPoint topLeft;
    LocationPoint topRight;
    LocationPoint bottomLeft;
    LocationPoint bottomRight;

    public Square(LocationPoint topLeft, LocationPoint topRight, LocationPoint bottomLeft, LocationPoint bottomRight) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }

    //probably not needed
    public boolean isInside(LocationPoint locationPoint) {
        throw new NotImplementedException();
    }


    public LocationPoint getTopLeft() {
        return topLeft;
    }

    public LocationPoint getTopRight() {
        return topRight;
    }

    public LocationPoint getBottomLeft() {
        return bottomLeft;
    }

    public LocationPoint getBottomRight() {
        return bottomRight;
    }
}
