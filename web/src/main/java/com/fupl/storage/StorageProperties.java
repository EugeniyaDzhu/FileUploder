package com.fupl.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";
    private String fileSufix = ".csv";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFileSufix() {
        return fileSufix;
    }

    public void setFileSufix(String fileSufix) {
        this.fileSufix = fileSufix;
    }
}
