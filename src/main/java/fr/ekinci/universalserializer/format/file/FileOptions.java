package fr.ekinci.universalserializer.format.file;

import fr.ekinci.universalserializer.format.file.excel.ExcelFormat;
import org.apache.commons.csv.CSVFormat;

import java.time.format.DateTimeFormatter;

/**
 * Immutable class for defining file options with a builder pattern
 *
 * @author Gokan EKINCI
 */
public class FileOptions {
	private final String dateFormat;
	private final String destinationPath;
	private final boolean hasHeader;
	// private final char separator; // For CSV
	private final CSVFormat csvFormat;
	private final ExcelFormat excelFormat;
	private final int sheetIndex; // For Excel

	private FileOptions(
		String dateFormat,
		String destinationPath,
		boolean hasHeader,
		CSVFormat csvFormat,
		ExcelFormat excelFormat,
		int sheetIndex
	) {
		this.dateFormat = dateFormat;
		this.destinationPath = destinationPath;
		this.hasHeader = hasHeader;
		this.csvFormat = csvFormat;
		this.excelFormat = excelFormat;
		this.sheetIndex = sheetIndex;
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

	public CSVFormat csvFormat() {
		return csvFormat;
	}

	public ExcelFormat excelFormat() {
		return excelFormat;
	}

	public int sheetIndex() {
		return sheetIndex;
	}

	public static FileOptionsBuilder builder() {
		return new FileOptionsBuilder();
	}


	public static class FileOptionsBuilder {
		private String dateFormat;
		private String destinationPath;
		private boolean hasHeader;
		private CSVFormat csvFormat;
		private ExcelFormat excelFormat;
		private int sheetIndex;

		private FileOptionsBuilder() {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
			destinationPath = null; // If null then temp file
			hasHeader = false;
			csvFormat = CSVFormat.DEFAULT;
			excelFormat = ExcelFormat.XLSX;
			sheetIndex = 0;
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

		public FileOptionsBuilder csvFormat(CSVFormat csvFormat) {
			this.csvFormat = csvFormat;
			return this;
		}

		public FileOptionsBuilder excelFormat(ExcelFormat excelFormat) {
			this.excelFormat = excelFormat;
			return this;
		}

		public FileOptionsBuilder sheetIndex(int sheetIndex) {
			this.sheetIndex = sheetIndex;
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
				csvFormat,
				excelFormat,
				sheetIndex
			);
		}
	}
}
