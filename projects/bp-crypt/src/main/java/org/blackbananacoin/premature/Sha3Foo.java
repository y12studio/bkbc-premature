/*
 * Copyright 2013 Y12STUDIO
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.blackbananacoin.premature;

import java.io.UnsupportedEncodingException;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.jcajce.provider.digest.SHA256;
import org.bouncycastle.jcajce.provider.digest.SHA256.Digest;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.jcajce.provider.digest.SHA3.Digest256;
import org.bouncycastle.jcajce.provider.digest.SHA3.DigestSHA3;
import org.bouncycastle.util.encoders.Hex;

public class Sha3Foo {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String pass = "Hello我是王小明";
		System.out.println(pass);

		DigestSHA3 sha3256 = new SHA3.Digest256();
		sha3256.update(pass.getBytes("UTF-8"));
		System.out.println(Hex.toHexString(sha3256.digest()));

		Digest sha256 = new SHA256.Digest();
		sha256.update(pass.getBytes("UTF-8"));
		byte[] d = sha256.digest();
		System.out.println(Hex.toHexString(d));
		sha256.reset();
		sha256.update(d);
		System.out.println(Hex.toHexString(sha256.digest()));
		
		sha3256.reset();
		sha3256.update(d);
		System.out.println(Hex.toHexString(sha3256.digest()));
	}

}
