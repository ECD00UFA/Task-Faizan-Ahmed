package com.payoneer.evaluation_task.models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

/**
 * This class is designed to hold information about applicable payment network.
 */
public class ApplicableNetwork implements Serializable {
    /**
     * Simple API, always present
     */
    private String code;
    /**
     * Simple API, always present
     */
    private String label;
    /**
     * Form elements descriptions
     */
    private List<InputElement> inputElements;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<InputElement> getInputElements() {
        return inputElements;
    }

    public void setInputElements(List<InputElement> inputElements) {
        this.inputElements = inputElements;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj != null) {
            if (obj instanceof ApplicableNetwork) {
                ApplicableNetwork instance = (ApplicableNetwork) obj;
                return instance.getCode().equalsIgnoreCase(this.getCode());
            }
        }
        return false;
    }
}