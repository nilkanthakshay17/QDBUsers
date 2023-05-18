package com.qdb.app.users.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FileDataUtilTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testCompressionAndDecompression() {
		byte[] fileInfo = "fileInfo".getBytes();

		byte[] fileInfoCompressionOutput = FileDataUtil.compressFile(fileInfo);
		byte[] fileInfoDecompressionOutput = FileDataUtil.decompressFile(fileInfoCompressionOutput);

		assertNotNull(fileInfoDecompressionOutput);
		String fileOutputResponse = new String(fileInfoDecompressionOutput);
		assertEquals("fileInfo", fileOutputResponse);
	}

}
