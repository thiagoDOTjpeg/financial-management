package br.com.gritti.app.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ErrorMessage(@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
                           LocalDateTime timestamp,
                           Integer code,
                           String status,
                           List<String> erros) {

}
