package com.example.demo.util;

import lombok.Data;
import java.time.LocalDateTime;

@Data

public class ErrorDetails {
    private LocalDateTime timestamp;
    private String mensaje;
    private String detalles;

    public ErrorDetails(LocalDateTime timestamp, String mensaje, String detalles) {
        this.timestamp = timestamp;
        this.mensaje = mensaje;
        this.detalles = detalles;
    }

}