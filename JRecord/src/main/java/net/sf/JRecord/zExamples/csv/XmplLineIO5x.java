package net.sf.JRecord.zExamples.csv;


import java.math.BigDecimal;
import java.math.RoundingMode;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.AbstractLineWriter;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.utilityClasses.SchemaLoader;
import net.sf.JRecord.common.CommonBits;
import net.sf.JRecord.common.TstConstants;

/**
 * Example of Reading /writing CSV files with names on the first line
 * @author Bruce Martin
 *
 */
public final class XmplLineIO5x {

	    private static final BigDecimal GST_CONVERSION = new BigDecimal("1.1");

	    private String inputSchemaFile     = this.getClass().getResource("XML_TabDelimCsvSchema.Xml").getFile();
	    private String outputSchemaFile    = this.getClass().getResource("XML_SaleCsvSchema.Xml").getFile();

	    private String salesFile           = this.getClass().getResource("DTAR020.csv").getFile();
	    private String salesFileOut        = TstConstants.TEMP_DIRECTORY + "DTAR020out1.csv";

	
	    /**
	     * Example of LineReader / LineWrite classes
	     */
	    private XmplLineIO5x() {
	        super();

	        int lineNum = 0;
	        BigDecimal gstExclusive, price, gst;
	        AbstractLine saleRecord;
	    	
	        try {
		          // When reading a Csv file with names on the first line you do not
		          // need to specify the fields
				LayoutDetail inSchema  = SchemaLoader.loadSchema(inputSchemaFile, CopybookLoader.SPLIT_NONE, "", 0)
													 .asLayoutDetail();
		    	  // Define the output record (with the fields)
				LayoutDetail outSchema = SchemaLoader.loadSchema(outputSchemaFile, CopybookLoader.SPLIT_NONE, "", 0)
													 .asLayoutDetail();

				AbstractLine outCsvRecord = new Line(outSchema);
				
				LineIOProvider ioProvider = LineIOProvider.getInstance();
				AbstractLineReader reader = ioProvider.getLineReader(inSchema);
				AbstractLineWriter writer = ioProvider.getLineWriter(outSchema);
				
				CommonBits.setUseCsvLine(true);

	            reader.open(salesFile, inSchema);       // Open with a null layout and let the reader create the schema
	            writer.open(salesFileOut);

	            while ((saleRecord = reader.read()) != null) {
	                lineNum += 1;

	                outCsvRecord.getFieldValue("Sku")  .set(saleRecord.getFieldValue("KEYCODE-NO").asString());
	                outCsvRecord.getFieldValue("Store").set(saleRecord.getFieldValue("Store-NO").asString());
	                outCsvRecord.getFieldValue("Date") .set(saleRecord.getFieldValue("Date").asString());
	                outCsvRecord.getFieldValue("Dept") .set(saleRecord.getFieldValue("Dept-NO").asString());
	                outCsvRecord.getFieldValue("Qty")  .set(saleRecord.getFieldValue("Qty-Sold").asString());
	                outCsvRecord.getFieldValue("GST")  .set(saleRecord.getFieldValue("KEYCODE-NO").asString());

	                price =  saleRecord.getFieldValue("SALE-PRICE").asBigDecimal();
	                gstExclusive = price.divide(GST_CONVERSION, 2, RoundingMode.HALF_DOWN);
	                gst = price.subtract(gstExclusive);
	                
	                outCsvRecord.getFieldValue("Price").set(gstExclusive);
	                outCsvRecord.getFieldValue("GST")  .set(gst);
	        
	                writer.write(outCsvRecord);
	            }

	            reader.close();
	            writer.close();
	        } catch (Exception e) {
	            System.out.println("~~> " + lineNum + " " + e.getMessage());
	            System.out.println();

	            e.printStackTrace();
	        }
	    }

	    public static void main(String[] args) {
	    	new XmplLineIO5x();
	    }
}
