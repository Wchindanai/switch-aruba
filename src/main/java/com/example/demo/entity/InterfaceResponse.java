package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class InterfaceResponse {

    private Integer total;
    private List<InterfaceDetail> results;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @Data
    public static class InterfaceDetail {

        private String port;
        private String status;
        private String mode;
    }
}
