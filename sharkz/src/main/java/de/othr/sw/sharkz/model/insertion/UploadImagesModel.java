package de.othr.sw.sharkz.model.insertion;

import de.othr.sw.sharkz.service.InsertionService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

@ViewScoped
@Named("uploadImages")
public class UploadImagesModel implements Serializable {
    
    private final static int IMAGE_SIZE = 3145728;
    
    // Attributes
    private Part file;
    private List<String> fileNames;
    private List<byte[]> images;
    
    // GET parameters
    private long insertionId;
    
    // Models & Services
    @Inject private InsertionService insertionService;
    
    public void init() {
        fileNames = new ArrayList<>();
        images = new ArrayList<>();
        
        insertionId = Long.parseLong(FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap()
                .get("insertion_id"));
    }
    
    public String nextStep() {
        if (images.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("insertion_form",
                    new FacesMessage("Bitte laden Sie mindestens ein Foto von"
                            + "ihrem Objekt hoch!"));
            
            return null;
        }
        
        insertionService.setInsertionImages(insertionId, images);
        
        return "publish";
    }
    
    /**
     * Uploads an image to the model and saves it as <code>byte[]</code>
     */
    public void uploadImage() {
        fileNames.add(file.getSubmittedFileName());
        
        try (InputStream input = file.getInputStream()) {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[IMAGE_SIZE];
            
            for (int length = 0; (length = input.read(buffer)) > 0;)
                output.write(buffer, 0, length);

            images.add(output.toByteArray());

        } catch (IOException e) {
            
        }
    }
    
    /**
     * Validates the files, the user wants to upload as an image for the
     * insertion.
     */
    public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
        Part file = (Part) value;
        List<FacesMessage> msgs = new ArrayList<>();
        
        // Too many pictures
        if (images.size() > 4) {
            msgs.add(new FacesMessage("Sie können nur 5 Bilder hochladen"));
            throw new ValidatorException(msgs);
        }
        
        // Picture size > 3MB
        if (file.getSize() > IMAGE_SIZE) {
            msgs.add(new FacesMessage("Die maximale Dateigröße beträgt 3MB!"));
        }
        
        String type = file.getContentType();
        
        // File is no picture
        if (!type.equalsIgnoreCase("image/png")
                && !(type.equalsIgnoreCase("image/jpeg"))) {
            msgs.add(new FacesMessage("Bitte laden Sie nur .jpeg oder .png "
                    + "Dateien hoch!"));
        }
        
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }

    }
    
    // Getter & Setter
    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }

    public long getInsertionId() {
        return insertionId;
    }

    public void setInsertionId(long insertionId) {
        this.insertionId = insertionId;
    }
    
    
}
