package fr.ekinci.universalserializer.format.file;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation for CSV and Excel file serialization
 *
 * @author Gokan EKINCI
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FileInfo {
    String[] orderedFieldNames();
    String[] headerColumnNames();
}
