package net.sf.JRecord.zExamples.other;

import java.io.ByteArrayInputStream;

import net.sf.JRecord.CsvParser.ParserManager;
import net.sf.JRecord.External.CobolCopybookLoader;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.Def.ExternalField;
import net.sf.JRecord.Log.AppendableLog;
import net.sf.JRecord.Numeric.ICopybookDialects;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.Types.TypeManager;
import net.sf.JRecord.common.Constants;
import net.sf.JRecord.common.Conversion;



/**
 * Purpose: Demonstrate using a "log" to retrieve the messages produced by JRecord
 *
 * @author Bruce Martin
 *
 */
public class LogExample {

	
	private static String cobolCopybook
			= "              03  DTAR020-KCODE-STORE-KEY.                        \n"
			+ "                  05 DTAR020-KEYCODE-NO      PIC X(08).           \n"
			+ "                  05 DTAR020-STORE-NO        PIC          COMP-3. \n"
			+ "              03  DTAR020-DATE               PIC S9(07)   COMP-3. \n"
			+ "              03  DTAR020-DEPT-NO            PIC S9(03)   COMP-3. \n";
	
	private static byte[] copyBookBytes = cobolCopybook.getBytes();
	
	


    public static void main(String[] args) throws Exception {
    	CobolCopybookLoader loaderCBL = new CobolCopybookLoader();
    	StringBuilder sb = new StringBuilder("\nError Log\n");
    	try {
			ExternalRecord extlayoutCBL = loaderCBL.loadCopyBook(
			    new ByteArrayInputStream(copyBookBytes),
			    Conversion.getCopyBookId("ErrorCopybook.cbl"),
			    CopybookLoader.SPLIT_NONE, 0, "", ICopybookDialects.FMT_FUJITSU, 0, new AppendableLog(sb));
		   	System.out.println();
	    	System.out.println("Normal End, Printing sb:");
	    	System.out.println(sb.toString());
		} catch (Exception e) {
			System.out.println();
			System.out.println("Message: " + e.getMessage());
			System.out.println();
	    	System.out.println("Error - Printing sb:");
	    	System.out.println(sb.toString());
	    	
			e.printStackTrace();
		}
 
 
   }
    
    
    /**
     * This method converts Fixed width fields to Csv Fields
     * @param rec Schema to be update
     */
    private static void updateFields(ExternalRecord rec) {
    	if (rec.getNumberOfRecords() == 0) {
    		updateFieldsForRecord(rec);
    	} else {
    		for (int i = 0; i < rec.getNumberOfRecords(); i++) {
    			updateFields(rec.getRecord(i));
    		}
    	}
    }
    
    
    /**
     * This method converts all Fixed width fields to Csv Fields
     * @param rec Schema to be update
     */
    private static void updateFieldsForRecord(ExternalRecord rec) {
    	ExternalField recordField;
    	rec.setFileStructure(Constants.IO_NAME_1ST_LINE);
    	rec.setRecordType(Constants.rtDelimited);
    	rec.setQuote("\"");
    	rec.setDelimiter("<Tab>");
    	rec.setRecordStyle(ParserManager.BASIC_CSV_PARSER);
    	
    	for (int i = 0; i < rec.getNumberOfRecordFields(); i++) {
    		recordField = rec.getRecordField(i);
    		recordField.setPos(i+1);
    		recordField.setLen(Constants.NULL_INTEGER);
    		recordField.setDecimal(0);
    		recordField.setCobolName("");
    		if (TypeManager.isNumeric(recordField.getType())) {
    			recordField.setType(Type.ftNumAnyDecimal);
    		} else {
    			recordField.setType(Type.ftChar);
    		}
    	}
    }
}