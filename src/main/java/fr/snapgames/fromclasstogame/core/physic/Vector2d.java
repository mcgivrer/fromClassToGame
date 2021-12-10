/*
 * SnapGames
 *
 * @year 2021
 *
 */
package fr.snapgames.fromclasstogame.core.physic;

import java.util.Objects;

/**
 * Internal Class to manipulate simple Vector2D.
 *
 * @author Frédéric Delorme
 */
public class Vector2d {
    public static final Vector2d ZERO = new Vector2d(0, 0);
    public static final Vector2d IDENTITY = new Vector2d(1, 1);
    public double x, y;

    public Vector2d() {
        x = 0.0f;
        y = 0.0f;
    }

    /**
     * @param x
     * @param y
     */
    public Vector2d(double x, double y) {
        super();
        this.x = x;
        this.y = y;
    }

    /**
     * Compute distance between this vector and the vector <code>v</code>.
     *
     * @param v the vector to compute distance with.
     * @return
     */
    public double distance(Vector2d v) {
        double v0 = x - v.x;
        double v1 = y - v.y;
        return (double) Math.sqrt(v0 * v0 + v1 * v1);
    }

    /**
     * Normalization of this vector.
     */
    public Vector2d normalize() {
        // sets length to 1
        //
        double length = Math.sqrt(x * x + y * y);

        if (length != 0.0) {
            double s = 1.0f / (double) length;
            x = x * s;
            y = y * s;
        }

        return new Vector2d(x, y);
    }

    /**
     * Add the <code>v</code> vector to this vector.
     *
     * @param v the vector to add to this vector.
     * @return this.
     */
    public Vector2d add(Vector2d v) {
        x += v.x;
        y += v.y;
        return this;
    }

    /**
     * Add the <code>v</code> vector to this vector and return the corresponding result in a new vector.
     *
     * @param v the vector to add to this vector.
     * @return this.
     */
    public Vector2d addTo(Vector2d v) {
        Vector2d r = new Vector2d();
        r.x = x + v.x;
        r.y = y + v.y;
        return r;
    }

    /**
     * Multiply vector by a factor.
     *
     * @param factor the factor to multiply the vector by.
     * @return this.
     */
    public Vector2d multiply(double factor) {
        x *= factor;
        y *= factor;
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("(%04.02f,%04.03f)", x, y);
    }

    /**
     * Substract the Vector2D v to this current and return result.
     *
     * @param v vector2D to be substracted.
     * @return Vector2D resulting of substraction.
     */
    public Vector2d substract(Vector2d v) {
        return new Vector2d(x - v.x, y - v.y);
    }

    /**
     * Dot product for current instance {@link Vector2d} and the <code>v1</code>
     * vector.
     *
     * @param v1
     * @return
     */
    public double dot(Vector2d v1) {
        return this.x * v1.x + this.y * v1.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2d)) return false;
        Vector2d vector2d = (Vector2d) o;
        return Double.compare(vector2d.x, x) == 0 && Double.compare(vector2d.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}