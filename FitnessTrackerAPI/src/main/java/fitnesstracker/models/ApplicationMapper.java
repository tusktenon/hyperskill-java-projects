package fitnesstracker.models;

public class ApplicationMapper {

    public static Application convert(ApplicationRegistration request, Developer developer) {
        return new Application(
                request.name(), request.description(), request.category(), developer);
    }
}
