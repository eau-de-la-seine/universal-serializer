package fr.ekinci.universalserializer.format.file;

/**
 * Immutable class for defining file options with a builder pattern
 *
 * @author Gokan EKINCI
 */
public class FileOptions {
    private final String dateFormat;
    private final String destinationPath;
    private final boolean hasHeader;
    private final char separator;

    private FileOptions(
        String dateFormat,
        String destinationPath,
        boolean hasHeader,
        char separator
    ) {
        this.dateFormat = dateFormat;
        this.destinationPath = destinationPath;
        this.hasHeader = hasHeader;
        this.separator = separator;
    }

    public String dateFormat() {
        return dateFormat;
    }

    public String destinationPath() {
        return destinationPath;
    }

    public boolean hasHeader() {
        return hasHeader;
    }

    public char separator() {
        return separator;
    }

    public static FileOptionsBuilder builder() {
        return new FileOptionsBuilder();
    }


    public static class FileOptionsBuilder {
        private String dateFormat;
        private String destinationPath;
        private boolean hasHeader;
        private char separator;

        private FileOptionsBuilder() {
            dateFormat = "yyyy-MM-dd";
            destinationPath = null; // If null then temp file
            hasHeader = false;
            separator = ',';
        }

        public FileOptionsBuilder dateFormat(String dateFormat) {
            this.dateFormat = dateFormat;
            return this;
        }

        public FileOptionsBuilder destinationPath(String destinationPath) {
            this.destinationPath = destinationPath;
            return this;
        }

        public FileOptionsBuilder hasHeader(boolean hasHeader) {
            this.hasHeader = hasHeader;
            return this;
        }

        public FileOptionsBuilder separator(char separator) {
            this.separator = separator;
            return this;
        }

        /**
         * Build operation
         *
         * @return FIleOptions object
         */
        public FileOptions build() {
            return new FileOptions(
                dateFormat,
                destinationPath,
                hasHeader,
                separator
            );
        }
    }
}
