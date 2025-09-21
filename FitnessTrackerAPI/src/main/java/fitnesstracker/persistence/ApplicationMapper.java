package fitnesstracker.persistence;

import fitnesstracker.presentation.ApplicationRegistration;

public class ApplicationMapper {

    public static Application convert(ApplicationRegistration request, Developer developer) {
        return new Application(
                request.name(), request.description(), request.category(), developer);
    }
}
