package com.qdb.app.users.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class FileDataUtil {

	public static byte[] compressFile(byte[] data) {
		Deflater deflater=new Deflater();
		deflater.setLevel(Deflater.BEST_COMPRESSION);
		deflater.setInput(data);
		deflater.finish();
		
		ByteArrayOutputStream bos=new ByteArrayOutputStream(data.length);
		byte[] tmp=new byte[4*1024];
		while(!deflater.finished()) {
			int size=deflater.deflate(tmp);
			bos.write(tmp,0,size);
		}
		try {
			bos.close();
		}	
		catch(Exception e) {
			
		}
		
		return bos.toByteArray();
	}
	
	public static byte[] decompressFile(byte[] data) {
		Inflater inflater=new Inflater();
		inflater.setInput(data);
		
		ByteArrayOutputStream bos=new ByteArrayOutputStream(data.length);
		byte[] tmp=new byte[4*1024];
		try {
			while(!inflater.finished()) {
				int count = inflater.inflate(tmp);
				bos.write(tmp,0,count);
			}
			bos.close();
		}
		catch(Exception e) {
			
		}
		
		return bos.toByteArray();
	}
}
