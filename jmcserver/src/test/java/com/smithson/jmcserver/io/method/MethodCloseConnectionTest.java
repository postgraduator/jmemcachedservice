package com.smithson.jmcserver.io.method;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.smithson.jmcserver.io.InvokedRequestData;
import com.smithson.jmcserver.io.JMCDataAccessor;
import com.smithson.jmcserver.io.PreparedResponse;
import com.smithson.jmcserver.io.constant.JMCMethod;
import com.smithson.jmcserver.io.constant.ProtocolInfo;
import com.smithson.jmcserver.io.constant.Status;

public class MethodCloseConnectionTest {
	private final ProtocolInfo PROTOCOL = ProtocolInfo.VERSION_1_0;
	private final JMCMethod EXPECTED_METHOD = JMCMethod.CLOSE_CONNECTION; 
	private final Status EXPECTED_STATUS = Status.CLOSED;
	private final MethodProcessor methodProcessor =  new MethodCloseConnection();
	private ByteBuffer responseBuffer = ByteBuffer.wrap(new byte[2]);
	private InputStream in = Mockito.mock(InputStream.class);
	private JMCDataAccessor accessor = Mockito.mock(JMCDataAccessor.class);
	
	
	@Before
	public void setUp() {
		responseBuffer.mark();
		responseBuffer.put(PROTOCOL.getVersion().getFullVersion());
		responseBuffer.put((byte) EXPECTED_STATUS.getStatusID());
		responseBuffer.reset();
	}
	@Test
	public void closeConnectionRequestTest() throws IOException {
		InvokedRequestData data = methodProcessor.processMethod(in, PROTOCOL);
		assertEquals(PROTOCOL, data.getProtocolInfo());
		assertEquals(EXPECTED_METHOD, data.getMethod());
	}
	
	@Test
	public void closeConnectionrResponseTest() throws IOException {
		PreparedResponse response = methodProcessor.doMethod(methodProcessor.processMethod(in, PROTOCOL), accessor);
		assertEquals(EXPECTED_STATUS, response.getStatusOfRequestProcessing());
		assertEquals(responseBuffer, response.getPreparedByteBuffer());
	}

}
