/*  -------------------------------------------------------------------------
 *
 *                Project: JRecord
 *    
 *    Sub-Project purpose: Provide support for reading Cobol-Data files 
 *                        using a Cobol Copybook in Java.
 *                         Support for reading Fixed Width / Binary / Csv files
 *                        using a Xml schema.
 *                         General Fixed Width / Csv file processing in Java.
 *    
 *                 Author: Bruce Martin
 *    
 *                License: LGPL 2.1 or latter
 *                
 *    Copyright (c) 2016, Bruce Martin, All Rights Reserved.
 *   
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *   
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 * ------------------------------------------------------------------------ */

package io;

import net.sf.JRecord.ByteIO.FixedLengthByteReader;
import net.sf.JRecord.ByteIO.FujitsuVbByteReader;
import net.sf.JRecord.ByteIO.VbByteReader;
import net.sf.JRecord.ByteIO.VbByteWriter;
import net.sf.JRecord.ByteIO.VbDumpByteWriter;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.AbstractLineWriter;
import net.sf.JRecord.IO.CobolIoProvider;
import net.sf.JRecord.IO.FixedLengthWriter;
import net.sf.JRecord.IO.LineReaderWrapper;
import net.sf.JRecord.IO.LineWriterWrapper;
import net.sf.JRecord.IO.TextLineWriter;
import net.sf.JRecord.Numeric.ICopybookDialects;
import net.sf.JRecord.common.BasicFileSchema;
import net.sf.JRecord.common.Constants;
import net.sf.cb2xml.def.Cb2xmlConstants;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Testing the correct LineReader's and LineWriter's are returned by
 * the CobolIoProvider class
 * 
 * @author Bruce Martin
 *
 */
public class TstCobolIoProvider {

	private static String copybookName = TstCobolIoProvider.class.getClassLoader().getResource("DTAR020.cbl").getFile();
	private static final String filename = TstCobolIoProvider.class.getClassLoader().getResource("DTAR020.bin").getFile();
	private static final String outfilename = "DTAR020.TTT.bin";

	/**
	 * Test GetLineReaders (standard structure
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetReader1() throws Exception {
		CobolIoProvider ioProvider = CobolIoProvider.getInstance();
		
		AbstractLineReader lineReader;
		lineReader = ioProvider.getLineReader(Constants.IO_FIXED_LENGTH, ICopybookDialects.FMT_MAINFRAME, CopybookLoader.SPLIT_NONE, copybookName, filename);
		
		System.out.println(lineReader.getClass().getName());
		System.out.println(((LineReaderWrapper) lineReader).getReader().getClass().getName());
		Assert.assertTrue(((LineReaderWrapper) lineReader).getReader() instanceof FixedLengthByteReader);
		lineReader.close();
		
		lineReader = ioProvider.getLineReader(Constants.IO_VB, ICopybookDialects.FMT_MAINFRAME, CopybookLoader.SPLIT_NONE, copybookName, filename);

		System.out.println(lineReader.getClass().getName());
		System.out.println(((LineReaderWrapper) lineReader).getReader().getClass().getName());
		Assert.assertTrue(((LineReaderWrapper) lineReader).getReader() instanceof VbByteReader);
		lineReader.close();
		
		lineReader = ioProvider.getLineReader(Constants.IO_VB_GNU_COBOL, ICopybookDialects.FMT_MAINFRAME, CopybookLoader.SPLIT_NONE, copybookName, filename);

		System.out.println(lineReader.getClass().getName());
		System.out.println(((LineReaderWrapper) lineReader).getReader().getClass().getName());
		Assert.assertTrue(((LineReaderWrapper) lineReader).getReader() instanceof VbByteReader);
		lineReader.close();
		
		lineReader = ioProvider.getLineReader(Constants.IO_VB_FUJITSU, ICopybookDialects.FMT_MAINFRAME, CopybookLoader.SPLIT_NONE, copybookName, filename);

		System.out.println(lineReader.getClass().getName());
		System.out.println(((LineReaderWrapper) lineReader).getReader().getClass().getName());
		Assert.assertTrue(((LineReaderWrapper) lineReader).getReader() instanceof FujitsuVbByteReader);
		lineReader.close();
	}
	

	/**
	 * Test getLineReader where (specifying a copybook structure
	 * @throws Exception
	 */
	@Test
    public void testGetReader2() throws Exception {
		CobolIoProvider ioProvider = CobolIoProvider.getInstance();
		
		AbstractLineReader lineReader;
		lineReader = ioProvider.getLineReader(Constants.IO_FIXED_LENGTH, ICopybookDialects.FMT_MAINFRAME, CopybookLoader.SPLIT_NONE, Cb2xmlConstants.USE_STANDARD_COLUMNS, copybookName, filename);
		
		System.out.println(lineReader.getClass().getName());
		System.out.println(((LineReaderWrapper) lineReader).getReader().getClass().getName());
		Assert.assertTrue(((LineReaderWrapper) lineReader).getReader() instanceof FixedLengthByteReader);
		lineReader.close();
		
		lineReader = ioProvider.getLineReader(Constants.IO_VB, ICopybookDialects.FMT_MAINFRAME, CopybookLoader.SPLIT_NONE, Cb2xmlConstants.USE_STANDARD_COLUMNS, copybookName, filename);

		System.out.println(lineReader.getClass().getName());
		System.out.println(((LineReaderWrapper) lineReader).getReader().getClass().getName());
		Assert.assertTrue(((LineReaderWrapper) lineReader).getReader() instanceof VbByteReader);
		lineReader.close();
		
		lineReader = ioProvider.getLineReader(Constants.IO_VB_GNU_COBOL, ICopybookDialects.FMT_MAINFRAME, CopybookLoader.SPLIT_NONE, Cb2xmlConstants.USE_STANDARD_COLUMNS, copybookName, filename);

		System.out.println(lineReader.getClass().getName());
		System.out.println(((LineReaderWrapper) lineReader).getReader().getClass().getName());
		Assert.assertTrue(((LineReaderWrapper) lineReader).getReader() instanceof VbByteReader);
		lineReader.close();
		
		lineReader = ioProvider.getLineReader(Constants.IO_VB_FUJITSU, ICopybookDialects.FMT_MAINFRAME, CopybookLoader.SPLIT_NONE, Cb2xmlConstants.USE_STANDARD_COLUMNS, copybookName, filename);

		System.out.println(lineReader.getClass().getName());
		System.out.println(((LineReaderWrapper) lineReader).getReader().getClass().getName());
		Assert.assertTrue(((LineReaderWrapper) lineReader).getReader() instanceof FujitsuVbByteReader);
		lineReader.close();
	}

