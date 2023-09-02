package ru.ashabelskii.dossier.model;

import lombok.Data;

@Data
public class EmailMessage {
    private String address;
    private String subject;
    private String text;
}
