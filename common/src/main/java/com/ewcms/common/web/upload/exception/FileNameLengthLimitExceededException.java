package com.ewcms.common.web.upload.exception;

import org.apache.commons.fileupload.FileUploadException;

/**
 * 文件名超长
 * 
 * @author wu_zhijun
 */
public class FileNameLengthLimitExceededException extends FileUploadException {

	private static final long serialVersionUID = -5448907400752838363L;
	
	private int length;
    private int maxLength;
    private String filename;

    public FileNameLengthLimitExceededException(String filename, int length, int maxLength) {
        super("file name : [" + filename + "], length : [" + length + "], max length : [" + maxLength + "]");
        this.length = length;
        this.maxLength = maxLength;
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public int getLength() {
        return length;
    }


    public int getMaxLength() {
        return maxLength;
    }

}
