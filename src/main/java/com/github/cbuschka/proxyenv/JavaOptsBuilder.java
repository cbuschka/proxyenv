package com.github.cbuschka.proxyenv;

import java.util.List;

public class JavaOptsBuilder
{
	private StringBuilder buf = new StringBuilder();

	public JavaOptsBuilder()
	{
	}

	public JavaOptsBuilder(ProxyConfig proxyConfig)
	{
		withHttpProxyHost(proxyConfig.getHttpProxyHost());
		withHttpsProxyHost(proxyConfig.getHttpsProxyHost());
		withHttpNonProxyHosts(proxyConfig.getHttpNonProxyHosts());
		withFtpProxyHost(proxyConfig.getFtpProxyHost());
		withFtpNonProxyHosts(proxyConfig.getFtpNonProxyHosts());
	}

	public JavaOptsBuilder withHttpProxyHost(HostWithPort httpProxyHost)
	{
		if (httpProxyHost == null)
		{
			return this;
		}

		appendAsSystemProperties("http.proxyHost", "http.proxyPort", httpProxyHost);
		return this;
	}

	public JavaOptsBuilder withHttpsProxyHost(HostWithPort httpsProxyHost)
	{
		if (httpsProxyHost == null)
		{
			return this;
		}

		appendAsSystemProperties("https.proxyHost", "https.proxyPort", httpsProxyHost);
		return this;
	}

	public JavaOptsBuilder withFtpProxyHost(HostWithPort ftpProxyHost)
	{
		if (ftpProxyHost == null)
		{
			return this;
		}

		appendAsSystemProperties("ftp.proxyHost", "ftp.proxyPort", ftpProxyHost);
		return this;
	}

	private void appendAsSystemProperties(String hostPropName, String portPropName, HostWithPort hostWithPort)
	{
		appendSystemProperty(hostPropName, hostWithPort.getHostName());
		appendSystemProperty(portPropName, String.valueOf(hostWithPort.getPort()));
	}

	public JavaOptsBuilder withHttpNonProxyHosts(List<NonProxyHost> nonProxyHosts)
	{
		if (nonProxyHosts == null || nonProxyHosts.isEmpty())
		{
			return this;
		}

		appendAsSystemProperty("http.nonProxyHosts", nonProxyHosts);
		return this;
	}

	public JavaOptsBuilder withFtpNonProxyHosts(List<NonProxyHost> nonProxyHosts)
	{
		if (nonProxyHosts == null || nonProxyHosts.isEmpty())
		{
			return this;
		}

		appendAsSystemProperty("ftp.nonProxyHosts", nonProxyHosts);
		return this;
	}

	private void appendAsSystemProperty(String propName, List<NonProxyHost> nonProxyHosts)
	{
		appendSystemProperty(propName, toString(nonProxyHosts));
	}

	private String toString(List<NonProxyHost> nonProxyHosts)
	{
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < nonProxyHosts.size(); ++i)
		{
			NonProxyHost nonProxyHost = nonProxyHosts.get(i);
			if (buf.length() > 0)
			{
				buf.append("|");
			}

			buf.append(nonProxyHost.getValue());

			if (!nonProxyHost.isLocalhost())
			{
				buf.append("|*.").append(nonProxyHost.getValue());
			}
		}

		return buf.toString();
	}

	private void appendSystemProperty(String propName, String propValue)
	{
		if (buf.length() > 0)
		{
			buf.append(" ");
		}

		buf.append("-D").append(propName).append("=").append(propValue);
	}

	public String build()
	{
		return this.buf.toString();
	}
}
