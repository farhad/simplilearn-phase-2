package simplilearn.farhadfaghihi.model;

import java.util.List;

public class OperationResult {
    private String message;
    private boolean isSuccessful;
    private List<FileObject> fileObjects;

    public OperationResult(String message, boolean isSuccessful, List<FileObject> fileObjects) {
        this.message = message;
        this.isSuccessful = isSuccessful;
        this.fileObjects = fileObjects;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public List<FileObject> getFileObjects() {
        return fileObjects;
    }

    public void setFileObjects(List<FileObject> fileObjects) {
        this.fileObjects = fileObjects;
    }
}
