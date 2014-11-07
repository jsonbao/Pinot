package com.linkedin.pinot.index.writer;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.linkedin.pinot.core.index.writer.impl.FixedByteWidthRowColDataFileWriter;

@Test
public class TestFixedByteWidthRowColDataFileWriter {
	@Test
	public void testSingleCol() throws Exception {

		File file = new File("test_single_col_writer.dat");
		file.delete();
		int rows = 100;
		int cols = 1;
		int[] columnSizes = new int[] { 4 };
		FixedByteWidthRowColDataFileWriter writer = new FixedByteWidthRowColDataFileWriter(
				file, rows, cols, columnSizes);
		int[] data = new int[rows];
		Random r = new Random();
		for (int i = 0; i < rows; i++) {
			data[i] = r.nextInt();
			writer.setInt(i, 0, data[i]);
		}
		writer.saveAndClose();
		DataInputStream dis = new DataInputStream(new FileInputStream(file));
		for (int i = 0; i < rows; i++) {
			Assert.assertEquals(dis.readInt(), data[i]);
		}
		dis.close();
		file.delete();
	}

	@Test
	public void testMultiCol() throws Exception {

		File file = new File("test_single_col_writer.dat");
		file.delete();
		int rows = 100;
		int cols = 2;
		int[] columnSizes = new int[] { 4, 4 };
		FixedByteWidthRowColDataFileWriter writer = new FixedByteWidthRowColDataFileWriter(
				file, rows, cols, columnSizes);
		int[][] data = new int[rows][cols];
		Random r = new Random();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = r.nextInt();
				writer.setInt(i, j, data[i][j]);
			}
		}
		writer.saveAndClose();
		DataInputStream dis = new DataInputStream(new FileInputStream(file));
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Assert.assertEquals(dis.readInt(), data[i][j]);
			}
		}
		dis.close();
		file.delete();
	}
}