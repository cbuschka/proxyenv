package com.github.cbuschka.proxyenv;

import java.util.ArrayList;
import java.util.List;

public class JavaOptsBuilder
{
	private static class JavaOpt
	{
		private String name;
		private String value;

		private JavaOpt(String name, String value)
		{
			this.name = name;
			this.value = value;
		}

		public String getName()
		{
			return name;
		}

		public String getValue()
		{
			return value;
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			JavaOpt javaOpt = (JavaOpt) o;

			if (!name.equals(javaOpt.name)) return false;
			return value.equals(javaOpt.value);
		}

		@Override
		public int hashCode()
		{
			int result = name.hashCode();
			result = 31 * result + value.hashCode();
			return result;
		}
	}

	private List<JavaOpt> javaOpts = new ArrayList<>();

	public JavaOptsBuilder()
	{
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
		this.javaOpts.add(new JavaOpt(hostPropName, hostWithPort.getHostName()));
		this.javaOpts.add(new JavaOpt(portPropName, String.valueOf(hostWithPort.getPort())));
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
		this.javaOpts.add(new JavaOpt(propName, propValue));
	}

	public String buildString()
	{
		StringBuilder buf = new StringBuilder();
		for (JavaOpt opt : javaOpts)
		{
			if (buf.length() > 0)
			{
				buf.append(" ");
			}
			buf.append("-D").append(opt.getName()).append("=").append(opt.getValue());
		}

		return buf.toString();
	}

	public JavaOptsBuilder withProxyConfig(ProxyConfig proxyConfig)
	{
		return withHttpProxyHost(proxyConfig.getHttpProxyHost())
				.withHttpsProxyHost(proxyConfig.getHttpsProxyHost())
				.withHttpNonProxyHosts(proxyConfig.getHttpNonProxyHosts())
				.withFtpProxyHost(proxyConfig.getFtpProxyHost())
				.withFtpNonProxyHosts(proxyConfig.getFtpNonProxyHosts());
	}
}
