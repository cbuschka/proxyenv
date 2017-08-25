package com.github.cbuschka.proxyenv;

import java.lang.reflect.UndeclaredThrowableException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HostWithPortParser
{
	private static Pattern URI_WITHOUT_PROTOCOL_PATTERN = Pattern.compile("^([^:]+)(:([0-9]+))?$");

	public HostWithPort parse(String value, int defaultPort)
	{
		if (value == null)
		{
			throw new NullPointerException("Cannot parse null.");
		}

		HostWithPort hostWithPort = tryUriWithoutProtocol(defaultPort, value);
		if (hostWithPort != null)
		{
			return hostWithPort;
		}

		URI uri = toURI(value);
		return new HostWithPort(uri.getHost(), uri.getPort() != -1 ? uri.getPort() : defaultPort);
	}

	private HostWithPort tryUriWithoutProtocol(int defaultPort, String value)
	{
		Matcher matcher = URI_WITHOUT_PROTOCOL_PATTERN.matcher(value);
		if (!matcher.matches())
		{
			return null;
		}

		String hostName = matcher.group(1);
		int port = defaultPort;
		if (matcher.groupCount() == 3 && matcher.group(3) != null)
		{
			port = Integer.parseInt(matcher.group(3));
		}
		return new HostWithPort(hostName, port);
	}

	private URI toURI(String value)
	{
		try
		{
			return new URI(value);
		}
		catch (URISyntaxException ex)
		{
			throw new IllegalArgumentException("Invalid uri: " + value, ex);
		}
	}

}
