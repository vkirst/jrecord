package net.sf.JRecord.zExamples.cobol.readWrite;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.CobolIoProvider;
import net.sf.JRecord.Numeric.ICopybookDialects;
import net.sf.JRecord.common.Constants;
import net.sf.JRecord.common.FieldDetail;
import net.sf.JRecord.common.TstConstants;
import net.sf.cb2xml.def.Cb2xmlConstants;

/**
 * Read / Write Mainframe Cobol file using a Cobol Copybook
 *
 * @author Bruce Martin
 *
 */
public final class XmplLineIOB {

	    private String installDir     = TstConstants.SAMPLE_DIRECTORY;
	    private String salesFile      = installDir + "DTAR020.bin";
	    private String copybookName   = TstConstants.COBOL_DIRECTORY + "DTAR020.cbl";

	    /**
	     * Example of LineReader / LineWrite classes
	     */
	    private XmplLineIOB() {
	        super();

	        int lineNum = 0;
	        AbstractLine saleRecord;

	        try {
	            int fileStructure = Constants.IO_FIXED_LENGTH;
	            CobolIoProvider ioProvider = CobolIoProvider.getInstance();
	            AbstractLineReader reader  = ioProvider.getLineReader(
	                   fileStructure, ICopybookDialects.FMT_MAINFRAME,
	                   CopybookLoader.SPLIT_NONE,  Cb2xmlConstants.USE_STANDARD_COLUMNS, 
	                   copybookName, salesFile
	            );
	            LayoutDetail schema = reader.getLayout();
	            int recordId = 0; // Assuming only one record In the file


	            while ((saleRecord = reader.read()) != null) {
	                lineNum += 1;

	                System.out.println("Line " + lineNum + " Record : " + schema.getRecord(recordId).getRecordName());
	                for (int i = 0; i < schema.getRecord(recordId).getFieldCount(); i++) {
	                	FieldDetail field = schema.getRecord(recordId).getField(i);
						System.out.println(
								  "\t" + field.getName()
								+ "\t\t" + saleRecord.getFieldValue(field).asString());
	                }

	                System.out.println();
	                System.out.println();
	            }

	            reader.close();
	        } catch (Exception e) {
	            System.out.println("~~> " + lineNum + " " + e.getMessage());
	            System.out.println();

	            e.printStackTrace();
	        }
	    }

	    public static void main(String[] args) {
	    	new XmplLineIOB();
	    }
}
