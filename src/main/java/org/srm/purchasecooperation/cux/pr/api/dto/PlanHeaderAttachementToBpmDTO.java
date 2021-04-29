package org.srm.purchasecooperation.cux.pr.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/4/27 19:10
 */
public class PlanHeaderAttachementToBpmDTO {
    @ApiModelProperty(value = "序号")
    @JsonProperty("FILENUMBER")
    private  String fileNumber;

    @ApiModelProperty(value = "附件名称")
    @JsonProperty("FILENAME")
    private  String fileName;

    @ApiModelProperty(value = "附件大小")
    @JsonProperty("FILESIZE")
    private  String fileSize;

    @ApiModelProperty(value = "附件名称")
    @JsonProperty("DESCRIPTION")
    private  String description;

    @ApiModelProperty(value = "附件url")
    @JsonProperty("URL")
    private  String url;

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PlanHeaderAttachementToBpmDTO{" +
                "fileNumber='" + fileNumber + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
