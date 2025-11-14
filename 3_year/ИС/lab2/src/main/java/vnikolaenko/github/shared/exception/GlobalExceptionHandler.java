package vnikolaenko.github.shared.exception;


import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof IllegalArgumentException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Invalid request: " + exception.getMessage()))
                    .build();
        }

        if (exception instanceof NotFoundException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Resource not found"))
                    .build();
        }

        if (exception instanceof WrongUsernameOrPassword) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(exception.getMessage()))
                    .build();
        }


        if (exception instanceof ObjectConnectionException || exception instanceof ObjectAlreadyExists) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponse(exception.getMessage()))
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Unexpected error: " + exception.getMessage()))
                .build();
    }

    @Getter
    @Setter
    public static class ErrorResponse {
        private String message;
        private Instant timestamp;

        public ErrorResponse(String message) {
            this.message = message;
            this.timestamp = Instant.now();
        }
    }
}
