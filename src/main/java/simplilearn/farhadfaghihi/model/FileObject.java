package simplilearn.farhadfaghihi.model;

public class FileObject implements Comparable {
    private String nameAndExtension;
    private String lastUpdated;
    private String totalSize;
    private boolean isDirectory;

    public FileObject(String nameAndExtension, String lastUpdated, String totalSize, boolean isDirectory) {
        this.nameAndExtension = nameAndExtension;
        this.lastUpdated = lastUpdated;
        this.totalSize = totalSize;
        this.isDirectory = isDirectory;
    }

    public String getNameAndExtension() {
        return nameAndExtension;
    }

    public void setNameAndExtension(String nameAndExtension) {
        this.nameAndExtension = nameAndExtension;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(String totalSize) {
        this.totalSize = totalSize;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    @Override
    public int compareTo(Object other) {
        return this.nameAndExtension.compareTo(((FileObject) other).nameAndExtension);
    }
}
