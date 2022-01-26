package com.payoneer.evaluation_task.models;

import java.io.Serializable;

/**
 * Form input element description.
 */

public class InputElement implements Serializable {
    /**
     * name
     */
    private String name;
    /**
     * type
     */
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}