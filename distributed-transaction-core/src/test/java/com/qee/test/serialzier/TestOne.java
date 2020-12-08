package com.qee.test.serialzier;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestOne implements Serializable {

    private static final long serialVersionUID = -5327694163412908770L;
    private String abc;
}
