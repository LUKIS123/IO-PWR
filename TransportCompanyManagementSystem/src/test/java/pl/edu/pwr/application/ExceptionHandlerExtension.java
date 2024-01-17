package pl.edu.pwr.application;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

public class ExceptionHandlerExtension implements TestExecutionExceptionHandler {
    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
        System.out.println("Błąd testu: " + throwable.getMessage()); // w konsoli nie wypisze ale moze tu coś być
        throw throwable; // jeszcze raz throw żeby test failował
    }
}
