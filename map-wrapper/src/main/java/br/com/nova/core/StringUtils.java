package br.com.nova.core;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;

public class StringUtils {

	
	public static String parse(OutputStream os) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.writeTo(os);
		return new String( baos.toByteArray(), "UTF-8" );
	}
	public static String parse(InputStream is) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        try {
        	String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
	}
}