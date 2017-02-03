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
    
    // Error messages:
    public static final String ERR_BANK_WRONG_INPUT
            = "Die Banktransaktion ist fehlgeschlagen! Bitte überprüfen Sie "
            + "Ihre Angaben!";
    public static final String ERR_BANK_SERVICE_UNAVAILABLE
            = "Die Banktransaktion ist fehlgeschlagen! Der Service ist im "
            + "Moment nicht erreichbar!";
    public static final String ERR_NEWSPAPER_WRONG_INPUT
            = "Die Veröffentlichung in der Zeitung ist fehlgeschlagen! Ihr "
            + "Inserat kann nur auf Sharkz veröffentlicht werden!";
    public static final String ERR_NEWSPAPER_SERVICE_UNAVAILABLE =
            "Der Service zum Veröffentlichen in der Zeitung steht im Moment "
            + "nicht zur Verfügung! Ihr Inserat kann nur auf Sharkz "
            + "veröffentlicht werden!";
}
