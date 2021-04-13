package org.srm.purchasecooperation.cux.act.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;;

/**
 * @author lu.cheng01@hand-china.com
 * @description 验收单bpm接口查询附件信息
 * @date 2021/4/6 9:50
 * @version:1.0
 */
@JsonInclude
public class ActListFilesDto {
    /*序号，自动生成1，2，3，4...*/
    private String fileNumber;
    /*附件名称*/
    private String fileName;
    /*附件大小*/
    private String fileSize;
    /*附件链接*/
    private String urlMX;

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

    public String getUrlMX() {
        return urlMX;
    }

    public void setUrlMX(String urlMX) {
        this.urlMX = urlMX;
    }
}
