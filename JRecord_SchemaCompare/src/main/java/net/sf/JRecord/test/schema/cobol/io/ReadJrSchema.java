package net.sf.JRecord.test.schema.cobol.io;
 /*
  *   Example program for JR_Schema_Test.cbl
  *   This class uses the JRecord project (https://sourceforge.net/projects/jrecord/)
  * -------------------------------------------------------------------
  *        
  * *------------- Keep this notice in your final code ---------------
  * *   This code was generated by JRecord projects CodeGen program
  * *            on the: 11 Jul 2016 10:17:9 
  * *     from Copybook: JR_Schema_Test.cbl
  * *          Template: lineWrapper
  * *             Split: 01   
  * * File Organization: 0   
  * *              Font: 
  * *   
  * *    CodeGen Author: Bruce Martin
  * *-----------------------------------------------------------------
  *
  *   This class should be used as an example of reading/writing files
  *   using JRecord. You will need to modify the code to meet your
  *   needs. The Author of CodeGen (Bruce Martin) program takes no 
  *   responsibility for the accuracy of the generated code. 
  *
  *   Good Luck
  *              Bruce Martin
  *
  * ------------------------------------------------------------------
  * v01  CodeGen        11 Jul 2016  Initial version
  *     (Bruce Martin)
  *
  * ------------------------------------------------------------------ 
  */

	

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import net.sf.JRecord.JRecordInterface1;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.common.Constants;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.def.IO.builders.ICobolIOBuilder;
import net.sf.JRecord.test.schema.cobol.gen.data.FieldNamesJrSchemaTest;
import net.sf.JRecord.test.schema.cobol.gen.data.LayoutDtls;
import net.sf.JRecord.test.schema.cobol.gen.data.LineCopybookRecordJR;
import net.sf.JRecord.test.schema.cobol.gen.data.LineDialectRecordJR;
import net.sf.JRecord.test.schema.cobol.gen.data.LineFieldRecordJR;
import net.sf.JRecord.test.schema.cobol.gen.data.LineSchemaRecordRecordJR;
import net.sf.JRecord.test.schema.cobol.gen.data.RecordDtls;


/**
 * Purpose:   Read one complete Layout from a file and return it as a 
 *          LayoutDtls. It is used by CompareCopybookDetails
 *          to compare the Layout stored in a File to the Layout
 *          Generated by JRecord.
 *            This is to support testing of JRecord for multiple 
 *          Cobol Copybooks
 *          
 * @author  Bruce Martin
 * 
 * @see LayoutDtls
 */
public final class ReadJrSchema {

    private String copybookName = "JR_Schema_Test.cbl";

    private ICobolIOBuilder iob;
    private AbstractLineReader reader;
    private AbstractLine line, copyBookLine;
    private int lineNum = 1;

    
    /**
     * Purpose:   Read one complete Layout from a file and return it as a 
     *          LayoutDtls. It is used by CompareCopybookDetails.
     *            This is to support testing of JRecord for multiple 
     *          Cobol Copybooks
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public ReadJrSchema(String dataFile) throws FileNotFoundException, IOException {
        super();

        iob = JRecordInterface1.COBOL
                                   .newIOBuilder(this.getClass().getResource(copybookName).openStream(), "JR_Schema_Test")
                                       .setFileOrganization(Constants.IO_TEXT_LINE)
                                       .setSplitCopybook(CopybookLoader.SPLIT_01_LEVEL)
                                   ;  
        reader = iob.newReader(new GZIPInputStream(new FileInputStream(dataFile)));
        line = reader.read();
    }

    /**
     * Read one complete 'Layout' (Cobol Copybook) from the file.
     * @return The next 'Layout'
     * @throws IOException
     */
    public LayoutDtls read() throws IOException {
   
    	if (line == null) { return null; } 
        LineCopybookRecordJR lineCopybookRecordJR = new LineCopybookRecordJR();

        FieldNamesJrSchemaTest.RecordCopybookRecord rCopybookRecord = FieldNamesJrSchemaTest.RECORD_COPYBOOK_RECORD;
        
        LayoutDtls schema = null;
        RecordDtls rec = null;

    	int recType = line.getFieldValue(rCopybookRecord.recordType).asInt();
    	

    	if (recType == 10) {
    		copyBookLine = line;
        	lineNum += 1;
    		line = reader.read();
    		lineCopybookRecordJR.setLine(copyBookLine);
    		if ("N".equals(lineCopybookRecordJR.getDoCompare())) {
    			return new LayoutDtls(lineCopybookRecordJR, null);
    		}
    	} else if (recType != 20) {
    		throw new RuntimeException("Error at line: "  + lineNum + " expecting a copybook/dialect record");
    	}
		lineCopybookRecordJR.setLine(copyBookLine);

        while (line != null) {  
        	if (line.getFullLine().trim().length() == 0) { break; }
        	recType = line.getFieldValue(rCopybookRecord.recordType).asInt();
            
            switch (recType) {
            case 10: 
            	if (schema == null) {
            		throw new RuntimeException("Error at line: "  + lineNum + " expecting a dialect record");
            	};
            	copyBookLine = line;
            	return schema;
            case 20:
            	if (schema != null) {
            		return schema;
            	};
            	schema = new LayoutDtls(lineCopybookRecordJR, new LineDialectRecordJR().setLine(line));
            	break;
            case 30:
            	if (schema == null) {
            		throw new RuntimeException("Error at line: "  + lineNum + " Found 'Record' record but no parent Dialect record");
            	};
            	
            	rec = new RecordDtls(new LineSchemaRecordRecordJR() .setLine(line));
            	schema.records.add(rec);
            	break;
            case 40:
            	if (rec == null) {
               		throw new RuntimeException("Error at line: "  + lineNum + " Found Field-record but no parent 'Record' record");
            	}
            	
            	rec.fields.add(new LineFieldRecordJR() .setLine(line));
            }
            
            line = reader.read();  
        	lineNum += 1;
        }
        return schema;
    }

	/**
	 * @throws IOException
	 * @see net.sf.JRecord.IO.AbstractLineReader#close()
	 */
	public void close() throws IOException {
		reader.close();
	}

}

