package net.sf.JRecord.common;

import java.io.IOException;
import java.util.Arrays;

import junit.framework.TestCase;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.common.FieldDetail;
import net.sf.JRecord.def.IO.builders.ICobolIOBuilder;

public class CommonCodeFields {

    private static final String[] JAVA_TYPE_NAME = new String[Type.LAST_SYSTEM_TYPE];

	static {
	   	Arrays.fill(JAVA_TYPE_NAME, null);
	}
  
	public static String getJRecordTypeName(int type) {
		initTypeNames();
		
		if (type < 0 || type > JAVA_TYPE_NAME.length || JAVA_TYPE_NAME[type] == null) {
			return Integer.toString(type);
		}
		
		return "Type." + JAVA_TYPE_NAME[type];
	}

	private static void initTypeNames() {
		if (JAVA_TYPE_NAME[0] == null || JAVA_TYPE_NAME [Type.ftHtmlField ] == null) {		
			JAVA_TYPE_NAME [Type.ftChar                     ] = "ftChar";
			JAVA_TYPE_NAME [Type.ftCharRightJust            ] = "ftCharRightJust";
			JAVA_TYPE_NAME [Type.ftCharNullTerminated       ] = "ftCharNullTerminated";
			JAVA_TYPE_NAME [Type.ftCharNullPadded           ] = "ftCharNullPadded";
			
			JAVA_TYPE_NAME [Type.ftHex                      ] = "ftHex";
			JAVA_TYPE_NAME [Type.ftNumLeftJustified         ] = "ftNumLeftJustified";
			JAVA_TYPE_NAME [Type.ftNumRightJustified        ] = "ftNumRightJustified";
			JAVA_TYPE_NAME [Type.ftNumZeroPadded            ] = "ftNumZeroPadded";
			JAVA_TYPE_NAME [Type.ftAssumedDecimal           ] = "ftAssumedDecimal";
			JAVA_TYPE_NAME [Type.ftSignSeparateLead         ] = "ftSignSeparateLead";
			JAVA_TYPE_NAME [Type.ftSignSeparateTrail        ] = "ftSignSeparateTrail";
			JAVA_TYPE_NAME [Type.ftDecimal                  ] = "ftDecimal";
			JAVA_TYPE_NAME [Type.ftBinaryInt                ] = "ftBinaryInt";
			JAVA_TYPE_NAME [Type.ftPostiveBinaryInt         ] = "ftPostiveBinaryInt";
			JAVA_TYPE_NAME [Type.ftFloat                    ] = "ftFloat";
			JAVA_TYPE_NAME [Type.ftDouble                   ] = "ftDouble";
			JAVA_TYPE_NAME [Type.ftNumAnyDecimal            ] = "ftNumAnyDecimal";
			JAVA_TYPE_NAME [Type.ftPositiveNumAnyDecimal    ] = "ftPositiveNumAnyDecimal";
			JAVA_TYPE_NAME [Type.ftBit                      ] = "ftBit";
			JAVA_TYPE_NAME [Type.ftAssumedDecimalPositive   ] = "ftAssumedDecimalPositive";
			JAVA_TYPE_NAME [Type.ftBinaryIntPositive        ] = "ftBinaryIntPositive";

			JAVA_TYPE_NAME [Type.ftNumZeroPaddedPN          ] = "ftNumZeroPaddedPN";
			JAVA_TYPE_NAME [Type.ftNumZeroPaddedPositive    ] = "ftNumZeroPaddedPositive";
			JAVA_TYPE_NAME [Type.ftNumCommaDecimal          ] = "ftNumCommaDecimal";
			JAVA_TYPE_NAME [Type.ftNumCommaDecimalPN        ] = "ftNumCommaDecimalPN";
			JAVA_TYPE_NAME [Type.ftNumCommaDecimalPositive  ] = "ftNumCommaDecimalPositive";

			JAVA_TYPE_NAME [Type.ftNumRightJustifiedPN      ] = "ftNumRightJustifiedPN";
			
			JAVA_TYPE_NAME [Type.ftPackedDecimal            ] = "ftPackedDecimal";
			JAVA_TYPE_NAME [Type.ftZonedNumeric             ] = "ftZonedNumeric";
			JAVA_TYPE_NAME [Type.ftPackedDecimalPostive     ] = "ftPackedDecimalPostive";
			JAVA_TYPE_NAME [Type.ftBinaryBigEndian          ] = "ftBinaryBigEndian";                                                      
			JAVA_TYPE_NAME [Type.ftBinaryBigEndianPositive  ] = "ftBinaryBigEndianPositive";
			JAVA_TYPE_NAME [Type.ftPositiveBinaryBigEndian  ] = "ftPositiveBinaryBigEndian";
			JAVA_TYPE_NAME [Type.ftRmComp                   ] = "ftRmComp";
			JAVA_TYPE_NAME [Type.ftRmCompPositive           ] = "ftRmCompPositive";

			JAVA_TYPE_NAME [Type.ftFjZonedNumeric           ] = "ftFjZonedNumeric";
			JAVA_TYPE_NAME [Type.ftNumRightJustCommaDp      ] = "ftNumRightJustCommaDp";
			JAVA_TYPE_NAME [Type.ftNumRightJustCommaDpPN    ] = "ftNumRightJustCommaDpPN";

		}
	}

