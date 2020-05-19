package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class InterfaceResponse {

    private Integer total;
    private List<InterfaceDetail> results;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class InterfaceDetail {

        private String port;
        private String status;
        private String mode;

        @JsonInclude(Include.NON_NULL)
        private Neighbor neighbor;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Neighbor {

        private String name;
    }
}
