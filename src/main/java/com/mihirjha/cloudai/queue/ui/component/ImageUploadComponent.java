
package com.mihirjha.cloudai.queue.ui.component;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.streams.UploadHandler;

/**
 * UI component responsible for accepting queue images.
 *
 * <p>
 * Supports JPEG and PNG images with a maximum file size of 10 MB.
 * Uploaded image content is retained in memory for subsequent processing.
 * </p>
 */
public class ImageUploadComponent extends VerticalLayout {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    private byte[] uploadedImage;
    private String uploadedFileName;
    private String uploadedContentType;

    private final Upload upload;

    /**
     * Creates and configures the image upload component.
     */
    public ImageUploadComponent() {
        setPadding(false);
        setSpacing(true);
        setWidthFull();

        H3 heading = new H3("Queue Image");

        Paragraph description = new Paragraph(
                "Upload a queue image in JPG or PNG format. "
                        + "Maximum file size: 10 MB.");

        upload = new Upload(
                UploadHandler.inMemory((metadata, bytes) -> {
                    this.uploadedImage = bytes;
                    this.uploadedFileName = metadata.fileName();
                    this.uploadedContentType = metadata.contentType();
                }));

        upload.setAcceptedFileTypes(
                "image/jpeg",
                ".jpg",
                ".jpeg",
                "image/png",
                ".png");

        upload.setMaxFileSize((int) MAX_FILE_SIZE);
        upload.setMaxFiles(1);

        add(heading, description, upload);
    }

    /**
     * Returns the uploaded image bytes.
     *
     * @return uploaded image bytes, or null if no image has been uploaded
     */
    public byte[] getUploadedImage() {
        return uploadedImage;
    }

    /**
     * Returns the uploaded file name.
     *
     * @return original file name, or null if no image has been uploaded
     */
    public String getUploadedFileName() {
        return uploadedFileName;
    }

    /**
     * Returns the uploaded content type.
     *
     * @return content type, or null if no image has been uploaded
     */
    public String getUploadedContentType() {
        return uploadedContentType;
    }

    /**
     * Checks whether an image has been uploaded.
     *
     * @return true if image data is available
     */
    public boolean hasUploadedImage() {
        return uploadedImage != null && uploadedImage.length > 0;
    }
}