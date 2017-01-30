package de.othr.sw.sharkz.util;

public abstract class Constants {
    // Constants for form input fields (standard SQL length = 255 bytes)
    public static final int INSERTION_TITLE_LENGTH = 100;
    public static final int INSERTION_DESCRIPTION_LENGTH = 1000;
    
    public static final int MESSAGE_TITLE_LENGTH = 100;
    public static final int MESSAGE_CONTENT_LENGTH = 1000;
    
    public static final int MAX_IMAGE_SIZE = 3145728;
    
    
    // BankTransaction constants
    public static final String SHARKZ_NAME = "Sharkz - Immobilienvergleichsportal";
    
    public static final String SHARKZ_BIC = "12345";
    public static final String SHARKZ_IBAN = "DE6030";
    public static final String SHARKZ_PASSWORD = "2338";
    
    // LivingInsuranceTransaction constants
    private static final String NO_ERRORS_OCURRED = "0#Es sind keine fehler aufgetreten.";
    private static final String INVALID_EMAIL= "1#Email ist ungueltig";
    private static final String INVALID_PASSWORD  = "2#Email Password ist ungültig(mindestens 4 Zeichen). Oder die Email wird bereits von ein anderer Benutzer verwendet.";
    private static final String INVALID_PARAMETERS = "3#Ungueltige eingaben.";
    private static final String CUSTOMER_HAS_ALREADY_LIVING_INSURANCE = "4#Kunde hat bereits eine Hausratversicherung";
    private static final String INTERNAL_ERRORS = "5#Internal Errors occurred.";
}
