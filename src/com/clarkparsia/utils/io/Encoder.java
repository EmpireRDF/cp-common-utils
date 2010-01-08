/*
 * Copyright (c) 2005-2010 Clark & Parsia, LLC. <http://www.clarkparsia.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.clarkparsia.utils.io;

import com.sun.corba.se.impl.interceptors.CDREncapsCodec;

import java.nio.charset.Charset;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLEncoder;
import java.net.URLDecoder;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

/**
 * <p>Collection of utility methods for encoding/decoding strings.</p>
 *
 * @author Michael Grove
 * @since 1.0
 */
public class Encoder {

	/**
	 * A base 64 encoder
	 */
	private static BASE64Encoder mBase64Encoder;

	/**
	 * A base 64 decoder
	 */
	private static BASE64Decoder mBase64Decoder;

	// these are the charsets that should be available in all jvms.  There might be more than just these...
	public static final Charset UTF8 = Charset.availableCharsets().get("UTF-8");
	public static final Charset UTF16 = Charset.availableCharsets().get("UTF-16");
	public static final Charset US_ASCII = Charset.availableCharsets().get("US-ASCII");

	/**
	 * URL encode the string using the UTF8 charset
	 * @param theString the string to encode
	 * @return the url encoded string
	 */
	public static String urlEncode(String theString) {
		return urlEncode(theString, UTF8);
	}

	/**
	 * URL encode the given string using the given Charset
	 * @param theString the string to encode
	 * @param theCharset the charset to encode the string using
	 * @return the string encoded with the given charset
	 */
	public static String urlEncode(String theString, Charset theCharset) {
		try {
			return URLEncoder.encode(theString, theCharset.displayName());
		}
		catch (UnsupportedEncodingException e) {
			// this can be safely ignored, you would not have a charset object for an unsupported charset
			return null;
		}
	}


	/**
	 * URL decode the string using the UTF8 charset
	 * @param theString the string to decode
	 * @return the decoded string
	 */
	public static String urlDecode(String theString) {
		return urlDecode(theString, UTF8);
	}

	/**
	 * URL decode the given string using the given Charset
	 * @param theString the string to decode
	 * @param theCharset the charset to decode the string using
	 * @return the string decoded with the given charset
	 */
	public static String urlDecode(String theString, Charset theCharset) {
		try {
			return URLDecoder.decode(theString, theCharset.displayName());
		}
		catch (UnsupportedEncodingException e) {
			// this can be safely ignored, you would not have a charset object for an unsupported charset
			return null;
		}
	}

	/**
	 * Base64 encodes the given byte array.  This utility is provided to abstract over the Sun implementation which
	 * is deprecated and marked for deletion.  The implementation of this method will always work; so if the existing
	 * Sun implementation goes away, this will be switched to an appropriate implementation without requiring any
	 * changes to dependant code.
	 * @param theArrayToEncode the bytes to encode
	 * @return the bytes base64 encoded
	 */
	public static String base64Encode(byte[] theArrayToEncode) {
		return getBase64Encoder().encode(theArrayToEncode);
	}

	/**
	 * Base64 encodes the given byte array.  This utility is provided to abstract over the Sun implementation which
	 * is deprecated and marked for deletion.  The implementation of this method will always work; so if the existing
	 * Sun implementation goes away, this will be switched to an appropriate implementation without requiring any
	 * changes to dependant code.
	 * @param theStringToDecode the string to decode
	 * @return the bytes base64 decoded
	 * @throws java.io.IOException throw if there is an error while decoding
	 */
	public static byte[] base64Decode(String theStringToDecode) throws IOException {
		return getBase64Decoder().decodeBuffer(theStringToDecode);
	}

	/**
	 * Return a Base64 decoder
	 * @return a Sun Base64 decoder
	 */
	private static BASE64Decoder getBase64Decoder() {
		if (mBase64Decoder == null) {
			mBase64Decoder = new BASE64Decoder();
		}

		return mBase64Decoder;
	}

	/**
	 * Return a base64 encoder
	 * @return an Sun encoder
	 */
	private static BASE64Encoder getBase64Encoder() {
		if (mBase64Encoder == null) {
			mBase64Encoder = new BASE64Encoder();
		}

		return mBase64Encoder;
	}
}
