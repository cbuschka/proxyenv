package com.github.cbuschka.proxyenv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UnixNonProxyHostsParser
{
	public List<NonProxyHost> parse(String noProxyValue)
	{
		if (noProxyValue == null || noProxyValue.trim().isEmpty())
		{
			return Collections.emptyList();
		}

		String[] parts = noProxyValue.trim().split("\\s+");
		List<NonProxyHost> nonProxyHosts = new ArrayList<>();
		for (String part : parts)
		{
			NonProxyHost nonProxyHost = NonProxyHost.valueOf(part);
			if (nonProxyHost.isLocalhost() || nonProxyHost.isIp() || nonProxyHost.getValue().indexOf('*') != -1)
			{
				nonProxyHosts.add(nonProxyHost);
			}
			else if (nonProxyHost.getValue().startsWith("."))
			{
				nonProxyHosts.add(new NonProxyHost("*" + nonProxyHost.getValue()));
			}
			else
			{
				nonProxyHosts.add(new NonProxyHost(nonProxyHost.getValue()));
				nonProxyHosts.add(new NonProxyHost("*." + nonProxyHost.getValue()));
			}
		}

		return nonProxyHosts;
	}
}
