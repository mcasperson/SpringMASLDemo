package com.matthewcasperson.demo.model;

import java.sql.Timestamp;

public record Audit(Long id, String message, Timestamp date) {

}
