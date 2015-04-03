package webservice.model;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
// Doc ok.
/**
 * This class represent a position, it can
 * <ul>
 * <li>return the latitude of the position</li>
 * <li>return the longitude of the position</li>
 * <li>return if a position is equal to another</li>
 * <li>set a latitude of a position</li>
 * <li>set a longitude of a position</li>
 * <li>return a String of the position</li>
 * </ul>
 *
 */
public class Position {
    // Two attributes who define a position
    private double latitude,longitude;
    /**
     * Create a Position with its latitude and its longitude
     * @param latitude the latitude of the position
     * @param longitude the longitude of the position
     */
    public Position(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    /**
     * Create a Position with no parameter.
     */
    public Position() { }
    /**
     * Returns if two positions are exactly the same
     * @param o
     * The tested object
     * @return result
     * Return true if the two objects are the same, false in others cases
     */
    public boolean equals(Object o){
// At first we create a result and set it at false
        boolean result = false;
// We test if the object is a Position
        if (o instanceof Position){
// And if it's true result may change his value
            Position otherPosition = (Position)o;
// If the two affirmations are true then result = true
// If they are not result = false
            result = otherPosition.latitude == this.latitude &&
                    otherPosition.longitude == this.longitude;
        }
// We return value
        return result;
    }
    /**
     * Returns the latitude of the position
     * @return latitude
     * The latitude of the position
     */
    public double getLatitude() {
        return latitude;
    }
    /**
     * Set the latitude of the position
     * @param latitude
     * The new latitude of the position
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    /**
     * Returns the longitude of the position
     * @return longitude
     * The longitude of the position
     */
    public double getLongitude() {
        return longitude;
    }
    /**
     * Set the longitude of the position
     * @param longitude
     * The new longitude of the position
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    /**
     * Return a string representation of a Position. The String representation consist to
     * the latitude and the longitude enclose in parenthesis ("()") and separate by a comma
     * like this : (latitude,longitude)
     * @return
     * return a string representation of a position
     */
    public String toString(){
// At first we make a new Stringbuffer
        final StringBuffer buffer = new StringBuffer();
// we stock the result in the buffer
        buffer.append("(").append(latitude).append(", ").append(longitude).append(")");
// and we return it with the method toString of the buffer
        return buffer.toString();
    }
    /**
     * @return distance in meter between 2 Position
     */
    public double distanceTo(Position p){
        float pk = (float) (180/3.14169);
        double a1 = p.getLatitude()/ pk;
        double a2 = p.getLongitude()/ pk;
        double b1 = this.getLatitude()/ pk;
        double b2 = this.getLongitude()/ pk;
        double t1 = Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2);
        double t2 = Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2);
        double t3 = Math.sin(a1)*Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);
        return 6366000*tt;
    }
}
