package nestedClasses;

/**
 * An example demonstrating the use of local classes
 * Based on https://docs.oracle.com/javase/tutorial/java/javaOO/localclasses.html
 *
 */
public class LocalClassExample {

    static String regularExpression = "[^0-9]";

    /** A method that formats and validates a given phone number */
    public static void validatePhoneNumber(String phoneNumber) {

        final int numberLength = 10; // in Java 8, can remove the keyword final (if it's effectively final)

        class PhoneNumberValidator {

            String formattedPhoneNumber = null;

            PhoneNumberValidator(String phoneNumber) {
                // remove non-digits
                String currentNumber = phoneNumber.replaceAll(regularExpression, "");

                // must contain exactly 10 digits
                if (currentNumber.length() == numberLength)
                    formattedPhoneNumber = currentNumber;
                else
                    formattedPhoneNumber = null;
            }

            public String getValidNumber() {
                return formattedPhoneNumber;
            }

            public void printParameter() {
                System.out.print("The original parameter is:");
                System.out.println(phoneNumber);

            }

        } // closes local class PhoneNumber

        PhoneNumberValidator myNumber = new PhoneNumberValidator(phoneNumber);
        myNumber.printParameter();
        System.out.println("After formating & validation:");
        String validNumber = myNumber.getValidNumber();
        if (validNumber == null)
            System.out.println("The number is invalid");
        else
            System.out.println("The number is " + validNumber);
        System.out.println();

    }

    public static void main(String... args) {
        validatePhoneNumber("123-456-7890");
        validatePhoneNumber("456-7890");
    }
}

