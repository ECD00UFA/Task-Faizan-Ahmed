package com.payoneer.evaluation_task.models;

import java.util.Date;
import java.util.List;

/**
 * This class is designed to hold list of applicable and registered payment network descriptions.
 */
public class Networks {
    /**
     * Simple API, always present
     */
    private List<ApplicableNetwork> applicable;
    /**
     * Simple API, always present
     */
    private Date resourcesLastUpdate;

    public List<ApplicableNetwork> getApplicable() {
        return applicable;
    }

    public void setApplicable(List<ApplicableNetwork> applicable) {
        this.applicable = applicable;
    }
}