	/**
	 * Test getLineWriter using just the FileStructure
	 * @throws Exception
	 */
	@Test
    @SuppressWarnings("deprecation")
	public void testGetWriter1() throws Exception {
		CobolIoProvider ioProvider = CobolIoProvider.getInstance();
		
		AbstractLineWriter lineWriter;
		lineWriter = ioProvider.getLineWriter(Constants.IO_FIXED_LENGTH, outfilename);
		System.out.println(lineWriter.getClass().getName());
		Assert.assertTrue(lineWriter instanceof FixedLengthWriter);
		lineWriter.close();
		
		lineWriter = ioProvider.getLineWriter(Constants.IO_VB, outfilename);
		System.out.println(lineWriter.getClass().getName());
		System.out.println(((LineWriterWrapper)lineWriter).getWriter().getClass().getName());
		Assert.assertTrue(((LineWriterWrapper) lineWriter).getWriter() instanceof VbByteWriter);
		lineWriter.close();
		
		
		lineWriter = ioProvider.getLineWriter(Constants.IO_VB_DUMP, outfilename);
		System.out.println(lineWriter.getClass().getName());
		System.out.println(((LineWriterWrapper)lineWriter).getWriter().getClass().getName());
		Assert.assertTrue(((LineWriterWrapper) lineWriter).getWriter() instanceof VbDumpByteWriter);
		lineWriter.close();
		
		lineWriter = ioProvider.getLineWriter(Constants.IO_TEXT_LINE, outfilename);
		System.out.println(lineWriter.getClass().getName());
		Assert.assertTrue(lineWriter instanceof TextLineWriter);
		lineWriter.close();
		
		lineWriter = ioProvider.getLineWriter(Constants.IO_UNICODE_TEXT, outfilename);
		System.out.println(lineWriter.getClass().getName());
		Assert.assertTrue(lineWriter instanceof TextLineWriter);
		lineWriter.close();

		
	}
	
	/**
	 * Test getLineWriter using a "FileSchema"
	 * @throws Exception
	 */
	@Test
    public void testGetWriter2() throws Exception {
		CobolIoProvider ioProvider = CobolIoProvider.getInstance();
		
		AbstractLineWriter lineWriter;
		lineWriter = ioProvider.getLineWriter(BasicFileSchema.newFixedSchema(Constants.IO_FIXED_LENGTH), outfilename);
		System.out.println(lineWriter.getClass().getName());
		Assert.assertTrue(lineWriter instanceof FixedLengthWriter);
		lineWriter.close();
		
		lineWriter = ioProvider.getLineWriter(BasicFileSchema.newFixedSchema(Constants.IO_VB), outfilename);
		System.out.println(lineWriter.getClass().getName());
		System.out.println(((LineWriterWrapper)lineWriter).getWriter().getClass().getName());
		Assert.assertTrue(((LineWriterWrapper) lineWriter).getWriter() instanceof VbByteWriter);
		lineWriter.close();
		
		
		lineWriter = ioProvider.getLineWriter(BasicFileSchema.newFixedSchema(Constants.IO_VB_DUMP), outfilename);
		System.out.println(lineWriter.getClass().getName());
		System.out.println(((LineWriterWrapper)lineWriter).getWriter().getClass().getName());
		Assert.assertTrue(((LineWriterWrapper) lineWriter).getWriter() instanceof VbDumpByteWriter);
		lineWriter.close();
		
		lineWriter = ioProvider.getLineWriter(BasicFileSchema.newFixedSchema(Constants.IO_TEXT_LINE), outfilename);
		System.out.println(lineWriter.getClass().getName());
		Assert.assertTrue(lineWriter instanceof TextLineWriter);
		lineWriter.close();
		
		lineWriter = ioProvider.getLineWriter(BasicFileSchema.newFixedSchema(Constants.IO_UNICODE_TEXT), outfilename);
		System.out.println(lineWriter.getClass().getName());
		Assert.assertTrue(lineWriter instanceof TextLineWriter);
		lineWriter.close();
	}

	@After
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(outfilename));
    }
}
