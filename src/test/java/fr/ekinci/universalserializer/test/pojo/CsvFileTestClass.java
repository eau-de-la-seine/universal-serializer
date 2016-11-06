package fr.ekinci.universalserializer.test.pojo;

import fr.ekinci.universalserializer.format.file.FileInfo;


/**
 * {@link CsvFileTestClass} is not a {@link ExcelFileTestClass}, but supports same types
 *
 * @author Gokan EKINCI
 */
@FileInfo(
		orderedFieldNames = {
				"attr0", "attr1", "attr2", "attr3", "attr4", "attr5", "attr6", "attr7", "attr8", "attr9", "attr10", "attr11", "attr12", "attr13", "attr14", "attr15", "attr16"
		},
		headerColumnNames = {
				"Col 0", "Col 1", "Col 2", "Col 3", "Col 4", "Col 5", "Col 6", "Col 7", "Col 8", "Col 9", "Col 10", "Col 11", "Col 12", "Col 13", "Col 14", "Col 15", "Col 16"
		}
)
public class CsvFileTestClass extends ExcelFileTestClass {

}
