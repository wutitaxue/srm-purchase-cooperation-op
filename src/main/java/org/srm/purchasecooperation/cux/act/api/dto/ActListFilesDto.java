package org.srm.purchasecooperation.cux.act.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;;

/**
 * @author lu.cheng01@hand-china.com
 * @description 验收单bpm接口查询附件信息
 * @date 2021/4/6 9:50
 * @version:1.0
 */
@JsonInclude
public class ActListFilesDto {
    @ApiModelProperty("序号，自动生成1，2，3，4...")
    @JsonProperty("FILENUMBER")
    private String fileNumber;

    @ApiModelProperty("附件名称")
    @JsonProperty("FILENAME")
    private String fileName;

    @JsonProperty("FILESIZE")
    @ApiModelProperty("附件大小")
    private String fileSize;

    @JsonProperty("DESCRIPTION")
    @ApiModelProperty("附件名称")
    private String description;

    @ApiModelProperty("附件链接")
    @JsonProperty("URL")
    private String urlMX;

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber( String fileNumber ) {
        this.fileNumber = fileNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName( String fileName ) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize( String fileSize ) {
        this.fileSize = fileSize;
    }

    public String getUrlMX() {
        return urlMX;
    }

    public void setUrlMX( String urlMX ) {
        this.urlMX = urlMX;
    }
}
