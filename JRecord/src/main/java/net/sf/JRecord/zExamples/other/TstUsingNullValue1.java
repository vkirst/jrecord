package net.sf.JRecord.zExamples.other;

import java.io.IOException;
import java.math.BigDecimal;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.Numeric.ICopybookDialects;
import net.sf.JRecord.common.CommonBits;
import net.sf.JRecord.common.RecordException;
import net.sf.JRecord.common.TestCommonCode;

public class TstUsingNullValue1 {
	private static String cobolCopybook
			= "        01 xxx.\n"
			+ "            15 field1                 PIC S9(4)V9(3).\n"
			+ "            15 field2                 PIC S9(4)V9(3) packed-decimal.\n"
			+ "            15 field3                 PIC x(8).\n";

	
	public static void main(String[] args) throws RecordException, IOException {
		// Create an Internal JRecord schema (or layout) from the cobol copybook
		LayoutDetail schema = TestCommonCode.getLayoutFromCobolStr(
				cobolCopybook, "xxx",
				CopybookLoader.SPLIT_NONE, "IBM237", ICopybookDialects.FMT_MAINFRAME);
		Line l = new Line(schema);
		
		test(l, CommonBits.NULL_VALUE);
		test(l, "0");
		test(l, new BigDecimal("0"));
		test(l, "0.000");
		test(l, new BigDecimal("0.000"));
		test(l, "0.003");
		test(l, new BigDecimal("0.003"));
		test(l, "-0.003");
		test(l, new BigDecimal("-0.003"));

		test(l, "-1.111");
		test(l, new BigDecimal("-1.111"));
		test(l, "2.222");
		test(l, new BigDecimal("2.222"));
		
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println();
		
		test(l, 0);
		test(l, 0.000);
		test(l, -0.000);
		test(l, 0.003);
		test(l, -0.003);
		
	}

	private static void test(Line l, Object o) throws RecordException {
		l.getFieldValue(0,0).set(o);
		l.getFieldValue(0,1).set(o);
		l.getFieldValue(0,2).set(o);
		
		System.out.println();
		System.out.print("Setting to " + o);
		System.out.print("\t" + l.getFullLine());
		System.out.print("\t" + l.getFieldHex(0, 0) + " " + l.getFieldHex(0, 1)  + " " + l.getFieldHex(0, 2));
		
	}
	
	

	private static void test(Line l, double o) throws RecordException {
		l.getFieldValue(0,0).set(o);
		l.getFieldValue(0,1).set(o);
		l.getFieldValue(0,2).set(o);
		
		System.out.println();
		System.out.print("** Setting to " + o);
		System.out.print("\t" + l.getFullLine());
		System.out.print("\t" + l.getFieldHex(0, 0) + " " + l.getFieldHex(0, 1)  + " " + l.getFieldHex(0, 2));
		
	}

}
