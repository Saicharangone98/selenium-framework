package api;

public final class ApiEndpoints {

    private ApiEndpoints() {
        // Prevent instantiation
    }

    public static final String PAYMENT_VERIFY = "/api/v1/payments/verify";
    public static final String USERS_BASE = "/api/v1/users";
    public static final String USER_BY_ID = USERS_BASE + "/{id}";
}