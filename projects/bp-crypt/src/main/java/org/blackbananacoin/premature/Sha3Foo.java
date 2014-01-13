package org.blackbananacoin.premature;

import java.io.UnsupportedEncodingException;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.jcajce.provider.digest.SHA3.DigestSHA3;
import org.bouncycastle.util.encoders.Hex;

public class Sha3Foo {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println("hello");
		DigestSHA3 md = new SHA3.Digest256(); 
	    md.update("secret".getBytes("UTF-8"));
	    byte[] digest = md.digest();
	    System.out.println(digest.length);
	    System.out.println(Hex.toHexString(digest));
	}

}