	public static void check(ICobolIOBuilder bldr, CblBldrOptions opts, FieldDetail[] expected, String id) throws IOException {
		String font = opts.font;
		bldr.setDialect(opts.dialect)
		    .setDropCopybookNameFromFields(opts.dropCopybookNameFromFields)
		    .setFileOrganization(opts.fileOrganization)
		    .setFont(font);
		LayoutDetail l = bldr.getLayout();
		RecordDetail record = l.getRecord(0);
		
		TestCase.assertEquals(opts.fileOrganization, l.getFileStructure());
		TestCase.assertEquals(font, l.getFontName());
	
		checkFields(id, font, expected, record);
	}

	public static void checkFields(String id, String font, FieldDetail[] expected, RecordDetail record) {
		if (expected == null) System.out.println("\t}, {");
		for (int i = 0; i < record.getFieldCount(); i++) {
			FieldDetail field = record.getField(i);
			if (expected == null) {
				System.out.println(
						  "\t\tCommonCodeFields.createField(\"" + field.getName()
						+ "\", " + field.getPos()
						+ ", "   + field.getLen()
						+ ", "   + field.getDecimal()
						+ ", "   + CommonCodeFields.getJRecordTypeName(field.getType())
						+ "),"
				);
			} else {
				FieldDetail eField = expected[i];
				String idx = id + " " + i + ": " + eField.getName();
				TestCase.assertEquals(idx, eField.getName(), field.getName());
				TestCase.assertEquals(idx, eField.getPos(),  field.getPos());
				TestCase.assertEquals(idx, eField.getLen(),  field.getLen());
				TestCase.assertEquals(idx, eField.getType(), field.getType());
				TestCase.assertEquals(idx, eField.getDecimal(), field.getDecimal());
				TestCase.assertEquals(idx, font, field.getFontName());
			}
		}
	}
	

	public static void checkCsvFields(String id, String font, FieldDetail[] expected, RecordDetail record) {
		if (expected == null) System.out.println("\t}, {");
		for (int i = 0; i < record.getFieldCount(); i++) {
			FieldDetail field = record.getField(i);
			if (expected == null) {
				System.out.println(
						  "\t\tCommonCodeFields.createCsvField(\"" + field.getName()
						+ "\", " + field.getPos()
						+ ", "   + field.getDecimal()
						+ ", "   + CommonCodeFields.getJRecordTypeName(field.getType())
						+ "),"
				);
			} else {
				FieldDetail eField = expected[i];
				TestCase.assertEquals(id, eField.getName(), field.getName());
				TestCase.assertEquals(id, eField.getPos(),  field.getPos());
				//TestCase.assertEquals(id, eField.getLen(),  field.getLen());
				TestCase.assertEquals(id, eField.getDecimal(), field.getDecimal());
				TestCase.assertEquals(id, eField.getType(), field.getType());
				TestCase.assertEquals(id, font, field.getFontName());
			}
		}
	}


	
	public static FieldDetail createField(String name, int pos, int len, int decimal, int type) {
		return FieldDetail.newFixedWidthField(name, type, pos, len, decimal, "");
	}

	
	public static FieldDetail createCsvField(String name, int pos, int decimal, int type) {
		return FieldDetail.newCsvField(name, type, pos, decimal, "");
	}


	public static class CblBldrOptions {
		public final int dialect, fileOrganization;
		public final boolean dropCopybookNameFromFields;
		public final String font;
		
		
		public CblBldrOptions(int dialect, int fileOrganization,
				boolean dropCopybookNameFromFields, String font) {
			super();
			this.dialect = dialect;
			this.fileOrganization = fileOrganization;
			this.dropCopybookNameFromFields = dropCopybookNameFromFields;
			this.font = font;
		}
		
		
	}
}
