package org.srm.purchasecooperation.cux.pr.api.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @description:
 * @author:yuanping.zhang
 * @createTime:2021/4/19 20:54
 * @version:1.0
 */
public class PrToBpmFileDTO implements Serializable {
    @JSONField(name = "FILENUMBER")
    private String index;
    @JSONField(name = "FILENAME")
    private String fileName;
    @JSONField(name = "FILESIZE")
    private String fileSize;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
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
}
