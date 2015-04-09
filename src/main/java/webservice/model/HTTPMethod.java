package webservice.model;

/**
 * Represent the http method used in the application
 */
public enum HTTPMethod {
    /**
     * The enumeration declaration
     */
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private String method;

    /**
     * Constructor of HTTPMethod
     *
     * @param method a string which represents the http method
     */
    HTTPMethod(String method) {
        this.method = method;
    }

    /**
     * Return a string reprensatation of a http method
     *
     * @return
     * A string who represents the http methods
     */
    public String getMethod() {
        return method;
    }
}
