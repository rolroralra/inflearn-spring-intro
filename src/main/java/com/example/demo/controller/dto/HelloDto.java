package com.example.demo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlRootElement(name = "dto", namespace = "jaxb")
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlType(propOrder = {"name"})
public class HelloDto {
    @XmlAttribute(name = "id")
    private Long id;

    @XmlElement(name = "name")
    private String name;

    public HelloDto(String name) {
        this((long) name.hashCode(), name);
    }
}
